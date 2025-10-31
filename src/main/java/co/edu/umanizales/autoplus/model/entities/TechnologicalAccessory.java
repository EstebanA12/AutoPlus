package co.edu.umanizales.autoplus.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import co.edu.umanizales.autoplus.model.abstracts.Accessory;

/**
 * Class representing technological accessories
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TechnologicalAccessory extends Accessory {
    private String technology;
    private String warranty;

    @Override
    public String getType() {
        return "TECHNOLOGICAL";
    }

    @Override
    public String toCsv() {
        return super.toCsv() + "," + technology + "," + warranty;
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
            this.technology = parts[6];
            this.warranty = parts[7];
        }
    }
}
