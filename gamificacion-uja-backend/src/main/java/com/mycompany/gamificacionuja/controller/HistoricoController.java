// src/main/java/com/mycompany/gamificacionuja/controller/HistoricoController.java
package com.mycompany.gamificacionuja.controller;

import com.mycompany.gamificacionuja.dto.InsigniasDTO;
import com.mycompany.gamificacionuja.dto.MejorAlumnoDTO;
import com.mycompany.gamificacionuja.dto.AlumnoDTO;
import com.mycompany.gamificacionuja.model.AlumnoInsignia;
import com.mycompany.gamificacionuja.service.AlumnoInsigniaService;
import com.mycompany.gamificacionuja.service.AlumnoService;
import com.mycompany.gamificacionuja.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/historico")
@RequiredArgsConstructor
public class HistoricoController {

    private final AlumnoService alumnoService;
    private final RankingService rankingService;
    private final AlumnoInsigniaService alumnoInsigniaService;

    /** GET /api/historico/cursos */
    @GetMapping("/cursos")
    public ResponseEntity<List<String>> listarCursos() {
        // Usamos tu DTO para extraer cursos
        Set<String> cursos = alumnoService.getAllAlumnosDTO().stream()
            .map(AlumnoDTO::getCurso)
            .filter(Objects::nonNull)
            .collect(Collectors.toCollection(TreeSet::new));
        return ResponseEntity.ok(new ArrayList<>(cursos));
    }

    /** GET /api/historico/mejor?curso={curso} */
    @GetMapping("/mejor")
    public ResponseEntity<List<MejorAlumnoDTO>> mejorPorCurso(@RequestParam String curso) {
        var ranking = rankingService.obtenerRanking();

        List<MejorAlumnoDTO> top = ranking.stream()
            // Filtrar solo alumnos de ese curso
            .filter(entry -> alumnoService.getAllAlumnosDTO().stream()
                .anyMatch(dto -> dto.getDni().equals(entry.getDni())
                              && curso.equals(dto.getCurso())))
            .map(entry -> {
                // Usamos tu método existente findByDni
                List<AlumnoInsignia> asigns = alumnoInsigniaService.findByDni(entry.getDni());

                int oro = (int) asigns.stream()
                    .filter(ai -> "Oro".equals(ai.getInsignia().getNombre()))
                    .count();
                int plata = (int) asigns.stream()
                    .filter(ai -> "Plata".equals(ai.getInsignia().getNombre()))
                    .count();
                int bronce = (int) asigns.stream()
                    .filter(ai -> "Bronce".equals(ai.getInsignia().getNombre()))
                    .count();

                InsigniasDTO insDto = new InsigniasDTO(oro, plata, bronce);
                return new MejorAlumnoDTO(
                    entry.getNombre(),
                    entry.getDni(),
                    insDto,
                    entry.getNivel(),
                    entry.getPuntos(),
                    entry.getPosicion()
                );
            })
            .limit(3)
            .collect(Collectors.toList());

        return ResponseEntity.ok(top);
    }
}
