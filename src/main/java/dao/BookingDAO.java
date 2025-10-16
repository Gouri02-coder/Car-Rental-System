package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Booking;
import util.DatabaseConnection;

public class BookingDAO {
    
    // Create a new booking
    public boolean createBooking(Booking booking) {
        String sql = "INSERT INTO bookings (user_id, car_id, start_date, end_date, total_price, status) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, booking.getUserId());
            stmt.setInt(2, booking.getCarId());
            stmt.setDate(3, booking.getStartDate());
            stmt.setDate(4, booking.getEndDate());
            stmt.setDouble(5, booking.getTotalPrice());
            stmt.setString(6, booking.getStatus());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Get all bookings for a specific user
    public List<Booking> getBookingsByUserId(int userId) {
        List<Booking> bookings = new ArrayList<>();
        // FIXED: Use first_name and last_name instead of name
        String sql = "SELECT b.*, u.first_name, u.last_name, c.brand as car_brand, c.model as car_model " +
                    "FROM bookings b " +
                    "JOIN users u ON b.user_id = u.id " +
                    "JOIN cars c ON b.car_id = c.id " +
                    "WHERE b.user_id = ? ORDER BY b.created_at DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Booking booking = extractBookingFromResultSet(rs);
                bookings.add(booking);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }
    
    // Get all bookings (for admin)
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        // FIXED: Use first_name and last_name instead of name
        String sql = "SELECT b.*, u.first_name, u.last_name, c.brand as car_brand, c.model as car_model " +
                    "FROM bookings b " +
                    "JOIN users u ON b.user_id = u.id " +
                    "JOIN cars c ON b.car_id = c.id " +
                    "ORDER BY b.created_at DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Booking booking = extractBookingFromResultSet(rs);
                bookings.add(booking);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }
    
