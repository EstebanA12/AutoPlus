package co.edu.umanizales.autoplus.service;

import co.edu.umanizales.autoplus.model.entities.Invoice;
import co.edu.umanizales.autoplus.model.entities.Sale;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class InvoiceService {
    private final SaleService saleService;
    private final CsvService csvService;

    public InvoiceService(SaleService saleService, CsvService csvService) {
        this.saleService = saleService;
        this.csvService = csvService;
    }

    public Invoice findById(String id) {
        String line = csvService.readLine("invoices.csv", id);
        if (line == null) return null;
        Invoice invoice = new Invoice();
        invoice.fromCsv(line);
        String[] parts = line.split(",");
        if (parts.length > 1) {
            Sale sale = saleService.findById(parts[1]);
            if (sale != null) {
                invoice.setSale(sale);
                return invoice;
            }
        }
        return null;
    }

    public List<Invoice> findAll() {
        List<String> lines = csvService.readAll("invoices.csv");
        List<Invoice> list = new ArrayList<>();
        for (String line : lines) {
            Invoice inv = new Invoice();
            inv.fromCsv(line);
            String[] parts = line.split(",");
            if (parts.length > 1) {
                Sale sale = saleService.findById(parts[1]);
                if (sale != null) {
                    inv.setSale(sale);
                    list.add(inv);
                }
            }
        }
        return list;
    }

    public Invoice create(Invoice invoice) {
        if (invoice.getSale() == null) {
            throw new IllegalArgumentException("Sale es requerido");
        }
        invoice.setId(generateId());
        invoice.calculateTotal();
        csvService.writeLine("invoices.csv", invoice.toCsv());
        return invoice;
    }

    private String generateId() { return UUID.randomUUID().toString(); }
}
