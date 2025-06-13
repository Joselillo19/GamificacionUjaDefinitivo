package com.mycompany.gamificacionuja.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor
public class AlumnoInsigniasDTO {

    @Getter @Setter @NoArgsConstructor
    public static class InsigniaInfo {
        private Long id;
        private String actividad;
        private String tipoInsignia;
    }

    private List<InsigniaInfo> insignias;
    private String nivel;
    private int puntos;
    private int posicion;

    // Constructor completo
    public AlumnoInsigniasDTO(List<InsigniaInfo> insignias, String nivel, int puntos, int posicion) {
        this.insignias = insignias;
        this.nivel = nivel;
        this.puntos = puntos;
        this.posicion = posicion;
    }
}
