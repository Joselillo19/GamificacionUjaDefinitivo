// src/main/java/com/mycompany/gamificacionuja/InicializadorDatos.java
package com.mycompany.gamificacionuja;

import com.mycompany.gamificacionuja.model.Insignia;
import com.mycompany.gamificacionuja.service.InsigniaService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InicializadorDatos {

    private final InsigniaService insigniaService;

    @PostConstruct
    public void inicializar() {
        // Definimos los tipos de insignia y sus puntos
        List<InsigniaSimple> insigniasSimples = List.of(
            new InsigniaSimple("Oro", 3),
            new InsigniaSimple("Plata", 2),
            new InsigniaSimple("Bronce", 1)
        );

        // 1) Realizar ejercicios en pizarra
        crearInsigniasSinRango("Realizar ejercicios en pizarra", insigniasSimples);

        // 2) Responder preguntas en clase
        crearInsigniasSinRango("Responder preguntas en clase", insigniasSimples);

        // 3) Realizar test en platea (valoración 0–10 con rangos)
        crearInsigniasConRango("Realizar test en platea", List.of(
            new InsigniaConRango("Oro",    3, 9, 10),
            new InsigniaConRango("Plata",  2, 7, 8),
            new InsigniaConRango("Bronce", 1, 5, 6)
        ));

        // 4) Ejercicios evaluables/revisados
        crearInsigniasConRango("Realizar ejercicios evaluables/revisados por el profesor", List.of(
            new InsigniaConRango("Oro",    3, 9, 10),
            new InsigniaConRango("Plata",  2, 7, 8),
            new InsigniaConRango("Bronce", 1, 5, 6)
        ));
    }

    private void crearInsigniasSinRango(String actividad, List<InsigniaSimple> lista) {
        for (InsigniaSimple dato : lista) {
            if (insigniaService.findByTipoActividadAndNombre(actividad, dato.nombre).isEmpty()) {
                Insignia ins = new Insignia();
                ins.setTipoActividad(actividad);
                ins.setNombre(dato.nombre);
                ins.setValor(dato.puntos);
                ins.setRangoMin(null);
                ins.setRangoMax(null);
                insigniaService.save(ins);
            }
        }
    }

    private void crearInsigniasConRango(String actividad, List<InsigniaConRango> lista) {
        for (InsigniaConRango dato : lista) {
            if (insigniaService.findByTipoActividadAndNombre(actividad, dato.nombre).isEmpty()) {
                Insignia ins = new Insignia();
                ins.setTipoActividad(actividad);
                ins.setNombre(dato.nombre);
                ins.setValor(dato.puntos);
                ins.setRangoMin(dato.minimo);
                ins.setRangoMax(dato.maximo);
                insigniaService.save(ins);
            }
        }
    }

    // Clase auxiliar para insignias sin rango
    private static class InsigniaSimple {
        final String nombre;
        final int puntos;
        InsigniaSimple(String nombre, int puntos) {
            this.nombre = nombre;
            this.puntos = puntos;
        }
    }

    // Clase auxiliar para insignias con rango
    private static class InsigniaConRango {
        final String nombre;
        final int puntos;
        final int minimo, maximo;
        InsigniaConRango(String nombre, int puntos, int minimo, int maximo) {
            this.nombre = nombre;
            this.puntos = puntos;
            this.minimo = minimo;
            this.maximo = maximo;
        }
    }
}
