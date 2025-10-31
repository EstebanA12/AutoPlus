package co.edu.umanizales.autoplus.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import co.edu.umanizales.autoplus.model.interfaces.Persistable;

/**
 * Class representing a shopping cart
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart implements Persistable {
    private String id;
    private String clientId;
    private int itemCount;
    private double totalPrice;
    private String creationDate;

    @Override
    public String toCsv() {
        return String.format("%s,%s,%d,%.2f,%s",
                id, clientId, itemCount, totalPrice, creationDate);
    }

    @Override
    public void fromCsv(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length >= 5) {
            this.id = parts[0];
            this.clientId = parts[1];
            this.itemCount = Integer.parseInt(parts[2]);
            this.totalPrice = Double.parseDouble(parts[3]);
            this.creationDate = parts[4];
        }
    }
}
