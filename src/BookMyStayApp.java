import java.io.*;
import java.util.*;

// Serializable Room class
class Room implements Serializable {
    private static final long serialVersionUID = 1L;
    int roomNumber;
    boolean isBooked;

    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
        this.isBooked = false;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " -> " + (isBooked ? "Booked" : "Available");
    }
}

// Serializable Hotel class with persistence methods
class Hotel implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Room> rooms;

    public Hotel(int totalRooms) {
        rooms = new ArrayList<>();
        for (int i = 1; i <= totalRooms; i++) {
            rooms.add(new Room(i));
        }
    }

    // Book a room
    public boolean bookRoom(String guestName) {
        for (Room room : rooms) {
            if (!room.isBooked) {
                room.isBooked = true;
                System.out.println("Room " + room.roomNumber + " booked successfully by " + guestName);
                return true;
            }
        }
        System.out.println("No rooms available for " + guestName);
        return false;
    }

    // Display room status
    public void displayRooms() {
        System.out.println("\nCurrent Room Status:");
        for (Room room : rooms) {
            System.out.println(room);
        }
    }

    // Save hotel state to file
    public void saveState(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(this);
            System.out.println("\nSystem state saved successfully to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    // Load hotel state from file
    public static Hotel loadState(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            Hotel hotel = (Hotel) in.readObject();
            System.out.println("System state restored from " + filename);
            return hotel;
        } catch (FileNotFoundException e) {
            System.out.println("No saved state found. Starting fresh.");
            return null;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error restoring state: " + e.getMessage());
            return null;
        }
    }
}

// Main class
public class BookMyStayApp {
    private static final String STATE_FILE = "hotel_state.ser";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Try to restore hotel state
        Hotel hotel = Hotel.loadState(STATE_FILE);

        if (hotel == null) {
            System.out.print("Enter total number of rooms in the hotel: ");
            int totalRooms = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            hotel = new Hotel(totalRooms);
        }

        while (true) {
            System.out.println("\n1. Book a Room");
            System.out.println("2. View Rooms");
            System.out.println("3. Save & Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter guest name: ");
                    String guestName = scanner.nextLine();
                    hotel.bookRoom(guestName);
                    break;
                case 2:
                    hotel.displayRooms();
                    break;
                case 3:
                    hotel.saveState(STATE_FILE);
                    System.out.println("Exiting system...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}