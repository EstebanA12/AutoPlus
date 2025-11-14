package co.edu.umanizales.autoplus.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import co.edu.umanizales.autoplus.model.interfaces.Persistable;
import co.edu.umanizales.autoplus.model.abstracts.Accessory;

/**
 * Class representing inventory management
 * Maintains a relationship with an Accessory entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory implements Persistable {
    private String id;
    
    @NonNull
    private Accessory accessory;
    
    private int quantity;
    private String location;
    private String lastUpdate;
    
    // Convenience method for ID access
    public String getAccessoryId() {
        return accessory != null ? accessory.getId() : null;
    }
    
    /**
     * Increase inventory quantity
     */
    public void addStock(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.quantity += amount;
    }
    
    /**
     * Decrease inventory quantity
     */
    public void removeStock(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (this.quantity < amount) {
            throw new IllegalArgumentException("Insufficient stock");
        }
        this.quantity -= amount;
    }
    
    /**
     * Check if item is in stock
     */
    public boolean isInStock() {
        return this.quantity > 0;
    }

    @Override
    public String toCsv() {
        return String.format("%s,%s,%d,%s,%s",
                id, getAccessoryId(), quantity, location, lastUpdate);
    }

    @Override
    public void fromCsv(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length >= 5) {
            this.id = parts[0];
            // accessoryId is stored but accessory object should be set separately through service layer
            this.quantity = Integer.parseInt(parts[2]);
            this.location = parts[3];
            this.lastUpdate = parts[4];
        }
    }
}
