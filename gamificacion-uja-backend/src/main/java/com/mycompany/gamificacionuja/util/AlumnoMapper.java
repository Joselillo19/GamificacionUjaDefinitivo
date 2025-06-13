package com.mycompany.gamificacionuja.util;

import com.mycompany.gamificacionuja.dto.AlumnoDTO;
import com.mycompany.gamificacionuja.model.Alumno;
import com.mycompany.gamificacionuja.model.Nivel;
import com.mycompany.gamificacionuja.service.InsigniaService;
import com.mycompany.gamificacionuja.service.NivelService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AlumnoMapper {

    private final InsigniaService insigniaService;
    private final NivelService nivelService;

    public AlumnoDTO toDto(Alumno alumno) {
        AlumnoDTO dto = new AlumnoDTO();
        dto.setDni(alumno.getDni());
        dto.setPrimerApellido(alumno.getPrimerApellido());
        dto.setSegundoApellido(alumno.getSegundoApellido());
        dto.setNombre(alumno.getNombre());
        dto.setPlan(alumno.getPlanDelAlumno());
        dto.setCorreoElectronico(alumno.getCorreoElectronico());
        dto.setCurso(alumno.getCurso());

        int cantidadInsignias = insigniaService.countInsigniasByAlumnoDni(alumno.getDni());
        int puntos = insigniaService.calculatePointsByAlumnoDni(alumno.getDni());
        dto.setCantidadInsignias(cantidadInsignias);
        dto.setPuntos(puntos);

        Optional<Nivel> nivelOpt = nivelService.findNivelByPoints(puntos);
        dto.setNivel(nivelOpt.map(Nivel::getNombre).orElse("Sin nivel"));

        return dto;
    }
}
