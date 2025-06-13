// src/main/java/com/mycompany/gamificacionuja/controller/AlumnoInsigniaController.java
package com.mycompany.gamificacionuja.controller;

import com.mycompany.gamificacionuja.dto.AlumnoInsigniasDTO;
import com.mycompany.gamificacionuja.service.AlumnoInsigniaService;
import com.mycompany.gamificacionuja.service.AlumnoService;
import com.mycompany.gamificacionuja.service.InsigniaService;
import com.mycompany.gamificacionuja.service.RankingService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alumnos/insignias")
@RequiredArgsConstructor
public class AlumnoInsigniaController {

    private final AlumnoInsigniaService alumnoInsigniaService;
    private final AlumnoService alumnoService;
    private final InsigniaService insigniaService;
    private final RankingService rankingService;

    /** Asignar una nueva insignia a un alumno */
    @PostMapping("/asignar")
    public ResponseEntity<String> asignarInsignia(@RequestBody AsignarDTO dto) {
        var alumnoOpt = alumnoService.getAlumnoByDni(dto.getIdentificador());
        if (alumnoOpt.isEmpty()) {
            return ResponseEntity.badRequest()
                                 .body("Alumno no encontrado: " + dto.getIdentificador());
        }
        var insOpt = insigniaService.findByTipoActividadAndNombre(dto.getActividad(), dto.getTipoInsignia());
        if (insOpt.isEmpty()) {
            return ResponseEntity.badRequest()
                                 .body("Insignia '" + dto.getTipoInsignia() +
                                       "' no encontrada para actividad '" + dto.getActividad() + "'");
        }
        alumnoInsigniaService.asignarInsignia(alumnoOpt.get(), insOpt.get());
        return ResponseEntity.ok("Insignia asignada correctamente");
    }

    /** Listar todas las insignias de un alumno, más nivel, puntos y posición */
    @GetMapping("/{dni}")
    public ResponseEntity<AlumnoInsigniasDTO> detalleInsignias(@PathVariable String dni) {
        var alumnoOpt = alumnoService.getAlumnoByDni(dni);
        if (alumnoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        // 1) Obtener asignaciones
        var asignaciones = alumnoInsigniaService.findByAlumno(alumnoOpt.get());

        // 2) Mapear a DTO.InsigniaInfo
        List<AlumnoInsigniasDTO.InsigniaInfo> lista = asignaciones.stream()
            .map(ai -> {
                var info = new AlumnoInsigniasDTO.InsigniaInfo();
                info.setActividad(ai.getInsignia().getTipoActividad());
                info.setTipoInsignia(ai.getInsignia().getNombre());
                return info;
            })
            .toList();

        // 3) Ranking global para posición y nivel
        var ranking = rankingService.obtenerRanking();
        int posicion = ranking.stream()
                               .filter(e -> e.getDni().equals(dni))
                               .findFirst()
                               .map(r -> r.getPosicion())
                               .orElse(-1);

        // 4) Calcular puntos
        int puntos = asignaciones.stream()
                                 .mapToInt(ai -> ai.getInsignia().getValor())
                                 .sum();

        // 5) Obtener nivel del ranking
        String nivel = ranking.stream()
                              .filter(e -> e.getDni().equals(dni))
                              .findFirst()
                              .map(r -> r.getNivel())
                              .orElse("Sin nivel");

        var dto = new AlumnoInsigniasDTO(lista, nivel, puntos, posicion);
        return ResponseEntity.ok(dto);
    }

    /** Quitar una asignación de insignia por su ID */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> quitarInsignia(@PathVariable Long id) {
        if (!alumnoInsigniaService.existePorId(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Asignación no encontrada");
        }
        alumnoInsigniaService.eliminarPorId(id);
        return ResponseEntity.ok("Insignia quitada correctamente");
    }

    @Getter @Setter @NoArgsConstructor
    public static class AsignarDTO {
        private String identificador;
        private String actividad;
        private String tipoInsignia;
    }
}
