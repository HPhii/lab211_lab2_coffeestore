package controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import dto.Drink;
import dto.Ingredient;
import dto.ManagementFunctions;
import utils.ValidationCheck;

public class DrinksManagement implements ManagementFunctions {

    private List<Drink> drinks;
    private IngreManagement ingredientManager;

    public DrinksManagement(IngreManagement ingredientManager) {
        this.drinks = new ArrayList<>();
        this.ingredientManager = ingredientManager;
    }

    public List<Drink> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<Drink> drinks) {
        this.drinks = drinks;
    }

    public void setIngredientManager(IngreManagement ingredientManager) {
        this.ingredientManager = ingredientManager;
    }

    @Override
    public void add() {
        while (true) {
            String code = ValidationCheck.getStringWithPattern("Enter code of new drinks: ",
                    "^[a-zA-Z0-9\\s]+$").toUpperCase();

            // Check for duplicate code
            if (findDrinks(code) != null) {
                System.out.println("Duplicated code. Try with another one");
                continue; // Continue the loop to prompt for a new code
            }

            String name = ValidationCheck.getStringWithPattern("Enter drinks name: ", "^[a-zA-Z0-9\\s]+$");

            List<Ingredient> ingredients = new ArrayList<>();

            while (true) {
                System.out.println("\n");
                ingredientManager.showAll();
                System.out.println("\n");
                String ingredientCode = ValidationCheck.getStringWithPattern("Enter ingredient code (or 'DONE'): ",
                        "^[a-zA-Z0-9\\s]+$");
                if (ingredientCode.equalsIgnoreCase("DONE")) {
                    break;
                }
                Ingredient ingredient = ingredientManager.findIngredient(ingredientCode);
                if (ingredient != null) {
                    try {
                        double quantityPerDrink = ValidationCheck.inputDouble("Enter quantity per drink",
                                0, ingredient.getQuantity());
                        ingredient.setQuantityPerDrink(quantityPerDrink);
                        ingredients.add(ingredient);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input for quantity per drinks. Please enter a valid number.");
                    }
                } else {
                    System.out.println("Ingredient not found.");
                }
            }

            Drink drink = new Drink(code, ValidationCheck.formatTitleCase(name), ingredients);
            drinks.add(drink);
            System.out.println("Drinks added successfully!");
            break; // Exit the loop as the drink has been successfully added
        }
    }

    @Override
    public void update() {
        System.out.println("----------------------");
        showAll();
        String code = ValidationCheck.inputStrWithPattern("Enter drinks code to update",
                "^[a-zA-Z0-9\\s]+$").toUpperCase();

        Drink drinks = findDrinks(code);
        if (drinks == null) {
            System.out.println("Drink does not exist.");
            return;
        }
        frame(drinks);
        System.out.println("----------------------");

        String newName = ValidationCheck.inputStrWithPattern("Enter new name (or leave blank)", ".*");
        if (!newName.isEmpty()) {
            drinks.setName(ValidationCheck.formatTitleCase(newName));
        }

        // Update ingredients similar to adding ingredients
        while (true) {
            String ingredientCode = ValidationCheck.inputStrWithPattern("Enter ingredient code to add/update/remove (or 'DONE')",
                    "^[a-zA-Z0-9\\s]+$").toUpperCase();
            if (ingredientCode.equalsIgnoreCase("DONE")) {
                break;
            }

            // Check if the ingredient already exists in the recipe
            Ingredient existingIngredient = null;
            for (Ingredient ingredient : drinks.getIngredients()) {
                if (ingredient.getCode().equalsIgnoreCase(ingredientCode)) {
                    existingIngredient = ingredient;
                    break;
                }
            }

            // If the ingredient does not exist in the recipe, add it
            if (existingIngredient == null) {
                Ingredient newIngredient = ingredientManager.findIngredient(ingredientCode);
                if (newIngredient != null) {
                    double quantity = ValidationCheck.inputDouble("Enter quantity per drinks", 0,
                            newIngredient.getQuantity());
                    newIngredient.setQuantityPerDrink(quantity);
                    drinks.getIngredients().add(newIngredient);
                    System.out.println("Ingredient added to the recipe.");
                } else {
                    System.out.println("Ingredient not found.");
                }
            } else {
                // The ingredient already exists in the recipe, ask for update or removal
                String quantityInput = ValidationCheck.inputStrWithPattern("Enter quantity per drinks(or 'remove')", "^(remove|[0-9]+(?:\\.[0-9]+)?)$");
                if (quantityInput.equalsIgnoreCase("remove")) {
                    drinks.getIngredients().removeIf(i -> i.getCode().equalsIgnoreCase(ingredientCode));
//                    Iterator<Ingredient> it = drinks.getIngredients().iterator();
//                    while (it.hasNext()) {
//                        Ingredient ingredient = it.next();
//                        if (ingredient.getCode().equalsIgnoreCase(ingredientCode)) {
//                            it.remove();
                    System.out.println("Ingredient removed from the recipe.");
                } else {
                    double quantity = Double.parseDouble(quantityInput);
                    if (quantity >= 0) {
                        existingIngredient.setQuantityPerDrink(quantity);
                        System.out.println("Quantity updated for the ingredient in the recipe.");
                    } else {
                        System.out.println("Invalid quantity input. Please enter a non-negative number or 'remove'.");
                    }
                }
            }
        }
    }

    // Delete a drink from the menu
    @Override
    public void delete() {
        showAll();
        String code = ValidationCheck.inputStrWithPattern("Enter drinks code to delete",
                "^[a-zA-Z0-9\\s]+$").toUpperCase();

        Drink drink = findDrinks(code);
        if (drink == null) {
            System.out.println("Drinks not found.");
            return;
        }

        frame(drink);

        if (ValidationCheck.readBoolean("Are you sure you want to delete " + drink.getName() + "? (y/n)")) {
            drinks.remove(drink);
            System.out.println("Drinks deleted successfully!");
        } else {
            System.out.println("Drinks deletion cancelled.");
        }
    }

    // Show all drinks on the menu
    @Override
    public void showAll() {
        if (drinks.isEmpty()) {
            System.out.println("Menu is empty.");
            return;
        }
//        Collections.sort(drinks, new Comparator<Drink>() {
//            @Override
//            public int compare(Drink d1, Drink d2) {
//                return d1.getName().compareTo(d2.getName());
//            }
//        });
        Collections.sort(drinks, (d1, d2) -> d1.getName().compareTo(d2.getName()));
        System.out.println("Menu:");
        System.out.printf("|%5s|%15s|%35s|\n", "Num", "Code", "Name");
        for (int index = 1; index <= 60; index++) {
            System.out.printf("=");
        }
        System.out.println("");
        int stt = 0;
        for (Drink d : drinks) {
            System.out.printf("|%5s|%15s|%35s|\n", ++stt, d.getCode(), d.getName());
            for (int index = 1; index <= 60; index++) {
                System.out.printf("-");
            }
            System.out.println("");
        }
    }

    public Drink findDrinks(String code) {
        for (Drink drink : drinks) {
            if (drink.getCode().equalsIgnoreCase(code)) {
                return drink;
            }
        }
        return null; // Drinks not found
    }

    private void frame(Drink drinks) {
        System.out.println("Current Drinks Details:");
        System.out.println("Code: " + drinks.getCode());
        System.out.println("Name: " + drinks.getName());
        System.out.println("Ingredients:");
        for (Ingredient ingredient : drinks.getIngredients()) {
            System.out.println("- " + ingredient.getName() + ": " + ingredient.getQuantityPerDrink()
                    + " | " + ingredient.getCode());
        }
    }
}
