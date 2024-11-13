package com.example.evertecdemo.repositories;

import com.example.evertecdemo.models.UsuarioModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UsuarioRepository extends CrudRepository<UsuarioModel, Long>{
}
