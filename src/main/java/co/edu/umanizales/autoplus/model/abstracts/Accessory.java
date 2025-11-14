package co.edu.umanizales.autoplus.model.abstracts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import co.edu.umanizales.autoplus.model.interfaces.Sellable;
import co.edu.umanizales.autoplus.model.interfaces.Discountable;
import co.edu.umanizales.autoplus.model.interfaces.Persistable;
import co.edu.umanizales.autoplus.model.entities.ExteriorAccessory;
import co.edu.umanizales.autoplus.model.entities.InteriorAccessory;
import co.edu.umanizales.autoplus.model.entities.TechnologicalAccessory;

/**
 * Abstract class representing an accessory in the AutoPlus store
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ExteriorAccessory.class, name = "EXTERIOR"),
    @JsonSubTypes.Type(value = InteriorAccessory.class, name = "INTERIOR"),
    @JsonSubTypes.Type(value = TechnologicalAccessory.class, name = "TECHNOLOGICAL")
})
public abstract class Accessory implements Sellable, Discountable, Persistable {
    private String id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private double discountPercentage;

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double applyDiscount(double discountPercentage) {
        this.discountPercentage = discountPercentage;
        return getDiscountedPrice();
    }

    @Override
    public double getDiscountedPrice() {
        return price * (1 - discountPercentage / 100);
    }

    @Override
    public String toCsv() {
        return String.format("%s,%s,%s,%.2f,%d,%.2f",
                id, name, description, price, stock, discountPercentage);
    }

    public abstract String getType();
}
