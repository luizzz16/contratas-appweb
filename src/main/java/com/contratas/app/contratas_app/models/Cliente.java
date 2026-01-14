package com.contratas.app.contratas_app.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity // Indicam que es una entidad de base de datos
public class Cliente {
    @Id // Indica que es la clave primaria
    @GeneratedValue // Indica que el valor se genera automaticamente

    private Long id;
    private String nombre;
    private String telefono;
    private String direccion;


    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference("cliente-prestamo")
    private List<Prestamo> prestamos;

    @ManyToOne
    @JoinColumn(name = "prestador_id")
    @JsonBackReference("prestador-cliente")
    private Prestador prestador;


    public Cliente() {
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


    public String getTelefono() {
        return telefono;
    }


    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    public String getDireccion() {
        return direccion;
    }


    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }

    public Prestador getPrestador() {
        return prestador;
    }

    public void setPrestador(Prestador prestador) {
        this.prestador = prestador;
    }



}
