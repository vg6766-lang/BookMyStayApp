import java.util.*;

public class BookMyStayApp {

    // Shared Resources
    private static int inventoryCount = 2; // Limited stock to test race conditions
    private static List<String> confirmedBookings = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("--- Starting Concurrent Booking Simulation ---");
        System.out.println("Initial Inventory: " + inventoryCount);

        // Create multiple threads representing different guests booking at once
        Thread guest1 = new Thread(() -> attemptBooking("Alice"), "Thread-Alice");
        Thread guest2 = new Thread(() -> attemptBooking("Bob"), "Thread-Bob");
        Thread guest3 = new Thread(() -> attemptBooking("Charlie"), "Thread-Charlie");

        // Start threads simultaneously
        guest1.start();
        guest2.start();
        guest3.start();

        // Wait for all threads to finish
        try {
            guest1.join();
            guest2.join();
            guest3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n--- Final System Audit ---");
        System.out.println("Final Inventory: " + inventoryCount);
        System.out.println("Confirmed Guests: " + confirmedBookings);
    }

    /**
     * attemptBooking is the CRITICAL SECTION.
     * The 'synchronized' keyword ensures only ONE thread enters this method at a time.
     */
    public static synchronized void attemptBooking(String guestName) {
        System.out.println(Thread.currentThread().getName() + " checking inventory...");

        if (inventoryCount > 0) {
            // Simulate a small delay in processing to expose potential race conditions
            try { Thread.sleep(100); } catch (InterruptedException e) {}

            inventoryCount--; // Decrement shared state
            confirmedBookings.add(guestName);
            System.out.println("SUCCESS: " + guestName + " secured a room.");
        } else {
            System.out.println("FAILED: No rooms left for " + guestName);
        }
    }
}