package com.contratas.app.contratas_app.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.contratas.app.contratas_app.models.Prestador;
import com.contratas.app.contratas_app.repositories.PrestadorRepo;

@Service
public class PrestadorServ {

    private final PrestadorRepo prestadorRepo;

    public PrestadorServ(PrestadorRepo prestadorRepo) {
        this.prestadorRepo = prestadorRepo;
    }

    public Prestador obtenerPrestadorActual(Authentication authentication) {
        OAuth2User user = (OAuth2User) authentication.getPrincipal();
        String googleId = user.getAttribute("sub");

        return prestadorRepo.findByGoogleId(googleId)
                .orElseThrow(() -> new RuntimeException("Prestador no encontrado"));
    }
}
