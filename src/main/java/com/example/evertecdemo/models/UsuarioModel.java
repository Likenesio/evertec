package com.example.evertecdemo.models;

import jakarta.persistence.*;

@Entity // se dice que es un modelo real
@Table(name = "usuario") // nombre de nuestra tabla
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false) // id unica, inclemental y no null 
    private Long id;

    private String name;
    private String lastname;
    private String email;
    private String password;
    private String rol;

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getLastname(){
        return lastname;
    }
    public void setLastname(String lastname){
        this.lastname = lastname;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getRol(){
        return rol;
    }
    public void setRol(String rol){
        this.rol = rol;
    }
}
