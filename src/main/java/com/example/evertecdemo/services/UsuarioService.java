package com.example.evertecdemo.services;

import java.util.ArrayList;
import java.util.Optional;

import com.example.evertecdemo.models.UsuarioModel;
import com.example.evertecdemo.repositories.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    public ArrayList<UsuarioModel>obtenerUsuarios(){ // buscar usuarios y castearlos en un array
        return (ArrayList<UsuarioModel>)usuarioRepository.findAll(); 
    }
    public UsuarioModel guardarUsuario(UsuarioModel usuario){
        return usuarioRepository.save(usuario); // se ocupa metodo save para guardar usuario
    }
    public Optional<UsuarioModel> obtenerUsuarioPorId(Long id){//servicio para obtener usuario por id
        return usuarioRepository.findById(id);
    }
    public boolean eliminarUsuario(Long id){ //servicio para eliminar usuarios por id
        try {
            usuarioRepository.deleteById(id);
            return true;
            } catch (Exception err) {
                return false;
                }
    }
}
