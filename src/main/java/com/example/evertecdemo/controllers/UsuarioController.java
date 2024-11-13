package com.example.evertecdemo.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.evertecdemo.services.UsuarioService;
import com.example.evertecdemo.models.UsuarioModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    @GetMapping()
    public ArrayList<UsuarioModel>obtenerUsuarios(){
    return usuarioService.obtenerUsuarios();
    }
    @PostMapping()
    public UsuarioModel guardarUsuario(@RequestBody UsuarioModel usuario) {
        return usuarioService.guardarUsuario(usuario);
    }
    @GetMapping(path="/{id}")
    public Optional<UsuarioModel>obtenerUsuarioPorId(@PathVariable("id") long id){
        return this.usuarioService.obtenerUsuarioPorId(id);
    }
    @DeleteMapping(path ="/{id}")
    public String eliminarUsuario(@PathVariable("id") long id){
        boolean respuesta = this.usuarioService.eliminarUsuario(id);
        if (respuesta){
            return "Usuario eliminado";
            }else{
                return "No se pudo eliminar el usuario";
                }
    }
}
