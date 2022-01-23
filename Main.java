package phonebook;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Main {
    static long startTime;
    static long finishTime;

    public static void main(String[] args) {

        PhoneBook.addContactsFromTxt("C:\\Users\\marki\\IdeaProjects\\Phone Book\\directory.txt");//filling List of contacts
        PhoneBook.readSearchedValues("C:\\Users\\marki\\IdeaProjects\\Phone Book\\find.txt");//filling List of searching names
        linearSearch();
        BubbleSortAndJumpOrLinearSearch();
        PhoneBook.addContactsFromTxt("C:\\Users\\marki\\IdeaProjects\\Phone Book\\directory.txt");
        quickSortAndBinarySearch();
        PhoneBook.addContactsFromTxt("C:\\Users\\marki\\IdeaProjects\\Phone Book\\directory.txt");
        hashSearch();
    }

    static void hashSearch() {
        System.out.println("Start searching (hash table)...");
        startTime = System.currentTimeMillis();
        String hashing = PhoneBook.addContactsToHash();
        String searching = PhoneBook.hashFinding();
        finishTime = System.currentTimeMillis() - startTime;
        System.out.println("Time taken: "+ (finishTime / 1000 / 60) + " min. " + (finishTime / 1000 % 60) + " sec. "+(finishTime % 1000)+" ms.");
        System.out.println(hashing);
        System.out.println(searching);
    }

    static void linearSearch () {
        System.out.println("Start searching (linear search)...");
        startTime = System.currentTimeMillis();
        PhoneBook.contactsLinearSearch();
        finishTime = System.currentTimeMillis() - startTime;
        System.out.println("Time taken: "+ (finishTime / 1000 / 60) + " min. " + (finishTime / 1000 % 60) + " sec. "+(finishTime % 1000)+" ms.\n");
    }

    static void BubbleSortAndJumpOrLinearSearch () {
        System.out.println("Start searching (bubble sort + jump search)...");
        String linearSearchLog = "";
        String jumpSearchLog = "";
        startTime = System.currentTimeMillis();
        String SortingLog = PhoneBook.bubbleSort(finishTime);
        if(SortingLog.contains("STOPPED")) {  //if Sorting was stopped
            linearSearchLog = PhoneBook.contactsLinearSearch();
        } else {
            jumpSearchLog = PhoneBook.contactsJumpSearch();
        }
        finishTime = System.currentTimeMillis() - startTime;
        System.out.println("Time taken: "+ (finishTime / 1000 / 60) + " min. " + (finishTime / 1000 % 60) + " sec. "+(finishTime % 1000)+" ms.");
        System.out.println(SortingLog);
        if (!linearSearchLog.equals("")) {
            System.out.println(linearSearchLog);
        }
        if (!jumpSearchLog.equals("")) {
            System.out.println(jumpSearchLog);
        }
    }

    static void quickSortAndBinarySearch() {
        System.out.println("Start searching (quick sort + binary search)...");
        startTime = System.currentTimeMillis();
        String sorting = PhoneBook.quickSort();
        String searching = PhoneBook.contactBinarySearch();
        finishTime = System.currentTimeMillis() - startTime;
        System.out.println("Time taken: "+ (finishTime / 1000 / 60) + " min. " + (finishTime / 1000 % 60) + " sec. "+(finishTime % 1000)+" ms.");
        System.out.println(sorting);
        System.out.println(searching + "\n");
    }
}


class PhoneBook {

    static List<Contact> listOfContacts = new ArrayList<>();        //stores <String - Name, Integer - Number>
    static List<String> listOfSearchedValues = new ArrayList<>();   //stores <String> contact names
    static Hashtable<String, Integer> tableOfContacts = new Hashtable<>();

    public static String hashFinding() {
        int total = 0;
        int counter = 0;
        long startTime = System.currentTimeMillis();
        for (var val : listOfSearchedValues) {
            if (tableOfContacts.containsKey(val)) {
                counter++;
            }
            total++;
        }
        System.out.print("Found " + counter + " / " + total + " entries. ");
        long endTime = System.currentTimeMillis() - startTime;
        return "Searching time: "+ (endTime / 1000 / 60) + " min. " + (endTime / 1000 % 60) + " sec. "+(endTime % 1000)+" ms.\n";
    }

    public static String addContactsToHash() {
        long startTime = System.currentTimeMillis();
        for (var contacts : listOfContacts) {
            tableOfContacts.put(contacts.name, contacts.number);
        }
        long endTime = System.currentTimeMillis() - startTime;
        return "Creating time: "+ (endTime / 1000 / 60) + " min. " + (endTime / 1000 % 60) + " sec. "+(endTime % 1000)+" ms.";
    }

