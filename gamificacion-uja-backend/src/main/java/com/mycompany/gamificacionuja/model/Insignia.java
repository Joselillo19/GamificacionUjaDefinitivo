package com.mycompany.gamificacionuja.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
  name = "insignias",
  uniqueConstraints = {
    @UniqueConstraint(columnNames = {"tipo_actividad", "nombre"})
  }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Insignia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private int valor;

    @Column(name="tipo_actividad", nullable = false)
    private String tipoActividad;

    private Integer rangoMin;
    private Integer rangoMax;

    @ManyToOne
    @JoinColumn(name = "alumno_dni", referencedColumnName = "dni")
    @JsonIgnore
    private Alumno alumno;
}
