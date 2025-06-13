package com.mycompany.gamificacionuja.repository;

import com.mycompany.gamificacionuja.model.AlumnoInsignia;

import jakarta.transaction.Transactional;

import com.mycompany.gamificacionuja.model.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlumnoInsigniaRepository extends JpaRepository<AlumnoInsignia, Long> {

    // Buscar todas las insignias de un alumno
    List<AlumnoInsignia> findByAlumno(Alumno alumno);
    List<AlumnoInsignia> findByAlumnoDni(String dni);
    List<AlumnoInsignia> findByAlumnoIn(List<Alumno> alumnos);

}
