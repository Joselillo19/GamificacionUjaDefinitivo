package com.mycompany.gamificacionuja.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "profesores")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profesor {

    @Id
    @Column(name = "dni", nullable = false, unique = true)
    private String dni;

    private String nombre;

    private String apellidos;

    @Column(name = "correo_uja", unique = true, nullable = false)
    private String correoUja;

    @Column(name="email", nullable=false)
    private String email;

    @Column(name = "usuario", unique = true, nullable = false)
    private String usuario; 

    @Column(name = "password", nullable = false)
    private String contrasena;

    @Enumerated(EnumType.STRING)
    private Rol rol;
}
