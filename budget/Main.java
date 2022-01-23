package budget;

import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    private static void handleInput(int option) {
        switch (option) {
            case 1:
                Menu.showIncomeMessage(sc);
                break ;
            case 2:
                Budget.addPurchase(sc);
                break ;
            case 3:
                Budget.showListOfPurchase(sc);
                break ;
            case 4:
                System.out.println("Balance: $" + Double.toString(Budget.getBalance()) + "\n");
                break ;
            case 5:
                Budget.safe();
                System.out.println("Purchases were saved!\n");
                break ;
            case 6:
                Budget.load();
                System.out.println("Purchases were loaded!\n");
                break ;
        }
    }

    public static void main(String[] args) {
        while (true) {
            Menu.show();
            int number = Integer.parseInt(sc.nextLine());
            if (number == 0) {
                System.out.println("\nBye!");
                break ;
            }
            System.out.println();
            handleInput(number);
        }
    }
}
