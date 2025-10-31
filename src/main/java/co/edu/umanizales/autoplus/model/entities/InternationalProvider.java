package co.edu.umanizales.autoplus.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import co.edu.umanizales.autoplus.model.abstracts.Provider;

/**
 * Class representing an international provider (extends Provider)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InternationalProvider extends Provider {
    private String country;
    private int deliveryDays;
    private double shippingCost;

    @Override
    public double calculateDeliveryTime() {
        return deliveryDays + 5; // Additional days for international shipping
    }

    @Override
    public String toCsv() {
        return super.toCsv() + "," + country + "," + deliveryDays + "," + shippingCost;
    }

    @Override
    public void fromCsv(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length >= 8) {
            this.setId(parts[0]);
            this.setName(parts[1]);
            this.setPhone(parts[2]);
            this.setAddress(parts[3]);
            this.setEmail(parts[4]);
            this.country = parts[5];
            this.deliveryDays = Integer.parseInt(parts[6]);
            this.shippingCost = Double.parseDouble(parts[7]);
        }
    }
}
