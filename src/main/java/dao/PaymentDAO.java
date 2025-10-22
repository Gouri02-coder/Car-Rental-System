package dao;

import model.Payment;
import util.DatabaseConnection;
import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {
    
    public boolean createPayment(Payment payment) {
        String sql = "INSERT INTO payments (payment_id, booking_id, amount, payment_method, " +
                    "payment_status, transaction_id, card_last_four, card_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
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
    
    public boolean updatePaymentStatus(String paymentId, String status, String transactionId) {
        String sql = "UPDATE payments SET payment_status = ?, transaction_id = ?, " +
                    "payment_date = CURRENT_TIMESTAMP WHERE payment_id = ?";
        
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
    
    public Payment getPaymentByBookingId(String bookingId) {
        String sql = "SELECT * FROM payments WHERE booking_id = ?";
        Payment payment = null;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, bookingId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                payment = extractPaymentFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payment;
    }
    
    public List<Payment> getPaymentsByUserId(String userId) {
        String sql = "SELECT p.* FROM payments p " +
                    "JOIN bookings b ON p.booking_id = b.booking_id " +
                    "WHERE b.user_id = ? ORDER BY p.created_at DESC";
        List<Payment> payments = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                payments.add(extractPaymentFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }
    
    private Payment extractPaymentFromResultSet(ResultSet rs) throws SQLException {
        Payment payment = new Payment();
        payment.setPaymentId(rs.getString("payment_id"));
        payment.setBookingId(rs.getString("booking_id"));
        payment.setAmount(rs.getBigDecimal("amount"));
        payment.setPaymentMethod(rs.getString("payment_method"));
        payment.setPaymentStatus(rs.getString("payment_status"));
        payment.setTransactionId(rs.getString("transaction_id"));
        payment.setCardLastFour(rs.getString("card_last_four"));
        payment.setCardType(rs.getString("card_type"));
        
        if (rs.getTimestamp("payment_date") != null) {
            payment.setPaymentDate(rs.getTimestamp("payment_date").toLocalDateTime());
        }
        if (rs.getTimestamp("created_at") != null) {
            payment.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        }
        return payment;
    }
}