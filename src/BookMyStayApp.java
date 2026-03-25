import java.util.*;
import java.util.concurrent.*;

// Class representing a hotel room
class Room {
    int roomNumber;
    boolean isBooked;

    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
        this.isBooked = false;
    }
}

// Class representing a booking request
class BookingRequest {
    String guestName;

    public BookingRequest(String guestName) {
        this.guestName = guestName;
    }
}

// Class representing the hotel with synchronized booking
class Hotel {
    private List<Room> rooms;

    public Hotel(int totalRooms) {
        rooms = new ArrayList<>();
        for (int i = 1; i <= totalRooms; i++) {
            rooms.add(new Room(i));
        }
    }

    // Thread-safe booking method
    public synchronized boolean bookRoom(String guestName) {
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

    // Display current room status
    public void displayRooms() {
        System.out.println("\nCurrent Room Status:");
        for (Room room : rooms) {
            System.out.println("Room " + room.roomNumber + " -> " + (room.isBooked ? "Booked" : "Available"));
        }
    }
}

// Runnable task for booking a room
class BookingTask implements Runnable {
    private Hotel hotel;
    private BookingRequest request;

    public BookingTask(Hotel hotel, BookingRequest request) {
        this.hotel = hotel;
        this.request = request;
    }

    @Override
    public void run() {
        hotel.bookRoom(request.guestName);
    }
}

// Main class
public class BookMyStayApp
{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter total number of rooms in the hotel: ");
        int totalRooms = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter number of guests trying to book simultaneously: ");
        int guestCount = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Hotel hotel = new Hotel(totalRooms);

        List<Thread> threads = new ArrayList<>();

        for (int i = 1; i <= guestCount; i++) {
            System.out.print("Enter name of Guest " + i + ": ");
            String guestName = scanner.nextLine();
            BookingRequest request = new BookingRequest(guestName);
            Thread t = new Thread(new BookingTask(hotel, request));
            threads.add(t);
        }

        // Start all threads (simulate concurrent booking)
        for (Thread t : threads) {
            t.start();
        }

        // Wait for all threads to finish
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Display final room allocation
        hotel.displayRooms();
        scanner.close();
    }
}