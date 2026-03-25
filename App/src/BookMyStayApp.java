

// Version 5.1

import java.util.*;

// -------------------- RESERVATION (REQUEST OBJECT) --------------------
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}

// -------------------- BOOKING REQUEST QUEUE --------------------
class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add booking request (enqueue)
    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
        System.out.println("Request added for " + reservation.getGuestName());
    }

    // View all queued requests (without removing)
    public void viewRequests() {
        System.out.println("\n===== Booking Request Queue =====");

        if (requestQueue.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }

        for (Reservation r : requestQueue) {
            r.displayReservation();
        }

        System.out.println("=================================");
    }

    // Get next request (peek only, no removal)
    public Reservation peekNextRequest() {
        return requestQueue.peek();
    }
}

// -------------------- MAIN CLASS --------------------
public class BookMyStayApp {

    public static void main(String[] args) {

        // Initialize Queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Simulate Incoming Booking Requests
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Double Room");
        Reservation r3 = new Reservation("Charlie", "Suite Room");
        Reservation r4 = new Reservation("Diana", "Single Room");

        // Add requests to queue (FIFO order)
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);
        bookingQueue.addRequest(r4);

        // View all requests
        bookingQueue.viewRequests();

        // Peek next request (without removing)
        System.out.println("\nNext request to process (FIFO):");
        Reservation next = bookingQueue.peekNextRequest();
        if (next != null) {
            next.displayReservation();
        }
    }
}