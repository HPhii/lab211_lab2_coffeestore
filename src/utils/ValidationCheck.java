package utils;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ValidationCheck {

    public static final Scanner sc = new Scanner(System.in);

    public static boolean readBoolean(String msg) {
        String choice;
        while (true) {
            System.out.print(msg + ":");
            choice = sc.nextLine();
            if (choice.equalsIgnoreCase("Y")) {
                return true;
            } else if (choice.equalsIgnoreCase("N")) {
                return false;
            } else {
                System.err.println("Must be Y or N");
                continue;
            }
        }
    }

    public static String getStringWithPattern(String welcome, String pt) {
        boolean check = true;
        Pattern patternMatcher = Pattern.compile(pt);
        String result = "";
        do {
            System.out.print(welcome);
            result = sc.nextLine();
            if (result.isEmpty()) {
                System.out.println("Please don't leave blank");
            } else if (!patternMatcher.matcher(result).matches()) {
                System.out.println("Please input with pattern" + pt);
            } else {
                check = false;
            }
        } while (check);
        return result;
    }

    public static String inputStrWithPattern(String msg, String pattern) {
        String input;
        boolean valid = false;
        do {
            // yêu cầu người dùng nhập
            System.out.print(msg + ": ");
            input = sc.nextLine();
            // không đúng với pattern => valid = true
            valid = input.matches(pattern);
        } while (!valid);
        return input;
    }

    public static int readInteger(String msg, int x, int y) {
        System.out.print(msg);
        boolean check = true;
        int input;
        while (check) {
            try {
                input = Integer.parseInt(sc.nextLine());
                if (input < x || input > y) {
                    System.out.println("This number must be " + x + " to " + y);
                    check = true;
                } else {
                    return input;
                }
            } catch (NumberFormatException e) {
                System.out.println("This must be number");
                check = true;
            }
        }
        return 0;
    }

    public static double inputDouble(String prompt, double min, double max) {
        double result = 0;
        boolean valid = false;
        do {
            System.out.print(prompt + ": ");
            try {
                result = Double.parseDouble(sc.nextLine());
                valid = result >= min && result <= max;
            } catch (NumberFormatException e) {
                valid = false;
            }
            if (!valid) {
                System.err.println("Invalid input!");
            }
        } while (!valid);
        return result;
    }

    // đùng để format lại tên theo dạng title
    public static String formatTitleCase(String input) {
        String[] words = input.toLowerCase().split(" ");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
        }
        return result.toString().trim();
    }
}
