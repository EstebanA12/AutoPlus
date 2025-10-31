package co.edu.umanizales.autoplus.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import co.edu.umanizales.autoplus.model.abstracts.Accessory;

/**
 * Class representing exterior accessories
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExteriorAccessory extends Accessory {
    private String material;
    private String weatherResistance;

    @Override
    public String getType() {
        return "EXTERIOR";
    }

    @Override
    public String toCsv() {
        return super.toCsv() + "," + material + "," + weatherResistance;
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
            this.weatherResistance = parts[7];
        }
    }
}
