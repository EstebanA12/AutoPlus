package co.edu.umanizales.autoplus.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import co.edu.umanizales.autoplus.model.interfaces.Persistable;

/**
 * Class representing a sale
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sale implements Persistable {
    private String id;
    private String clientId;
    private String sellerId;
    private double totalAmount;
    private String saleDate;
    private String status;

    @Override
    public String toCsv() {
        return String.format("%s,%s,%s,%.2f,%s,%s",
                id, clientId, sellerId, totalAmount, saleDate, status);
    }

    @Override
    public void fromCsv(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length >= 6) {
            this.id = parts[0];
            this.clientId = parts[1];
            this.sellerId = parts[2];
            this.totalAmount = Double.parseDouble(parts[3]);
            this.saleDate = parts[4];
            this.status = parts[5];
        }
    }
}
