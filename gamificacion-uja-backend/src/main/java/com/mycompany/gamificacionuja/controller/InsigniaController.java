package com.mycompany.gamificacionuja.controller;

import com.mycompany.gamificacionuja.model.Insignia;
import com.mycompany.gamificacionuja.service.InsigniaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/insignias")
public class InsigniaController {

    private final InsigniaService insigniaService;

    public InsigniaController(InsigniaService insigniaService) {
        this.insigniaService = insigniaService;
    }

    @GetMapping
    public List<Insignia> getAll() {
        return insigniaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Insignia> getById(@PathVariable Long id) {
        return insigniaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Insignia> create(@RequestBody Insignia insignia) {
        Insignia saved = insigniaService.save(insignia);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        insigniaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
