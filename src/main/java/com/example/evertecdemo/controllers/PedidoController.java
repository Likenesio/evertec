package com.example.evertecdemo.controllers;

import com.example.evertecdemo.models.*;
import com.example.evertecdemo.services.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }
/* 
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public PedidoModel createPedido(@RequestBody CreatePedidoRequest request) {
        return pedidoService.createPedido(request.getClienteId(), request.getItems());
    } */

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public PedidoModel getPedido(@PathVariable Long id) {
        return pedidoService.getPedidoById(id);
    }

    @GetMapping("/cliente/{clienteId}")
    @PreAuthorize("isAuthenticated()")
    public List<PedidoModel> getPedidosByCliente(@PathVariable Long clienteId) {
        return pedidoService.getPedidosByCliente(clienteId);
    }

 /*    @PutMapping("/{id}/estado")
    @PreAuthorize("hasRole('ADMIN')")
    public PedidoModel updateEstadoPedido(@PathVariable Long id, @RequestBody UpdateEstadoRequest request) {
        return pedidoService.updateEstadoPedido(id, request.getEstado());
    } */

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> cancelarPedido(@PathVariable Long id) {
        pedidoService.cancelarPedido(id);
        return ResponseEntity.ok().build();
    }
}