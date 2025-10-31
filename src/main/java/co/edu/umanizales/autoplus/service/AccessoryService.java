package co.edu.umanizales.autoplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.umanizales.autoplus.model.abstracts.Accessory;
import co.edu.umanizales.autoplus.model.entities.*;
import java.util.*;

/**
 * Service for managing accessories
 */
@Service
public class AccessoryService {
    @Autowired
    private CsvService csvService;

    private static final String ACCESSORIES_FILE = "accessories.csv";
    private List<Accessory> accessories = new ArrayList<>();

    public AccessoryService() {
    }

    public void loadAccessories() {
        accessories.clear();
        accessories.addAll(csvService.loadFromCSV(ACCESSORIES_FILE, InteriorAccessory.class));
    }

    public List<Accessory> getAllAccessories() {
        return new ArrayList<>(accessories);
    }

    public Accessory getAccessoryById(String id) {
        return accessories.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void addAccessory(Accessory accessory) {
        accessories.add(accessory);
        csvService.appendToCSV(accessory, ACCESSORIES_FILE);
    }

    public void updateAccessory(Accessory accessory) {
        accessories.removeIf(a -> a.getId().equals(accessory.getId()));
        accessories.add(accessory);
        saveAll();
    }

    public void deleteAccessory(String id) {
        accessories.removeIf(a -> a.getId().equals(id));
        saveAll();
    }

    private void saveAll() {
        csvService.saveToCSV(accessories, ACCESSORIES_FILE);
    }
}
