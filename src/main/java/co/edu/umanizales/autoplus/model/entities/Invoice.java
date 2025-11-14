package co.edu.umanizales.autoplus.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import co.edu.umanizales.autoplus.model.interfaces.Persistable;

/**
 * Class representing an invoice
 * Maintains a relationship with a Sale entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice implements Persistable {
    private String id;
    
    @NonNull
    private Sale sale;
    
    private double subtotal;
    private double tax;
    private double total;
    private String invoiceDate;
    
    // Convenience method for ID access
    public String getSaleId() {
        return sale != null ? sale.getId() : null;
    }
    
    /**
     * Calculate total from subtotal and tax
     */
    public void calculateTotal() {
        this.total = this.subtotal + this.tax;
    }

    @Override
    public String toCsv() {
        return String.format("%s,%s,%.2f,%.2f,%.2f,%s",
                id, getSaleId(), subtotal, tax, total, invoiceDate);
    }

    @Override
    public void fromCsv(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length >= 6) {
            this.id = parts[0];
            // saleId is stored but sale object should be set separately through service layer
            this.subtotal = Double.parseDouble(parts[2]);
            this.tax = Double.parseDouble(parts[3]);
            this.total = Double.parseDouble(parts[4]);
            this.invoiceDate = parts[5];
        }
    }
}
