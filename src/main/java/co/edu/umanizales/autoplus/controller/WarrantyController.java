package co.edu.umanizales.autoplus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.edu.umanizales.autoplus.model.entities.Warranty;
import co.edu.umanizales.autoplus.service.WarrantyService;
import java.util.List;

/**
 * REST Controller for managing warranties
 */
@RestController
@RequestMapping("/api/warranties")
public class WarrantyController {
    @Autowired
    private WarrantyService warrantyService;

    @GetMapping
    public ResponseEntity<List<Warranty>> getAllWarranties() {
        warrantyService.loadWarranties();
        return ResponseEntity.ok(warrantyService.getAllWarranties());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Warranty> getWarrantyById(@PathVariable String id) {
        warrantyService.loadWarranties();
        Warranty warranty = warrantyService.getWarrantyById(id);
        return warranty != null ? ResponseEntity.ok(warranty) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Warranty> createWarranty(@RequestBody Warranty warranty) {
        warrantyService.addWarranty(warranty);
        return ResponseEntity.ok(warranty);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Warranty> updateWarranty(@PathVariable String id, @RequestBody Warranty warranty) {
        warranty.setId(id);
        warrantyService.updateWarranty(warranty);
        return ResponseEntity.ok(warranty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWarranty(@PathVariable String id) {
        warrantyService.deleteWarranty(id);
        return ResponseEntity.noContent().build();
    }
}
