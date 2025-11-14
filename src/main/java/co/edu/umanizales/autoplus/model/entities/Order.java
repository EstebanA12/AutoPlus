package co.edu.umanizales.autoplus.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import co.edu.umanizales.autoplus.model.interfaces.Persistable;
import co.edu.umanizales.autoplus.model.abstracts.Provider;
import co.edu.umanizales.autoplus.model.abstracts.Accessory;

/**
 * Class representing an order from a provider
 * Maintains relationships with Provider and Accessory entities
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Persistable {
    private String id;
    
    // Object relationships instead of just IDs
    @NonNull
    private Provider provider;
    
    @NonNull
    private Accessory accessory;
    
    private int quantity;
    private double totalCost;
    private String orderDate;
    private String status;
    
    // Convenience methods for ID access (for CSV persistence)
    public String getProviderId() {
        return provider != null ? provider.getId() : null;
    }
    
    public String getAccessoryId() {
        return accessory != null ? accessory.getId() : null;
    }

    @Override
    public String toCsv() {
        return String.format("%s,%s,%s,%d,%.2f,%s,%s",
                id, getProviderId(), getAccessoryId(), quantity, totalCost, orderDate, status);
    }

    @Override
    public void fromCsv(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length >= 7) {
            this.id = parts[0];
            // Note: providerId and accessoryId are stored but provider/accessory objects
            // should be set separately through service layer
            this.quantity = Integer.parseInt(parts[3]);
            this.totalCost = Double.parseDouble(parts[4]);
            this.orderDate = parts[5];
            this.status = parts[6];
        }
    }
}
