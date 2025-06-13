package com.mycompany.gamificacionuja.repository;

import com.mycompany.gamificacionuja.model.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, String> {
    Optional<Profesor> findByCorreoUja(String correoUja);
    boolean existsByCorreoUja(String correoUja);
    boolean existsByUsuario(String usuario);
}
