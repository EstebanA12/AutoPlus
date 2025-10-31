package co.edu.umanizales.autoplus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.edu.umanizales.autoplus.model.abstracts.Accessory;
import co.edu.umanizales.autoplus.service.AccessoryService;
import java.util.List;

/**
 * REST Controller for managing accessories
 */
@RestController
@RequestMapping("/api/accessories")
public class AccessoryController {
    @Autowired
    private AccessoryService accessoryService;

    @GetMapping
    public ResponseEntity<List<Accessory>> getAllAccessories() {
        accessoryService.loadAccessories();
        return ResponseEntity.ok(accessoryService.getAllAccessories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Accessory> getAccessoryById(@PathVariable String id) {
        accessoryService.loadAccessories();
        Accessory accessory = accessoryService.getAccessoryById(id);
        return accessory != null ? ResponseEntity.ok(accessory) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Accessory> createAccessory(@RequestBody Accessory accessory) {
        accessoryService.addAccessory(accessory);
        return ResponseEntity.ok(accessory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Accessory> updateAccessory(@PathVariable String id, @RequestBody Accessory accessory) {
        accessory.setId(id);
        accessoryService.updateAccessory(accessory);
        return ResponseEntity.ok(accessory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccessory(@PathVariable String id) {
        accessoryService.deleteAccessory(id);
        return ResponseEntity.noContent().build();
    }
}
