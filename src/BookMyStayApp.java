import java.util.*;

// Custom exception for invalid bookings
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Inventory service manages room availability with validation
class InventoryService {
    private Map<String, Integer> availability = new HashMap<>();
    private static final Set<String> VALID_ROOM_TYPES = Set.of("Single", "Double", "Suite");

    public InventoryService(int singleCount, int doubleCount, int suiteCount) {
        availability.put("Single", singleCount);
        availability.put("Double", doubleCount);
        availability.put("Suite", suiteCount);
    }

    // Validate room type
    public void validateRoomType(String roomType) throws InvalidBookingException {
        if (!VALID_ROOM_TYPES.contains(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
    }

    // Check if room is available for booking
    public boolean isAvailable(String roomType) throws InvalidBookingException {
        validateRoomType(roomType);
        int count = availability.getOrDefault(roomType, 0);
        if (count <= 0) {
            throw new InvalidBookingException("No availability for room type: " + roomType);
        }
        return true;
    }

    // Decrement availability safely
    public void decrementAvailability(String roomType) throws InvalidBookingException {
        validateRoomType(roomType);
        int count = availability.getOrDefault(roomType, 0);
        if (count <= 0) {
            throw new InvalidBookingException("Cannot decrement availability below zero for: " + roomType);
        }
        availability.put(roomType, count - 1);
    }

    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }
}

// Reservation class stores booking info
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize inventory (example values)
        InventoryService inventoryService = new InventoryService(2, 2, 1);

        // Queue for booking requests
        Queue<Reservation> bookingQueue = new LinkedList<>();

        System.out.print("Enter number of booking requests: ");
        int n = scanner.nextInt();
        scanner.nextLine(); // consume newline

        // Accept booking inputs with validation
        for (int i = 1; i <= n; i++) {
            System.out.println("\nBooking request " + i + ":");
            System.out.print("Guest Name: ");
            String guestName = scanner.nextLine();

            System.out.print("Room Type (Single/Double/Suite): ");
            String roomType = scanner.nextLine();

            try {
                inventoryService.validateRoomType(roomType);
                bookingQueue.offer(new Reservation(guestName, roomType));
            } catch (InvalidBookingException e) {
                System.out.println("Error: " + e.getMessage() + ". Booking request rejected.");
                // Optionally reduce i to retry the same booking number or skip to next
            }
        }

        // Process bookings with validation and error handling
        System.out.println("\nProcessing bookings...");
        while (!bookingQueue.isEmpty()) {
            Reservation r = bookingQueue.poll();
            try {
                if (inventoryService.isAvailable(r.getRoomType())) {
                    inventoryService.decrementAvailability(r.getRoomType());
                    System.out.println("Booking confirmed for Guest: " + r.getGuestName() + ", Room Type: " + r.getRoomType());
                }
            } catch (InvalidBookingException e) {
                System.out.println("Booking failed for Guest: " + r.getGuestName() + ". Reason: " + e.getMessage());
            }
        }

        scanner.close();
    }
}