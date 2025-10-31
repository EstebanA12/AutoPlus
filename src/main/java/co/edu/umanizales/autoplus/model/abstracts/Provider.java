package co.edu.umanizales.autoplus.model.abstracts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import co.edu.umanizales.autoplus.model.interfaces.Persistable;

/**
 * Abstract class representing a provider
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Provider implements Persistable {
    private String id;
    private String name;
    private String phone;
    private String address;
    private String email;

    @Override
    public String toCsv() {
        return String.format("%s,%s,%s,%s,%s",
                id, name, phone, address, email);
    }

    public abstract double calculateDeliveryTime();
}
