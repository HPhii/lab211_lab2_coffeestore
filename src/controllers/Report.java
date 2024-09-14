package controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import dto.DispensingDrink;
import dto.Drink;
import dto.IReport;
import dto.Ingredient;

public class Report implements IReport {

    @Override
    public void showAvailableIngredients(IngreManagement ingredientManager) {
        List<Ingredient> availableIngredients = new ArrayList<>();
        for (Ingredient ingredient : ingredientManager.getArrIngre()) {
            if (ingredient.isAvailable()) {
                availableIngredients.add(ingredient);
            }
        }

        printReport("Available Ingredients", availableIngredients);
    }

    @Override
    public void showUnavailableDrinks(DrinksManagement drinkManager) {
        List<Drink> unavailableDrinks = new ArrayList<>();
        for (Drink drink : drinkManager.getDrinks()) {
            if (!drink.isAvailable()) {
                unavailableDrinks.add(drink);
            }
        }

        printReport("Drinks with Unavailable Ingredients", unavailableDrinks);
    }

    @Override
    public void showDispensingDrinks(Order dispensingManager) {
        List<DispensingDrink> dispensingDrinks = dispensingManager.getDispensedDrinks();
        Collections.sort(dispensingDrinks, (d1, d2) -> d1.getDrink().getName().compareTo(d2.getDrink().getName())); // Sort by drink name
//        Collections.sort(dispensingDrinks, new Comparator<DispensingDrink>() {
//            @Override
//            public int compare(DispensingDrink d1, DispensingDrink d2) {
//                return d1.getDrink().getName().compareTo(d2.getDrink().getName());
//            }
//        });

        printReport("Dispensing Drinks", dispensingDrinks);
    }

// Helper method for printing reports (flexible for various reports)
    private void printReport(String title, List<?> data) {
        System.out.println("\n**" + title + "**");
        if (data.isEmpty()) {
            System.out.println("No items found.");
        } else {
            for (Object item : data) {
                String output = "";
                if (item instanceof Ingredient) {
                    Ingredient ingredient = (Ingredient) item;
                    output = String.format("- Code: %s, Name: %s, Unit: %s",
                            ingredient.getCode(), ingredient.getName(), ingredient.getUnit());
                } else if (item instanceof Drink) {
                    Drink drink = (Drink) item;
                    output = String.format("- Code: %s, Name: %s", drink.getCode(), drink.getName());
                } else if (item instanceof DispensingDrink) {
                    DispensingDrink dispensingDrink = (DispensingDrink) item;
                    output = String.format("- Drink: %s, Quantity: %d",
                            dispensingDrink.getDrink().getName(), dispensingDrink.getQuantityDispensed());
                }
                System.out.println(output);
            }
        }
        System.out.println();
    }

}
