package co.edu.umanizales.autoplus.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import co.edu.umanizales.autoplus.model.abstracts.Provider;

/**
 * Class representing a local provider (extends Provider)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocalProvider extends Provider {
    private String city;
    private int deliveryDays;

    @Override
    public double calculateDeliveryTime() {
        return deliveryDays;
    }

    @Override
    public String toCsv() {
        return super.toCsv() + "," + city + "," + deliveryDays;
    }

    @Override
    public void fromCsv(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length >= 7) {
            this.setId(parts[0]);
            this.setName(parts[1]);
            this.setPhone(parts[2]);
            this.setAddress(parts[3]);
            this.setEmail(parts[4]);
            this.city = parts[5];
            this.deliveryDays = Integer.parseInt(parts[6]);
        }
    }
}
