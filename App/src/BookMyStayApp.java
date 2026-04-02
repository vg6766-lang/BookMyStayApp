import java.util.*;

public class BookMyStayApp {

    // Model for an Add-On Service
    static class Service {
        String name;
        double price;

        Service(String name, double price) {
            this.name = name;
            this.price = price;
        }

        @Override
        public String toString() {
            return name + " ($" + price + ")";
        }
    }

    // Mapping: Reservation ID -> List of Selected Services
    private static Map<String, List<Service>> reservationAddOns = new HashMap<>();

    public static void main(String[] args) {
        // Assume these IDs were generated in Use Case 6
        String resId1 = "RES-101";
        String resId2 = "RES-102";

        System.out.println("--- Selecting Add-On Services ---");

        // 1. Guest 1 selects multiple services
        addServiceToReservation(resId1, new Service("Breakfast Buffet", 25.0));
        addServiceToReservation(resId1, new Service("Late Check-out", 15.0));

        // 2. Guest 2 selects one service
        addServiceToReservation(resId2, new Service("Airport Shuttle", 40.0));

        // 3. Display and Calculate Costs
        displayReservationDetails(resId1);
        displayReservationDetails(resId2);
    }

    public static void addServiceToReservation(String resId, Service service) {
        // If the ID isn't in the map, initialize a new ArrayList
        reservationAddOns.putIfAbsent(resId, new ArrayList<>());

        // Add the service to the list (One-to-Many)
        reservationAddOns.get(resId).add(service);

        System.out.println("Added " + service.name + " to Reservation: " + resId);
    }

    public static void displayReservationDetails(String resId) {
        System.out.println("\n--- Summary for " + resId + " ---");

        List<Service> services = reservationAddOns.getOrDefault(resId, new ArrayList<>());

        if (services.isEmpty()) {
            System.out.println("No add-on services selected.");
        } else {
            double totalCost = 0;
            System.out.println("Services Selected:");
            for (Service s : services) {
                System.out.println(" - " + s);
                totalCost += s.price;
            }
            System.out.println("Total Additional Cost: $" + totalCost);
        }
    }
}