    public static void addContactsFromTxt(String path) {             //putting values from .txt file to setOfContacts
        File contactsFile = new File(path);
        try {
            listOfContacts.clear();
            Scanner sc = new Scanner(contactsFile);
            while (sc.hasNext()) {
                int tempNumber = sc.nextInt();
                String tempName = sc.nextLine().trim();
                listOfContacts.add(new Contact(tempName, tempNumber));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not Found");
        }
    }

    private static void swap(int i, int j) {
        Contact tmp = listOfContacts.get(i);
        listOfContacts.set(i,listOfContacts.get(j));
        listOfContacts.set(j,tmp);
    }

    private static int partition(int low, int high) {
        Contact pivot = listOfContacts.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (listOfContacts.get(j).name.compareTo(pivot.name) < 0) {
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, high);
        return i + 1;
    }

    private static void quickSorting(int low, int high) {
        if (low < high) {
            int pi = partition(low, high);
            quickSorting(low, pi - 1);
            quickSorting(pi + 1, high);
        }
    }

    public static String quickSort() {
        long startTime = System.currentTimeMillis();
        quickSorting(0, listOfContacts.size() - 1);
        long endTime = System.currentTimeMillis() - startTime;
        return "Sorting time: "+ (endTime / 1000 / 60) + " min. " + (endTime / 1000 % 60) + " sec. "+(endTime % 1000)+" ms.";
    }

    private static String binarySearch(int begin, int last, String value) {
        if (last >= begin) {
            int middle = begin + (last - begin) / 2;
            if (listOfContacts.get(middle).name.equals(value)) {
                return listOfContacts.get(middle).name;
            }
            if (listOfContacts.get(middle).name.compareTo(value) > 0) {
                return binarySearch(begin, middle - 1, value);
            }
            return binarySearch(middle + 1, last, value);
        }
        return "No such contact";
    }

    public static String contactBinarySearch() {
        long startTime = System.currentTimeMillis();
        int total = 0;
        int counter = 0;
        for (String val : listOfSearchedValues) {
            String someone = binarySearch(0, listOfContacts.size() - 1, val);
            if (someone.equals(val)) {
                counter++;
            }
            total++;
        }
        System.out.print("Found " + counter + " / " + total + " entries. ");
        long endTime = System.currentTimeMillis() - startTime;
        return "Searching time: "+ (endTime / 1000 / 60) + " min. " + (endTime / 1000 % 60) + " sec. "+(endTime % 1000)+" ms.";
    }

    public static String contactsLinearSearch() {            //finding names from .txt files, counting them, and printing amount
        long startTime = System.currentTimeMillis();
        int foundValuesCount = 0;
        int searchedValuesCount = 0;
        for (String str : listOfSearchedValues) {
            for (Contact c : listOfContacts) {
                if (str.equals(c.name)) {
                    foundValuesCount++;
                }
            }
            searchedValuesCount++;
        }
        System.out.print("Found " + foundValuesCount + " / " + searchedValuesCount + " entries. ");
        long endTime = System.currentTimeMillis() - startTime;
        return "Searching time: "+ (endTime / 1000 / 60) + " min. " + (endTime / 1000 % 60) + " sec. "+(endTime % 1000)+" ms.";
    }

    public static void readSearchedValues(String path) {
        try {
            Scanner sc = new Scanner(new File(path));

            while (sc.hasNext()) {
                listOfSearchedValues.add(sc.nextLine().trim());
            }

        } catch (FileNotFoundException e) {
            System.out.println("FileNot found!");
        }
    }

    public static String bubbleSort(long timeLimit) { //returns log message about execution duration
        timeLimit *= 10;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < listOfContacts.size(); i++) {

            long searchingTime = System.currentTimeMillis() - startTime;
            if(timeLimit < searchingTime) {     // if sorting time is longer than 10 times linear search time, stopping sorting and retuning sorting time.
                return "Sorting time: "+ (searchingTime / 1000 / 60) + " min. " + (searchingTime / 1000 % 60) + " sec. "+(searchingTime % 1000)+" ms. - STOPPED, moved to linear search";
            }

            for (int j = i + 1; j < listOfContacts.size(); j++) {
                if (listOfContacts.get(j).name.compareTo(listOfContacts.get(i).name) < 0) {
                    Contact temp = listOfContacts.get(i);
                    listOfContacts.set(i,listOfContacts.get(j));
                    listOfContacts.set(j,temp);
                }
            }
        }
        long searchingTime = System.currentTimeMillis() - startTime;
        return  "Sorting time: "+ (searchingTime / 1000 / 60) + " min. " + (searchingTime / 1000 % 60) + " sec. "+(searchingTime % 1000)+" ms.";

    }

    public static String contactsJumpSearch() { //returns log message about execution duration
        long startTime = System.currentTimeMillis();
        int foundValuesCount = 0;
        int searchedValuesCount = 0;
        for (String str : listOfSearchedValues) {
            searchedValuesCount++;
            int step = (int) Math.sqrt(listOfContacts.size());
            int start = 0;
            int end = step;

            while (str.compareTo(listOfContacts.get(end).name) > 0) {//jumps through values until finds value bigger or equal to searched one
                start = end;
                if(end + step <= listOfContacts.size()){
                    end = listOfContacts.size() - 1;
                } else {
                    end += step;
                }
            }

            for (int i = end; i >= start; i--){//reversed linear search in block of values
                if (listOfContacts.get(i).name.equals(str)) {
                    foundValuesCount++;
                }
            }
        }
        System.out.print("Found " + foundValuesCount + " / " + searchedValuesCount + " entries. ");
        long endTime = System.currentTimeMillis() - startTime;
        return "Searching time: "+ (endTime / 1000 / 60) + " min. " + (endTime / 1000 % 60) + " sec. "+(endTime % 1000)+" ms.";
    }
}
class Contact {
    String name;
    int number;

    public Contact(String name, int number) {
        this.name = name;
        this.number = number;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", number=" + number +
                '}';
    }
}