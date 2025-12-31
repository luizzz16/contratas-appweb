package com.contratas.app.contratas_app.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.contratas.app.contratas_app.models.Cliente;
import com.contratas.app.contratas_app.models.Prestamo;
import com.contratas.app.contratas_app.repositories.ClienteRepo;
import com.contratas.app.contratas_app.repositories.PrestamoRepo;
// import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.transaction.Transactional;

// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;

@Service
public class PrestamoServ {


    private final PrestamoRepo prestamoRepo;
    private final ClienteRepo clienteRepo;

    public PrestamoServ(PrestamoRepo prestamoRepo, ClienteRepo clienteRepo) {
        this.prestamoRepo = prestamoRepo;
        this.clienteRepo = clienteRepo;
    }

    public Prestamo guardarPrestamo(Long clienteId, Prestamo prestamo) {
        Cliente cliente = clienteRepo.findById(clienteId)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        prestamo.setCliente(cliente);
        return prestamoRepo.save(prestamo);
    }

    public List<Prestamo> obtenerPrestamosPorCliente(Long clienteId) {
        Cliente cliente = clienteRepo.findById(clienteId)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        return cliente.getPrestamos();
    }

    @Transactional
    public Prestamo registrarInteresRetraso(Long prestamoId, double interesRetraso) {

        Prestamo prestamo = prestamoRepo.findById(prestamoId)
            .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));

        if (prestamo.getSaldoRestante() == null) {
            prestamo.setSaldoRestante(prestamo.getTotalAPagar());
        }

        prestamo.setTotalAPagar(prestamo.getTotalAPagar() + interesRetraso);

        prestamo.setSaldoRestante(prestamo.getSaldoRestante() + interesRetraso);

        return prestamoRepo.save(prestamo);
    }


    @Transactional
    public Prestamo cerrarPrestamo(long prestamoId) {
        Prestamo prestamo = prestamoRepo.findById(prestamoId)
            .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));

        if (prestamo.getSaldoRestante() != null && prestamo.getSaldoRestante() > 0) {
            throw new RuntimeException("No se puede cerrar el préstamo con saldo restante");
        }

        prestamo.setEstado(true);
        return prestamoRepo.save(prestamo);
    }


    // @Transactional
    // public void eliminarPrestamo(long prestamoId) {
    //     Prestamo prestamo = prestamoRepo.findById(prestamoId).orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));
    //     prestamo.getPagos().clear(); // Elimina todos los pagos asociados
    //     prestamoRepo.delete(prestamo);
    // }


    @Transactional
public void eliminarPrestamo(long prestamoId) {

    Prestamo prestamo = prestamoRepo.findById(prestamoId)
        .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));

    // 1️⃣ Quitar pagos (ok)
    prestamo.getPagos().clear();

    // 2️⃣ ROMPER relación con cliente (ESTO ES LO QUE FALTABA)
    Cliente cliente = prestamo.getCliente();
    cliente.getPrestamos().remove(prestamo);
    prestamo.setCliente(null);

    // 3️⃣ Eliminar préstamo
    prestamoRepo.delete(prestamo);
}

}   