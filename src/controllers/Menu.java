package controllers;//user interface

import java.util.Scanner;
import dto.IMenu;

//menu sẽ lưu mảng các option để mình chọn
public class Menu implements IMenu {

    private static String title;
    private static String optionList[] = new String[100];
    private static int size = 0;

    //constructor nhận vào title
    public Menu(String title) {
        this.title = title;
    }

    //getter
    public String getTitle() {
        return title;
    }

    //1. addOption
    @Override
    public void addNewOption(String newOption) {
        optionList[size] = newOption;
        size++;
    }

    //2. printOption
    @Override
    public void printMenu() {
        System.out.println(getTitle());
        for (int i = 0; i <= size - 1; i++) {
            String str = String.format("%-1d.%s", i + 1, optionList[i]);
            System.out.println(str);
        }
    }

    //3. gotChoice
    //không cho người dùng lấy lựa chọn ngoài optionList
    @Override
    public int getChoice() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your choice: ");
        while (true) {
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
                if (choice < 1 || choice > size) {
                    throw new Exception();
                }
                return choice;
            } catch (Exception e) {
                System.err.println("Enter again [1 - " + size + "]");
            }
        }
    }
}
