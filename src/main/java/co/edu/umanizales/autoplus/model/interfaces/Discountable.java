package co.edu.umanizales.autoplus.model.interfaces;

/**
 * Interface that defines behavior for items that can have discounts
 */
public interface Discountable {
    double applyDiscount(double discountPercentage);
    double getDiscountedPrice();
}
