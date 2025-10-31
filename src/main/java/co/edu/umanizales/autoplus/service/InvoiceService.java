package co.edu.umanizales.autoplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.umanizales.autoplus.model.entities.Invoice;
import java.util.*;

/**
 * Service for managing invoices
 */
@Service
public class InvoiceService {
    @Autowired
    private CsvService csvService;

    private static final String INVOICES_FILE = "invoices.csv";
    private List<Invoice> invoices = new ArrayList<>();

    public void loadInvoices() {
        invoices.clear();
        invoices.addAll(csvService.loadFromCSV(INVOICES_FILE, Invoice.class));
    }

    public List<Invoice> getAllInvoices() {
        return new ArrayList<>(invoices);
    }

    public Invoice getInvoiceById(String id) {
        return invoices.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void addInvoice(Invoice invoice) {
        invoices.add(invoice);
        csvService.appendToCSV(invoice, INVOICES_FILE);
    }

    public void updateInvoice(Invoice invoice) {
        invoices.removeIf(i -> i.getId().equals(invoice.getId()));
        invoices.add(invoice);
        saveAll();
    }

    public void deleteInvoice(String id) {
        invoices.removeIf(i -> i.getId().equals(id));
        saveAll();
    }

    private void saveAll() {
        csvService.saveToCSV(invoices, INVOICES_FILE);
    }
}
