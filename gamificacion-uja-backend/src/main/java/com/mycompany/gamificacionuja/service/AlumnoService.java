package com.mycompany.gamificacionuja.service;

import com.mycompany.gamificacionuja.dto.AlumnoDTO;
import com.mycompany.gamificacionuja.dto.AlumnoInsigniasDTO;
import com.mycompany.gamificacionuja.dto.AlumnoInsigniasDTO.InsigniaInfo;
import com.mycompany.gamificacionuja.model.Alumno;
import com.mycompany.gamificacionuja.model.AlumnoInsignia;
import com.mycompany.gamificacionuja.model.Nivel;
import com.mycompany.gamificacionuja.model.Rol;
import com.mycompany.gamificacionuja.repository.AlumnoInsigniaRepository;
import com.mycompany.gamificacionuja.repository.AlumnoRepository;
import com.mycompany.gamificacionuja.repository.InsigniaRepository;
import com.mycompany.gamificacionuja.repository.NivelRepository;
import com.mycompany.gamificacionuja.util.Utils;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AlumnoService {

    private final AlumnoRepository alumnoRepository;
    private final InsigniaRepository insigniaRepository;
    private final NivelRepository nivelRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final AlumnoInsigniaRepository alumnoInsigniaRepository;

    public List<Alumno> listarTodosLosAlumnos() {
    return alumnoRepository.findAll();
}

    // Obtener todos los alumnos convertidos a DTO
    public List<AlumnoDTO> getAllAlumnosDTO() {
        List<Alumno> alumnos = alumnoRepository.findAll();

        return alumnos.stream().map(alumno -> {
            AlumnoDTO dto = new AlumnoDTO();
            dto.setDni(alumno.getDni());
            dto.setPrimerApellido(alumno.getPrimerApellido());
            dto.setSegundoApellido(alumno.getSegundoApellido());
            dto.setNombre(alumno.getNombre());
            dto.setPlan(alumno.getPlanDelAlumno());
            dto.setCorreoElectronico(alumno.getCorreoElectronico());
            dto.setCurso(alumno.getCurso());

            // Obtener cantidad de insignias
            int cantidadInsignias = insigniaRepository.countByAlumnoDni(alumno.getDni());
            dto.setCantidadInsignias(cantidadInsignias);

            // Obtener puntos (sumar puntos por insignias)
            int puntos = insigniaRepository.sumPuntosByAlumnoDni(alumno.getDni());
            dto.setPuntos(puntos);

             // Buscar nivel por puntos
            Optional<Nivel> nivelOpt = nivelRepository.findAll()
                    .stream()
                    .filter(n -> puntos >= n.getPuntosMinimos() && puntos <= n.getPuntosMaximos())
                    .findFirst();
            dto.setNivel(nivelOpt.map(Nivel::getNombre).orElse("Sin nivel"));

            return dto;
        }).collect(Collectors.toList());
    }

    // Otros métodos (saveAlumno, findByDni, etc.) se mantienen igual
    @Transactional(readOnly = true)
    public AlumnoInsigniasDTO getInsigniasDTO(String dni) {
        Alumno alumno = alumnoRepository.findById(dni)
            .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        List<AlumnoInsignia> asignaciones = alumnoInsigniaRepository.findByAlumnoDni(dni);
        List<InsigniaInfo> infos = asignaciones.stream().map(ai -> {
            InsigniaInfo info = new InsigniaInfo();
            info.setId(ai.getId());
            info.setActividad(ai.getInsignia().getTipoActividad());
            info.setTipoInsignia(ai.getInsignia().getNombre());
            return info;
        }).toList();

        int puntos = insigniaRepository.sumPuntosByAlumnoDni(dni);
        Optional<Nivel> nivelOpt = nivelRepository.findAll().stream()
            .filter(n -> puntos >= n.getPuntosMinimos() && puntos <= n.getPuntosMaximos())
            .findFirst();
        String nivel = nivelOpt.map(Nivel::getNombre).orElse("Sin nivel");

        List<Alumno> ranking = alumnoRepository.findAll(); // suposición: ya ordenas en RankingService
        int posicion = ranking.indexOf(alumno) + 1;

        return new AlumnoInsigniasDTO(infos, nivel, puntos, posicion);
    }

    public void eliminarAlumnoPorDni(String dni) {
        if (!alumnoRepository.existsById(dni)) {
            throw new RuntimeException("No se encontró ningún alumno con DNI: " + dni);
        }
        alumnoRepository.deleteById(dni);
    }

    public Optional<Alumno> getAlumnoByDni(String dni) {
        return alumnoRepository.findById(dni);
    }

    public boolean existsByDni(String dni) {
        return alumnoRepository.existsById(dni);
    }


    public Alumno saveAlumno(Alumno alumno) {
        String contrasenaTemporal = Utils.generarContrasenaTemporal();
        alumno.setPassword(passwordEncoder.encode(contrasenaTemporal));
        alumno.setRol(Rol.ALUMNO);
        Alumno guardado = alumnoRepository.save(alumno);
        emailService.enviarContrasenaAlumno(alumno.getCorreoElectronico(), contrasenaTemporal);
        return guardado;
    }


}
