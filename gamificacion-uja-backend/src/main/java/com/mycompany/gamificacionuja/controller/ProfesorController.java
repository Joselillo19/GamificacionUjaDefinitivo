package com.mycompany.gamificacionuja.controller;

import com.mycompany.gamificacionuja.dto.ProfesorDTO;
import com.mycompany.gamificacionuja.model.Profesor;
import com.mycompany.gamificacionuja.model.Rol;
import com.mycompany.gamificacionuja.service.ProfesorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/profesores")
@RequiredArgsConstructor
public class ProfesorController {

    private final ProfesorService profesorService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/registrar-profesor")
    public ResponseEntity<?> registrarProfesor(@RequestBody ProfesorDTO dto) {
        try {
            // Llamada simplificada con el DTO
            profesorService.registrarProfesor(dto);
            return ResponseEntity.ok("Profesor registrado y correo enviado.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{correoUja}")
    public ResponseEntity<?> obtenerProfesorPorCorreo(@PathVariable String correoUja) {
        Optional<Profesor> profesor = profesorService.obtenerPorCorreo(correoUja);
        return profesor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
