package co.edu.umanizales.autoplus.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import co.edu.umanizales.autoplus.model.interfaces.Persistable;
import co.edu.umanizales.autoplus.model.abstracts.Accessory;

/**
 * Class representing a warranty for an accessory
 * Maintains a relationship with an Accessory entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Warranty implements Persistable {
    private String id;
    
    @NonNull
    private Accessory accessory;
    
    private int months;
    private String description;
    private String startDate;
    private String endDate;
    
    // Convenience method for ID access
    public String getAccessoryId() {
        return accessory != null ? accessory.getId() : null;
    }

    @Override
    public String toCsv() {
        return String.format("%s,%s,%d,%s,%s,%s",
                id, getAccessoryId(), months, description, startDate, endDate);
    }

    @Override
    public void fromCsv(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length >= 6) {
            this.id = parts[0];
            // accessoryId is stored but accessory object should be set separately through service layer
            this.months = Integer.parseInt(parts[2]);
            this.description = parts[3];
            this.startDate = parts[4];
            this.endDate = parts[5];
        }
    }
}
