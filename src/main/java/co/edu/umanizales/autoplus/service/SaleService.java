package co.edu.umanizales.autoplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.umanizales.autoplus.model.entities.Sale;
import java.util.*;

/**
 * Service for managing sales
 */
@Service
public class SaleService {
    @Autowired
    private CsvService csvService;

    private static final String SALES_FILE = "sales.csv";
    private List<Sale> sales = new ArrayList<>();

    public void loadSales() {
        sales.clear();
        sales.addAll(csvService.loadFromCSV(SALES_FILE, Sale.class));
    }

    public List<Sale> getAllSales() {
        return new ArrayList<>(sales);
    }

    public Sale getSaleById(String id) {
        return sales.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void addSale(Sale sale) {
        sales.add(sale);
        csvService.appendToCSV(sale, SALES_FILE);
    }

    public void updateSale(Sale sale) {
        sales.removeIf(s -> s.getId().equals(sale.getId()));
        sales.add(sale);
        saveAll();
    }

    public void deleteSale(String id) {
        sales.removeIf(s -> s.getId().equals(id));
        saveAll();
    }

    private void saveAll() {
        csvService.saveToCSV(sales, SALES_FILE);
    }
}
