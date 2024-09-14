package controllers;

import java.util.ArrayList;
import java.util.List;
import dto.DispensingDrink;
import dto.Drink;
import dto.Ingredient;
import utils.ValidationCheck;

public class Order {

    private DrinksManagement drinkManager;
    private List<DispensingDrink> dispensedDrinks;

    public Order(DrinksManagement drinkManager) {
        this.drinkManager = drinkManager;
        this.dispensedDrinks = new ArrayList<>();
    }

    public List<DispensingDrink> getDispensedDrinks() {
        return dispensedDrinks;
    }

    public void setDrinkManager(DrinksManagement drinkManager) {
        this.drinkManager = drinkManager;
    }

    public void setDispensedDrinks(List<DispensingDrink> dispensedDrinks) {
        this.dispensedDrinks = dispensedDrinks;
    }

    public void dispenseDrink() {
        if (drinkManager.getDrinks().isEmpty()) {
            System.out.println("Menu is empty. Add a new drink to the menu");
            drinkManager.add();
            return;
        }

        System.out.println("Here is the menu of our Store:");
        drinkManager.showAll();
        String code = ValidationCheck.inputStrWithPattern("Enter code of the drink", ".*");
        Drink drink = drinkManager.findDrinks(code);

        if (drink == null) {
            System.out.println("The drink does not exist.");
            return;
        }

        int quantity = ValidationCheck.readInteger("Enter quantity to dispense: ", 1, 100000);

        DispensingDrink dispensingDrink = new DispensingDrink(drink, quantity);

        if (!dispensingDrink.sufficientQuantity()) {
            System.out.println("Insufficient ingredients for the requested quantity.");
            return;
        }

        dispensingDrink.dispense();
        System.out.println("Drink dispensed successfully!");
        dispensedDrinks.add(dispensingDrink);

    }

    public void updateDispensingDrink() {
        String code = ValidationCheck.inputStrWithPattern("Enter dispensing drink code to update", ".*");

        DispensingDrink dispensingDrink = findDispensedDrink(code);

        if (dispensingDrink == null) {
            System.out.println("The drink does not exist.");
            return;
        }

        int newQuantity = ValidationCheck.readInteger("Enter new quantity (or 0 to remove): ", 0, 10000);

        if (newQuantity < 0) {
            System.out.println("Invalid quantity. Please enter a non-negative number.");
            return;
        }

        if (newQuantity == 0) {
            removeDispensingDrink(dispensingDrink);
            System.out.println("Dispensing drink removed successfully.");
            return;
        }

        // Calculate the difference in quantity to update ingredients accordingly
        int difference = newQuantity - dispensingDrink.getQuantityDispensed();

        // Check if ingredients are sufficient for the updated quantity
        for (Ingredient ingredient : dispensingDrink.getDrink().getIngredients()) {
            double requiredQuantity = ingredient.getQuantityPerDrink() * difference;
            if (!ingredient.hasSufficientQuantity(requiredQuantity)) {
                System.out.println("Insufficient ingredients for the requested quantity.");
                return;
            }
        }

        // Update ingredient quantities
        for (Ingredient ingredient : dispensingDrink.getDrink().getIngredients()) {
            double quantityToUpdate = ingredient.getQuantityPerDrink() * difference;
            ingredient.removeQuantity(quantityToUpdate);
        }

        // Update dispensing drink quantity
        dispensingDrink.setQuantityDispensed(newQuantity);
        System.out.println("Dispensing drink quantity updated successfully!");
    }

    private DispensingDrink findDispensedDrink(String code) {
        for (DispensingDrink dispensedDrink : dispensedDrinks) {
            if (dispensedDrink.getDrink().getCode().equalsIgnoreCase(code)) {
                return dispensedDrink;
            }
        }
        return null;
    }

    private void removeDispensingDrink(DispensingDrink dispensingDrink) {
        dispensedDrinks.remove(dispensingDrink);
    }

}
