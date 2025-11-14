package co.edu.umanizales.autoplus.service;

import co.edu.umanizales.autoplus.model.abstracts.Accessory;
import co.edu.umanizales.autoplus.model.abstracts.Provider;
import co.edu.umanizales.autoplus.model.entities.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final ProviderService providerService;
    private final AccessoryService accessoryService;
    private final CsvService csvService;

    public OrderService(ProviderService providerService,
                        AccessoryService accessoryService,
                        CsvService csvService) {
        this.providerService = providerService;
        this.accessoryService = accessoryService;
        this.csvService = csvService;
    }

    public Order findById(String id) {
        String csvLine = csvService.readLine("orders.csv", id);
        if (csvLine == null) return null;
        Order order = new Order();
        order.fromCsv(csvLine);
        String[] parts = csvLine.split(",");
        if (parts.length >= 3) {
            Provider provider = providerService.findById(parts[1]);
            Accessory accessory = accessoryService.findById(parts[2]);
            if (provider != null && accessory != null) {
                order.setProvider(provider);
                order.setAccessory(accessory);
                return order;
            }
        }
        return null;
    }

    public List<Order> findAll() {
        List<String> lines = csvService.readAll("orders.csv");
        List<Order> orders = new ArrayList<>();
        for (String line : lines) {
            Order order = new Order();
            order.fromCsv(line);
            String[] parts = line.split(",");
            if (parts.length >= 3) {
                Provider provider = providerService.findById(parts[1]);
                Accessory accessory = accessoryService.findById(parts[2]);
                if (provider != null && accessory != null) {
                    order.setProvider(provider);
                    order.setAccessory(accessory);
                    orders.add(order);
                }
            }
        }
        return orders;
    }

    public Order create(Order order) {
        if (order.getProvider() == null || order.getAccessory() == null) {
            throw new IllegalArgumentException("Provider y Accessory son requeridos");
        }
        order.setId(generateId());
        csvService.writeLine("orders.csv", order.toCsv());
        return order;
    }

    public Order update(Order order) {
        if (order.getId() == null) {
            throw new IllegalArgumentException("Order ID es requerido para actualizar");
        }
        csvService.updateLine("orders.csv", order.getId(), order.toCsv());
        return order;
    }

    private String generateId() { return UUID.randomUUID().toString(); }
}
