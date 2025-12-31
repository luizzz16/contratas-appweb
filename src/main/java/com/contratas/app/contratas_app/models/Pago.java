package com.contratas.app.contratas_app.models;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Pago {
    @Id
    @GeneratedValue


    private Long id;
    private Double monto;
    private Date fechaPago;

    @ManyToOne
    @JoinColumn(name = "prestamo_id", nullable = false)
    @JsonBackReference("prestamo-pago")
    private Prestamo prestamo;

    public Pago() {
    }

    public Long getId() {
        return id;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }
}