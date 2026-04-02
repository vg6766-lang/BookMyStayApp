import java.util.*;

public class BookMyStayApp {

    // Model for a Confirmed Reservation
    static class Reservation {
        String reservationId;
        String guestName;
        String roomType;
        double totalCost;

        Reservation(String id, String name, String type, double cost) {
            this.reservationId = id;
            this.guestName = name;
            this.roomType = type;
            this.totalCost = cost;
        }

        @Override
        public String toString() {
            return String.format("| %-8s | %-10s | %-8s | $%-8.2f |",
                    reservationId, guestName, roomType, totalCost);
        }
    }

    // Historical Data Store: List preserves insertion order for auditing
    private static List<Reservation> bookingHistory = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("--- Simulating Booking Confirmations ---");

        // 1. Simulate bookings being confirmed and added to history
        recordBooking(new Reservation("RES-101", "Alice", "Deluxe", 240.0));
        recordBooking(new Reservation("RES-102", "Bob", "Suite", 450.0));
        recordBooking(new Reservation("RES-103", "Charlie", "Deluxe", 240.0));

        // 2. Generate the Administrative Report
        generateBookingReport();
    }

    /**
     * Historical Tracking: Adds a confirmed reservation to the audit trail.
     */
    public static void recordBooking(Reservation res) {
        bookingHistory.add(res);
        System.out.println("Audit Log: Recorded " + res.reservationId + " for " + res.guestName);
    }

    /**
     * Reporting Service: Processes the list to provide operational visibility.
     */
    public static void generateBookingReport() {
        System.out.println("\n============================================");
        System.out.println("        OFFICIAL BOOKING HISTORY REPORT      ");
        System.out.println("============================================");
        System.out.println("| Reg ID   | Guest      | Room     | Total     |");
        System.out.println("--------------------------------------------");

        double revenueTotal = 0;

        // Iterating through the List (Ordered Storage)
        for (Reservation res : bookingHistory) {
            System.out.println(res);
            revenueTotal += res.totalCost;
        }

        System.out.println("--------------------------------------------");
        System.out.println("Total Reservations: " + bookingHistory.size());
        System.out.printf("Total Revenue Generated: $%.2f\n", revenueTotal);
        System.out.println("============================================\n");
    }
}