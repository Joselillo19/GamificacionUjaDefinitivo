package com.mycompany.gamificacionuja.service;

import com.mycompany.gamificacionuja.model.AlumnoInsignia;
import com.mycompany.gamificacionuja.model.Alumno;
import com.mycompany.gamificacionuja.model.Insignia;
import com.mycompany.gamificacionuja.repository.AlumnoInsigniaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlumnoInsigniaService {

    private final AlumnoInsigniaRepository alumnoInsigniaRepository;

    public AlumnoInsigniaService(AlumnoInsigniaRepository alumnoInsigniaRepository) {
        this.alumnoInsigniaRepository = alumnoInsigniaRepository;
    }

    public List<AlumnoInsignia> findByAlumno(Alumno alumno) {
        return alumnoInsigniaRepository.findByAlumno(alumno);
    }

    public AlumnoInsignia asignarInsignia(Alumno alumno, Insignia insignia) {
        AlumnoInsignia ai = AlumnoInsignia.builder()
                .alumno(alumno)
                .insignia(insignia)
                .fechaOtorgacion(LocalDateTime.now())
                .build();

        return alumnoInsigniaRepository.save(ai);
    }

    // Calcular los puntos totales que tiene un alumno según sus insignias
    public int calcularPuntosTotales(Alumno alumno) {
        return alumnoInsigniaRepository.findByAlumno(alumno)
                .stream()
                .mapToInt(ai -> ai.getInsignia().getValor())
                .sum();
    }

    public List<AlumnoInsignia> findByDni(String dni) {
        return alumnoInsigniaRepository.findByAlumnoDni(dni);
    }

    // Métodos para eliminar asignación por ID
    public boolean existePorId(Long id) {
        return alumnoInsigniaRepository.existsById(id);
    }

    public void eliminarPorId(Long id) {
        alumnoInsigniaRepository.deleteById(id);
    }
}
