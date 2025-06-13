package com.mycompany.gamificacionuja.model;

import java.util.List;

public class RankingEntry {
    private int posicion;
    private String dni;
    private String nombre;
    private String apellidos;
    private int puntos;
    private String nivel;
    private List<Insignia> insignias;

    // Constructor
    public RankingEntry(int posicion, String dni, String nombre, String apellidos, int puntos, String nivel, List<Insignia> insignias) {
        this.posicion = posicion;
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.puntos = puntos;
        this.nivel = nivel;
        this.insignias = insignias;
    }

    // Getters y setters
    public int getPosicion() { return posicion; }
    public void setPosicion(int posicion) { this.posicion = posicion; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public int getPuntos() { return puntos; }
    public void setPuntos(int puntos) { this.puntos = puntos; }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }

    public List<Insignia> getInsignias() { return insignias; }
    public void setInsignias(List<Insignia> insignias) { this.insignias = insignias; }
}
