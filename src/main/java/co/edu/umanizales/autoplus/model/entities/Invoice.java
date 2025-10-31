package co.edu.umanizales.autoplus.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import co.edu.umanizales.autoplus.model.interfaces.Persistable;

/**
 * Class representing an invoice
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice implements Persistable {
    private String id;
    private String saleId;
    private double subtotal;
    private double tax;
    private double total;
    private String invoiceDate;

    @Override
    public String toCsv() {
        return String.format("%s,%s,%.2f,%.2f,%.2f,%s",
                id, saleId, subtotal, tax, total, invoiceDate);
    }

    @Override
    public void fromCsv(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length >= 6) {
            this.id = parts[0];
            this.saleId = parts[1];
            this.subtotal = Double.parseDouble(parts[2]);
            this.tax = Double.parseDouble(parts[3]);
            this.total = Double.parseDouble(parts[4]);
            this.invoiceDate = parts[5];
        }
    }
}
