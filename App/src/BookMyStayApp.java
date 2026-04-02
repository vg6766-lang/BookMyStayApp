import java.util.*;

// 1. Define Custom Exceptions for Domain-Specific Errors
class InvalidRoomTypeException extends Exception {
    public InvalidRoomTypeException(String message) { super(message); }
}

class OutOfStockException extends Exception {
    public OutOfStockException(String message) { super(message); }
}

public class BookMyStayApp {

    private static Map<String, Integer> inventory = new HashMap<>();

    static {
        inventory.put("Deluxe", 1); // Only 1 room for testing
        inventory.put("Suite", 5);
    }

    public static void main(String[] args) {
        System.out.println("--- Starting Validated Booking Process ---");

        // Test Case 1: Valid Booking
        processBookingRequest("Alice", "Deluxe");

        // Test Case 2: Invalid Room Type (Case Sensitive check)
        processBookingRequest("Bob", "deluxe"); // Should fail (lowercase 'd')

        // Test Case 3: Out of Stock
        processBookingRequest("Charlie", "Deluxe"); // Should fail (already taken)

        // Test Case 4: Non-existent Room Type
        processBookingRequest("David", "Penthouse");

        System.out.println("\nSystem remains stable after all errors.");
        System.out.println("Final Inventory State: " + inventory);
    }

    /**
     * Core logic with Fail-Fast Validation and Guarding System State
     */
    public static void processBookingRequest(String guest, String type) {
        try {
            System.out.print("\nProcessing request for " + guest + " (" + type + ")... ");

            // VALIDATION 1: Check if room type exists
            if (!inventory.containsKey(type)) {
                throw new InvalidRoomTypeException("Error: Room type '" + type + "' does not exist.");
            }

            // VALIDATION 2: Check inventory availability
            if (inventory.get(type) <= 0) {
                throw new OutOfStockException("Error: No '" + type + "' rooms left in inventory.");
            }

            // If we reach here, input is valid. UPDATE STATE.
            inventory.put(type, inventory.get(type) - 1);
            System.out.println("SUCCESS: Room allocated.");

        } catch (InvalidRoomTypeException | OutOfStockException e) {
            // GRACEFUL FAILURE: Catch the specific domain error and print message
            System.err.println("\n[VALIDATION FAILED] " + e.getMessage());
        } catch (Exception e) {
            // CATCH-ALL for unexpected issues
            System.err.println("\n[SYSTEM ERROR] An unexpected error occurred: " + e.getMessage());
        }
    }
}