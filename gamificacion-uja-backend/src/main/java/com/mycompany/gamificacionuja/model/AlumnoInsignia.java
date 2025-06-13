package com.mycompany.gamificacionuja.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "alumnos_insignias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlumnoInsignia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con el alumno (clave foránea DNI)
    @ManyToOne
    @JoinColumn(name = "dni_alumno", referencedColumnName = "dni")
    @JsonIgnore
    private Alumno alumno;

    // Relación con la insignia
    @ManyToOne
    @JoinColumn(name = "insignia_id", referencedColumnName = "id")
    private Insignia insignia;

    // Fecha y hora en que se otorgó la insignia
    @Column(nullable = false)
    private LocalDateTime fechaOtorgacion;
}
