package co.edu.umanizales.autoplus.service;

import co.edu.umanizales.autoplus.model.abstracts.Accessory;
import co.edu.umanizales.autoplus.model.entities.Warranty;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class WarrantyService {
    private final AccessoryService accessoryService;
    private final CsvService csvService;

    public WarrantyService(AccessoryService accessoryService, CsvService csvService) {
        this.accessoryService = accessoryService;
        this.csvService = csvService;
    }

    public Warranty findById(String id) {
        String line = csvService.readLine("warranties.csv", id);
        if (line == null) return null;
        Warranty warranty = new Warranty();
        warranty.fromCsv(line);
        String[] parts = line.split(",");
        if (parts.length > 1) {
            Accessory accessory = accessoryService.findById(parts[1]);
            warranty.setAccessory(accessory);
        }
        return warranty;
    }

    public List<Warranty> findAll() {
        List<String> lines = csvService.readAll("warranties.csv");
        List<Warranty> list = new ArrayList<>();
        for (String line : lines) {
            Warranty w = new Warranty();
            w.fromCsv(line);
            String[] parts = line.split(",");
            if (parts.length > 1) {
                w.setAccessory(accessoryService.findById(parts[1]));
            }
            list.add(w);
        }
        return list;
    }

    public Warranty create(Warranty warranty) {
        if (warranty.getAccessory() == null) {
            throw new IllegalArgumentException("Accessory es requerido");
        }
        warranty.setId(generateId());
        csvService.writeLine("warranties.csv", warranty.toCsv());
        return warranty;
    }

    private String generateId() { return UUID.randomUUID().toString(); }
}
