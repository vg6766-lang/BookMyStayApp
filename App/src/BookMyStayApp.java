import java.io.*;
import java.util.*;

public class BookMyStayApp {

    private static final String DATA_FILE = "hotel_state.txt";
    private static Map<String, Integer> inventory = new HashMap<>();
    private static List<String> bookingHistory = new ArrayList<>();

    public static void main(String[] args) {
        // 1. SYSTEM STARTUP: Attempt to restore state
        loadSystemState();

        System.out.println("--- Current System State ---");
        System.out.println("Inventory: " + inventory);
        System.out.println("History: " + bookingHistory);

        // 2. Simulate new activity if the system was empty
        if (bookingHistory.isEmpty()) {
            System.out.println("\nNo previous data found. Creating new records...");
            inventory.put("Deluxe", 5);
            inventory.put("Suite", 2);
            bookingHistory.add("RES-101: Alice (Deluxe)");
            bookingHistory.add("RES-102: Bob (Suite)");
        } else {
            System.out.println("\nResuming operations with recovered data.");
        }

        // 3. SYSTEM SHUTDOWN: Persist state before exiting
        saveSystemState();
        System.out.println("\nSystem state saved successfully. You can now restart the app.");
    }

    /**
     * Serialization: Writing in-memory data to a durable file.
     */
    public static void saveSystemState() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE))) {
            // Save Inventory
            for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
                writer.println("INV:" + entry.getKey() + ":" + entry.getValue());
            }
            // Save History
            for (String record : bookingHistory) {
                writer.println("HIS:" + record);
            }
        } catch (IOException e) {
            System.err.println("Error saving system state: " + e.getMessage());
        }
    }

    /**
     * Deserialization: Reconstructing objects from the persisted file.
     */
    public static void loadSystemState() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            System.out.println("No persistence file found. Starting fresh.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts[0].equals("INV")) {
                    inventory.put(parts[1], Integer.parseInt(parts[2]));
                } else if (parts[0].equals("HIS")) {
                    bookingHistory.add(parts[1] + ":" + parts[2]);
                }
            }
            System.out.println("SUCCESS: System state restored from " + DATA_FILE);
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Failure Tolerance: Persisted data corrupted. Starting fresh.");
            inventory.clear();
            bookingHistory.clear();
        }
    }
}