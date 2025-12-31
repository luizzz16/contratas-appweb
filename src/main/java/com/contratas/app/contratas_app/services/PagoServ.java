package com.contratas.app.contratas_app.services;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.contratas.app.contratas_app.models.Pago;
import com.contratas.app.contratas_app.models.Prestamo;
import com.contratas.app.contratas_app.repositories.PagoRepo;
import com.contratas.app.contratas_app.repositories.PrestamoRepo;

import jakarta.transaction.Transactional;

@Service
public class PagoServ {

    private final PagoRepo pagoRepo;
    private final PrestamoRepo prestamoRepo;

    

    public PagoServ(PagoRepo pagoRepo , PrestamoRepo prestamoRepo) {
        this.pagoRepo = pagoRepo;
        this.prestamoRepo = prestamoRepo;
    }

    public Pago guardarPago(@PathVariable Long prestamoId, @RequestBody Pago pago) {
        Prestamo prestamo = prestamoRepo.findById(prestamoId)
        .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));

        pago.setPrestamo(prestamo);
        return pagoRepo.save(pago);
    }


    public List<Pago> obtenerPagosPorPrestamo(Long prestamoId) {
        return pagoRepo.findByPrestamoId(prestamoId);
    }


    @Transactional
    public Pago registrarPago(Long prestamoId, double monto) {

        Prestamo prestamo = prestamoRepo.findById(prestamoId)
            .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));

        // ✅ Inicializar saldo si está null
        if (prestamo.getSaldoRestante() == null) {
            prestamo.setSaldoRestante(prestamo.getTotalAPagar());
        }

        if (monto > prestamo.getSaldoRestante()) {
            throw new RuntimeException("El pago excede el saldo restante");
        }

        Pago pago = new Pago();
        pago.setMonto(monto);
        pago.setFechaPago(new java.sql.Date(System.currentTimeMillis()));
        pago.setPrestamo(prestamo);

        pagoRepo.save(pago);

        prestamo.setSaldoRestante(
            prestamo.getSaldoRestante() - monto
        );

        prestamoRepo.save(prestamo);

        return pago;
    }




}
