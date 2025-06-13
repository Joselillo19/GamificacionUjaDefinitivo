package com.mycompany.gamificacionuja.repository;

import com.mycompany.gamificacionuja.model.Nivel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NivelRepository extends JpaRepository<Nivel, Long> {

    // Buscar nivel por nombre
    Optional<Nivel> findByNombre(String nombre);

    boolean existsByNombre(String nombre);

    // Consulta para obtener el nivel cuyo rango incluye los puntos dados
    @Query("SELECT n FROM Nivel n WHERE n.puntosMinimos <= :puntos AND n.puntosMaximos >= :puntos")
    Optional<Nivel> findByPuntosMenorIgualAndPuntosMayor(@Param("puntos") int puntos);

}
