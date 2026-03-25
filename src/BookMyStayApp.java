import java.util.*;

// Reservation class to hold guest booking request details
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

// Inventory service maintaining room availability
class InventoryService {
    private Map<String, Integer> availability = new HashMap<>();

    public InventoryService(int singleCount, int doubleCount, int suiteCount) {
        availability.put("Single", singleCount);
        availability.put("Double", doubleCount);
        availability.put("Suite", suiteCount);
    }

    // Check availability for room type
    public boolean isAvailable(String roomType) {
        return availability.getOrDefault(roomType, 0) > 0;
    }

    // Decrement inventory count by one after allocation
    public void decrementAvailability(String roomType) {
        int count = availability.getOrDefault(roomType, 0);
        if (count > 0) {
            availability.put(roomType, count - 1);
        }
    }

    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }
}

// Booking service processes reservation queue and allocates rooms
class BookingService {
    private InventoryService inventoryService;
    private Queue<Reservation> bookingQueue;
    // Maps room type to set of allocated room IDs to prevent double booking
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();

    public BookingService(InventoryService inventoryService, Queue<Reservation> bookingQueue) {
        this.inventoryService = inventoryService;
        this.bookingQueue = bookingQueue;
    }

    // Processes booking requests FIFO, assigns unique room IDs, updates inventory
    public void processBookings() {
        System.out.println("Room Allocation Processing");

        while (!bookingQueue.isEmpty()) {
            Reservation reservation = bookingQueue.poll();
            String roomType = reservation.getRoomType();

            if (inventoryService.isAvailable(roomType)) {
                // Allocate room ID uniquely (e.g., Single-1, Single-2 ...)
                int allocatedCount = allocatedRooms.getOrDefault(roomType, new HashSet<>()).size() + 1;
                String roomId = roomType + "-" + allocatedCount;

                // Ensure allocatedRooms set exists for this type
                allocatedRooms.putIfAbsent(roomType, new HashSet<>());

                // Check for uniqueness just in case (should always be unique)
                if (!allocatedRooms.get(roomType).contains(roomId)) {
                    allocatedRooms.get(roomType).add(roomId);
                    inventoryService.decrementAvailability(roomType);

                    System.out.println("Booking confirmed for Guest: " + reservation.getGuestName() + ", Room ID: " + roomId);
                }
            } else {
                System.out.println("Booking failed for Guest: " + reservation.getGuestName() + ". No " + roomType + " rooms available.");
            }
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize room availability (you can customize these numbers or ask user input if needed)
        System.out.print("Enter available Single rooms: ");
        int singleCount = scanner.nextInt();

        System.out.print("Enter available Double rooms: ");
        int doubleCount = scanner.nextInt();

        System.out.print("Enter available Suite rooms: ");
        int suiteCount = scanner.nextInt();
        scanner.nextLine(); // consume newline

        InventoryService inventoryService = new InventoryService(singleCount, doubleCount, suiteCount);

        // Booking request queue
        Queue<Reservation> bookingQueue = new LinkedList<>();

        System.out.print("Enter number of booking requests: ");
        int n = scanner.nextInt();
        scanner.nextLine(); // consume newline

        for (int i = 1; i <= n; i++) {
            System.out.println("\nEnter details for Guest " + i + ":");
            System.out.print("Guest Name: ");
            String guestName = scanner.nextLine();

            System.out.print("Room Type (Single/Double/Suite): ");
            String roomType = scanner.nextLine();

            // Add booking request to queue
            bookingQueue.offer(new Reservation(guestName, roomType));
        }

        // Process booking requests
        BookingService bookingService = new BookingService(inventoryService, bookingQueue);
        bookingService.processBookings();

        scanner.close();
    }
}