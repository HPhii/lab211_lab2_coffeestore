package dto;

import java.io.Serializable;

public class Ingredient implements Serializable {

    private static final long serialVersionUID = 1L; // Ensure serialization compatibility

    private String code;
    private String name;
    private double quantity;
    private String unit;
    private double quantityPerDrink;

    public Ingredient() {
    }

    public Ingredient(String code, String name, double quantity, String unit) {
        this.code = code;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void removeQuantity(double requiredQuantity) {
        this.quantity -= requiredQuantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAvailable() {
        return quantity > 0;
    }

    public double getQuantityPerDrink() {
        return quantityPerDrink;
    }

    public void setQuantityPerDrink(double quantityPerDrink) {
        this.quantityPerDrink = quantityPerDrink;
    }

    public boolean hasSufficientQuantity(double requiredQuantity) {
        if (requiredQuantity < 0) {
            throw new IllegalArgumentException("Required quantity cannot be negative.");
        }
        return quantity >= requiredQuantity;
    }

    @Override
    public String toString() {
        return "Ingredient{" + "code=" + code + ", name=" + name + ", quantity=" + quantity + ", unit=" + unit + ", quantityPerDrink=" + quantityPerDrink + '}';
    }
}
