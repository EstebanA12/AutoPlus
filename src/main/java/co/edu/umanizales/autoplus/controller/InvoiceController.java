package co.edu.umanizales.autoplus.controller;

import co.edu.umanizales.autoplus.model.entities.Invoice;
import co.edu.umanizales.autoplus.service.InvoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public List<Invoice> findAll() { return invoiceService.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> findById(@PathVariable String id) {
        Invoice inv = invoiceService.findById(id);
        return inv != null ? ResponseEntity.ok(inv) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Invoice> create(@RequestBody Invoice invoice) {
        Invoice created = invoiceService.create(invoice);
        return ResponseEntity.created(URI.create("/api/invoices/" + created.getId())).body(created);
    }
}
