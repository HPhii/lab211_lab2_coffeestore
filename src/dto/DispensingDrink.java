package dto;

import java.io.Serializable;

public class DispensingDrink implements Serializable {

    private Drink drink;
    private int quantityDispensed;

    public boolean sufficientQuantity() {
        for (Ingredient ingredient : drink.getIngredients()) {
            double requiredQuantity = ingredient.getQuantityPerDrink() * quantityDispensed;
            if (!ingredient.hasSufficientQuantity(requiredQuantity)) {
                return false;
            }
        }
        return true;
    }

    public void dispense() {

        for (Ingredient ingredient : drink.getIngredients()) {
            double requiredQuantity = ingredient.getQuantityPerDrink() * quantityDispensed;
            ingredient.removeQuantity(requiredQuantity);
        }

    }

    public DispensingDrink(Drink drink, int quantityDispensed) {
        this.drink = drink;
        this.quantityDispensed = quantityDispensed;
    }

    public Drink getDrink() {
        return drink;
    }

    public void setDrink(Drink drink) {
        this.drink = drink;
    }

    public int getQuantityDispensed() {
        return quantityDispensed;
    }

    public void setQuantityDispensed(int quantityDispensed) {
        this.quantityDispensed = quantityDispensed;
    }

}
