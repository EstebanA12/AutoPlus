package co.edu.umanizales.autoplus.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import co.edu.umanizales.autoplus.model.interfaces.Persistable;

/**
 * Class representing an order
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Persistable {
    private String id;
    private String providerId;
    private String accessoryId;
    private int quantity;
    private double totalCost;
    private String orderDate;
    private String status;

    @Override
    public String toCsv() {
        return String.format("%s,%s,%s,%d,%.2f,%s,%s",
                id, providerId, accessoryId, quantity, totalCost, orderDate, status);
    }

    @Override
    public void fromCsv(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length >= 7) {
            this.id = parts[0];
            this.providerId = parts[1];
            this.accessoryId = parts[2];
            this.quantity = Integer.parseInt(parts[3]);
            this.totalCost = Double.parseDouble(parts[4]);
            this.orderDate = parts[5];
            this.status = parts[6];
        }
    }
}
