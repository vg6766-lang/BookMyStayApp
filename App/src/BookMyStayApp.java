

// Version 3.1

import java.util.HashMap;
import java.util.Map;

// Inventory Class (Centralized State Management)
class RoomInventory {

    private Map<String, Integer> availabilityMap;

    // Constructor - Initialize Inventory
    public RoomInventory() {
        availabilityMap = new HashMap<>();
    }

    // Add / Register Room Type
    public void addRoomType(String roomType, int count) {
        availabilityMap.put(roomType, count);
    }

    // Get Availability
    public int getAvailability(String roomType) {
        return availabilityMap.getOrDefault(roomType, 0);
    }

    // Update Availability (Controlled)
    public void updateAvailability(String roomType, int newCount) {
        if (availabilityMap.containsKey(roomType)) {
            availabilityMap.put(roomType, newCount);
        } else {
            System.out.println("Room type not found: " + roomType);
        }
    }

    // Display Inventory
    public void displayInventory() {
        System.out.println("===== Room Inventory =====");
        for (Map.Entry<String, Integer> entry : availabilityMap.entrySet()) {
            System.out.println(entry.getKey() + " -> Available: " + entry.getValue());
        }
        System.out.println("==========================");
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        // Initialize Inventory
        RoomInventory inventory = new RoomInventory();

        // Register Room Types
        inventory.addRoomType("Single Room", 5);
        inventory.addRoomType("Double Room", 3);
        inventory.addRoomType("Suite Room", 2);

        // Display Initial Inventory
        inventory.displayInventory();

        // Retrieve Availability
        System.out.println("\nChecking availability for Double Room:");
        System.out.println("Available: " + inventory.getAvailability("Double Room"));

        // Update Availability
        System.out.println("\nUpdating availability for Double Room...");
        inventory.updateAvailability("Double Room", 4);

        // Display Updated Inventory
        System.out.println();
        inventory.displayInventory();
    }
}