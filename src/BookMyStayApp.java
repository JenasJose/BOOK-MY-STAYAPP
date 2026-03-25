import java.util.*;

// Service class represents an add-on service
class Service {
    private String name;
    private double price;

    public Service(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

// Reservation class with reservation ID and guest name
class Reservation {
    private String reservationId;
    private String guestName;

    public Reservation(String reservationId, String guestName) {
        this.reservationId = reservationId;
        this.guestName = guestName;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }
}

// AddOnServiceManager manages mapping from reservations to selected services
class AddOnServiceManager {
    private Map<String, List<Service>> reservationServices = new HashMap<>();

    // Add a service to a reservation ID
    public void addService(String reservationId, Service service) {
        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        reservationServices.get(reservationId).add(service);
    }

    // Get all services for a reservation ID
    public List<Service> getServices(String reservationId) {
        return reservationServices.getOrDefault(reservationId, Collections.emptyList());
    }

    // Calculate total cost of services for a reservation ID
    public double calculateTotalCost(String reservationId) {
        double total = 0.0;
        for (Service s : getServices(reservationId)) {
            total += s.getPrice();
        }
        return total;
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Sample confirmed reservations (usually from previous booking flow)
        List<Reservation> confirmedReservations = new ArrayList<>();
        confirmedReservations.add(new Reservation("Single-1", "Abhi"));
        confirmedReservations.add(new Reservation("Single-2", "Subha"));
        confirmedReservations.add(new Reservation("Suite-1", "Vanmathi"));

        // Sample available services
        List<Service> availableServices = new ArrayList<>();
        availableServices.add(new Service("Breakfast", 500.0));
        availableServices.add(new Service("Airport Pickup", 1200.0));
        availableServices.add(new Service("Extra Bed", 1000.0));

        AddOnServiceManager serviceManager = new AddOnServiceManager();

        System.out.println("Available Add-On Services:");
        for (int i = 0; i < availableServices.size(); i++) {
            Service s = availableServices.get(i);
            System.out.printf("%d. %s (₹%.2f)\n", i + 1, s.getName(), s.getPrice());
        }

        // User selects add-on services per reservation
        for (Reservation r : confirmedReservations) {
            System.out.println("\nSelect add-on services for Reservation ID: " + r.getReservationId()
                    + ", Guest: " + r.getGuestName());
            System.out.println("Enter service numbers separated by commas (e.g. 1,3) or '0' for none:");

            String input = scanner.nextLine().trim();
            if (!input.equals("0") && !input.isEmpty()) {
                String[] choices = input.split(",");
                for (String choice : choices) {
                    try {
                        int index = Integer.parseInt(choice.trim()) - 1;
                        if (index >= 0 && index < availableServices.size()) {
                            serviceManager.addService(r.getReservationId(), availableServices.get(index));
                        }
                    } catch (NumberFormatException e) {
                        // Ignore invalid inputs
                    }
                }
            }
        }

        // Display summary of reservations and their selected add-ons
        System.out.println("\n--- Reservation Add-On Service Summary ---");
        for (Reservation r : confirmedReservations) {
            System.out.println("Reservation ID: " + r.getReservationId() + ", Guest: " + r.getGuestName());

            List<Service> selected = serviceManager.getServices(r.getReservationId());
            if (selected.isEmpty()) {
                System.out.println("No add-on services selected.");
            } else {
                System.out.println("Selected Add-On Services:");
                for (Service s : selected) {
                    System.out.printf(" - %s (₹%.2f)\n", s.getName(), s.getPrice());
                }
                System.out.printf("Total Additional Cost: ₹%.2f\n", serviceManager.calculateTotalCost(r.getReservationId()));
            }
            System.out.println();
        }

        scanner.close();
    }
}