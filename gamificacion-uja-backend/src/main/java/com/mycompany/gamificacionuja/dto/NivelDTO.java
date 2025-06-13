// src/main/java/com/mycompany/gamificacionuja/dto/NivelDTO.java
package com.mycompany.gamificacionuja.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NivelDTO {
    private String nombre;
    private Integer puntosMinimos;
    private Integer puntosMaximos;
}
