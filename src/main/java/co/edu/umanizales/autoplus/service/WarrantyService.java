package co.edu.umanizales.autoplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.umanizales.autoplus.model.entities.Warranty;
import java.util.*;

/**
 * Service for managing warranties
 */
@Service
public class WarrantyService {
    @Autowired
    private CsvService csvService;

    private static final String WARRANTIES_FILE = "warranties.csv";
    private List<Warranty> warranties = new ArrayList<>();

    public void loadWarranties() {
        warranties.clear();
        warranties.addAll(csvService.loadFromCSV(WARRANTIES_FILE, Warranty.class));
    }

    public List<Warranty> getAllWarranties() {
        return new ArrayList<>(warranties);
    }

    public Warranty getWarrantyById(String id) {
        return warranties.stream()
                .filter(w -> w.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void addWarranty(Warranty warranty) {
        warranties.add(warranty);
        csvService.appendToCSV(warranty, WARRANTIES_FILE);
    }

    public void updateWarranty(Warranty warranty) {
        warranties.removeIf(w -> w.getId().equals(warranty.getId()));
        warranties.add(warranty);
        saveAll();
    }

    public void deleteWarranty(String id) {
        warranties.removeIf(w -> w.getId().equals(id));
        saveAll();
    }

    private void saveAll() {
        csvService.saveToCSV(warranties, WARRANTIES_FILE);
    }
}
