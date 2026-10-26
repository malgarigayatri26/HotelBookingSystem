
package hotelbooking;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BookingService {

    public void bookRoom(
            int customerId,
            int roomId,
            LocalDate checkIn,
            LocalDate checkOut) {

        if (customerId <= 0 || roomId <= 0) {
            System.out.println(
                "Customer ID and Room ID must be positive."
            );
            return;
        }

        if (checkIn == null || checkOut == null
                || !checkOut.isAfter(checkIn)
                || checkIn.isBefore(LocalDate.now())) {
            System.out.println("Invalid booking dates.");
            return;
        }

        String roomSql =
                "SELECT price_per_night FROM rooms WHERE room_id = ?";

        String availabilitySql =
                "SELECT booking_id FROM bookings " +
                "WHERE room_id = ? AND booking_status = 'CONFIRMED' " +
                "AND check_in < ? AND check_out > ?";

        String insertSql =
                "INSERT INTO bookings " +
                "(customer_id, room_id, check_in, check_out, total_price) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try {
                BigDecimal pricePerNight;

                // Lock the room row to prevent simultaneous bookings.
                try (PreparedStatement ps = con.prepareStatement(
                        "SELECT room_id FROM rooms WHERE room_id = ? FOR UPDATE")) {

                    ps.setInt(1, roomId);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Room not found.");
                        }
                    }
                }

                // Check that the customer exists.
                try (PreparedStatement ps = con.prepareStatement(
                        "SELECT customer_id FROM customers WHERE customer_id = ?")) {

                    ps.setInt(1, customerId);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Customer not found.");
                        }
                    }
                }

                // Retrieve room price.
                try (PreparedStatement ps =
                        con.prepareStatement(roomSql)) {

                    ps.setInt(1, roomId);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Room not found.");
                        }

                        pricePerNight =
                                rs.getBigDecimal("price_per_night");
                    }
                }

                // Check overlapping confirmed bookings.
                try (PreparedStatement ps =
                        con.prepareStatement(availabilitySql)) {

                    ps.setInt(1, roomId);
                    ps.setDate(2, Date.valueOf(checkOut));
                    ps.setDate(3, Date.valueOf(checkIn));

                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            throw new SQLException(
                                "Room is already booked for these dates."
                            );
                        }
                    }
                }

                long nights =
                        ChronoUnit.DAYS.between(checkIn, checkOut);

                BigDecimal totalPrice =
                        pricePerNight.multiply(
                                BigDecimal.valueOf(nights));

                // Save the booking.
                try (PreparedStatement ps =
                        con.prepareStatement(insertSql)) {

                    ps.setInt(1, customerId);
                    ps.setInt(2, roomId);
                    ps.setDate(3, Date.valueOf(checkIn));
                    ps.setDate(4, Date.valueOf(checkOut));
                    ps.setBigDecimal(5, totalPrice);

                    ps.executeUpdate();
                }

                con.commit();

                System.out.println("\n===== BOOKING CONFIRMED =====");
                System.out.println("Customer ID: " + customerId);
                System.out.println("Room ID: " + roomId);
                System.out.println("Check-in: " + checkIn);
                System.out.println("Check-out: " + checkOut);
                System.out.println("Number of nights: " + nights);
                System.out.println("Total price: Rs. " + totalPrice);

            } catch (SQLException e) {
                con.rollback();
                System.out.println("Booking failed: " + e.getMessage());

            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
