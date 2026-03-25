

// Version 4.1

import java.util.*;

// -------------------- DOMAIN MODEL --------------------
abstract class Room {
    private String roomType;
    private int numberOfBeds;
    private double pricePerNight;

    public Room(String roomType, int numberOfBeds, double pricePerNight) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.pricePerNight = pricePerNight;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public abstract void displayRoomDetails();
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 2000.0);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println("Room Type: " + getRoomType());
        System.out.println("Beds: " + getNumberOfBeds());
        System.out.println("Price: ₹" + getPricePerNight());
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 3500.0);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println("Room Type: " + getRoomType());
        System.out.println("Beds: " + getNumberOfBeds());
        System.out.println("Price: ₹" + getPricePerNight());
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 6000.0);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println("Room Type: " + getRoomType());
        System.out.println("Beds: " + getNumberOfBeds());
        System.out.println("Price: ₹" + getPricePerNight());
    }
}

// -------------------- INVENTORY (STATE HOLDER) --------------------
class RoomInventory {

    private Map<String, Integer> availabilityMap;

    public RoomInventory() {
        availabilityMap = new HashMap<>();
    }

    public void addRoomType(String roomType, int count) {
        availabilityMap.put(roomType, count);
    }

    public int getAvailability(String roomType) {
        return availabilityMap.getOrDefault(roomType, 0);
    }
}

// -------------------- SEARCH SERVICE (READ-ONLY) --------------------
class RoomSearchService {

    private RoomInventory inventory;
    private List<Room> rooms;

    public RoomSearchService(RoomInventory inventory, List<Room> rooms) {
        this.inventory = inventory;
        this.rooms = rooms;
    }

    // Read-only search
    public void searchAvailableRooms() {
        System.out.println("===== Available Rooms =====\n");

        for (Room room : rooms) {
            int available = inventory.getAvailability(room.getRoomType());

            // Validation: show only available rooms
            if (available > 0) {
                room.displayRoomDetails();
                System.out.println("Available: " + available);
                System.out.println();
            }
        }

        System.out.println("===== End of Results =====");
    }
}

// -------------------- MAIN CLASS --------------------
public class BookMyStayApp{

    public static void main(String[] args) {

        // Initialize Inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 5);
        inventory.addRoomType("Double Room", 0); // intentionally unavailable
        inventory.addRoomType("Suite Room", 2);

        // Initialize Room Objects (Domain)
        List<Room> rooms = new ArrayList<>();
        rooms.add(new SingleRoom());
        rooms.add(new DoubleRoom());
        rooms.add(new SuiteRoom());

        // Initialize Search Service
        RoomSearchService searchService = new RoomSearchService(inventory, rooms);

        // Perform Search (Read-Only)
        searchService.searchAvailableRooms();
    }
}