package co.edu.umanizales.autoplus.service;

import co.edu.umanizales.autoplus.model.abstracts.Accessory;
import co.edu.umanizales.autoplus.model.entities.Inventory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class InventoryService {
    private final AccessoryService accessoryService;
    private final CsvService csvService;

    public InventoryService(AccessoryService accessoryService, CsvService csvService) {
        this.accessoryService = accessoryService;
        this.csvService = csvService;
    }

    public Inventory findById(String id) {
        String csvLine = csvService.readLine("inventory.csv", id);
        if (csvLine == null) return null;
        Inventory inventory = new Inventory();
        inventory.fromCsv(csvLine);
        String[] parts = csvLine.split(",");
        if (parts.length > 1) {
            Accessory accessory = accessoryService.findById(parts[1]);
            if (accessory != null) {
                inventory.setAccessory(accessory);
                return inventory;
            }
        }
        return null;
    }

    public Inventory findByAccessory(Accessory accessory) {
        List<String> lines = csvService.readAll("inventory.csv");
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length > 1 && parts[1].equals(accessory.getId())) {
                Inventory inventory = new Inventory();
                inventory.fromCsv(line);
                inventory.setAccessory(accessory);
                return inventory;
            }
        }
        return null;
    }

    public List<Inventory> findAll() {
        List<String> lines = csvService.readAll("inventory.csv");
        List<Inventory> list = new ArrayList<>();
        for (String line : lines) {
            Inventory inventory = new Inventory();
            inventory.fromCsv(line);
            String[] parts = line.split(",");
            if (parts.length > 1) {
                Accessory accessory = accessoryService.findById(parts[1]);
                if (accessory != null) {
                    inventory.setAccessory(accessory);
                    list.add(inventory);
                }
            }
        }
        return list;
    }

    public Inventory create(Inventory inventory) {
        if (inventory.getAccessory() == null) {
            throw new IllegalArgumentException("Accessory es requerido");
        }
        inventory.setId(generateId());
        csvService.writeLine("inventory.csv", inventory.toCsv());
        return inventory;
    }

    public Inventory update(Inventory inventory) {
        if (inventory.getId() == null) {
            throw new IllegalArgumentException("Inventory ID es requerido para actualizar");
        }
        csvService.updateLine("inventory.csv", inventory.getId(), inventory.toCsv());
        return inventory;
    }

    public void addStock(String inventoryId, int amount) {
        Inventory inv = findById(inventoryId);
        if (inv == null) {
            throw new IllegalArgumentException("Inventory not found: " + inventoryId);
        }
        inv.addStock(amount);
        update(inv);
    }

    public void removeStock(String inventoryId, int amount) {
        Inventory inv = findById(inventoryId);
        if (inv == null) {
            throw new IllegalArgumentException("Inventory not found: " + inventoryId);
        }
        inv.removeStock(amount);
        update(inv);
    }

    private String generateId() { return UUID.randomUUID().toString(); }
}
