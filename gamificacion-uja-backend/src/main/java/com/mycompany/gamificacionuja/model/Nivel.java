package com.mycompany.gamificacionuja.model;

import jakarta.persistence.*;

@Entity
@Table(name = "nivel")
public class Nivel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(name = "puntos_minimos", nullable = false)
    private int puntosMinimos;

    @Column(name = "puntos_maximos", nullable = false)
    private int puntosMaximos;

    public Nivel() {
    }

    public Nivel(String nombre, int puntosMinimos, int puntosMaximos) {
        this.nombre = nombre;
        this.puntosMinimos = puntosMinimos;
        this.puntosMaximos = puntosMaximos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntosMinimos() {
        return puntosMinimos;
    }

    public void setPuntosMinimos(int puntosMinimos) {
        this.puntosMinimos = puntosMinimos;
    }

    public int getPuntosMaximos() {
        return puntosMaximos;
    }

    public void setPuntosMaximos(int puntosMaximos) {
        this.puntosMaximos = puntosMaximos;
    }
}
