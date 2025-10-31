package co.edu.umanizales.autoplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.umanizales.autoplus.model.entities.ShoppingCart;
import java.util.*;

/**
 * Service for managing shopping carts
 */
@Service
public class ShoppingCartService {
    @Autowired
    private CsvService csvService;

    private static final String CARTS_FILE = "shopping_carts.csv";
    private List<ShoppingCart> carts = new ArrayList<>();

    public void loadCarts() {
        carts.clear();
        carts.addAll(csvService.loadFromCSV(CARTS_FILE, ShoppingCart.class));
    }

    public List<ShoppingCart> getAllCarts() {
        return new ArrayList<>(carts);
    }

    public ShoppingCart getCartById(String id) {
        return carts.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void addCart(ShoppingCart cart) {
        carts.add(cart);
        csvService.appendToCSV(cart, CARTS_FILE);
    }

    public void updateCart(ShoppingCart cart) {
        carts.removeIf(c -> c.getId().equals(cart.getId()));
        carts.add(cart);
        saveAll();
    }

    public void deleteCart(String id) {
        carts.removeIf(c -> c.getId().equals(id));
        saveAll();
    }

    private void saveAll() {
        csvService.saveToCSV(carts, CARTS_FILE);
    }
}
