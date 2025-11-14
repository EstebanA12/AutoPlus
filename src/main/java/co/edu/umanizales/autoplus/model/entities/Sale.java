package co.edu.umanizales.autoplus.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import co.edu.umanizales.autoplus.model.interfaces.Persistable;
import java.util.*;

/**
 * Class representing a sale transaction
 * Maintains relationships with Client and Seller entities
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sale implements Persistable {
    private String id;
    
    @NonNull
    private Client client;
    
    @NonNull
    private Seller seller;
    
    // List of accessories sold in this transaction
    private List<SaleItem> items = new ArrayList<>();
    
    private double totalAmount;
    private String saleDate;
    private String status;
    
    // Convenience methods for ID access
    public String getClientId() {
        return client != null ? client.getId() : null;
    }
    
    public String getSellerId() {
        return seller != null ? seller.getId() : null;
    }
    
    /**
     * Add an item to the sale
     */
    public void addItem(SaleItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Sale item cannot be null");
        }
        items.add(item);
        recalculateTotal();
    }
    
    /**
     * Remove an item from the sale
     */
    public void removeItem(SaleItem item) {
        items.remove(item);
        recalculateTotal();
    }
    
    /**
     * Recalculate total amount
     */
    private void recalculateTotal() {
        this.totalAmount = items.stream()
                .mapToDouble(SaleItem::getSubtotal)
                .sum();
    }

    @Override
    public String toCsv() {
        return String.format("%s,%s,%s,%.2f,%s,%s",
                id, getClientId(), getSellerId(), totalAmount, saleDate, status);
    }

    @Override
    public void fromCsv(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length >= 6) {
            this.id = parts[0];
            // clientId and sellerId are stored but objects should be set separately through service layer
            this.totalAmount = Double.parseDouble(parts[3]);
            this.saleDate = parts[4];
            this.status = parts[5];
        }
    }
}
