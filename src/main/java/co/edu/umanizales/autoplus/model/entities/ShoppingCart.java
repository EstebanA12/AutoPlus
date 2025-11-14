package co.edu.umanizales.autoplus.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import co.edu.umanizales.autoplus.model.interfaces.Persistable;
import co.edu.umanizales.autoplus.model.abstracts.Accessory;
import java.util.*;

/**
 * Class representing a shopping cart
 * Contains a collection of accessories with quantities
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart implements Persistable {
    private String id;
    
    @NonNull
    private Client client;
    
    // Map to store accessories and their quantities
    private Map<Accessory, Integer> items = new HashMap<>();
    
    private String creationDate;
    
    // Convenience method for ID access
    public String getClientId() {
        return client != null ? client.getId() : null;
    }
    
    /**
     * Add an accessory to the cart
     */
    public void addItem(Accessory accessory, int quantity) {
        if (accessory == null || quantity <= 0) {
            throw new IllegalArgumentException("Accessory cannot be null and quantity must be positive");
        }
        items.put(accessory, items.getOrDefault(accessory, 0) + quantity);
    }
    
    /**
     * Remove an accessory from the cart
     */
    public void removeItem(Accessory accessory) {
        items.remove(accessory);
    }
    
    /**
     * Get total number of items
     */
    public int getItemCount() {
        return items.values().stream().mapToInt(Integer::intValue).sum();
    }
    
    /**
     * Calculate total price
     */
    public double getTotalPrice() {
        return items.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }
    
    /**
     * Clear the cart
     */
    public void clear() {
        items.clear();
    }

    @Override
    public String toCsv() {
        return String.format("%s,%s,%d,%.2f,%s",
                id, getClientId(), getItemCount(), getTotalPrice(), creationDate);
    }

    @Override
    public void fromCsv(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length >= 5) {
            this.id = parts[0];
            // clientId is stored but client object should be set separately through service layer
            // itemCount and totalPrice are calculated from items map
            this.creationDate = parts[4];
        }
    }
}
