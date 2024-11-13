package com.example.evertecdemo.services;

import com.example.evertecdemo.models.*;
import com.example.evertecdemo.repositories.*;
import com.example.evertecdemo.exceptions.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;

    public PedidoService(PedidoRepository pedidoRepository, 
                        ClienteRepository clienteRepository,
                        ProductoRepository productoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.productoRepository = productoRepository;
    }

    @Transactional
    public PedidoModel createPedido(Long clienteId, List<PedidoItemModel> items) {
        ClienteModel cliente = clienteRepository.findById(clienteId)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        PedidoModel pedido = new PedidoModel();
        pedido.setCliente(cliente);
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setEstado(EstadoPedido.PENDIENTE);

        double total = 0.0;
        for (PedidoItemModel item : items) {
            ProductoModel producto = productoRepository.findById(item.getProducto().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
            
            if (producto.getStock() < item.getCantidad()) {
                throw new BadRequestException("Stock insuficiente para " + producto.getNombre());
            }

            item.setPedido(pedido);
            item.setPrecioUnitario(producto.getPrecio());
            item.setSubtotal(producto.getPrecio() * item.getCantidad());
            total += item.getSubtotal();

            producto.setStock(producto.getStock() - item.getCantidad());
            productoRepository.save(producto);
        }

        pedido.setItems(items);
        pedido.setTotalPedido(total);
        return pedidoRepository.save(pedido);
    }

    public PedidoModel getPedidoById(Long id) {
        return pedidoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado"));
    }

    public List<PedidoModel> getPedidosByCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    @Transactional
    public PedidoModel updateEstadoPedido(Long id, EstadoPedido estado) {
        PedidoModel pedido = getPedidoById(id);
        pedido.setEstado(estado);
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public void cancelarPedido(Long id) {
        PedidoModel pedido = getPedidoById(id);
        if (pedido.getEstado() == EstadoPedido.ENVIADO || 
            pedido.getEstado() == EstadoPedido.ENTREGADO) {
            throw new BadRequestException("No se puede cancelar un pedido enviado o entregado");
        }
        pedido.setEstado(EstadoPedido.CANCELADO);
        pedidoRepository.save(pedido);
    }
}