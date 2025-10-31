package co.edu.umanizales.autoplus.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import co.edu.umanizales.autoplus.model.interfaces.Persistable;

/**
 * Class representing a client
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client implements Persistable {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private double totalSpent;

    @Override
    public String toCsv() {
        return String.format("%s,%s,%s,%s,%s,%.2f",
                id, name, email, phone, address, totalSpent);
    }

    @Override
    public void fromCsv(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length >= 6) {
            this.id = parts[0];
            this.name = parts[1];
            this.email = parts[2];
            this.phone = parts[3];
            this.address = parts[4];
            this.totalSpent = Double.parseDouble(parts[5]);
        }
    }
}
