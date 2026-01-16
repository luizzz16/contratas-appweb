package com.contratas.app.contratas_app.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.contratas.app.contratas_app.models.Prestador;
import com.contratas.app.contratas_app.repositories.PrestadorRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final PrestadorRepo prestadorRepo;

    public OAuth2LoginSuccessHandler(PrestadorRepo prestadorRepo) {
        this.prestadorRepo = prestadorRepo;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User user = (OAuth2User) authentication.getPrincipal();

        String googleId = user.getAttribute("sub");
        String email = user.getAttribute("email");
        String nombre = user.getAttribute("name");

        Optional<Prestador> existente = prestadorRepo.findByGoogleId(googleId);

        if (existente.isEmpty()) {
            Prestador nuevo = new Prestador();
            nuevo.setGoogleId(googleId);
            nuevo.setCorreo(email);
            nuevo.setNombre(nombre);
            prestadorRepo.save(nuevo);
        }

        response.sendRedirect("/");
    }
}
