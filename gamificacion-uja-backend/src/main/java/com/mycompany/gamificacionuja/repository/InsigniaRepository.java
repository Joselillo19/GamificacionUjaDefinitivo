package com.mycompany.gamificacionuja.repository;

import com.mycompany.gamificacionuja.model.AlumnoInsignia;
import com.mycompany.gamificacionuja.model.Insignia;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface InsigniaRepository extends JpaRepository<Insignia, Long> {

    Optional<Insignia> findByNombre(String nombre);

    List<Insignia> findByTipoActividad(String tipoActividad);

    // Buscar insignia por tipo de actividad y rango que contenga el valor dado (por ejemplo, para test y valor 8)
    Optional<Insignia> findByTipoActividadAndRangoMinLessThanEqualAndRangoMaxGreaterThanEqual(
            String tipoActividad, Integer valor, Integer valor2);

    List<AlumnoInsignia> findByAlumnoDni(String dni);
    
    Optional<Insignia> findByTipoActividadAndNombre(String tipoActividad, String nombre);


    @Query("SELECT COALESCE(SUM(i.valor), 0) FROM Insignia i WHERE i.alumno.dni = :dni")
    int sumPuntosByAlumnoDni(@Param("dni") String dni);

    @Query("SELECT COUNT(ai) FROM AlumnoInsignia ai WHERE ai.alumno.dni = :dni")
    int countByAlumnoDni(String dni);

}
