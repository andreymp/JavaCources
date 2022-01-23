package budget;

import java.util.*;
import java.io.*;

public class Budget {

    private static double balance = 0;
    private static double[] purchaseBalance = {0, 0, 0, 0};
    private static final HashMap<String, ArrayList<String>> list = new HashMap<>();
    private static final String path = "purchases.txt";
    private static final File file = new File(path);

    public static double getBalance() {
        return balance;
    }

    public static void addIncome(double income) {
        balance += income;
    }

    public static void showListOfPurchase(Scanner sc) {
        if (list.isEmpty())
            System.out.println("The purchase list is empty\n");
        else {
            while (true) {
                String key = Menu.optionToShow(sc);
                if (key.equals("back")) {
                    System.out.println();
                    return;
                }
                if (!key.equals("All")) {
                    System.out.println("\n" + key + ":");
                    if (list.get(key).isEmpty())
                        System.out.println("The purchase list is empty\n");
                    else {
                        for (String str : list.get(key))
                            System.out.println(str);
                        System.out.printf("Total sum: $%.2f\n\n", purchaseBalance[transformToIndex(key)]);
                    }
                } else {
                    System.out.println("\n" + key + ":");
                    for (String keys : list.keySet())
                        for (String str : list.get(keys))
                            System.out.println(str);
                    System.out.printf("Total sum: $%.2f\n\n", summa(purchaseBalance));
                }
            }
        }
    }

    private static double summa(double[] arr) {
        double sum = 0;
        for (var dig : arr)
            sum += dig;
        return sum;
    }

    private static int transformToIndex(String option) {
        switch (option) {
            case "Food":
                return 0;
            case "Clothes":
                return 1;
            case "Entertainment":
                return 2;
            case "Other":
                return 3;
        }
        return -1;
    }

    public static void addPurchase(Scanner sc) {
        while (true) {
            String key = Menu.showPurchase(sc);
            if (key.equals("back")) {
                System.out.println();
                break;
            }
            if (!list.containsKey(key))
                list.put(key, new ArrayList<>());
            System.out.println("\n" + key);
            System.out.println("Enter purchase name:");
            String item = sc.nextLine();
            System.out.println("Enter its price:");
            double price = Double.parseDouble(sc.nextLine());
            list.get(key).add(String.format("%s $%.2f", item, price));
            purchaseBalance[transformToIndex(key)] += price;
            balance -= price;
            System.out.println("Purchase was added!\n");
        }
    }

    public static void safe() {
        try {
            file.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, false));
            writer.write((String.format("%.2f\n", balance)));
            if (!list.isEmpty()) {
                for (String key : list.keySet()) {
                    writer.write(key + "\n");
                    for (String str : list.get(key))
                        writer.write(str + "\n");
                    writer.write(String.format("%.2f\n", purchaseBalance[transformToIndex(key)]));
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        try (Scanner scanner = new Scanner(file)) {
            if (!scanner.hasNext())
                return ;
            balance = Double.parseDouble(scanner.nextLine());
            while (scanner.hasNext()) {
                String key = scanner.nextLine();
                list.put(key, new ArrayList<>());
                while (!scanner.hasNextDouble())
                    list.get(key).add(scanner.nextLine());
                purchaseBalance[transformToIndex(key)] = Double.parseDouble(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
