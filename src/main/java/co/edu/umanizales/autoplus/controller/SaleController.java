package co.edu.umanizales.autoplus.controller;

import co.edu.umanizales.autoplus.model.entities.Sale;
import co.edu.umanizales.autoplus.service.SaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping
    public List<Sale> findAll() {
        return saleService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sale> findById(@PathVariable String id) {
        Sale sale = saleService.findById(id);
        return sale != null ? ResponseEntity.ok(sale) : ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = {"application/json", "application/json;charset=UTF-8"})
    public ResponseEntity<?> create(@RequestBody Sale sale) {
        try {
            if (sale == null) {
                return ResponseEntity.badRequest().body("Sale object is required");
            }
            if (sale.getClient() == null) {
                return ResponseEntity.badRequest().body("Client is required");
            }
            if (sale.getSeller() == null) {
                return ResponseEntity.badRequest().body("Seller is required");
            }
            if (sale.getItems() == null || sale.getItems().isEmpty()) {
                return ResponseEntity.badRequest().body("At least one item is required");
            }
            Sale created = saleService.create(sale);
            return ResponseEntity.created(URI.create("/api/sales/" + created.getId())).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating sale: " + e.getMessage());
        }
    }

    @PutMapping(value = "/{id}", consumes = {"application/json", "application/json;charset=UTF-8"})
    public ResponseEntity<Sale> update(@PathVariable String id, @RequestBody Sale sale) {
        Sale existing = saleService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        sale.setId(id);
        Sale updated = saleService.update(sale);
        return ResponseEntity.ok(updated);
    }
}
