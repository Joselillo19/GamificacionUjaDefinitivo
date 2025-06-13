package com.mycompany.gamificacionuja.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfesorDTO {
    private String dni;
    private String nombre;
    private String apellidos;
    private String correoUja;
    private String usuario;
    private String contrasena;

    public String getUsuario() {
        return this.usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
