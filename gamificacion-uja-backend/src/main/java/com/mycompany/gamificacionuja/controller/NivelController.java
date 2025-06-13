package com.mycompany.gamificacionuja.controller;

import com.mycompany.gamificacionuja.dto.NivelDTO;
import com.mycompany.gamificacionuja.model.Nivel;
import com.mycompany.gamificacionuja.service.NivelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/niveles")
public class NivelController {

    @Autowired
    private NivelService nivelService;

    @GetMapping
    public ResponseEntity<List<Nivel>> getAll() {
        return ResponseEntity.ok(nivelService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Nivel> getById(@PathVariable Long id) {
        Optional<Nivel> nivel = nivelService.findById(id);
        return nivel.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

     @PostMapping
    public ResponseEntity<Nivel> createOrUpdate(@RequestBody NivelDTO dto) {
        // Validaciones básicas
        if (dto.getNombre() == null
         || dto.getPuntosMinimos() == null
         || dto.getPuntosMaximos() == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Nivel> opt = nivelService.findByNombre(dto.getNombre());
        Nivel nivel = opt.orElseGet(Nivel::new);

        nivel.setNombre(dto.getNombre());
        nivel.setPuntosMinimos(dto.getPuntosMinimos());
        nivel.setPuntosMaximos(dto.getPuntosMaximos());

        Nivel saved = nivelService.save(nivel);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Nivel> update(@PathVariable Long id, @RequestBody Nivel nivel) {
        Optional<Nivel> existing = nivelService.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Nivel toUpdate = existing.get();
        toUpdate.setNombre(nivel.getNombre());
        toUpdate.setPuntosMinimos(nivel.getPuntosMinimos());
        toUpdate.setPuntosMaximos(nivel.getPuntosMaximos());

        Nivel updated = nivelService.save(toUpdate);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        nivelService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
