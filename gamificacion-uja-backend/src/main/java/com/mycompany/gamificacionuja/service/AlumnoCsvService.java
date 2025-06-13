package com.mycompany.gamificacionuja.service;

import com.mycompany.gamificacionuja.model.Alumno;
import com.mycompany.gamificacionuja.repository.AlumnoRepository;
import com.mycompany.gamificacionuja.util.CsvUtils;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class AlumnoCsvService {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AlumnoService alumnoService;

    public void importCsv(MultipartFile file) throws IOException {
        try {
            byte[] csvBytes = file.getBytes();

            // Extraer curso de la segunda línea
            String cursoExtraido = extractCursoFromBytes(csvBytes);

            // Leer todos los registros válidos
            List<CSVRecord> records = CsvUtils.parseCsv(csvBytes);

            for (CSVRecord record : records) {
                String rawDni = clean(record.get("DNI"));
                String dni = rawDni.replace("NIF -", "").trim();

                if (dni.isBlank())
                    continue;

                String correo = clean(record.get("Correo electrónico (1)"));
                if (correo.isBlank()) {
                    String plan = clean(record.get("Plan del alumno")).toLowerCase();
                    if (plan.contains("red")) {
                        correo = dni + "@red.ujaen.es";
                    } else {
                        correo = dni + "@ujaen.es";
                    }
                }

                // Generar contraseña temporal aleatoria
                String contraTemp = UUID.randomUUID().toString().substring(0, 8);

                Alumno alumno = new Alumno();
                alumno.setDni(dni);
                alumno.setPrimerApellido(clean(record.get("Primer apellido")));
                alumno.setSegundoApellido(clean(record.get("Segundo apellido")));
                alumno.setNombre(clean(record.get("Nombre")));
                alumno.setPlanDelAlumno(clean(record.get("Plan del alumno")));
                alumno.setCorreoElectronico(correo);
                alumno.setCurso(cursoExtraido);
                alumno.setPassword(passwordEncoder.encode(contraTemp));

                alumnoRepository.save(alumno);

                // Enviar correo de bienvenida
                emailService.enviarContrasenaAlumno(correo, contraTemp);
            }

        } catch (IOException e) {
            throw new IOException("Error al leer el archivo CSV: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al procesar los datos del archivo CSV: " + e.getMessage(), e);
        }
    }

    private String clean(String value) {
        return (value == null) ? "" : value.replace("\"", "").trim();
    }

    private String extractCursoFromBytes(byte[] csvBytes) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(csvBytes), StandardCharsets.UTF_8))) {

            reader.readLine(); // Línea 0
            String line2 = reader.readLine(); // Línea 1

            if (line2 != null && line2.contains("-")) {
                if (line2.startsWith("\uFEFF")) {
                    line2 = line2.replace("\uFEFF", "");
                }
                String[] parts = line2.split("-");
                if (parts.length >= 2) {
                    String p0 = parts[0].trim();
                    String p1 = parts[1].trim();
                    return p0 + "-" + p1;
                }
            }
        }
        int year = LocalDate.now().getYear();
        return year + "-" + (year + 1);
    }
}