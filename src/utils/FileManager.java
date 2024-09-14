package utils;

import java.util.ArrayList;
import java.util.List;
import dto.DispensingDrink;
import dto.Drink;
import dto.Ingredient;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;

public class FileManager {

    private static final String ingrePath = new File("").getAbsolutePath() + "\\Ingredients.dat";
    private static final String drinkPath = new File("").getAbsolutePath() + "\\Menu.dat";
    private final static String orderPath = new File("").getAbsolutePath() + "\\Order.dat";
    private final static String drinksPath = new File("").getAbsolutePath() + "\\Menu.txt";

    // Generic load function
    private static <T> List<T> load(String filePath) throws IOException, ClassNotFoundException {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (List<T>) in.readObject();
        }
    }

    // Generic store function
    private static <T> void store(List<T> data, String filePath) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(data);
        }
    }

    // Store ingredients to file
//    public static void storeIngredients(List<Ingredient> ingredients) throws IOException {
//        store(ingredients, ingrePath);
//    }
// Store ingredients to file with SHA-256 encoding
    public static void storeIngredients(List<Ingredient> ingredients) throws IOException {
        List<String> encodedIngredients = new ArrayList<>();
        for (Ingredient i : ingredients) {
            String encodedIngredient = getSha256(i.toString());
            encodedIngredients.add(encodedIngredient);
        }
        store(encodedIngredients, ingrePath);
    }

    // Store drinks to file
    public static void storeDrinks(List<Drink> drinks) throws IOException {
        store(drinks, drinkPath);
    }

    // Store dispensing drink information to file
    public static void storeDispensingDrinks(List<DispensingDrink> dispensingDrinks) throws IOException {
        store(dispensingDrinks, orderPath);
    }

    // Load ingredients from file
    public static List<Ingredient> loadIngredients() throws IOException, ClassNotFoundException {
        return load(ingrePath);
    }

    // Load drinks from file
    public static List<Drink> loadDrinks() throws IOException, ClassNotFoundException {
        return load(drinkPath);
    }

    // Load dispensing drink information from file
    public static List<DispensingDrink> loadDispensingDrinks() throws IOException, ClassNotFoundException {
        return load(orderPath);
    }

    public static String getSha256(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(value.getBytes());
            return bytesToHex(md.digest());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte b : bytes) {
            result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }

    // Store dispensing drink information to a text file
    public static void storeDrinktxt(List<Drink> drinks) throws IOException {
        try (FileWriter writer = new FileWriter(drinksPath)) {
            for (Drink drink : drinks) {
                writer.write(drink.toString());
            }
        }
    }

}
