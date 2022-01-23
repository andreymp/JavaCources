package budget;

import java.util.Scanner;

public class Menu {

    public static void show() {
        System.out.println("Choose your action:");
        System.out.println("1) Add income");
        System.out.println("2) Add purchase");
        System.out.println("3) Show list of purchases");
        System.out.println("4) Balance");
        System.out.println("5) Save");
        System.out.println("6) Load");
        System.out.println("7) Analyze (Sort)");
        System.out.println("0) Exit");
    }

    private static void showPurchaseAddMenu() {
        System.out.println("Choose the type of purchase");
        System.out.println("1) Food");
        System.out.println("2) Clothes");
        System.out.println("3) Entertainment");
        System.out.println("4) Other");
        System.out.println("5) Back");
    }

    private static void  showPurchaseMenu() {
        System.out.println("Choose the type of purchase");
        System.out.println("1) Food");
        System.out.println("2) Clothes");
        System.out.println("3) Entertainment");
        System.out.println("4) Other");
        System.out.println("5) All");
        System.out.println("6) Back");
    }

    public static String optionToShow(Scanner sc) {
        showPurchaseMenu();
        int option = Integer.parseInt(sc.nextLine());
        switch (option) {
            case 1:
                return "Food";
            case 2:
                return "Clothes";
            case 3:
                return "Entertainment";
            case 4:
                return "Other";
            case 5:
                return "All";
            case 6:
                return "back";
        }
        return "";
    }

    public static void showIncomeMessage(Scanner sc) {
        System.out.println("Enter income:");
        int income = Integer.parseInt(sc.nextLine());
        Budget.addIncome(income);
        System.out.println("Income was added!\n");
    }

    public static String showPurchase(Scanner sc) {
        showPurchaseAddMenu();
        String  option = sc.nextLine();
        switch (Integer.parseInt(option)) {
            case 1:
                return "Food";
            case 2:
                return "Clothes";
            case 3:
                return "Entertainment";
            case 4:
                return "Other";
            case 5:
                return "back";
        }
        return "";
    }
}
