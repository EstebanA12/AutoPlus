package co.edu.umanizales.autoplus.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CsvService {
    private static final String BASE_DIR = "data/csv";

    private Path resolvePath(String fileName) {
        Path dir = Paths.get(BASE_DIR);
        try {
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to create directory: " + dir, e);
        }
        return dir.resolve(fileName);
    }

    private List<String> safeReadAll(Path path) {
        try {
            if (!Files.exists(path)) {
                return new ArrayList<>();
            }
            return Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + path, e);
        }
    }

    public List<String> readAll(String fileName) {
        return safeReadAll(resolvePath(fileName));
    }

    public String readLine(String fileName, String id) {
        Path path = resolvePath(fileName);
        return safeReadAll(path).stream()
                .filter(line -> firstField(line).equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<String> readLines(String fileName, String prefixId) {
        Path path = resolvePath(fileName);
        String prefix = prefixId + ",";
        return safeReadAll(path).stream()
                .filter(line -> line.startsWith(prefix))
                .collect(Collectors.toList());
    }

    public void writeLine(String fileName, String line) {
        Path path = resolvePath(fileName);
        try {
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
            Files.write(path, (line + System.lineSeparator()).getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Error writing line to file: " + path, e);
        }
    }

    public void updateLine(String fileName, String id, String newLine) {
        Path path = resolvePath(fileName);
        List<String> lines = safeReadAll(path);
        boolean updated = false;
        for (int i = 0; i < lines.size(); i++) {
            if (firstField(lines.get(i)).equals(id)) {
                lines.set(i, newLine);
                updated = true;
                break;
            }
        }
        if (!updated) {
            // If not found, append
            lines.add(newLine);
        }
        try {
            Files.write(path, lines, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException("Error updating file: " + path, e);
        }
    }

    public void deleteLines(String fileName, String prefixId) {
        Path path = resolvePath(fileName);
        String prefix = prefixId + ",";
        List<String> filtered = safeReadAll(path).stream()
                .filter(line -> !line.startsWith(prefix))
                .collect(Collectors.toList());
        try {
            Files.write(path, filtered, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException("Error deleting lines from file: " + path, e);
        }
    }

    public void deleteLineByPrefix(String fileName, String linePrefix) {
        Path path = resolvePath(fileName);
        List<String> filtered = safeReadAll(path).stream()
                .filter(line -> !Objects.equals(line, linePrefix) && !line.startsWith(linePrefix + ","))
                .collect(Collectors.toList());
        try {
            Files.write(path, filtered, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException("Error deleting line from file: " + path, e);
        }
    }

    private String firstField(String line) {
        int idx = line.indexOf(',');
        return idx >= 0 ? line.substring(0, idx) : line;
    }
}
