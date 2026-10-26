
package hotelbooking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class CustomerService {

    public int registerCustomer(String name, String phone, String email) {
        String sql = "INSERT INTO customers (name, phone, email) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setString(3, email);

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int customerId = rs.getInt(1);
                    System.out.println("Customer registered successfully!");
                    System.out.println("Customer ID: " + customerId);
                    return customerId;
                }
            }

        } catch (SQLException e) {
            System.out.println("Customer registration failed: " + e.getMessage());
        }

        return -1;
    }
}
