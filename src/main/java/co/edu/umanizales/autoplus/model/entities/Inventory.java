package co.edu.umanizales.autoplus.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import co.edu.umanizales.autoplus.model.interfaces.Persistable;

/**
 * Class representing inventory management
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory implements Persistable {
    private String id;
    private String accessoryId;
    private int quantity;
    private String location;
    private String lastUpdate;

    @Override
    public String toCsv() {
        return String.format("%s,%s,%d,%s,%s",
                id, accessoryId, quantity, location, lastUpdate);
    }

    @Override
    public void fromCsv(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length >= 5) {
            this.id = parts[0];
            this.accessoryId = parts[1];
            this.quantity = Integer.parseInt(parts[2]);
            this.location = parts[3];
            this.lastUpdate = parts[4];
        }
    }
}
