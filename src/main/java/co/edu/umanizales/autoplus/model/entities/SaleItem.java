package co.edu.umanizales.autoplus.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import co.edu.umanizales.autoplus.model.interfaces.Persistable;
import co.edu.umanizales.autoplus.model.abstracts.Accessory;

/**
 * Class representing a single item in a sale
 * Maintains relationship with an Accessory entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleItem implements Persistable {
    private String id;
    
    @NonNull
    private Accessory accessory;
    
    private int quantity;
    private double unitPrice;
    
    // Convenience method for ID access
    public String getAccessoryId() {
        return accessory != null ? accessory.getId() : null;
    }
    
    /**
     * Calculate subtotal for this item
     */
    public double getSubtotal() {
        return unitPrice * quantity;
    }

    @Override
    public String toCsv() {
        return String.format("%s,%s,%d,%.2f",
                id, getAccessoryId(), quantity, unitPrice);
    }

    @Override
    public void fromCsv(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length >= 4) {
            this.id = parts[0];
            // accessoryId is stored but accessory object should be set separately through service layer
            this.quantity = Integer.parseInt(parts[2]);
            this.unitPrice = Double.parseDouble(parts[3]);
        }
    }
}
