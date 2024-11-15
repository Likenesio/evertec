package com.example.evertecdemo.services;

import com.example.evertecdemo.dto.PedidoDTO;
import com.example.evertecdemo.exceptions.RecursoNoEncontradoException;
import com.example.evertecdemo.models.Cliente;
import com.example.evertecdemo.models.EstadoPedido;
import com.example.evertecdemo.models.Pedido;
import com.example.evertecdemo.models.Producto;
import com.example.evertecdemo.repositories.ClienteRepository;
import com.example.evertecdemo.repositories.PedidoRepository;
import com.example.evertecdemo.repositories.ProductoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;

    public PedidoService(PedidoRepository pedidoRepository, ClienteRepository clienteRepository,
            ProductoRepository productoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.productoRepository = productoRepository;
    }

    // Método para crear un nuevo pedido
    public PedidoDTO crearPedido(PedidoDTO pedidoDTO) {
        Cliente cliente = clienteRepository.findById(pedidoDTO.getClienteId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Cliente no encontrado con id: " + pedidoDTO.getClienteId()));

        // Mapeo de productos del pedido
        List<Producto> productos = pedidoDTO.getProductosIds().stream()
                .map(id -> productoRepository.findById(id)
                        .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con id: " + id)))
                .collect(Collectors.toList());

        // Crear el objeto Pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setProductos(productos);
        pedido.setEstado(EstadoPedido.PENDIENTE);
        pedido.setFecha(LocalDate.now());

        pedidoRepository.save(pedido);

        return new PedidoDTO(pedido.getId(), pedido.getCliente().getId(), pedido.getEstado().toString(),
                pedido.getProductos().stream().map(Producto::getId).collect(Collectors.toList()));
    }

    // Método para obtener un pedido por ID
    public PedidoDTO obtenerPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado con id: " + id));

        return new PedidoDTO(pedido.getId(), pedido.getCliente().getId(), pedido.getEstado().toString(),
                pedido.getProductos().stream().map(Producto::getId).collect(Collectors.toList()));
    }

    // Método para listar todos los pedidos de un cliente
    public List<PedidoDTO> listarPedidosCliente(Long clienteId) {
        clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado con id: " + clienteId));

        return pedidoRepository.findByClienteId(clienteId).stream()
                .map(pedido -> new PedidoDTO(pedido.getId(), pedido.getCliente().getId(), pedido.getEstado().toString(),
                        pedido.getProductos().stream().map(Producto::getId).collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    // Método para actualizar el estado de un pedido
    public PedidoDTO actualizarEstadoPedido(Long id, EstadoPedido nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado con id: " + id));

        pedido.setEstado(nuevoEstado);
        pedidoRepository.save(pedido);

        return new PedidoDTO(pedido.getId(), pedido.getCliente().getId(), pedido.getEstado().toString(),
                pedido.getProductos().stream().map(Producto::getId).collect(Collectors.toList()));
    }
    // Método para cancelar un pedido
    public void cancelarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado con id: " + id));

        if (!pedido.getEstado().equals(EstadoPedido.PENDIENTE)) {
            throw new IllegalArgumentException("Solo se pueden cancelar pedidos en estado PENDIENTE.");
        }
        // Cambiar el estado a CANCELADO
        pedido.setEstado(EstadoPedido.CANCELADO);
        pedidoRepository.save(pedido);
    }
}
