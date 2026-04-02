import java.util.*;

public class BookMyStayApp {

    // Inventory: Maps Room Type (e.g., "Deluxe") to available count
    private static Map<String, Integer> inventory = new HashMap<>();

    // Allocation Tracking: Maps Room Type to a Set of assigned Room IDs
    // The Set ensures no two bookings get the same Room ID
    private static Map<String, Set<String>> allocatedRooms = new HashMap<>();

    // Queue for incoming booking requests (FIFO)
    private static Queue<BookingRequest> requestQueue = new LinkedList<>();

    // Internal class to represent a booking request
    static class BookingRequest {
        String guestName;
        String roomType;

        BookingRequest(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }
    }

    public static void main(String[] args) {
        // 1. Initialize Inventory
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);

        // Initialize Allocation Sets for each room type
        allocatedRooms.put("Deluxe", new HashSet<>());
        allocatedRooms.put("Suite", new HashSet<>());

        // 2. Enqueue Sample Requests
        System.out.println("--- Incoming Requests ---");
        requestQueue.add(new BookingRequest("Alice", "Deluxe"));
        requestQueue.add(new BookingRequest("Bob", "Deluxe"));
        requestQueue.add(new BookingRequest("Charlie", "Deluxe")); // Should fail (out of stock)
        requestQueue.add(new BookingRequest("David", "Suite"));

        // 3. Process the Queue (Allocation Service)
        processBookings();
    }

    public static void processBookings() {
        System.out.println("\n--- Processing Room Allocations ---");

        while (!requestQueue.isEmpty()) {
            BookingRequest request = requestQueue.poll();
            String type = request.roomType;

            // Check availability
            if (inventory.containsKey(type) && inventory.get(type) > 0) {

                // GENERATE UNIQUE ROOM ID (Atomic Logical Operation)
                // In a real app, this might come from a DB, here we generate a simple string
                String roomID = type.substring(0, 1).toUpperCase() + "-" + (100 + allocatedRooms.get(type).size() + 1);

                // ENFORCE UNIQUENESS using Set
                if (!allocatedRooms.get(type).contains(roomID)) {
                    allocatedRooms.get(type).add(roomID);

                    // UPDATE INVENTORY IMMEDIATELY (Synchronization)
                    inventory.put(type, inventory.get(type) - 1);

                    System.out.println("CONFIRMED: " + request.guestName + " assigned to " + roomID + " [" + type + "]");
                }
            } else {
                System.out.println("FAILED: No " + type + " rooms available for " + request.guestName);
            }
        }

        printFinalSummary();
    }

    private static void printFinalSummary() {
        System.out.println("\n--- Final System State ---");
        System.out.println("Remaining Inventory: " + inventory);
        System.out.println("Allocated Room IDs: " + allocatedRooms);
    }
}