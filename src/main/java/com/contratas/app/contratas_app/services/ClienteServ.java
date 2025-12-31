package com.contratas.app.contratas_app.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.contratas.app.contratas_app.models.Cliente;
import com.contratas.app.contratas_app.repositories.ClienteRepo;


@Service // Indica que es un servicio de Spring
public class ClienteServ {



    private final ClienteRepo clienteRepo;

    public ClienteServ(ClienteRepo clienteRepo) {
        this.clienteRepo = clienteRepo;
    }

    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepo.save(cliente);
    }

    public List<Cliente> obttenerTodos() {
        return clienteRepo.findAll();
    }

    public Cliente obtenerPorId(Long id) {
        return clienteRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    public void eliminarPorId(Long id) {
        clienteRepo.deleteById(id);
    }
}
