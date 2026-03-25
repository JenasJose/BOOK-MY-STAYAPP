import java.util.*;

// Abstract Room class
abstract class Room {
    protected String roomType;
    protected int beds;
    protected int size; // in sqft
    protected double price;

    public Room(String roomType, int beds, int size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayDetails(int availability) {
        System.out.println(roomType + ":");
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqft");
        System.out.println("Price per night: " + price);
        System.out.println("Available: " + availability);
        System.out.println();
    }
}

// Concrete classes
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 250, 1500.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 400, 2500.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 750, 5000.0);
    }
}

// Inventory (centralized, read-only here)
class RoomInventory {
    private HashMap<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

// Search Service
class RoomSearchService {
    private RoomInventory inventory;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void searchRooms(List<Room> rooms) {
        System.out.println("Room Search\n");

        for (Room room : rooms) {
            int available = inventory.getAvailability(room.getRoomType());

            // Show only if available > 0
            if (available > 0) {
                room.displayDetails(available);
            }
        }
    }
}

// Main class
public class BookMyStayApp {
    public static void main(String[] args) {

        List<Room> rooms = new ArrayList<>();
        rooms.add(new SingleRoom());
        rooms.add(new DoubleRoom());
        rooms.add(new SuiteRoom());

        RoomInventory inventory = new RoomInventory();

        RoomSearchService searchService = new RoomSearchService(inventory);

        // Perform search (read-only)
        searchService.searchRooms(rooms);
    }
}