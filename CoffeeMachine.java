package machine;

import java.util.Scanner;

public class CoffeeMachine {

    private static String action = "";

    private static int water = 400;
    private static int milk = 540;
    private static int coffee = 120;
    private static int cups = 9;
    private static int money = 550;

    private static boolean flagAct = false;
    private static boolean flagFill = false;
    private static boolean flagBuy = false;

    private static boolean addWater = false;
    private static boolean addMilk = false;
    private static boolean addCoffee = false;
    private static boolean addCups = false;



    private static void chooseAction() {
        if (action.equals("remaining")) {
            printStatus();
            flagAct = false;
        } else if (action.equals("take")) {
            System.out.println("I gave you $" + money);
            money = 0;
            flagAct = false;
        } else if (action.equals("fill")) {
            flagFill = true;
        } else if (action.equals("buy")) {
            flagBuy = true;
        }
    }

    private static void makeBuy() {
        switch (action) {
            case "1":
                if (water >= 250 && coffee >= 16 && cups != 0) {
                    water -= 250;
                    coffee -= 16;
                    money += 4;
                    cups--;
                    System.out.println("I have enough resources, making you a coffee!");
                } else if (water < 250) {
                    System.out.println("Sorry, not enough water!");
                }
                break;
            case "2":
                if (water >= 350 && milk >= 75 && coffee >= 20 && cups != 0) {
                    water -= 350;
                    milk -= 75;
                    coffee -= 20;
                    money += 7;
                    cups--;
                    System.out.println("I have enough resources, making you a coffee!");
                } else if (water < 350) {
                    System.out.println("Sorry, not enough water!");
                }
                break;
            case "3":
                if (water >= 200 && milk >= 100 && coffee >= 12 && cups != 0) {
                    water -= 200;
                    milk -= 100;
                    coffee -= 12;
                    money += 6;
                    cups--;
                    System.out.println("I have enough resources, making you a coffee!");
                } else if (water < 250) {
                    System.out.println("Sorry, not enough water!");
                }
                break ;
        }
    }

    public static void makeFill(int add) {
        if (!addWater) {
            addWater = true;
            water += add;
        } else if (!addMilk) {
            addMilk = true;
            milk += add;
        } else if (!addCoffee) {
            addCoffee = true;
            coffee += add;
        } else if (!addCups) {
            addCups = true;
            cups += add;
        }
    }

    public static void takeInput(String act) {
        action = act;
        if (!flagAct) {
            flagAct = true;
            chooseAction();
        } else if (flagBuy) {
            makeBuy();
            flagBuy = false;
            flagAct = false;
        } else if (flagFill) {
            makeFill(Integer.parseInt(action));
            if (addWater && addMilk && addCoffee && addCups) {
                flagFill = false;
                flagAct = false;
                addCoffee = false;
                addCups = false;
                addWater = false;
                addMilk = false;
            }
        }
    }

    private static void printStatus() {
        System.out.println("The coffee machine has:");
        System.out.println(water + " ml of water");
        System.out.println(milk + " ml of milk");
        System.out.println(coffee + " g of coffee beans");
        System.out.println(cups + " disposable cups");
        System.out.println("$" + money + " of money");
    }

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        while (!action.equals("exit")) {
            String act = scanner.next();
            if (!flagAct) {
                System.out.println("Write action (buy, fill, take, remaining, exit):");
                takeInput(act);
            } else if (flagBuy) {
                System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                takeInput(act);
            } else if (!addWater && flagFill) {
                System.out.println("Write how many ml of water you want to add:");
                takeInput(act);
            } else if (!addMilk && flagFill) {
                System.out.println("Write how many ml of milk you want to add:");
                takeInput(act);
            } else if (!addCoffee && flagFill) {
                System.out.println("Write how many grams of coffee beans you want to add: ");
                takeInput(act);
            } else if (!addCups && flagFill) {
                System.out.println("Write how many disposable cups of coffee you want to add:");
                takeInput(act);
            }
            System.out.println();
        }
    }
}
