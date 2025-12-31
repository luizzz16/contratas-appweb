package com.contratas.app.contratas_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contratas.app.contratas_app.models.Prestamo;

@Repository
public interface PrestamoRepo extends JpaRepository<Prestamo, Long> {
}
