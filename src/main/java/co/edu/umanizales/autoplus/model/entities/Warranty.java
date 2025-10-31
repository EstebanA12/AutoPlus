package co.edu.umanizales.autoplus.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import co.edu.umanizales.autoplus.model.interfaces.Persistable;

/**
 * Class representing a warranty
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Warranty implements Persistable {
    private String id;
    private String accessoryId;
    private int months;
    private String description;
    private String startDate;
    private String endDate;

    @Override
    public String toCsv() {
        return String.format("%s,%s,%d,%s,%s,%s",
                id, accessoryId, months, description, startDate, endDate);
    }

    @Override
    public void fromCsv(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length >= 6) {
            this.id = parts[0];
            this.accessoryId = parts[1];
            this.months = Integer.parseInt(parts[2]);
            this.description = parts[3];
            this.startDate = parts[4];
            this.endDate = parts[5];
        }
    }
}
