package com.contratas.app.contratas_app.controllers; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.contratas.app.contratas_app.models.Prestamo;
import com.contratas.app.contratas_app.services.PrestamoServ;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/prestamos")
public class PrestamoController {

    private final PrestamoServ prestamoService;
    public PrestamoController(PrestamoServ prestamoService) {
        this.prestamoService = prestamoService;
    }


    @PostMapping("/cliente/{clienteId}")
    public Prestamo guardarPrestamo(
            @PathVariable Long clienteId,
            @RequestBody Prestamo prestamo) {

        return prestamoService.guardarPrestamo(clienteId, prestamo);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Prestamo> prestamosPorCliente(@PathVariable Long clienteId) {
    return prestamoService.obtenerPrestamosPorCliente(clienteId);
}

    @PutMapping("/{prestamoId}/interes")
    public Prestamo agregarInteres(@PathVariable Long prestamoId, @RequestBody Map<String, Double> body) {
        double monto = body.get("monto");
        return prestamoService.registrarInteresRetraso(prestamoId, monto);
    }

    @PutMapping("/{prestamoId}/cerrar")
    public Prestamo cerrarPrestamo(@PathVariable Long prestamoId) {
        return prestamoService.cerrarPrestamo(prestamoId);
    }

    @DeleteMapping("/{prestamoId}/")
    public ResponseEntity<Void> eliminarPrestamo(@PathVariable Long prestamoId) {
        prestamoService.eliminarPrestamo(prestamoId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/renovar")
    public ResponseEntity<Prestamo> renovar(@PathVariable Long id, @RequestBody Prestamo dto ) {
        return ResponseEntity.ok(
            prestamoService.renovarPrestamo(
                id,
                dto.getMonto(),
                dto.getInteres(),
                dto.getPlazo()
            )
        );
    }



}
