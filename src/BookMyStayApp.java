/**
 * UseCase2RoomInitialization
 *
 * This class demonstrates basic object-oriented modeling using abstraction,
 * inheritance, and polymorphism for a Hotel Booking System.
 *
 * @author YourName
 * @version 2.1
 */

// Abstract Room class
abstract class Room {
    protected String roomType;
    protected int numberOfBeds;
    protected double pricePerNight;

    // Constructor
    public Room(String roomType, int numberOfBeds, double pricePerNight) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.pricePerNight = pricePerNight;
    }

    // Method to display room details
    public void displayRoomDetails() {
        System.out.println("Room Type       : " + roomType);
        System.out.println("Number of Beds  : " + numberOfBeds);
        System.out.println("Price per Night : ₹" + pricePerNight);
    }
}

// Single Room class
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 2000.0);
    }
}

// Double Room class
class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 3500.0);
    }
}

// Suite Room class
class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 6000.0);
    }
}

// Main Application Class
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("   Book My Stay Application v2.1       ");
        System.out.println("=======================================\n");

        // Create Room objects (Polymorphism)
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Static availability (simple variables)
        int singleRoomAvailability = 5;
        int doubleRoomAvailability = 3;
        int suiteRoomAvailability = 2;

        // Display details
        System.out.println("---- Room Details & Availability ----\n");

        singleRoom.displayRoomDetails();
        System.out.println("Available Rooms : " + singleRoomAvailability + "\n");

        doubleRoom.displayRoomDetails();
        System.out.println("Available Rooms : " + doubleRoomAvailability + "\n");

        suiteRoom.displayRoomDetails();
        System.out.println("Available Rooms : " + suiteRoomAvailability + "\n");

        System.out.println("=======================================");
    }
}