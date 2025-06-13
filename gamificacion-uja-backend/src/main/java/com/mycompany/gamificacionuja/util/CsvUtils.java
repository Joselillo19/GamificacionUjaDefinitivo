package com.mycompany.gamificacionuja.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class CsvUtils {

    public static List<CSVRecord> parseCsv(byte[] csvBytes) throws IOException {
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(new ByteArrayInputStream(csvBytes), StandardCharsets.UTF_8)
        );

        // Saltar dos líneas basura
        reader.readLine(); // Línea 0: "Listas de clase"
        reader.readLine(); // Línea 1: "2024-25 - ..."

        // Leer línea 2 (cabecera real)
        String rawHeaderLine = reader.readLine();
        if (rawHeaderLine == null) {
            throw new IOException("El CSV no contiene una tercera línea con el encabezado.");
        }

        // Dividir cabecera cruda
        String[] rawHeaders = rawHeaderLine.split(";", -1);
        String[] finalHeaders = new String[rawHeaders.length];
        List<String> desired = Arrays.asList("DNI", "Primer apellido", "Segundo apellido", "Nombre", "Plan del alumno", "Correo electrónico (1)");
        int ignoreCounter = 0;

        for (int i = 0; i < rawHeaders.length; i++) {
            String token = rawHeaders[i].replace("\"", "").trim();
            if (desired.contains(token)) {
                finalHeaders[i] = token;
            } else {
                finalHeaders[i] = "IGNORED_" + (ignoreCounter++);
            }
        }

        // Leer el resto del contenido CSV
        StringBuilder cleanedCsvContent = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            cleanedCsvContent.append(line).append("\n");
        }

        Reader cleanedReader = new InputStreamReader(
            new ByteArrayInputStream(cleanedCsvContent.toString().getBytes(StandardCharsets.UTF_8)),
            StandardCharsets.UTF_8
        );

        CSVParser parser = CSVFormat.DEFAULT
            .withDelimiter(';')
            .withHeader(finalHeaders)
            .withIgnoreEmptyLines()
            .withIgnoreSurroundingSpaces()
            .withTrim()
            .withQuote('"')
            .parse(cleanedReader);

        return parser.getRecords();
    }
}
