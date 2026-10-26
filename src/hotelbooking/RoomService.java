
package hotelbooking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class RoomService {

    public void viewRooms() {
        String sql = "SELECT * FROM rooms";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n===== HOTEL ROOMS =====");

            while (rs.next()) {
                System.out.println(
                    "Room ID: " + rs.getInt("room_id")
                    + " | Room Number: " + rs.getString("room_number")
                    + " | Type: " + rs.getString("room_type")
                    + " | Price per night: Rs. "
                    + rs.getBigDecimal("price_per_night")
                    + " | Capacity: " + rs.getInt("capacity")
                );
            }

        } catch (SQLException e) {
            System.out.println("Unable to retrieve rooms.");
            e.printStackTrace();
        }
    }

    public void viewAvailableRooms(LocalDate checkIn, LocalDate checkOut) {

        if (checkIn == null || checkOut == null
                || !checkOut.isAfter(checkIn)
                || checkIn.isBefore(LocalDate.now())) {
            System.out.println("Invalid booking dates.");
            return;
        }

        String sql = """
            SELECT r.room_id, r.room_number, r.room_type,
                   r.price_per_night, r.capacity
            FROM rooms r
            WHERE NOT EXISTS (
                SELECT 1
                FROM bookings b
                WHERE b.room_id = r.room_id
                  AND b.booking_status = 'CONFIRMED'
                  AND b.check_in < ?
                  AND b.check_out > ?
            )
            ORDER BY r.room_id
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, java.sql.Date.valueOf(checkOut));
            ps.setDate(2, java.sql.Date.valueOf(checkIn));

            try (ResultSet rs = ps.executeQuery()) {
                boolean found = false;

                System.out.println("\n===== AVAILABLE ROOMS =====");

                while (rs.next()) {
                    found = true;

                    System.out.println(
                        "Room ID: " + rs.getInt("room_id")
                        + " | Room Number: " + rs.getString("room_number")
                        + " | Type: " + rs.getString("room_type")
                        + " | Price per night: Rs. "
                        + rs.getBigDecimal("price_per_night")
                        + " | Capacity: " + rs.getInt("capacity")
                    );
                }

                if (!found) {
                    System.out.println("No rooms available for these dates.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Unable to check room availability.");
            e.printStackTrace();
        }
    }
}
