import java.util.*;

public class BookMyStayApp {

    // Current active reservations: Map<ReservationID, RoomType>
    private static Map<String, String> activeReservations = new HashMap<>();

    // Inventory: Map<RoomType, Count>
    private static Map<String, Integer> inventory = new HashMap<>();

    // Stack for Rollback: Stores released Room IDs in LIFO order
    private static Stack<String> releasedRoomsStack = new Stack<>();

    static {
        // Initial State
        inventory.put("Deluxe", 5);
        activeReservations.put("RES-101", "Deluxe");
        activeReservations.put("RES-102", "Deluxe");
    }

    public static void main(String[] args) {
        System.out.println("--- Starting Cancellation Service ---");
        System.out.println("Initial Inventory: " + inventory);

        // 1. Valid Cancellation
        cancelBooking("RES-102");

        // 2. Invalid Cancellation (Non-existent ID)
        cancelBooking("RES-999");

        // 3. Attempting to cancel an already cancelled booking
        cancelBooking("RES-102");

        System.out.println("\nFinal Inventory after Rollback: " + inventory);
        System.out.println("Recently Released Rooms (Stack): " + releasedRoomsStack);
    }

    /**
     * Performs a controlled rollback of a booking.
     */
    public static void cancelBooking(String resId) {
        System.out.print("\nInitiating cancellation for: " + resId + "... ");

        // VALIDATION: Does the reservation exist?
        if (!activeReservations.containsKey(resId)) {
            System.err.println("FAILED: Reservation ID not found or already cancelled.");
            return;
        }

        // 1. Identify the Room Type to rollback
        String type = activeReservations.get(resId);

        // 2. Perform STATE REVERSAL (Inventory Restoration)
        inventory.put(type, inventory.get(type) + 1);

        // 3. LIFO ROLLBACK: Push the room "resource" onto the stack
        // (Simulating the return of a specific room ID to the pool)
        String releasedRoomId = "ROOM-" + resId.split("-")[1];
        releasedRoomsStack.push(releasedRoomId);

        // 4. Remove from active records
        activeReservations.remove(resId);

        System.out.println("SUCCESS: Inventory incremented and Room ID " + releasedRoomId + " released.");
    }
}