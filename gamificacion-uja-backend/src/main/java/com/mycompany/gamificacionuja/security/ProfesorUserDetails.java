package com.mycompany.gamificacionuja.security;

import com.mycompany.gamificacionuja.model.Profesor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class ProfesorUserDetails implements UserDetails {

    private final Profesor profesor;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + profesor.getRol().name()));
    }

    @Override
    public String getPassword() {
        return profesor.getContrasena();
    }

    @Override
    public String getUsername() {
        return profesor.getCorreoUja(); // o dni si lo prefieres
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
