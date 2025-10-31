package co.edu.umanizales.autoplus.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import co.edu.umanizales.autoplus.model.abstracts.Accessory;

/**
 * Class representing interior accessories
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InteriorAccessory extends Accessory {
    private String material;
    private String color;

    @Override
    public String getType() {
        return "INTERIOR";
    }

    @Override
    public String toCsv() {
        return super.toCsv() + "," + material + "," + color;
    }

    @Override
    public void fromCsv(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length >= 8) {
            this.setId(parts[0]);
            this.setName(parts[1]);
            this.setDescription(parts[2]);
            this.setPrice(Double.parseDouble(parts[3]));
            this.setStock(Integer.parseInt(parts[4]));
            this.setDiscountPercentage(Double.parseDouble(parts[5]));
            this.material = parts[6];
            this.color = parts[7];
        }
    }
}
