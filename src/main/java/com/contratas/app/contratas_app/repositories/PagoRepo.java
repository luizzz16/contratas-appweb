package com.contratas.app.contratas_app.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.contratas.app.contratas_app.models.Pago;

public interface PagoRepo extends JpaRepository<Pago, Long> {
    List<Pago> findByPrestamoId(Long prestamoId);
}
