package com.example.evertecdemo.services;

import com.example.evertecdemo.models.ProductoModel;
import com.example.evertecdemo.repositories.ProductoRepository;
import com.example.evertecdemo.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<ProductoModel> getAllProductos() {
        return productoRepository.findAll();
    }

    public ProductoModel getProductoById(Long id) {
        return productoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
    }

    public ProductoModel createProducto(ProductoModel producto) {
        return productoRepository.save(producto);
    }

    public ProductoModel updateProducto(Long id, ProductoModel producto) {
        if (!productoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto no encontrado");
        }
        producto.setId(id);
        return productoRepository.save(producto);
    }

    public void deleteProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto no encontrado");
        }
        productoRepository.deleteById(id);
    }
}
