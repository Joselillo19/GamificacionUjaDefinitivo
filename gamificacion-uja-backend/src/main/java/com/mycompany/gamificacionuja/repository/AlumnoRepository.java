package com.mycompany.gamificacionuja.repository;

import com.mycompany.gamificacionuja.model.Alumno;
import com.mycompany.gamificacionuja.model.PasswordResetToken;
import com.mycompany.gamificacionuja.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, String> {
    Optional<Alumno> findByCorreoElectronico(String correoElectronico);
    Optional<Alumno> findByDni(String dni);
    // Si quieres luego, se puede crear método para buscar por DNI, nombre, apellidos (o concatenado)
    List<Alumno> findByCurso(String curso);
    boolean existsByDni(String dni);
    @Query("SELECT a FROM Alumno a")
List<Alumno> findAllAlumnosIncluyendoTodo();

 // Elimina un alumno por su DNI
    void deleteByDni(String dni);

}


