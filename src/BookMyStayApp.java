import java.util.*;

// Reservation class represents a confirmed booking
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

// BookingHistory class stores confirmed reservations in insertion order
class BookingHistory {
    private List<Reservation> confirmedBookings = new ArrayList<>();

    // Add a confirmed booking to history
    public void addBooking(Reservation reservation) {
        confirmedBookings.add(reservation);
    }

    // Get all bookings
    public List<Reservation> getAllBookings() {
        return Collections.unmodifiableList(confirmedBookings);
    }
}

// BookingReportService generates reports from booking history
class BookingReportService {
    private BookingHistory bookingHistory;

    public BookingReportService(BookingHistory bookingHistory) {
        this.bookingHistory = bookingHistory;
    }

    // Print a simple report summarizing bookings
    public void printBookingReport() {
        List<Reservation> bookings = bookingHistory.getAllBookings();

        System.out.println("Booking History Report");
        System.out.println("----------------------");

        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : bookings) {
            System.out.printf("Reservation ID: %s, Guest: %s, Room Type: %s%n",
                    r.getReservationId(), r.getGuestName(), r.getRoomType());
        }

        System.out.println("----------------------");
        System.out.println("Total Bookings: " + bookings.size());
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        BookingHistory bookingHistory = new BookingHistory();

        System.out.print("Enter number of confirmed bookings to add: ");
        int n = scanner.nextInt();
        scanner.nextLine(); // consume newline

        for (int i = 1; i <= n; i++) {
            System.out.println("\nEnter details for confirmed booking " + i + ":");

            System.out.print("Reservation ID: ");
            String reservationId = scanner.nextLine();

            System.out.print("Guest Name: ");
            String guestName = scanner.nextLine();

            System.out.print("Room Type (Single/Double/Suite): ");
            String roomType = scanner.nextLine();

            // Add to booking history
            bookingHistory.addBooking(new Reservation(reservationId, guestName, roomType));
        }

        // Generate and display booking report
        BookingReportService reportService = new BookingReportService(bookingHistory);
        System.out.println();
        reportService.printBookingReport();

        scanner.close();
    }
}