package com.mycompany.gamificacionuja.service;

import com.mycompany.gamificacionuja.model.Insignia;
import com.mycompany.gamificacionuja.repository.InsigniaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InsigniaService {

    private final InsigniaRepository insigniaRepository;

    public InsigniaService(InsigniaRepository insigniaRepository) {
        this.insigniaRepository = insigniaRepository;
    }

    public List<Insignia> findAll() {
        return insigniaRepository.findAll();
    }

    public Optional<Insignia> findById(Long id) {
        return insigniaRepository.findById(id);
    }

    public Optional<Insignia> findByNombre(String nombre) {
        return insigniaRepository.findByNombre(nombre);
    }

    public List<Insignia> findByTipoActividad(String tipoActividad) {
        return insigniaRepository.findByTipoActividad(tipoActividad);
    }

    public Optional<Insignia> findByTipoActividadAndValorEnRango(String tipoActividad, int valor) {
        return insigniaRepository.findByTipoActividadAndRangoMinLessThanEqualAndRangoMaxGreaterThanEqual(
                tipoActividad, valor, valor);
    }

    public Insignia save(Insignia insignia) {
        return insigniaRepository.save(insignia);
    }

    public void deleteById(Long id) {
        insigniaRepository.deleteById(id);
    }

    public Optional<Insignia> findByTipoActividadAndNombre(String tipoActividad, String nombre) {
        return insigniaRepository.findByTipoActividadAndNombre(tipoActividad, nombre);
    }

    public int countInsigniasByAlumnoDni(String dni) {
        // Implementa la lógica para contar insignias del alumno
        return 0; // placeholder
    }

    public int calculatePointsByAlumnoDni(String dni) {
        // Implementa la lógica para calcular puntos del alumno según sus insignias
        return 0; // placeholder
    }
}
