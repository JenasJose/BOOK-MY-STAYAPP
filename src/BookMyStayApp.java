import java.util.*;

// Reservation class holds booking details
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// InventoryService manages availability counts for room types
class InventoryService {
    private Map<String, Integer> availability = new HashMap<>();

    public InventoryService(int single, int dbl, int suite) {
        availability.put("Single", single);
        availability.put("Double", dbl);
        availability.put("Suite", suite);
    }

    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }

    public void incrementAvailability(String roomType) {
        availability.put(roomType, availability.getOrDefault(roomType, 0) + 1);
    }

    public boolean decrementAvailability(String roomType) {
        int count = availability.getOrDefault(roomType, 0);
        if (count > 0) {
            availability.put(roomType, count - 1);
            return true;
        }
        return false;
    }
}

// BookingHistory stores confirmed reservations in insertion order
class BookingHistory {
    private Map<String, Reservation> reservations = new HashMap<>();

    public void addReservation(Reservation reservation) {
        reservations.put(reservation.getReservationId(), reservation);
    }

    public boolean contains(String reservationId) {
        return reservations.containsKey(reservationId);
    }

    public Reservation getReservation(String reservationId) {
        return reservations.get(reservationId);
    }

    public void removeReservation(String reservationId) {
        reservations.remove(reservationId);
    }

    public Collection<Reservation> getAllReservations() {
        return reservations.values();
    }
}

// CancellationService processes cancellations and rolls back inventory
class CancellationService {
    private BookingHistory bookingHistory;
    private InventoryService inventoryService;
    private Stack<String> rollbackStack = new Stack<>();

    public CancellationService(BookingHistory bookingHistory, InventoryService inventoryService) {
        this.bookingHistory = bookingHistory;
        this.inventoryService = inventoryService;
    }

    public boolean cancelBooking(String reservationId) {
        if (!bookingHistory.contains(reservationId)) {
            System.out.println("Cancellation failed: Reservation ID not found.");
            return false;
        }
        Reservation reservation = bookingHistory.getReservation(reservationId);

        // Rollback inventory
        inventoryService.incrementAvailability(reservation.getRoomType());

        // Record rollback
        rollbackStack.push(reservationId);

        // Remove reservation from history
        bookingHistory.removeReservation(reservationId);

        System.out.println("Booking cancelled: Reservation ID " + reservationId +
                ", Guest: " + reservation.getGuestName() +
                ", Room Type: " + reservation.getRoomType());
        return true;
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize inventory and booking history with sample data
        InventoryService inventoryService = new InventoryService(2, 2, 1);

        BookingHistory bookingHistory = new BookingHistory();
        // Prepopulate booking history
        bookingHistory.addReservation(new Reservation("Single-1", "Abhi", "Single"));
        bookingHistory.addReservation(new Reservation("Double-1", "Subha", "Double"));
        bookingHistory.addReservation(new Reservation("Suite-1", "Vanmathi", "Suite"));

        // Decrement inventory to reflect pre-bookings
        inventoryService.decrementAvailability("Single");
        inventoryService.decrementAvailability("Double");
        inventoryService.decrementAvailability("Suite");

        CancellationService cancellationService = new CancellationService(bookingHistory, inventoryService);

        System.out.println("Current bookings:");
        for (Reservation r : bookingHistory.getAllReservations()) {
            System.out.println("Reservation ID: " + r.getReservationId() + ", Guest: " + r.getGuestName() + ", Room Type: " + r.getRoomType());
        }

        System.out.print("\nEnter number of cancellations to process: ");
        int cancelCount = scanner.nextInt();
        scanner.nextLine(); // consume newline

        for (int i = 1; i <= cancelCount; i++) {
            System.out.print("Enter Reservation ID to cancel (" + i + "): ");
            String resId = scanner.nextLine();

            cancellationService.cancelBooking(resId);
        }

        System.out.println("\nUpdated booking list:");
        for (Reservation r : bookingHistory.getAllReservations()) {
            System.out.println("Reservation ID: " + r.getReservationId() + ", Guest: " + r.getGuestName() + ", Room Type: " + r.getRoomType());
        }

        System.out.println("\nCurrent inventory:");
        System.out.println("Single Rooms Available: " + inventoryService.getAvailability("Single"));
        System.out.println("Double Rooms Available: " + inventoryService.getAvailability("Double"));
        System.out.println("Suite Rooms Available: " + inventoryService.getAvailability("Suite"));

        scanner.close();
    }
}