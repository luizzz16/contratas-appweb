package com.contratas.app.contratas_app.repositories;
import com.contratas.app.contratas_app.models.Cliente;
import com.contratas.app.contratas_app.models.Prestador;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Crea un repositorio de Spring Data JPA para la entidad Cliente
// Le dice a Spring que esta interfaz se encarga de hablar con la base de datos
// Spring la detecta automáticamente y crea su implementación


// Extiende JpaRepository para proporcionar operaciones CRUD y de paginación
// Al extender JpaRepository, Spring nos da métodos listos para:
// guardar, buscar, listar, actualizar y borrar clientes
// sin escribir código SQL
public interface ClienteRepo extends JpaRepository<Cliente, Long> {
    List<Cliente> findByPrestador(Prestador prestador);
} 

