package com.mycompany.gamificacionuja.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequiredArgsConstructor
@RequestMapping("/api/email")
public class EmailService {

    private final JavaMailSender mailSender;

    public void enviarCorreo(String destinatario, String asunto, String cuerpo) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinatario);
        mensaje.setSubject(asunto);
        mensaje.setText(cuerpo);
        mailSender.send(mensaje);
    }

    // Antes: enviarContrasenaTemporal → lo renombramos para dejar claro "Profesor"
    public void enviarContrasenaProfesor(String destinatario, String contrasena) {
        String asunto = "Alta como profesor en la plataforma de gamificación UJA";
        String cuerpo = "Hola,\n\n" +
                        "Tu cuenta de PROFESOR ha sido registrada en la plataforma.\n" +
                        "Tu contraseña temporal es: " + contrasena + "\n\n" +
                        "Por favor, cámbiala tras tu primer inicio de sesión.\n\n" +
                        "Un saludo.";
        enviarCorreo(destinatario, asunto, cuerpo);
    }

    // Nuevo método para alumnos
    public void enviarContrasenaAlumno(String destinatario, String contrasena) {
        String asunto = "Bienvenido a la plataforma de gamificación UJA";
        String cuerpo = "Hola,\n\n" +
                        "Se ha creado tu cuenta de ALUMNO en la plataforma.\n" +
                        "Tu contraseña temporal es: " + contrasena + "\n\n" +
                        "Accede desde: https://tu-frontend-url/login\n" +
                        "Por favor, cámbiala tras tu primer inicio de sesión.\n\n" +
                        "Un saludo.";
        enviarCorreo(destinatario, asunto, cuerpo);
    }
}
