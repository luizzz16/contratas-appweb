package com.contratas.app.contratas_app.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.contratas.app.contratas_app.models.Pago;

import com.contratas.app.contratas_app.services.PagoServ;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/pagos")
@CrossOrigin(origins = "*")
public class PagoController {

    private final PagoServ pagoServ;


    public PagoController(PagoServ pagoServ) {
        this.pagoServ = pagoServ;
    }


    @PostMapping("/prestamo/{prestamoId}")
    public Pago guardarPago(@PathVariable Long prestamoId, @RequestBody Pago pago ) {

        return pagoServ.registrarPago(prestamoId, pago.getMonto(), pago.getFechaPago());
    }


    @GetMapping("/prestamo/{prestamoId}")
    public List<Pago> getMethodName(@PathVariable Long prestamoId) {
        return pagoServ.obtenerPagosPorPrestamo(prestamoId);
    }

    @DeleteMapping("/{pagoId}")
    public void eliminarPago(@PathVariable Long pagoId) {
        pagoServ.eliminarPago(pagoId);
    }



}
