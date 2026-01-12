package com.contratas.app.contratas_app.models;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Prestador {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;

    private String correo; // viene de Google (email único)

    private String googleId; // ID único de Google

    @OneToMany(mappedBy = "prestador", cascade = CascadeType.ALL)
    @JsonManagedReference("prestador-cliente")
    private List<Cliente> clientes;


    public Prestador() {
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


    public String getCorreo() {
        return correo;
    }


    public void setCorreo(String correo) {
        this.correo = correo;
    }


    public List<Cliente> getClientes() {
        return clientes;
    }


    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }


    public String getGoogleId() {
        return googleId;
    }


    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    


    

}
