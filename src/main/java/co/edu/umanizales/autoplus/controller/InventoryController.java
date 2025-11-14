package co.edu.umanizales.autoplus.controller;

import co.edu.umanizales.autoplus.model.entities.Inventory;
import co.edu.umanizales.autoplus.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import co.edu.umanizales.autoplus.service.AccessoryService;
import co.edu.umanizales.autoplus.model.abstracts.Accessory;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;
    private final AccessoryService accessoryService;

    public InventoryController(InventoryService inventoryService, AccessoryService accessoryService) {
        this.inventoryService = inventoryService;
        this.accessoryService = accessoryService;
    }

    @GetMapping
    public List<Inventory> findAll() { return inventoryService.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> findById(@PathVariable String id) {
        Inventory inv = inventoryService.findById(id);
        return inv != null ? ResponseEntity.ok(inv) : ResponseEntity.notFound().build();
        
    }

    @PostMapping(consumes = {"application/json", "application/json;charset=UTF-8"})
    public ResponseEntity<Inventory> create(@RequestBody JsonNode body) {
        String accessoryId = null;
        if (body.has("accessory") && body.get("accessory").has("id")) {
            accessoryId = body.get("accessory").get("id").asText();
        } else if (body.has("accessoryId")) {
            accessoryId = body.get("accessoryId").asText();
        }
        Accessory accessory = accessoryService.findById(accessoryId);
        if (accessory == null) {
            return ResponseEntity.badRequest().build();
        }

        Inventory inventory = new Inventory();
        inventory.setAccessory(accessory);
        if (body.has("quantity")) inventory.setQuantity(body.get("quantity").asInt());
        if (body.has("location")) inventory.setLocation(body.get("location").asText());
        if (body.has("lastUpdate")) inventory.setLastUpdate(body.get("lastUpdate").asText());

        Inventory created = inventoryService.create(inventory);
        return ResponseEntity.created(URI.create("/api/inventory/" + created.getId())).body(created);
    }

    @PutMapping(value = "/{id}", consumes = {"application/json", "application/json;charset=UTF-8"})
    public ResponseEntity<Inventory> update(@PathVariable String id, @RequestBody JsonNode body) {
        Inventory existing = inventoryService.findById(id);
        if (existing == null) return ResponseEntity.notFound().build();

        if (body.has("accessory") && body.get("accessory").has("id")) {
            String accessoryId = body.get("accessory").get("id").asText();
            Accessory accessory = accessoryService.findById(accessoryId);
            if (accessory == null) return ResponseEntity.badRequest().build();
            existing.setAccessory(accessory);
        } else if (body.has("accessoryId")) {
            String accessoryId = body.get("accessoryId").asText();
            Accessory accessory = accessoryService.findById(accessoryId);
            if (accessory == null) return ResponseEntity.badRequest().build();
            existing.setAccessory(accessory);
        }
        if (body.has("quantity")) existing.setQuantity(body.get("quantity").asInt());
        if (body.has("location")) existing.setLocation(body.get("location").asText());
        if (body.has("lastUpdate")) existing.setLastUpdate(body.get("lastUpdate").asText());

        return ResponseEntity.ok(inventoryService.update(existing));
    }

    @PostMapping(value = "/{id}/add-stock", consumes = {"application/x-www-form-urlencoded"})
    public ResponseEntity<Void> addStock(@PathVariable String id, @RequestParam int amount) {
        Inventory existing = inventoryService.findById(id);
        if (existing == null) return ResponseEntity.notFound().build();
        inventoryService.addStock(id, amount);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/remove-stock", consumes = {"application/x-www-form-urlencoded"})
    public ResponseEntity<Void> removeStock(@PathVariable String id, @RequestParam int amount) {
        Inventory existing = inventoryService.findById(id);
        if (existing == null) return ResponseEntity.notFound().build();
        inventoryService.removeStock(id, amount);
        return ResponseEntity.noContent().build();
    }
}
