package controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import dto.Ingredient;
import dto.ManagementFunctions;
import utils.ValidationCheck;

public class IngreManagement implements ManagementFunctions {

    private List<Ingredient> arrIngre;

    public IngreManagement() {
        this.arrIngre = new ArrayList<>();
    }

    public List<Ingredient> getArrIngre() {
        return arrIngre;
    }

    public void setArrIngre(List<Ingredient> arrIngre) {
        this.arrIngre = arrIngre;
    }

    // Add an ingredient
    @Override
    public void add() {
        while (true) {
            String code = ValidationCheck.getStringWithPattern("Enter code of new ingredient: ",
                    "^[a-zA-Z0-9\\s]+$").toUpperCase();

            if (findIngredient(code) != null) {
                System.out.println("Duplicated code. Try with another one");
                continue;
            }

            String name = ValidationCheck.getStringWithPattern("Enter ingredient name: ", "^[a-zA-Z0-9\\s]+$");

            double quantity = ValidationCheck.inputDouble("Enter quantity", 1, 100000);

            String unit = ValidationCheck.getStringWithPattern("Enter unit (e.g., ml, g): ", "^[a-zA-Z0-9\\s]+$");

            Ingredient ingredient = new Ingredient(code, ValidationCheck.formatTitleCase(name), quantity, unit);
            arrIngre.add(ingredient);
            System.out.println("Ingredient added successfully!");
            if (!ValidationCheck.readBoolean("Do you want to continue add ingredient ? (Y/N)")) {
                break;
            }
        }
    }

    // Update ingredient information
    @Override
    public void update() {
        System.out.println("----------------------");
        String code = ValidationCheck.inputStrWithPattern("Enter ingredient code to update",
                "^[a-zA-Z0-9\\s]+$").toUpperCase();
        Ingredient ingredient = findIngredient(code);
        if (ingredient == null) {
            System.out.println("Ingredient not found.");
            return;
        }

        frame(ingredient);

        String newName = ValidationCheck.inputStrWithPattern("Enter new name (or leave blank)", ".*");
        if (!newName.isEmpty()) {
            ingredient.setName(ValidationCheck.formatTitleCase(newName));
        }

        String quantityInput = ValidationCheck.inputStrWithPattern("Enter new quantity (or leave blank)", ".*");
        if (!quantityInput.isEmpty()) {
            double newQuantity = Double.parseDouble(quantityInput);
            ingredient.setQuantity(newQuantity);
        }

        String newUnit = ValidationCheck.inputStrWithPattern("Enter new unit (or leave blank)", ".*");
        if (!newUnit.isEmpty()) {
            ingredient.setUnit(newUnit);
        }

        System.out.println("Ingredient updated successfully!");

    }

    // Delete an ingredient
    @Override
    public void delete() {
        showAll();
        String code = ValidationCheck.inputStrWithPattern("Enter ingredient code to delete",
                "^[a-zA-Z0-9\\s]+$").toUpperCase();

        Ingredient ingredient = findIngredient(code);
        if (ingredient == null) {
            System.out.println("Ingredient not found.");
            return;
        }

        frame(ingredient);

        if (ValidationCheck.readBoolean("Are you sure you want to delete " + ingredient.getName() + "? (y/n)")) {
            arrIngre.remove(ingredient);
            System.out.println("Ingredient deleted successfully!");
        } else {
            System.out.println("Ingredient deletion cancelled.");
        }
    }

    // Show all ingredients
    @Override
    public void showAll() {

        if (arrIngre.isEmpty()) {
            System.out.println("No ingredients found.");
            return;
        }

        Collections.sort(arrIngre, (i1, i2) -> i1.getName().compareTo(i2.getName()));
        System.out.println("Ingredients:");
        System.out.printf("%5s%15s%25s%15s%15s\n", "Num", "Code", "Name", "Quantity", "Unit");
        for (int index = 1; index <= 80; index++) {
            System.out.printf("=");
        }
        System.out.println("");
        int stt = 0;
        for (Ingredient i : arrIngre) {
            System.out.printf("%5s%15s%25s%15.2f%15s\n", ++stt, i.getCode(), i.getName(), i.getQuantity(), i.getUnit());
            for (int index = 1; index <= 80; index++) {
                System.out.printf("-");
            }
            System.out.println("");
        }

    }

    public Ingredient findIngredient(String code) {
        for (Ingredient ingredient : arrIngre) {
            if (ingredient.getCode().equalsIgnoreCase(code)) {
                return ingredient;
            }
        }
        return null;
    }

    private void frame(Ingredient ingredient) {
        System.out.println("Current Ingredient Details:");
        System.out.println("Code: " + ingredient.getCode());
        System.out.println("Name: " + ingredient.getName());
        System.out.println("Quantity: " + ingredient.getQuantity());
        System.out.println("Unit: " + ingredient.getUnit());
        System.out.println("----------------------");
    }

//    public void printLastIngredient() {
//        if (arrIngre.isEmpty()) {
//            System.out.println("No ingredients found.");
//        } else {
//            Ingredient lastIngredient = arrIngre.get(arrIngre.size() - 1);
//            System.out.println("Last Ingredient:");
//            System.out.println("Code: " + lastIngredient.getCode());
//            System.out.println("Name: " + lastIngredient.getName());
//            System.out.println("Quantity: " + lastIngredient.getQuantity());
//            System.out.println("Unit: " + lastIngredient.getUnit());
//        }
//    }

}
