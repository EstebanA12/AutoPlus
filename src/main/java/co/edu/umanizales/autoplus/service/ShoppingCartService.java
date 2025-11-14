package co.edu.umanizales.autoplus.service;

import co.edu.umanizales.autoplus.model.abstracts.Accessory;
import co.edu.umanizales.autoplus.model.entities.Client;
import co.edu.umanizales.autoplus.model.entities.ShoppingCart;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ShoppingCartService {
    private final ClientService clientService;
    private final AccessoryService accessoryService;
    private final CsvService csvService;

    public ShoppingCartService(ClientService clientService,
                               AccessoryService accessoryService,
                               CsvService csvService) {
        this.clientService = clientService;
        this.accessoryService = accessoryService;
        this.csvService = csvService;
    }

    public ShoppingCart findById(String id) {
        String line = csvService.readLine("shopping_carts.csv", id);
        if (line == null) return null;
        ShoppingCart cart = new ShoppingCart();
        cart.fromCsv(line);
        String[] parts = line.split(",");
        if (parts.length > 1) {
            Client client = clientService.findById(parts[1]);
            cart.setClient(client);
        }
        cart.setItems(loadCartItems(id));
        return cart;
    }

    public ShoppingCart create(ShoppingCart cart) {
        if (cart.getClient() == null) {
            throw new IllegalArgumentException("Client es requerido");
        }
        cart.setId(generateId());
        csvService.writeLine("shopping_carts.csv", cart.toCsv());
        return cart;
    }

    public void addItem(String cartId, String accessoryId, int quantity) {
        ShoppingCart cart = findById(cartId);
        Accessory accessory = accessoryService.findById(accessoryId);
        cart.addItem(accessory, quantity);
        csvService.updateLine("shopping_carts.csv", cartId, cart.toCsv());
        csvService.writeLine("cart_items.csv", cartId + "," + accessoryId + "," + quantity);
    }

    public void removeItem(String cartId, String accessoryId) {
        ShoppingCart cart = findById(cartId);
        Accessory accessory = accessoryService.findById(accessoryId);
        cart.removeItem(accessory);
        csvService.updateLine("shopping_carts.csv", cartId, cart.toCsv());
        csvService.deleteLineByPrefix("cart_items.csv", cartId + "," + accessoryId);
    }

    public void clear(String cartId) {
        ShoppingCart cart = findById(cartId);
        cart.clear();
        csvService.updateLine("shopping_carts.csv", cartId, cart.toCsv());
        csvService.deleteLines("cart_items.csv", cartId);
    }

    private Map<Accessory, Integer> loadCartItems(String cartId) {
        List<String> lines = csvService.readLines("cart_items.csv", cartId);
        Map<Accessory, Integer> items = new HashMap<>();
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 3) {
                String accessoryId = parts[1];
                int quantity = Integer.parseInt(parts[2]);
                Accessory accessory = accessoryService.findById(accessoryId);
                items.put(accessory, quantity);
            }
        }
        return items;
    }

    private String generateId() { return UUID.randomUUID().toString(); }
}
