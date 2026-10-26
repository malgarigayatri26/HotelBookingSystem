
package hotelbooking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingManagementService {

    public void viewBookings(int customerId) {
        String sql = """
            SELECT b.booking_id, r.room_number, r.room_type,
                   b.check_in, b.check_out, b.total_price,
                   b.booking_status
            FROM bookings b
            JOIN rooms r ON b.room_id = r.room_id
            WHERE b.customer_id = ?
            ORDER BY b.booking_id DESC
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, customerId);

            try (ResultSet rs = ps.executeQuery()) {
                boolean found = false;

                System.out.println("\n===== YOUR BOOKINGS =====");

                while (rs.next()) {
                    found = true;
                    System.out.println(
                        "Booking ID: " + rs.getInt("booking_id")
                        + " | Room: " + rs.getString("room_number")
                        + " | Type: " + rs.getString("room_type")
                        + " | Check-in: " + rs.getDate("check_in")
                        + " | Check-out: " + rs.getDate("check_out")
                        + " | Total: Rs. " + rs.getBigDecimal("total_price")
                        + " | Status: " + rs.getString("booking_status")
                    );
                }

                if (!found) {
                    System.out.println("No bookings found for this customer.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Unable to retrieve bookings: " + e.getMessage());
        }
    }

    public void cancelBooking(int customerId, int bookingId) {
        String sql = """
            UPDATE bookings
            SET booking_status = 'CANCELLED'
            WHERE booking_id = ?
              AND customer_id = ?
              AND booking_status = 'CONFIRMED'
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, bookingId);
            ps.setInt(2, customerId);

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Booking cancelled successfully!");
            } else {
                System.out.println(
                    "Booking not found, already cancelled, or not yours."
                );
            }

        } catch (SQLException e) {
            System.out.println("Cancellation failed: " + e.getMessage());
        }
    }
}