    // Update booking status
    public boolean updateBookingStatus(int bookingId, String status) {
        String sql = "UPDATE bookings SET status = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            stmt.setInt(2, bookingId);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Get booking by ID
    public Booking getBookingById(int bookingId) {
        Booking booking = null;
        // FIXED: Use first_name and last_name instead of name
        String sql = "SELECT b.*, u.first_name, u.last_name, c.brand as car_brand, c.model as car_model " +
                    "FROM bookings b " +
                    "JOIN users u ON b.user_id = u.id " +
                    "JOIN cars c ON b.car_id = c.id " +
                    "WHERE b.id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                booking = extractBookingFromResultSet(rs);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booking;
    }
    
    // Get total number of bookings for a user
    public int getUserTotalBookings(int userId) {
        String query = "SELECT COUNT(*) FROM bookings WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    // Get active bookings count for a user
    public int getUserActiveBookings(int userId) {
        String query = "SELECT COUNT(*) FROM bookings WHERE user_id = ? AND status IN ('confirmed', 'active', 'pending')";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    // Get total amount spent by a user
    public double getUserTotalSpent(int userId) {
        String query = "SELECT COALESCE(SUM(total_price), 0) FROM bookings WHERE user_id = ? AND status IN ('confirmed', 'active', 'completed')";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
    
    // Get recent bookings for a user with limit
    public List<Booking> getUserRecentBookings(int userId, int limit) {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT b.*, c.brand as car_brand, c.model as car_model " +
                      "FROM bookings b " +
                      "LEFT JOIN cars c ON b.car_id = c.id " +
                      "WHERE b.user_id = ? ORDER BY b.created_at DESC LIMIT ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Booking booking = extractBookingFromResultSet(rs);
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }
    
    // Get bookings by date range for a user
    public List<Booking> getBookingsByDateRange(int userId, String startDate, String endDate) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT b.*, c.brand as car_brand, c.model as car_model " +
                    "FROM bookings b " +
                    "JOIN cars c ON b.car_id = c.id " +
                    "WHERE b.user_id = ? AND b.start_date >= ? AND b.end_date <= ? " +
                    "ORDER BY b.created_at DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            stmt.setString(2, startDate);
            stmt.setString(3, endDate);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Booking booking = extractBookingFromResultSet(rs);
                bookings.add(booking);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }
    
    // Cancel a booking
    public boolean cancelBooking(int bookingId) {
        String sql = "UPDATE bookings SET status = 'cancelled' WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, bookingId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Check if car is available for given dates
    public boolean isCarAvailable(int carId, Date startDate, Date endDate) {
        String sql = "SELECT COUNT(*) FROM bookings WHERE car_id = ? AND status IN ('confirmed', 'active') " +
                    "AND ((start_date BETWEEN ? AND ?) OR (end_date BETWEEN ? AND ?) " +
                    "OR (start_date <= ? AND end_date >= ?))";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, carId);
            stmt.setDate(2, startDate);
            stmt.setDate(3, endDate);
            stmt.setDate(4, startDate);
            stmt.setDate(5, endDate);
            stmt.setDate(6, startDate);
            stmt.setDate(7, endDate);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0; // Return true if no conflicting bookings
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Get bookings by status
    public List<Booking> getBookingsByStatus(String status) {
        List<Booking> bookings = new ArrayList<>();
        // FIXED: Use first_name and last_name instead of name
        String sql = "SELECT b.*, u.first_name, u.last_name, c.brand as car_brand, c.model as car_model " +
                    "FROM bookings b " +
                    "JOIN users u ON b.user_id = u.id " +
                    "JOIN cars c ON b.car_id = c.id " +
                    "WHERE b.status = ? ORDER BY b.created_at DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Booking booking = extractBookingFromResultSet(rs);
                bookings.add(booking);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }
    
    // Get today's active bookings
    public List<Booking> getTodaysActiveBookings() {
        List<Booking> bookings = new ArrayList<>();
        // FIXED: Use first_name and last_name instead of name
        String sql = "SELECT b.*, u.first_name, u.last_name, c.brand as car_brand, c.model as car_model " +
                    "FROM bookings b " +
                    "JOIN users u ON b.user_id = u.id " +
                    "JOIN cars c ON b.car_id = c.id " +
                    "WHERE b.status IN ('active', 'confirmed') AND b.start_date <= CURDATE() AND b.end_date >= CURDATE() " +
                    "ORDER BY b.start_date ASC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Booking booking = extractBookingFromResultSet(rs);
                bookings.add(booking);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }
    
    // Update booking details
    public boolean updateBooking(Booking booking) {
        String sql = "UPDATE bookings SET car_id = ?, start_date = ?, end_date = ?, total_price = ?, status = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, booking.getCarId());
            stmt.setDate(2, booking.getStartDate());
            stmt.setDate(3, booking.getEndDate());
            stmt.setDouble(4, booking.getTotalPrice());
            stmt.setString(5, booking.getStatus());
            stmt.setInt(6, booking.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Delete a booking
    public boolean deleteBooking(int bookingId) {
        String sql = "DELETE FROM bookings WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, bookingId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Get booking statistics for admin dashboard
    public java.util.Map<String, Integer> getBookingStatistics() {
        java.util.Map<String, Integer> stats = new java.util.HashMap<>();
        String sql = "SELECT status, COUNT(*) as count FROM bookings GROUP BY status";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                stats.put(rs.getString("status"), rs.getInt("count"));
            }
            
            // Ensure all statuses are present
            String[] allStatuses = {"pending", "confirmed", "active", "completed", "cancelled"};
            for (String status : allStatuses) {
                stats.putIfAbsent(status, 0);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stats;
    }
    
    // Extract booking from ResultSet (helper method) - UPDATED
    private Booking extractBookingFromResultSet(ResultSet rs) throws SQLException {
        Booking booking = new Booking();
        booking.setId(rs.getInt("id"));
        booking.setUserId(rs.getInt("user_id"));
        booking.setCarId(rs.getInt("car_id"));
        booking.setStartDate(rs.getDate("start_date"));
        booking.setEndDate(rs.getDate("end_date"));
        booking.setTotalPrice(rs.getDouble("total_price"));
        booking.setStatus(rs.getString("status"));
        booking.setCreatedAt(rs.getTimestamp("created_at"));
        
        // FIXED: Combine first_name and last_name to create user_name
        try {
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            if (firstName != null && lastName != null) {
                booking.setUserName(firstName + " " + lastName);
            } else if (firstName != null) {
                booking.setUserName(firstName);
            } else {
                booking.setUserName("User #" + booking.getUserId());
            }
        } catch (SQLException e) {
            // Field doesn't exist in this query, ignore
            booking.setUserName("User #" + booking.getUserId());
        }
        
        try {
            booking.setCarBrand(rs.getString("car_brand"));
        } catch (SQLException e) {
            // Field doesn't exist in this query, ignore
        }
        
        try {
            booking.setCarModel(rs.getString("car_model"));
        } catch (SQLException e) {
            // Field doesn't exist in this query, ignore
        }
        
        return booking;
    }
}