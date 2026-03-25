import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * UseCase3InventorySetup
 *
 * Demonstrates centralized room inventory management using HashMap.
 * This version accepts user input for initializing room availability.
 *
 * @author YourName
 * @version 3.1
 */

// Inventory Class
class RoomInventory {

    private HashMap<String, Integer> inventory;

    // Constructor initializes inventory
    public RoomInventory() {
        inventory = new HashMap<>();
    }

    // Add or update room availability
    public void setAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Get availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Display full inventory
    public void displayInventory() {
        System.out.println("\n--- Current Room Inventory ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("=======================================");
        System.out.println("   Book My Stay Application v3.1       ");
        System.out.println("=======================================\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // User input for room availability
        System.out.print("Enter available Single Rooms: ");
        int single = scanner.nextInt();

        System.out.print("Enter available Double Rooms: ");
        int dbl = scanner.nextInt();

        System.out.print("Enter available Suite Rooms: ");
        int suite = scanner.nextInt();

        // Store in HashMap (centralized)
        inventory.setAvailability("Single Room", single);
        inventory.setAvailability("Double Room", dbl);
        inventory.setAvailability("Suite Room", suite);

        // Display inventory
        inventory.displayInventory();

        // Example retrieval
        System.out.println("\nAvailable Single Rooms: " +
                inventory.getAvailability("Single Room"));

        scanner.close();
    }
}