package co.edu.umanizales.autoplus.model.interfaces;

/**
 * Interface that defines behavior for sellable items
 */
public interface Sellable {
    double getPrice();
    void setPrice(double price);
    String getDescription();
}
