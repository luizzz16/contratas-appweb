package com.contratas.app.contratas_app.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.contratas.app.contratas_app.models.Cliente;
import com.contratas.app.contratas_app.models.Prestamo;
import com.contratas.app.contratas_app.repositories.ClienteRepo;
import com.contratas.app.contratas_app.repositories.PrestamoRepo;


import jakarta.transaction.Transactional;

@Service
public class PrestamoServ {
    private final PrestamoRepo prestamoRepo;
    private final ClienteRepo clienteRepo;

    public PrestamoServ(PrestamoRepo prestamoRepo, ClienteRepo clienteRepo) {
        this.prestamoRepo = prestamoRepo;
        this.clienteRepo = clienteRepo;
    }

    // Guardar préstamo asociado a un cliente
    public Prestamo guardarPrestamo(Long clienteId, Prestamo prestamo) {
        Cliente cliente = clienteRepo.findById(clienteId).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        prestamo.setCliente(cliente);

        if (prestamo.getTotalAPagar() == null) {
            prestamo.setTotalAPagar(
                prestamo.getMonto() +
                (prestamo.getInteres() * prestamo.getMonto() / 1000)
            );
        }

        prestamo.setSaldoRestante(prestamo.getTotalAPagar());
        prestamo.setEstado(false);

        return prestamoRepo.save(prestamo);
    }

    // Obtener préstamos por cliente
    public List<Prestamo> obtenerPrestamosPorCliente(Long clienteId) {
        Cliente cliente = clienteRepo.findById(clienteId).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        return cliente.getPrestamos();
    }

    // Registrar interés por retraso
    @Transactional
    public Prestamo registrarInteresRetraso(Long prestamoId, double interesRetraso) {
        Prestamo prestamo = prestamoRepo.findById(prestamoId).orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));

        if (prestamo.getSaldoRestante() == null) {
            prestamo.setSaldoRestante(prestamo.getTotalAPagar());
        }
        prestamo.setTotalAPagar(prestamo.getTotalAPagar() + interesRetraso);
        prestamo.setSaldoRestante(prestamo.getSaldoRestante() + interesRetraso);
        return prestamoRepo.save(prestamo);
    }

    // Cerrar préstamo
    @Transactional
    public Prestamo cerrarPrestamo(long prestamoId) {
        Prestamo prestamo = prestamoRepo.findById(prestamoId).orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));

        if (prestamo.getSaldoRestante() != null && prestamo.getSaldoRestante() > 0) {
            throw new RuntimeException("No se puede cerrar el préstamo con saldo restante");
        }
        prestamo.setEstado(true);
        prestamo.setFechaFinalizacion(LocalDate.now().toString());
        return prestamoRepo.save(prestamo);
    }

    // Eliminar préstamo
    @Transactional
    public void eliminarPrestamo(long prestamoId) {

        Prestamo prestamo = prestamoRepo.findById(prestamoId).orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));

        prestamo.getPagos().clear();
        Cliente cliente = prestamo.getCliente();
        cliente.getPrestamos().remove(prestamo);
        prestamo.setCliente(null);
        prestamoRepo.delete(prestamo);
    }


    // Renover préstamo
    @Transactional
    public Prestamo renovarPrestamo(Long prestamoId, double nuevoMonto, double nuevoInteres, int nuevoPlazo) {
        Prestamo prestamoAnterior = prestamoRepo.findById(prestamoId).orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));
        double saldoPendiente = prestamoAnterior.getSaldoRestante();


        prestamoAnterior.setSaldoRestante(0.0);
        prestamoAnterior.setEstado(true);
        prestamoRepo.save(prestamoAnterior);

        double montoEntregado = nuevoMonto - saldoPendiente;

        Prestamo nuevoPrestamo = new Prestamo();
        nuevoPrestamo.setCliente(prestamoAnterior.getCliente());
        nuevoPrestamo.setMonto(montoEntregado);
        nuevoPrestamo.setInteres(nuevoInteres);
        nuevoPrestamo.setPlazo(nuevoPlazo);
        nuevoPrestamo.setEstado(false);
        nuevoPrestamo.setFechaRegistro(LocalDate.now().toString());
        nuevoPrestamo.setTotalAPagar(
            nuevoMonto + (nuevoInteres* nuevoMonto/1000)
        );
        nuevoPrestamo.setSaldoRestante(nuevoPrestamo.getTotalAPagar());

        prestamoRepo.save(nuevoPrestamo);

        return nuevoPrestamo;
}




}   