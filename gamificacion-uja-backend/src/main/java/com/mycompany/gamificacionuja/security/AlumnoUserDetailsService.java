package com.mycompany.gamificacionuja.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mycompany.gamificacionuja.repository.AlumnoRepository;

@Service
public class AlumnoUserDetailsService implements UserDetailsService {

    private final AlumnoRepository alumnoRepository;

    public AlumnoUserDetailsService(AlumnoRepository alumnoRepository) {
        this.alumnoRepository = alumnoRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return alumnoRepository.findById(username)
                .map(AlumnoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Alumno no encontrado"));
    }
}

