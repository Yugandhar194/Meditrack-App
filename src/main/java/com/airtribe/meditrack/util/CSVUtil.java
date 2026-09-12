package com.airtribe.meditrack.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Minimal CSV read/write helper used for basic file persistence (I/O).
 */
public class CSVUtil {

    private CSVUtil() {}

    public static List<String[]> readCSV(String filePath) throws IOException {
        List<String[]> rows = new ArrayList<>();
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) return rows;

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                rows.add(line.split(","));
            }
        }
        return rows;
    }

    public static void writeCSV(String filePath, List<String[]> rows) throws IOException {
        Path path = Paths.get(filePath);
        if (path.getParent() != null) Files.createDirectories(path.getParent());
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (String[] row : rows) {
                writer.write(String.join(",", row));
                writer.newLine();
            }
        }
    }

    public static void appendRow(String filePath, String[] row) throws IOException {
        Path path = Paths.get(filePath);
        if (path.getParent() != null) Files.createDirectories(path.getParent());
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(String.join(",", row));
            writer.newLine();
        }
    }
}
