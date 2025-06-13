package com.mycompany.gamificacionuja.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "alumno")
public class Alumno {

    @Id
    @Column(name = "dni", nullable = false, unique = true)
    private String dni;

    @Column(name = "primer_apellido")
    private String primerApellido;

    @Column(name = "segundo_apellido")
    private String segundoApellido;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "plan_del_alumno")
    private String planDelAlumno;

    @Column(name = "correo1")
    private String correoElectronico;

    @Column(name = "curso")
    private String curso;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol")
    private Rol rol;

    public Alumno() {
        this.rol = Rol.ALUMNO;
    }

    // Constructor básico
    public Alumno(String dni, String primerApellido, String segundoApellido, String nombre,
                  String correoElectronico, String password) {
        this.dni = dni;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
        this.password = password;
        this.rol = Rol.ALUMNO;
    }



    // Relación con asignaciones de insignias, con borrado en cascada
     @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AlumnoInsignia> asignaciones = new ArrayList<>();

    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Insignia> insignias = new ArrayList<>();

    public List<AlumnoInsignia> getAsignaciones() {
        return asignaciones;
    }

    public void setAsignaciones(List<AlumnoInsignia> asignaciones) {
        this.asignaciones = asignaciones;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPlanDelAlumno() {
        return planDelAlumno;
    }

    public void setPlanDelAlumno(String planDelAlumno) {
        this.planDelAlumno = planDelAlumno;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

     @Transient
    public String getApellidos() {
        StringBuilder apellidos = new StringBuilder();
        if (primerApellido != null && !primerApellido.isBlank()) {
            apellidos.append(primerApellido.trim());
        }
        if (segundoApellido != null && !segundoApellido.isBlank()) {
            if (apellidos.length() > 0) {
                apellidos.append(" ");
            }
            apellidos.append(segundoApellido.trim());
        }
        return apellidos.toString();
    }

    
}
