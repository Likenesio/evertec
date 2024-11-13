package com.example.evertecdemo.repositories;

import com.example.evertecdemo.models.ClienteModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ClienteRepository extends CrudRepository<ClienteModel, Long>{
}
