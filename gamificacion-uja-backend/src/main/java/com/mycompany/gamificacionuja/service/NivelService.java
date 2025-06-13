package com.mycompany.gamificacionuja.service;

import com.mycompany.gamificacionuja.model.Nivel;
import com.mycompany.gamificacionuja.repository.NivelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NivelService {

    private final NivelRepository nivelRepository;

    public NivelService(NivelRepository nivelRepository) {
        this.nivelRepository = nivelRepository;
    }

    public List<Nivel> findAll() {
        return nivelRepository.findAll();
    }

    public Optional<Nivel> findById(Long id) {
        return nivelRepository.findById(id);
    }

    public Nivel save(Nivel nivel) {
        boolean existe;
        if (nivel.getId() == null) {
            // Nuevo nivel: basta con comprobar nombre
            existe = nivelRepository.existsByNombre(nivel.getNombre());
        } else {
            // Actualización: solo si cambia el nombre
            Optional<Nivel> original = nivelRepository.findById(nivel.getId());
            existe = original.isPresent()
                    && !original.get().getNombre().equals(nivel.getNombre())
                    && nivelRepository.existsByNombre(nivel.getNombre());
        }
        if (existe) {
            throw new IllegalArgumentException("Ya existe un nivel con el nombre: " + nivel.getNombre());
        }
        return nivelRepository.save(nivel);
    }

    public void deleteById(Long id) {
        nivelRepository.deleteById(id);
    }

    // Además puedes añadir métodos personalizados para buscar niveles por rango de puntos o nombre
    public Optional<Nivel> findByNombre(String nombre) {
        return nivelRepository.findByNombre(nombre);
    }

    public Optional<Nivel> findNivelByPoints(int puntos) {
        List<Nivel> niveles = nivelRepository.findAll();
        return niveles.stream()
                .filter(n -> puntos >= n.getPuntosMinimos() && puntos <= n.getPuntosMaximos())
                .findFirst();
    }
}
