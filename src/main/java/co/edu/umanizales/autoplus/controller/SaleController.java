package co.edu.umanizales.autoplus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.edu.umanizales.autoplus.model.entities.Sale;
import co.edu.umanizales.autoplus.service.SaleService;
import java.util.List;

/**
 * REST Controller for managing sales
 */
@RestController
@RequestMapping("/api/sales")
public class SaleController {
    @Autowired
    private SaleService saleService;

    @GetMapping
    public ResponseEntity<List<Sale>> getAllSales() {
        saleService.loadSales();
        return ResponseEntity.ok(saleService.getAllSales());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sale> getSaleById(@PathVariable String id) {
        saleService.loadSales();
        Sale sale = saleService.getSaleById(id);
        return sale != null ? ResponseEntity.ok(sale) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Sale> createSale(@RequestBody Sale sale) {
        saleService.addSale(sale);
        return ResponseEntity.ok(sale);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sale> updateSale(@PathVariable String id, @RequestBody Sale sale) {
        sale.setId(id);
        saleService.updateSale(sale);
        return ResponseEntity.ok(sale);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable String id) {
        saleService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }
}
