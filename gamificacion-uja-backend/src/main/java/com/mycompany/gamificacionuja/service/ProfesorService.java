package com.mycompany.gamificacionuja.service;

import com.mycompany.gamificacionuja.dto.ProfesorDTO;
import com.mycompany.gamificacionuja.model.Profesor;
import com.mycompany.gamificacionuja.model.Rol;
import com.mycompany.gamificacionuja.repository.ProfesorRepository;
import com.mycompany.gamificacionuja.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
// src/main/java/com/mycompany/gamificacionuja/service/ProfesorService.java

@Service
@RequiredArgsConstructor
public class ProfesorService {

    private final ProfesorRepository profesorRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    // Antes: recibía 5 parámetros sueltos
    // public Profesor registrarProfesor(String dni, String nombre, String apellidos, String correoUja, String usuario) {

    // Ahora: recibirá el DTO completo
    public Profesor registrarProfesor(ProfesorDTO dto) {
        // Validaciones
        if (profesorRepository.existsByCorreoUja(dto.getCorreoUja())) {
            throw new IllegalArgumentException("Ya existe un profesor con ese correo UJA");
        }
        if (profesorRepository.existsByUsuario(dto.getUsuario())) {
            throw new IllegalArgumentException("Ya existe un profesor con ese usuario");
        }

        // Generar contraseña temporal
        String contrasenaTemporal = Utils.generarContrasenaTemporal();

        // Montar entidad
        Profesor profesor = Profesor.builder()
            .dni(dto.getDni())
            .nombre(dto.getNombre())
            .apellidos(dto.getApellidos())
            .correoUja(dto.getCorreoUja())
            .email(dto.getCorreoUja())
            .usuario(dto.getUsuario())
            .contrasena(passwordEncoder.encode(contrasenaTemporal))
            .rol(Rol.PROFESOR)
            .build();

        // Guardar y enviar correo
        Profesor guardado = profesorRepository.save(profesor);
        emailService.enviarContrasenaProfesor(dto.getCorreoUja(), contrasenaTemporal);
        return guardado;
    }

    public Optional<Profesor> obtenerPorCorreo(String correoUja) {
        return profesorRepository.findByCorreoUja(correoUja);
    }
}
