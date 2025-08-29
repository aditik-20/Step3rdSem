import java.util.*;

class Room {
    String roomNumber;
    String roomType;
    double pricePerNight;
    boolean isAvailable;
    int maxOccupancy;

    public Room(String roomNumber, String roomType, double pricePerNight, int maxOccupancy) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.maxOccupancy = maxOccupancy;
        this.isAvailable = true;
    }
}

class Guest {
    String guestId;
    String guestName;
    String phoneNumber;
    String email;
    String[] bookingHistory;
    int historyCount;
    private static int totalGuests = 0;

    public Guest(String guestName, String phoneNumber, String email) {
        this.guestId = String.format("G%03d", ++totalGuests);
        this.guestName = guestName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bookingHistory = new String[20];
        this.historyCount = 0;
    }

    public void addHistory(String bookingId) {
        if (historyCount < bookingHistory.length) bookingHistory[historyCount++] = bookingId;
    }
}

class Booking {
    String bookingId;
    Guest guest;
    Room room;
    String checkInDate;
    String checkOutDate;
    double totalAmount;
    static int totalBookings = 0;
    static double hotelRevenue = 0;
    static String hotelName = "StayEasy Hotels";

    public Booking(Guest guest, Room room, String checkInDate, String checkOutDate, int nights) {
        this.bookingId = String.format("B%04d", totalBookings + 1);
        this.guest = guest;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalAmount = room.pricePerNight * nights;
    }

    public static Booking makeReservation(Guest guest, Room room, String checkInDate, String checkOutDate, int nights) {
        if (!room.isAvailable) {
            System.out.println("Room not available");
            return null;
        }
        Booking b = new Booking(guest, room, checkInDate, checkOutDate, nights);
        room.isAvailable = false;
        totalBookings++;
        hotelRevenue += b.totalAmount;
        guest.addHistory(b.bookingId);
        System.out.println("Booked " + room.roomNumber + " for " + guest.guestName + " | " + b.totalAmount);
        return b;
    }

    public static void cancelReservation(Booking b) {
        if (b == null) return;
        if (!b.room.isAvailable) b.room.isAvailable = true;
        System.out.println("Cancelled " + b.bookingId);
    }

    public static boolean checkAvailability(Room[] rooms, String roomType) {
        for (Room r : rooms) if (r != null && r.isAvailable && r.roomType.equalsIgnoreCase(roomType)) return true;
        return false;
    }

    public static double calculateBill(Booking b) {
        return b == null ? 0 : b.totalAmount;
    }

    public static double getOccupancyRate(Room[] rooms) {
        int total = 0, occupied = 0;
        for (Room r : rooms) if (r != null) {
            total++;
            if (!r.isAvailable) occupied++;
        }
        if (total == 0) return 0;
        return (occupied * 100.0) / total;
    }

    public static double getTotalRevenue() {
        return hotelRevenue;
    }

    public static String getMostPopularRoomType(Room[] rooms, Booking[] bookings) {
        String[] types = new String[rooms.length];
        int[] counts = new int[rooms.length];
        int k = 0;
        for (Room r : rooms) {
            boolean seen = false;
            for (int i = 0; i < k; i++) if (types[i].equals(r.roomType)) { seen = true; break; }
            if (!seen) types[k++] = r.roomType;
        }
        for (Booking b : bookings) if (b != null) {
            for (int i = 0; i < k; i++) if (types[i].equals(b.room.roomType)) counts[i]++;
        }
        int maxIdx = -1, max = -1;
        for (int i = 0; i < k; i++) if (counts[i] > max) { max = counts[i]; maxIdx = i; }
        return maxIdx == -1 ? "NA" : types[maxIdx];
    }
}

public class HotelReservationSystem {
    public static void main(String[] args) {
        Room[] rooms = {
            new Room("101","Deluxe",3500,2),
            new Room("102","Deluxe",3500,2),
            new Room("201","Suite",6000,4),
            new Room("301","Standard",2500,2)
        };

        Guest g1 = new Guest("Aditi","9999999999","a@x.com");
        Guest g2 = new Guest("Rahul","8888888888","r@x.com");

        System.out.println("Deluxe available: " + Booking.checkAvailability(rooms,"Deluxe"));
        Booking b1 = Booking.makeReservation(g1, rooms[0], "2025-09-01", "2025-09-03", 2);
        Booking b2 = Booking.makeReservation(g2, rooms[2], "2025-09-05", "2025-09-08", 3);

        System.out.println("Occupancy Rate: " + Booking.getOccupancyRate(rooms) + "%");
        System.out.println("Revenue: " + Booking.getTotalRevenue());

        Booking[] blist = new Booking[5];
        blist[0] = b1; blist[1] = b2;
        System.out.println("Popular Type: " + Booking.getMostPopularRoomType(rooms, blist));

        Booking.cancelReservation(b1);
        System.out.println("Deluxe available: " + Booking.checkAvailability(rooms,"Deluxe"));
    }
}
