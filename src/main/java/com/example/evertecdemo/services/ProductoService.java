package com.example.evertecdemo.services;

import com.example.evertecdemo.dto.ProductoDTO;
import com.example.evertecdemo.exceptions.RecursoNoEncontradoException;
import com.example.evertecdemo.models.Producto;
import com.example.evertecdemo.repositories.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // Método para crear un nuevo producto
    public ProductoDTO crearProducto(ProductoDTO productoDTO) {
        // Crear el objeto Producto a partir del DTO
        Producto producto = new Producto();
        producto.setNombre(productoDTO.getNombre());
        producto.setPrecio(productoDTO.getPrecio());

        // Guardar el producto en la base de datos
        productoRepository.save(producto);

        // Retornar el producto creado como DTO
        return new ProductoDTO(producto.getId(), producto.getNombre(), producto.getPrecio());
    }

    public List<ProductoDTO> listarProductos() {
        return productoRepository.findAll().stream()
                .map(producto -> new ProductoDTO(producto.getId(), producto.getNombre(), producto.getPrecio()))
                .collect(Collectors.toList());
    }

    public ProductoDTO obtenerProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con id: " + id));
        return new ProductoDTO(producto.getId(), producto.getNombre(), producto.getPrecio());
    }
}
