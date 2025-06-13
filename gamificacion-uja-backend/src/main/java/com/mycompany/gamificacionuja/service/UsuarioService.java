package com.mycompany.gamificacionuja.service;

import com.mycompany.gamificacionuja.model.Rol;
import com.mycompany.gamificacionuja.model.Usuario;
import com.mycompany.gamificacionuja.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario registrarProfesor(String username, String password, boolean esAdmin) throws Exception {
        if (usuarioRepository.existsByUsername(username)) {
            throw new Exception("El nombre de usuario ya existe");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setRol(esAdmin ? Rol.ADMIN : Rol.PROFESOR);

        return usuarioRepository.save(usuario);
    }

    // --- Nuevo método para cambio de contraseña ---
    public void cambiarPassword(String username, String passwordActual, String nuevaPassword) {
        Usuario user = usuarioRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(passwordActual, user.getPassword())) {
            throw new RuntimeException("Contraseña actual incorrecta");
        }
        user.setPassword(passwordEncoder.encode(nuevaPassword));
        usuarioRepository.save(user);
    }
}
