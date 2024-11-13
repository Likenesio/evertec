package com.example.evertecdemo.controllers;

import com.example.evertecdemo.models.ProductoModel;
import com.example.evertecdemo.services.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<ProductoModel> getAllProductos() {
        return productoService.getAllProductos();
    }

    @GetMapping("/{id}")
    public ProductoModel getProducto(@PathVariable Long id) {
        return productoService.getProductoById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ProductoModel createProducto(@RequestBody ProductoModel producto) {
        return productoService.createProducto(producto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ProductoModel updateProducto(@PathVariable Long id, @RequestBody ProductoModel producto) {
        return productoService.updateProducto(id, producto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProducto(@PathVariable Long id) {
        productoService.deleteProducto(id);
        return ResponseEntity.ok().build();
    }
}