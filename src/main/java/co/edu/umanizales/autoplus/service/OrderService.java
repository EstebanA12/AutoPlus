package co.edu.umanizales.autoplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.umanizales.autoplus.model.entities.Order;
import java.util.*;

/**
 * Service for managing orders
 */
@Service
public class OrderService {
    @Autowired
    private CsvService csvService;

    private static final String ORDERS_FILE = "orders.csv";
    private List<Order> orders = new ArrayList<>();

    public void loadOrders() {
        orders.clear();
        orders.addAll(csvService.loadFromCSV(ORDERS_FILE, Order.class));
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }

    public Order getOrderById(String id) {
        return orders.stream()
                .filter(o -> o.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void addOrder(Order order) {
        orders.add(order);
        csvService.appendToCSV(order, ORDERS_FILE);
    }

    public void updateOrder(Order order) {
        orders.removeIf(o -> o.getId().equals(order.getId()));
        orders.add(order);
        saveAll();
    }

    public void deleteOrder(String id) {
        orders.removeIf(o -> o.getId().equals(id));
        saveAll();
    }

    private void saveAll() {
        csvService.saveToCSV(orders, ORDERS_FILE);
    }
}
