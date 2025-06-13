package com.mycompany.gamificacionuja.dto;

public class CambiarPasswordRequest {
    private String username;         // correoUja en el proyecto es el username
    private String passwordActual;
    private String nuevaPassword;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPasswordActual() { return passwordActual; }
    public void setPasswordActual(String passwordActual) { this.passwordActual = passwordActual; }

    public String getNuevaPassword() { return nuevaPassword; }
    public void setNuevaPassword(String nuevaPassword) { this.nuevaPassword = nuevaPassword; }
}