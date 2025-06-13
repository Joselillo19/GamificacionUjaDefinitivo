package com.mycompany.gamificacionuja.security;

import com.mycompany.gamificacionuja.model.Alumno;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class AlumnoDetails implements UserDetails {

    private final Alumno alumno;

    public AlumnoDetails(Alumno alumno) {
        this.alumno = alumno;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + alumno.getRol().name()));
    }

    @Override
    public String getPassword() {
        return alumno.getPassword();
    }

    @Override
    public String getUsername() {
        return alumno.getDni(); // usa DNI para login
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    public Alumno getAlumno() {
        return alumno;
    }
}
