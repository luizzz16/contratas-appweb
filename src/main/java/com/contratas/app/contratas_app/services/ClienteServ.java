package com.contratas.app.contratas_app.services;


import java.util.List;

import org.springframework.stereotype.Service;

import com.contratas.app.contratas_app.models.Cliente;
import com.contratas.app.contratas_app.models.Prestador;
import com.contratas.app.contratas_app.repositories.ClienteRepo;
import org.springframework.security.core.Authentication;


@Service // Indica que es un servicio de Spring
public class ClienteServ {

    private final ClienteRepo clienteRepo;
    private final PrestadorServ prestadorServ;

    public ClienteServ(ClienteRepo clienteRepo, PrestadorServ prestadorServ) {
        this.clienteRepo = clienteRepo;
        this.prestadorServ = prestadorServ;
    }

    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepo.save(cliente);
    }

    public Cliente guardarCliente(Cliente cliente, Authentication authentication) {
        Prestador prestador = prestadorServ.obtenerPrestadorActual(authentication);
        cliente.setPrestador(prestador);
        return clienteRepo.save(cliente);
    }


    public List<Cliente> obtenerClientes() {
        return clienteRepo.findAll();
    }

    public List<Cliente> obtenerClientesDelPrestador(Authentication authentication) {

        Prestador prestador = prestadorServ.obtenerPrestadorActual(authentication);
        return clienteRepo.findByPrestador(prestador);
    }


    public Cliente obtenerPorId(Long id) {
        return clienteRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    public void eliminarPorId(Long id) {
        clienteRepo.deleteById(id);
    }
}
