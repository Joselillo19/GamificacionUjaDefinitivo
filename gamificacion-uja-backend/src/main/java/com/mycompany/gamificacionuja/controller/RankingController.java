package com.mycompany.gamificacionuja.controller;

import com.mycompany.gamificacionuja.model.RankingEntry;
import com.mycompany.gamificacionuja.service.RankingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ranking")
public class RankingController {

    private final RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @GetMapping
    public List<RankingEntry> obtenerRanking() {
        // Llamamos al servicio que ya calcula la lista completa con posición, puntos, nivel e insignias.
        return rankingService.obtenerRanking();
    }
}
