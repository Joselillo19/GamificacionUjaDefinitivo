package com.mycompany.gamificacionuja.controller;

import com.mycompany.gamificacionuja.dto.AlumnoDTO;
import com.mycompany.gamificacionuja.dto.AlumnoInsigniasDTO;
import com.mycompany.gamificacionuja.model.Alumno;
import com.mycompany.gamificacionuja.model.AlumnoInsignia;
import com.mycompany.gamificacionuja.service.AlumnoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alumnos")
@RequiredArgsConstructor
public class AlumnoController {

    private final AlumnoService alumnoService;

    @GetMapping
    public ResponseEntity<List<AlumnoDTO>> listarAlumnos() {
        return ResponseEntity.ok(alumnoService.getAllAlumnosDTO());
    }

    @GetMapping("/{dni}")
    public ResponseEntity<Alumno> getAlumnoPorDni(@PathVariable String dni) {
        return alumnoService.getAlumnoByDni(dni)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    public ResponseEntity<?> registrarAlumno(@RequestBody Alumno alumno) {
        if (alumnoService.existsByDni(alumno.getDni())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Ya existe un alumno con ese DNI");
        }
        Alumno nuevoAlumno = alumnoService.saveAlumno(alumno);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAlumno);
    }

    @DeleteMapping("/{identificador}")
    public ResponseEntity<?> eliminarAlumnoPorDni(@PathVariable String identificador) {
        alumnoService.eliminarAlumnoPorDni(identificador);
        return ResponseEntity.ok("Alumno con DNI " + identificador + " eliminado correctamente.");

    }

    @GetMapping("/{dni}/insignias")
    public ResponseEntity<AlumnoInsigniasDTO> getInsigniasPorAlumno(@PathVariable String dni) {
        try {
            AlumnoInsigniasDTO dto = alumnoService.getInsigniasDTO(dni);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
