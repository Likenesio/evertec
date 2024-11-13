package com.example.evertecdemo.services;

import java.util.ArrayList;
import java.util.Optional;

import com.example.evertecdemo.models.ClienteModel;
import com.example.evertecdemo.repositories.ClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    PasswordEncoder passwordEncoder; // Inyección del PasswordEncoder
    
    public ClienteModel registrarCliente(ClienteModel cliente) {
        cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
        return clienteRepository.save(cliente);
    }
    
    public ArrayList<ClienteModel> obtenerCliente() { 
        return (ArrayList<ClienteModel>) clienteRepository.findAll(); 
    }


    public Optional<ClienteModel> obtenerClientePorId(Long id) {
        return clienteRepository.findById(id);
    }

    public boolean eliminarCliente(Long id) {
        try {
            clienteRepository.deleteById(id);
            return true;
        } catch (Exception err) {
            return false;
        }
    }
}