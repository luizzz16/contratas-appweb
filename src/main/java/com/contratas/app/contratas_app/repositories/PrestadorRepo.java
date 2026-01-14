package com.contratas.app.contratas_app.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.contratas.app.contratas_app.models.Prestador;
import java.util.Optional;
public interface PrestadorRepo extends JpaRepository<Prestador, Long> {
    Optional<Prestador> findByGoogleId(String googleId);
}
