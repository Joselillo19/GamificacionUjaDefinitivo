package com.mycompany.gamificacionuja.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Primary
@Service
public class CombinedUserDetailsService implements UserDetailsService {

    private final UsuarioUserDetailsService usuarioService;
    private final AlumnoUserDetailsService alumnoService;

    public CombinedUserDetailsService(UsuarioUserDetailsService usuarioService, AlumnoUserDetailsService alumnoService) {
        this.usuarioService = usuarioService;
        this.alumnoService = alumnoService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return usuarioService.loadUserByUsername(username); // intenta con email
        } catch (UsernameNotFoundException e) {
            return alumnoService.loadUserByUsername(username); // si no, prueba con dni
        }
    }
}

