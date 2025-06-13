package com.mycompany.gamificacionuja.controller;

import com.mycompany.gamificacionuja.service.AlumnoCsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/alumnos/csv")
public class AlumnoCsvController {

    @Autowired
    private AlumnoCsvService alumnoCsvService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file,
                                                @RequestParam("rol") String rol) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("El archivo está vacío", HttpStatus.BAD_REQUEST);
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.equals("text/csv")) {
            return new ResponseEntity<>("El archivo debe ser de tipo CSV", HttpStatus.BAD_REQUEST);
        }

        try {
            alumnoCsvService.importCsv(file);
            return new ResponseEntity<>("Archivo CSV importado con éxito", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Error al procesar el archivo CSV: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("Error inesperado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
