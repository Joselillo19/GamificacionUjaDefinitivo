package com.mycompany.gamificacionuja.security;

import com.mycompany.gamificacionuja.repository.ProfesorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfesorUserDetailsService implements UserDetailsService {

    private final ProfesorRepository profesorRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return profesorRepository.findByCorreoUja(username) // “username” aquí es el correoUja, no el DNI ni otro campo
                .map(ProfesorUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Profesor no encontrado con correo: " + username));
    }
}
