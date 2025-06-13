// src/main/java/com/mycompany/gamificacionuja/config/TutorDataLoader.java
package com.mycompany.gamificacionuja.config;

import com.mycompany.gamificacionuja.model.Usuario;
import com.mycompany.gamificacionuja.model.Rol;
import com.mycompany.gamificacionuja.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class TutorDataLoader implements CommandLineRunner {

    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        String email   = "maria.tutora@ujaen.es";
        String rawPass = "Tutora123!";

        if (usuarioRepo.findByUsername(email).isEmpty()) {
            Usuario tutor = new Usuario();
            tutor.setUsername(email);
            tutor.setPassword(passwordEncoder.encode(rawPass));
            tutor.setEmail(email);
            tutor.setRol(Rol.ADMIN);
            tutor.setEnabled(true);  // explícito
            usuarioRepo.save(tutor);
            System.out.println("✅ Cuenta ADMIN creada → " + email + " / " + rawPass);
        }
    }
}
