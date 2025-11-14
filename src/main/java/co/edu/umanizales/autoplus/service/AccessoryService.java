package co.edu.umanizales.autoplus.service;

import co.edu.umanizales.autoplus.model.abstracts.Accessory;
import co.edu.umanizales.autoplus.model.entities.ExteriorAccessory;
import co.edu.umanizales.autoplus.model.entities.InteriorAccessory;
import co.edu.umanizales.autoplus.model.entities.TechnologicalAccessory;
import org.springframework.stereotype.Service;

@Service
public class AccessoryService {
    private final CsvService csvService;

    public AccessoryService(CsvService csvService) {
        this.csvService = csvService;
    }

    public Accessory findById(String id) {
        // Try technological
        String tech = csvService.readLine("technological_accessories.csv", id);
        if (tech != null) {
            TechnologicalAccessory a = new TechnologicalAccessory();
            a.fromCsv(tech);
            return a;
        }
        // Try interior
        String interior = csvService.readLine("interior_accessories.csv", id);
        if (interior != null) {
            InteriorAccessory a = new InteriorAccessory();
            a.fromCsv(interior);
            return a;
        }
        // Try exterior
        String exterior = csvService.readLine("exterior_accessories.csv", id);
        if (exterior != null) {
            ExteriorAccessory a = new ExteriorAccessory();
            a.fromCsv(exterior);
            return a;
        }
        return null;
    }
}
