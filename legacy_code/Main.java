
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; // For user input
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



public class Main {
    public static void main(String[] args) {

        // User input
        System.out.println("Welcome to FocusPlanner, your Daily Planner!");
        Scanner userInput = new Scanner(System.in);

        // choice variable to hold user selection
        int choice;


        // defining a list that contains the date and entry
        List<Entry> entryList = new ArrayList<>();


        // Menu Implementation : Uses cases to use selected application feature
        do {
            choice = getChoice(userInput);

            switch (choice) {
                case 1:
                    currentDate();
                    choice = getChoice(userInput);
                    break;

                case 2:
                    // Create a new Event object
                    Entry entry = new Entry();

                    // Prompt the user for input and populate the Entry object
                    System.out.println("Enter the day:");

                    int day = userInput.nextInt();
                    entry.setDay(day);

                    // Consume the leftover newline
                    userInput.nextLine();

                    System.out.println("Enter the month:");
                    String month = userInput.nextLine();
                    entry.setMonth(month);

                    System.out.println("Enter the year:");
                    int year = userInput.nextInt();
                    entry.setYear(year);

                    // Consume the leftover newline
                    userInput.nextLine();

                    System.out.println("Enter the description:");
                    String description = userInput.nextLine();
                    entry.setDescription(description);
                    System.out.println();

                    // Print the populated event
                    System.out.println("Entry Details:\n");
                    System.out.println(entry);
                    System.out.println();
                    entryList.add(entry);

                    break;

                case 3: // prints the list
                    System.out.println("Displaying the Current Days that are marked!\n");
                    int entryCounter = 0;
                    for (Entry e : entryList) {
                        System.out.printf("Entry number %d: \n", entryCounter);
                        System.out.println(e);
                        System.out.println();
                        entryCounter++;
                    }
                    break;

                case 5:
                    System.out.println("Exiting FocusPlanner!");
                    System.out.println();
                    break;
            }
        } while (choice != 5);
    }

    private static int getChoice(Scanner userInput) {
        System.out.println("Menu: \n 1. Display Current Date & Time \n 2. Add Calendar Entry \n 3. Print Entries \n 5. Exit The Program \n");
        System.out.println("Please enter your choice: ");
        int choice = userInput.nextInt();
        System.out.println("You have selected " + choice + "!\n");
        return choice;
    }

    private static void currentDate() {
        // Get the current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Format the date and time for display
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);

        System.out.println("Current Date and Time: " + formattedDateTime +"\n");
    }
}