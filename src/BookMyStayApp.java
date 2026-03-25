import java.util.*;

// Reservation class (represents a booking request)
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

// Main class
public class BookMyStayApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Queue to store booking requests (FIFO)
        Queue<Reservation> bookingQueue = new LinkedList<>();

        System.out.println("Enter number of booking requests:");
        int n = scanner.nextInt();
        scanner.nextLine(); // consume newline

        // Taking user input
        for (int i = 1; i <= n; i++) {
            System.out.println("\nEnter details for Guest " + i + ":");

            System.out.print("Guest Name: ");
            String name = scanner.nextLine();

            System.out.print("Room Type (Single/Double/Suite): ");
            String roomType = scanner.nextLine();

            // Add to queue
            bookingQueue.offer(new Reservation(name, roomType));
        }

        // Processing queue (FIFO order)
        System.out.println("\nBooking Request Queue");

        while (!bookingQueue.isEmpty()) {
            Reservation r = bookingQueue.poll();

            System.out.println("Processing booking for Guest: "
                    + r.getGuestName()
                    + ", Room Type: "
                    + r.getRoomType());
        }

        scanner.close();
    }
}