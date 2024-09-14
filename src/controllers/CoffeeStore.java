package controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import dto.ICoffeeStore;
import utils.FileManager;
import utils.ValidationCheck;

public class CoffeeStore implements ICoffeeStore {

    private static IngreManagement ingredientManager = new IngreManagement();
    private static DrinksManagement drinkManager = new DrinksManagement(ingredientManager);
    private static Order dispensingManager = new Order(drinkManager);
    private static Report report = new Report();

    public CoffeeStore() {
    }

    //submenu for manage Ingredient functions
    @Override
    public void manageIngredients() {
        while (true) {
            System.out.println("\n--- Manage Ingredients ---");
            System.out.println("1. Add an ingredient");
            System.out.println("2. Update ingredient information");
            System.out.println("3. Delete ingredient");
            System.out.println("4. Show all ingredients");
            System.out.println("0. Back to main menu");

            int choice = ValidationCheck.readInteger("Enter your choice: ", 0, 4);

            switch (choice) {
                case 1:
                    ingredientManager.add();
                    break;
                case 2:
                    ingredientManager.update();
                    break;
                case 3:
                    ingredientManager.delete();
                    break;
                case 4:
                    ingredientManager.showAll();
                    break;
                case 0:
                    return; // Exit submenu
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    //submenu for manage Drinks functions
    @Override
    public void manageDrinks() {
        while (true) {
            System.out.println("\n--- Manage Drinks ---");
            System.out.println("1. Add a drink to menu");
            System.out.println("2. Update drink information");
            System.out.println("3. Delete drink from menu");
            System.out.println("4. Show menu");
            System.out.println("0. Back to main menu");

            int choice = ValidationCheck.readInteger("Enter your choice: ", 0, 4);

            switch (choice) {
                case 1:
                    drinkManager.add();
                    break;
                case 2:
                    drinkManager.update();
                    break;
                case 3:
                    drinkManager.delete();
                    break;
                case 4:
                    drinkManager.showAll();
                    break;
                case 0:
                    return; // Exit submenu
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    //submenu for manage Order
    @Override
    public void manageDispensingDrinks() {
        while (true) {
            System.out.println("\n--- Manage Dispensing Drinks ---");
            System.out.println("1. Dispense a drink");
            System.out.println("2. Update dispensing drink information");
            System.out.println("0. Back to main menu");

            int choice = ValidationCheck.readInteger("Enter your choice: ", 0, 2);;

            switch (choice) {
                case 1:
                    dispensingManager.dispenseDrink();
                    break;
                case 2:
                    dispensingManager.updateDispensingDrink();
                    break;
                case 0:
                    return; // Exit submenu
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    //submenu for report functions
    @Override
    public void showReports() {
        while (true) {
            System.out.println("\n--- Show Reports ---");
            System.out.println("1. Ingredients available");
            System.out.println("2. Drinks out of ingredients");
            System.out.println("3. Show all dispensing drinks");
            System.out.println("0. Back to main menu");

            int choice = ValidationCheck.readInteger("Enter your choice: ", 0, 3);

            switch (choice) {
                case 1:
                    report.showAvailableIngredients(ingredientManager);
                    break;
                case 2:
                    report.showUnavailableDrinks(drinkManager);
                    break;
                case 3:
                    report.showDispensingDrinks(dispensingManager);
                    break;
                case 0:
                    return; // Exit submenu
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    @Override
    public void savaData() {
        try {
            FileManager.storeIngredients(ingredientManager.getArrIngre());
            FileManager.storeDrinktxt(drinkManager.getDrinks());
            FileManager.storeDispensingDrinks(dispensingManager.getDispensedDrinks());
        } catch (IOException ex) {
            Logger.getLogger(CoffeeStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Save data successfully !");
    }

    public void loadDataFromFile() {
        try {
            // Load data from files
            ingredientManager.setArrIngre(FileManager.loadIngredients());
            drinkManager.setDrinks(FileManager.loadDrinks());
            dispensingManager.setDispensedDrinks(FileManager.loadDispensingDrinks());

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); // Print stack trace for debugging
            System.out.println("Load data failed: " + e.getMessage());
        }
    }

}
