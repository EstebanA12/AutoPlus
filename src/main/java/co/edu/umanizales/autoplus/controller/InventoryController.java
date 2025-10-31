package co.edu.umanizales.autoplus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.edu.umanizales.autoplus.model.entities.Inventory;
import co.edu.umanizales.autoplus.service.InventoryService;
import java.util.List;

/**
 * REST Controller for managing inventory
 */
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventory() {
        inventoryService.loadInventory();
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable String id) {
        inventoryService.loadInventory();
        Inventory inventory = inventoryService.getInventoryById(id);
        return inventory != null ? ResponseEntity.ok(inventory) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Inventory> createInventory(@RequestBody Inventory inventory) {
        inventoryService.addInventory(inventory);
        return ResponseEntity.ok(inventory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable String id, @RequestBody Inventory inventory) {
        inventory.setId(id);
        inventoryService.updateInventory(inventory);
        return ResponseEntity.ok(inventory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable String id) {
        inventoryService.deleteInventory(id);
        return ResponseEntity.noContent().build();
    }
}
