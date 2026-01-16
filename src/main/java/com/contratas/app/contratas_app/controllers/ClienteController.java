package com.contratas.app.contratas_app.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.contratas.app.contratas_app.models.Cliente;
import com.contratas.app.contratas_app.services.ClienteServ;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.security.core.Authentication;




@RestController // Indica que la clase recibe solicitudes HTTP y devuelve datos en formato JSON
@RequestMapping("/clientes") // Ruta base para las solicitudes relacionadas con "clientes"
@CrossOrigin(origins = "*")// Permite que el html pueda comunicarse con este controlador 

public class ClienteController {

    private final ClienteServ clienteServ;

    public ClienteController(ClienteServ clienteServ) {
        this.clienteServ = clienteServ;
    }

    @PostMapping // Maneja solicitudes HTTP POST para crear un nuevo cliente
    //  El requestbody conviere el JSON del cuerpo de la solicitud en un objeto Cliente
    public Cliente guardar(@RequestBody Cliente cliente, Authentication authentication) {
        return clienteServ.guardarCliente(cliente, authentication);
    }

    @GetMapping
    public List<Cliente> listarClientes(Authentication authentication) {
        return clienteServ.obtenerClientesDelPrestador(authentication);
    }

    @GetMapping("/{id}")
    public Cliente obtenerClientePorId(@PathVariable Long id) {
        return clienteServ.obtenerPorId(id);
    }

    @DeleteMapping("/{id}")
    public void eliminarClientePorId(@PathVariable Long id) {
        clienteServ.eliminarPorId(id);
    }
}