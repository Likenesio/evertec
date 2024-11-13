package com.example.evertecdemo.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.evertecdemo.services.ClienteService;
import com.example.evertecdemo.models.ClienteModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    ClienteService clienteService;

    @GetMapping()
    public ArrayList<ClienteModel>obtenerCliente(){
    return clienteService.obtenerCliente();
    }
    @PostMapping()
    public ClienteModel registrarCliente(@RequestBody ClienteModel cliente) {
        return clienteService.registrarCliente(cliente);
    }
    @GetMapping(path="/{id}")
    public Optional<ClienteModel>obtenerClientePorId(@PathVariable("id") long id){
        return this.clienteService.obtenerClientePorId(id);
    }
    @DeleteMapping(path ="/{id}")
    public String eliminarCliente(@PathVariable("id") long id){
        boolean respuesta = this.clienteService.eliminarCliente(id);
        if (respuesta){
            return "Cliente eliminado";
            }else{
                return "No se pudo eliminar el cliente";
                }
    }
}
