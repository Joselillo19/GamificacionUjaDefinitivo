package com.mycompany.gamificacionuja.service;

import com.mycompany.gamificacionuja.model.*;
import com.mycompany.gamificacionuja.repository.AlumnoRepository;
import com.mycompany.gamificacionuja.repository.AlumnoInsigniaRepository;
import com.mycompany.gamificacionuja.repository.NivelRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RankingService {

    private final AlumnoRepository alumnoRepository;
    private final AlumnoInsigniaRepository alumnoInsigniaRepository;
    private final NivelRepository nivelRepository;

    public RankingService(AlumnoRepository alumnoRepository,
                          AlumnoInsigniaRepository alumnoInsigniaRepository,
                          NivelRepository nivelRepository) {
        this.alumnoRepository = alumnoRepository;
        this.alumnoInsigniaRepository = alumnoInsigniaRepository;
        this.nivelRepository = nivelRepository;
    }

    public List<RankingEntry> obtenerRanking() {
        List<Alumno> alumnos = alumnoRepository.findAll();
        List<Nivel> niveles = nivelRepository.findAll();

        // Traemos todas las insignias de todos los alumnos de golpe
        List<AlumnoInsignia> todasInsignias = alumnoInsigniaRepository.findByAlumnoIn(alumnos);

        // Agrupamos insignias por alumno DNI para acceso rápido
        Map<String, List<AlumnoInsignia>> insigniasPorAlumno = todasInsignias.stream()
            .collect(Collectors.groupingBy(ai -> ai.getAlumno().getDni()));

        List<RankingEntry> ranking = alumnos.stream().map(alumno -> {
            List<AlumnoInsignia> insigniasAlumno = insigniasPorAlumno.getOrDefault(alumno.getDni(), Collections.emptyList());

            int puntosTotales = insigniasAlumno.stream()
                .mapToInt(ai -> ai.getInsignia().getValor())
                .sum();

            String nivel = niveles.stream()
                .filter(n -> puntosTotales >= n.getPuntosMinimos() && puntosTotales <= n.getPuntosMaximos())
                .map(Nivel::getNombre)
                .findFirst()
                .orElse("Sin nivel");

            List<Insignia> listaInsignias = insigniasAlumno.stream()
                .map(AlumnoInsignia::getInsignia)
                .collect(Collectors.toList());

            return new RankingEntry(0, alumno.getDni(), alumno.getNombre(), alumno.getApellidos(), puntosTotales, nivel, listaInsignias);
        }).sorted(Comparator.comparingInt(RankingEntry::getPuntos).reversed())
          .collect(Collectors.toList());

        for (int i = 0; i < ranking.size(); i++) {
            ranking.get(i).setPosicion(i + 1);
        }

        return ranking;
    }
}
