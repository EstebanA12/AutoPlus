package co.edu.umanizales.autoplus.service;

import co.edu.umanizales.autoplus.model.abstracts.Accessory;
import co.edu.umanizales.autoplus.model.entities.Client;
import co.edu.umanizales.autoplus.model.entities.Sale;
import co.edu.umanizales.autoplus.model.entities.SaleItem;
import co.edu.umanizales.autoplus.model.entities.Seller;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SaleService {
    private final ClientService clientService;
    private final SellerService sellerService;
    private final AccessoryService accessoryService;
    private final CsvService csvService;

    public SaleService(ClientService clientService,
                       SellerService sellerService,
                       AccessoryService accessoryService,
                       CsvService csvService) {
        this.clientService = clientService;
        this.sellerService = sellerService;
        this.accessoryService = accessoryService;
        this.csvService = csvService;
    }

    public Sale findById(String id) {
        String csvLine = csvService.readLine("sales.csv", id);
        if (csvLine == null) return null;
        Sale sale = new Sale();
        sale.fromCsv(csvLine);

        String[] parts = csvLine.split(",");
        String clientId = parts.length > 1 ? parts[1] : null;
        String sellerId = parts.length > 2 ? parts[2] : null;

        if (clientId != null) {
            Client client = clientService.findById(clientId);
            if (client != null) {
                sale.setClient(client);
            } else {
                return null;
            }
        }
        if (sellerId != null) {
            Seller seller = sellerService.findById(sellerId);
            if (seller != null) {
                sale.setSeller(seller);
            } else {
                return null;
            }
        }

        List<SaleItem> items = loadSaleItems(id);
        sale.setItems(items);
        return sale;
    }

    public List<Sale> findAll() {
        List<String> lines = csvService.readAll("sales.csv");
        List<Sale> sales = new ArrayList<>();
        for (String line : lines) {
            Sale sale = new Sale();
            sale.fromCsv(line);
            String[] parts = line.split(",");
            if (parts.length >= 3) {
                Client client = clientService.findById(parts[1]);
                Seller seller = sellerService.findById(parts[2]);
                if (client != null && seller != null) {
                    sale.setClient(client);
                    sale.setSeller(seller);
                    sale.setItems(loadSaleItems(sale.getId()));
                    sales.add(sale);
                }
            }
        }
        return sales;
    }

    public Sale create(Sale sale) {
        if (sale.getClient() == null || sale.getSeller() == null) {
            throw new IllegalArgumentException("Client y Seller son requeridos");
        }
        if (sale.getItems() == null || sale.getItems().isEmpty()) {
            throw new IllegalArgumentException("La venta debe contener al menos un item");
        }
        sale.setId(generateId());
        // Persist sale
        csvService.writeLine("sales.csv", sale.toCsv());
        // Persist items (prefix with saleId)
        for (SaleItem item : sale.getItems()) {
            if (item.getId() == null || item.getId().isEmpty()) {
                item.setId(generateItemId());
            }
            csvService.writeLine("sale_items.csv", sale.getId() + "," + item.toCsv());
        }
        return sale;
    }

    public Sale update(Sale sale) {
        if (sale.getId() == null) {
            throw new IllegalArgumentException("Sale ID es requerido para actualizar");
        }
        csvService.updateLine("sales.csv", sale.getId(), sale.toCsv());
        csvService.deleteLines("sale_items.csv", sale.getId());
        for (SaleItem item : sale.getItems()) {
            if (item.getId() == null || item.getId().isEmpty()) {
                item.setId(generateItemId());
            }
            csvService.writeLine("sale_items.csv", sale.getId() + "," + item.toCsv());
        }
        return sale;
    }

    private List<SaleItem> loadSaleItems(String saleId) {
        List<String> itemLines = csvService.readLines("sale_items.csv", saleId);
        return itemLines.stream().map(line -> {
            // expected: saleId,itemId,accessoryId,quantity,unitPrice
            // our SaleItem.fromCsv reads id,accessoryId,quantity,unitPrice
            // so we need to strip leading saleId,
            String withoutSale = line.substring(line.indexOf(',') + 1);
            SaleItem item = new SaleItem();
            item.fromCsv(withoutSale);
            String[] parts = withoutSale.split(",");
            if (parts.length >= 2) {
                String accessoryId = parts[1];
                Accessory accessory = accessoryService.findById(accessoryId);
                item.setAccessory(accessory);
            }
            return item;
        }).collect(Collectors.toList());
    }

    private String generateId() { return UUID.randomUUID().toString(); }
    private String generateItemId() { return UUID.randomUUID().toString(); }
}
