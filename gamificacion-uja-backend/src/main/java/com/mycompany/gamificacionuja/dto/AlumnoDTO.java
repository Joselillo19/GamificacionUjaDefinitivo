package com.mycompany.gamificacionuja.dto;

public class AlumnoDTO {
    private String dni;
    private String primerApellido;
    private String segundoApellido;
    private String nombre;
    private String plan;
    private String correoElectronico;
    private String curso;
    private String nivel;
    private int cantidadInsignias;
    private int puntos;

    public AlumnoDTO() {}

    // Getters y setters
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getPrimerApellido() { return primerApellido; }
    public void setPrimerApellido(String primerApellido) { this.primerApellido = primerApellido; }
    public String getSegundoApellido() { return segundoApellido; }
    public void setSegundoApellido(String segundoApellido) { this.segundoApellido = segundoApellido; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getPlan() { return plan; }
    public void setPlan(String plan) { this.plan = plan; }
    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }
    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }
    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }
    public int getCantidadInsignias() { return cantidadInsignias; }
    public void setCantidadInsignias(int cantidadInsignias) { this.cantidadInsignias = cantidadInsignias; }
    public int getPuntos() { return puntos; }
    public void setPuntos(int puntos) { this.puntos = puntos; }
}
