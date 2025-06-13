package com.mycompany.gamificacionuja.controller;

import com.mycompany.gamificacionuja.dto.AuthRequest;
import com.mycompany.gamificacionuja.dto.AuthResponse;
import com.mycompany.gamificacionuja.dto.ForgotPasswordRequest;
import com.mycompany.gamificacionuja.dto.ResetPasswordRequest;
import com.mycompany.gamificacionuja.model.Alumno;
import com.mycompany.gamificacionuja.model.PasswordResetToken;
import com.mycompany.gamificacionuja.repository.AlumnoRepository;
import com.mycompany.gamificacionuja.repository.PasswordResetTokenRepository;
import com.mycompany.gamificacionuja.security.JwtService;
import com.mycompany.gamificacionuja.service.EmailService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
// Permite llamadas desde tu front React en localhost:3000
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AlumnoRepository alumnoRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final EmailService emailService;

     @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
      /*  try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            UserDetails user = (UserDetails) authentication.getPrincipal();*/
            return ResponseEntity.ok(new AuthResponse("fake-token"));
           /* String token = jwtService.generateToken(user);
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body("Usuario o contraseña incorrectos");
        }*/
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest req) {
        Optional<Alumno> optionalAlumno = alumnoRepository.findByCorreoElectronico(req.getEmailOrDni());
        if (optionalAlumno.isEmpty()) {
            optionalAlumno = alumnoRepository.findByDni(req.getEmailOrDni());
        }

        optionalAlumno.ifPresent(alumno -> {
            String token = UUID.randomUUID().toString();
            PasswordResetToken prt = new PasswordResetToken(
                token,
                alumno,
                LocalDateTime.now().plusHours(1)
            );
            tokenRepository.save(prt);
            String link = "http://localhost:3000/reset-password?token=" + token;
            emailService.enviarCorreo(
                alumno.getCorreoElectronico(),
                "Recuperar Contraseña",
                "Para restablecer tu contraseña pulsa aquí:\n" + link
            );
        });

        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    @Transactional
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest req) {
        PasswordResetToken prt = tokenRepository.findByToken(req.getToken())
            .orElseThrow(() -> new RuntimeException("Token no válido"));

        if (prt.isExpired()) {
            tokenRepository.delete(prt);
            return ResponseEntity.badRequest().body("Token expirado");
        }

        Alumno alumno = prt.getAlumno();
        alumno.setPassword(req.getNewPassword());
        alumnoRepository.save(alumno);
        tokenRepository.delete(prt);
        return ResponseEntity.ok().build();
    }
}
