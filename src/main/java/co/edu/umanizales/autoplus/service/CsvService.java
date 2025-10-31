package co.edu.umanizales.autoplus.service;

import org.springframework.stereotype.Service;
import co.edu.umanizales.autoplus.model.interfaces.Persistable;
import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Service for managing CSV file operations
 */
@Service
public class CsvService {
    private static final String CSV_DIR = "data/csv/";

    public CsvService() {
        try {
            Files.createDirectories(Paths.get(CSV_DIR));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save a list of objects to a CSV file
     */
    public <T extends Persistable> void saveToCSV(List<T> items, String filename) {
        try (FileWriter writer = new FileWriter(CSV_DIR + filename)) {
            for (T item : items) {
                writer.write(item.toCsv() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load objects from a CSV file
     */
    public <T extends Persistable> List<T> loadFromCSV(String filename, Class<T> clazz) {
        List<T> items = new ArrayList<>();
        File file = new File(CSV_DIR + filename);

        if (!file.exists()) {
            return items;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    try {
                        T item = clazz.getDeclaredConstructor().newInstance();
                        item.fromCsv(line);
                        items.add(item);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return items;
    }

    /**
     * Append an object to a CSV file
     */
    public <T extends Persistable> void appendToCSV(T item, String filename) {
        try (FileWriter writer = new FileWriter(CSV_DIR + filename, true)) {
            writer.write(item.toCsv() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete a CSV file
     */
    public void deleteCSV(String filename) {
        try {
            Files.deleteIfExists(Paths.get(CSV_DIR + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if CSV file exists
     */
    public boolean csvExists(String filename) {
        return Files.exists(Paths.get(CSV_DIR + filename));
    }
}
