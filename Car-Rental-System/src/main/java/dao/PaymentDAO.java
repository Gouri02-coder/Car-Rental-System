package dao;

import model.Payment;
import util.DatabaseConnection;

import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    // ✅ Create payment record
    public boolean createPayment(Payment payment) {
        String sql = "INSERT INTO payments (payment_id, booking_id, amount, payment_method, payment_status, transaction_id, card_last_four, card_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, payment.getPaymentId());
            stmt.setString(2, payment.getBookingId());
            stmt.setBigDecimal(3, payment.getAmount());
            stmt.setString(4, payment.getPaymentMethod());
            stmt.setString(5, payment.getPaymentStatus());
            stmt.setString(6, payment.getTransactionId());
            stmt.setString(7, payment.getCardLastFour());
            stmt.setString(8, payment.getCardType());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Update payment after confirmation
    public boolean updatePaymentStatus(String paymentId, String status, String transactionId) {
        String sql = "UPDATE payments SET payment_status=?, transaction_id=?, payment_date=CURRENT_TIMESTAMP WHERE payment_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setString(2, transactionId);
            stmt.setString(3, paymentId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Fetch payment by Booking ID
    public Payment getPaymentByBookingId(String bookingId) {
        String sql = "SELECT * FROM payments WHERE booking_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, bookingId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractPayment(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Payment extractPayment(ResultSet rs) throws SQLException {
        Payment p = new Payment();
        p.setPaymentId(rs.getString("payment_id"));
        p.setBookingId(rs.getString("booking_id"));
        p.setAmount(rs.getBigDecimal("amount"));
        p.setPaymentMethod(rs.getString("payment_method"));
        p.setPaymentStatus(rs.getString("payment_status"));
        p.setTransactionId(rs.getString("transaction_id"));
        p.setCardLastFour(rs.getString("card_last_four"));
        p.setCardType(rs.getString("card_type"));
        return p;
    }
}
