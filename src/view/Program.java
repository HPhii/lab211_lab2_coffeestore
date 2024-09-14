package view;

import controllers.CoffeeStore;
import controllers.Menu;
import java.io.IOException;

public class Program {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        CoffeeStore cfs = new CoffeeStore();

        cfs.loadDataFromFile();

        Menu menu = new Menu("\n------Coffee Store Management------");
        menu.addNewOption(" Manage Ingredients");
        menu.addNewOption(" Manage Drinks");
        menu.addNewOption(" Manage Dispensing Drinks");
        menu.addNewOption(" Reports");
        menu.addNewOption(" Save data");
        menu.addNewOption(" Exit");

        int choice;
        do {
            menu.printMenu();
            choice = menu.getChoice();
            switch (choice) {
                case 1:
                    cfs.manageIngredients();
                    break;
                case 2:
                    cfs.manageDrinks();
                    break;
                case 3:
                    cfs.manageDispensingDrinks();
                    break;
                case 4:
                    cfs.showReports();
                    break;
                case 5:
                    cfs.savaData();
                    break;
                case 6:
                    System.out.println("Good bye!!!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice >= 1 && choice <= 6);
    }
}
