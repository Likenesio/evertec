package com.example.evertecdemo.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "cliente") // nombre de nuestra tabla
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false) // id unica, inclemental y no null
    private Long id;
    private String nombre;
    private String email;
    private String password;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> pedidos;

    public Cliente() {
    }

    public Cliente(Long id, String nombre, String email, String password, List<Pedido> pedidos) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.pedidos = pedidos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", pedidos=" + pedidos +
                '}';
    }
}