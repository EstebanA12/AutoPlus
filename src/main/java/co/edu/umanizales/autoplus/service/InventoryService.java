package co.edu.umanizales.autoplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.umanizales.autoplus.model.entities.Inventory;
import java.util.*;

/**
 * Service for managing inventory
 */
@Service
public class InventoryService {
    @Autowired
    private CsvService csvService;

    private static final String INVENTORY_FILE = "inventory.csv";
    private List<Inventory> inventory = new ArrayList<>();

    public void loadInventory() {
        inventory.clear();
        inventory.addAll(csvService.loadFromCSV(INVENTORY_FILE, Inventory.class));
    }

    public List<Inventory> getAllInventory() {
        return new ArrayList<>(inventory);
    }

    public Inventory getInventoryById(String id) {
        return inventory.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void addInventory(Inventory inv) {
        inventory.add(inv);
        csvService.appendToCSV(inv, INVENTORY_FILE);
    }

    public void updateInventory(Inventory inv) {
        inventory.removeIf(i -> i.getId().equals(inv.getId()));
        inventory.add(inv);
        saveAll();
    }

    public void deleteInventory(String id) {
        inventory.removeIf(i -> i.getId().equals(id));
        saveAll();
    }

    private void saveAll() {
        csvService.saveToCSV(inventory, INVENTORY_FILE);
    }
}
