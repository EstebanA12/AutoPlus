package co.edu.umanizales.autoplus.service;

import co.edu.umanizales.autoplus.model.entities.Seller;
import org.springframework.stereotype.Service;

@Service
public class SellerService {
    private final CsvService csvService;

    public SellerService(CsvService csvService) {
        this.csvService = csvService;
    }

    public Seller findById(String id) {
        String line = csvService.readLine("sellers.csv", id);
        if (line == null) return null;
        Seller seller = new Seller();
        seller.fromCsv(line);
        return seller;
    }
}
