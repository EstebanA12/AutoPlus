package co.edu.umanizales.autoplus.controller;

import co.edu.umanizales.autoplus.model.entities.Warranty;
import co.edu.umanizales.autoplus.service.WarrantyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import co.edu.umanizales.autoplus.service.AccessoryService;
import co.edu.umanizales.autoplus.model.abstracts.Accessory;

@RestController
@RequestMapping("/api/warranties")
public class WarrantyController {

    private final WarrantyService warrantyService;
    private final AccessoryService accessoryService;

    public WarrantyController(WarrantyService warrantyService, AccessoryService accessoryService) {
        this.warrantyService = warrantyService;
        this.accessoryService = accessoryService;
    }

    @GetMapping
    public List<Warranty> findAll() {
        return warrantyService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Warranty> findById(@PathVariable String id) {
        Warranty w = warrantyService.findById(id);
        return w != null ? ResponseEntity.ok(w) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Warranty> create(@RequestBody JsonNode body) {
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

        Warranty warranty = new Warranty();
        warranty.setAccessory(accessory);
        if (body.has("months")) warranty.setMonths(body.get("months").asInt());
        if (body.has("description")) warranty.setDescription(body.get("description").asText());
        if (body.has("startDate")) warranty.setStartDate(body.get("startDate").asText());
        if (body.has("endDate")) warranty.setEndDate(body.get("endDate").asText());

        Warranty created = warrantyService.create(warranty);
        return ResponseEntity.created(URI.create("/api/warranties/" + created.getId())).body(created);
    }
}
