package com.mycompany.gamificacionuja.controller;

import com.mycompany.gamificacionuja.dto.CambiarPasswordRequest;
import com.mycompany.gamificacionuja.model.Usuario;
import com.mycompany.gamificacionuja.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // DTO para recibir datos de registro
    public static class RegistroProfesorRequest {
        public String username;
        public String password;
        public boolean esAdmin;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/registrar-profesor")
    public ResponseEntity<?> registrarProfesor(@RequestBody RegistroProfesorRequest request) {
        try {
            Usuario nuevoProfesor = usuarioService.registrarProfesor(request.username, request.password,
                    request.esAdmin);
            return new ResponseEntity<>("Profesor registrado con éxito: " + nuevoProfesor.getUsername(),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al registrar profesor: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/cambiar-password")
    public ResponseEntity<?> cambiarPassword(@RequestBody CambiarPasswordRequest req) {
        try {
            usuarioService.cambiarPassword(req.getUsername(), req.getPasswordActual(), req.getNuevaPassword());
            return ResponseEntity.ok("Contraseña actualizada con éxito");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
