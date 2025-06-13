// src/main/java/com/mycompany/gamificacionuja/dto/MejorAlumnoDTO.java
package com.mycompany.gamificacionuja.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MejorAlumnoDTO {
    private String nombre;
    private String dni;
    private InsigniasDTO insignias;
    private String nivel;
    private int puntuacion;
    private int posicion;
}
