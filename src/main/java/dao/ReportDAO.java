package dao;

import java.sql.*;
import java.util.*;

import util.DatabaseConnection;

public class ReportDAO {
    private Connection connection;
    
    public ReportDAO() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }
    
    // Dashboard Statistics Methods
    public int getTotalBookingsCount() {
        String sql = "SELECT COUNT(*) as count FROM bookings";
        return getCount(sql);
    }
    
    public int getActiveBookingsCount() {
        String sql = "SELECT COUNT(*) as count FROM bookings WHERE status IN ('confirmed', 'active', 'pending')";
        return getCount(sql);
    }
    
    public int getTotalUsersCount() {
        String sql = "SELECT COUNT(*) as count FROM users";
        return getCount(sql);
    }
    
    public int getTotalCarsCount() {
        String sql = "SELECT COUNT(*) as count FROM cars";
        return getCount(sql);
    }
    
    public int getAvailableCarsCount() {
        String sql = "SELECT COUNT(*) as count FROM cars WHERE status = 'available'";
        return getCount(sql);
    }
    
    public int getTodaysBookingsCount() {
        String sql = "SELECT COUNT(*) as count FROM bookings WHERE DATE(created_at) = CURDATE()";
        return getCount(sql);
    }
    
    public double getMonthlyRevenue() {
        String sql = "SELECT COALESCE(SUM(total_price), 0) as revenue FROM bookings " +
                    "WHERE status = 'completed' AND MONTH(created_at) = MONTH(CURDATE()) " +
                    "AND YEAR(created_at) = YEAR(CURDATE())";
        return getDoubleValue(sql, "revenue");
    }
    
    public int getPendingBookingsCount() {
        String sql = "SELECT COUNT(*) as count FROM bookings WHERE status = 'pending'";
        return getCount(sql);
    }
    
    public List<Map<String, Object>> getRecentBookings(int limit) {
        String sql = "SELECT b.id, u.name as user_name, c.brand, c.model, " +
                    "b.start_date, b.end_date, b.status, b.total_price " +
                    "FROM bookings b " +
                    "JOIN users u ON b.user_id = u.id " +
                    "JOIN cars c ON b.car_id = c.id " +
                    "ORDER BY b.created_at DESC LIMIT ?";
        
        List<Map<String, Object>> results = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", rs.getInt("id"));
                row.put("userName", rs.getString("user_name"));
                row.put("carInfo", rs.getString("brand") + " " + rs.getString("model"));
                row.put("startDate", rs.getDate("start_date"));
                row.put("endDate", rs.getDate("end_date"));
                row.put("status", rs.getString("status"));
                row.put("totalPrice", rs.getDouble("total_price"));
                results.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return results;
    }
    
    public List<Map<String, Object>> getTopRentedCars(int limit) {
        String sql = "SELECT c.id, c.brand, c.model, COUNT(b.id) as rental_count " +
                    "FROM cars c " +
                    "LEFT JOIN bookings b ON c.id = b.car_id " +
                    "GROUP BY c.id, c.brand, c.model " +
                    "ORDER BY rental_count DESC LIMIT ?";
        
        List<Map<String, Object>> results = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", rs.getInt("id"));
                row.put("brand", rs.getString("brand"));
                row.put("model", rs.getString("model"));
                row.put("rentalCount", rs.getInt("rental_count"));
                results.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return results;
    }
    
    // Revenue Report Methods
    public Map<String, Object> getDailyRevenueReport() {
        String sql = "SELECT DATE(created_at) as date, SUM(total_price) as revenue " +
                    "FROM bookings WHERE status = 'completed' AND created_at >= DATE_SUB(CURDATE(), INTERVAL 30 DAY) " +
                    "GROUP BY DATE(created_at) ORDER BY date";
        return executeRevenueQuery(sql);
    }
    
    public Map<String, Object> getWeeklyRevenueReport() {
        String sql = "SELECT YEAR(created_at) as year, WEEK(created_at) as week, SUM(total_price) as revenue " +
                    "FROM bookings WHERE status = 'completed' AND created_at >= DATE_SUB(CURDATE(), INTERVAL 12 WEEK) " +
                    "GROUP BY YEAR(created_at), WEEK(created_at) ORDER BY year, week";
        return executeRevenueQuery(sql);
    }
    
    public Map<String, Object> getMonthlyRevenueReport() {
        String sql = "SELECT YEAR(created_at) as year, MONTH(created_at) as month, SUM(total_price) as revenue " +
                    "FROM bookings WHERE status = 'completed' AND created_at >= DATE_SUB(CURDATE(), INTERVAL 12 MONTH) " +
                    "GROUP BY YEAR(created_at), MONTH(created_at) ORDER BY year, month";
        return executeRevenueQuery(sql);
    }
    
    public Map<String, Object> getYearlyRevenueReport() {
        String sql = "SELECT YEAR(created_at) as year, SUM(total_price) as revenue " +
                    "FROM bookings WHERE status = 'completed' " +
                    "GROUP BY YEAR(created_at) ORDER BY year";
        return executeRevenueQuery(sql);
    }
    
    public double getTotalRevenueForPeriod(String period) {
        String sql = "";
        switch (period.toLowerCase()) {
            case "daily":
                sql = "SELECT COALESCE(SUM(total_price), 0) as revenue FROM bookings " +
                     "WHERE status = 'completed' AND DATE(created_at) = CURDATE()";
                break;
            case "weekly":
                sql = "SELECT COALESCE(SUM(total_price), 0) as revenue FROM bookings " +
                     "WHERE status = 'completed' AND YEARWEEK(created_at) = YEARWEEK(CURDATE())";
                break;
            case "monthly":
                sql = "SELECT COALESCE(SUM(total_price), 0) as revenue FROM bookings " +
                     "WHERE status = 'completed' AND MONTH(created_at) = MONTH(CURDATE()) " +
                     "AND YEAR(created_at) = YEAR(CURDATE())";
                break;
            case "yearly":
                sql = "SELECT COALESCE(SUM(total_price), 0) as revenue FROM bookings " +
                     "WHERE status = 'completed' AND YEAR(created_at) = YEAR(CURDATE())";
                break;
        }
        return getDoubleValue(sql, "revenue");
    }
    
    // Car Utilization Methods
    public List<Map<String, Object>> getCarUtilizationStats() {
        String sql = "SELECT c.id, c.brand, c.model, " +
                    "COUNT(b.id) as total_bookings, " +
                    "SUM(CASE WHEN b.status = 'completed' THEN 1 ELSE 0 END) as completed_bookings, " +
                    "COALESCE(SUM(b.total_price), 0) as total_revenue " +
                    "FROM cars c " +
                    "LEFT JOIN bookings b ON c.id = b.car_id " +
                    "GROUP BY c.id, c.brand, c.model " +
                    "ORDER BY total_revenue DESC";
        
        return executeListQuery(sql);
    }
    
    public double getOverallUtilizationRate() {
        String sql = "SELECT " +
                    "(SELECT COUNT(DISTINCT car_id) FROM bookings WHERE status = 'completed' AND " +
                    "start_date <= CURDATE() AND end_date >= CURDATE()) * 100.0 / " +
                    "NULLIF((SELECT COUNT(*) FROM cars WHERE status = 'available'), 0) as utilization_rate";
        
        return getDoubleValue(sql, "utilization_rate");
    }
    
    // User Activity Methods
    public List<Map<String, Object>> getUserRegistrationTrends() {
        String sql = "SELECT DATE(created_at) as date, COUNT(*) as registrations " +
                    "FROM users WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL 30 DAY) " +
                    "GROUP BY DATE(created_at) ORDER BY date";
        
        return executeListQuery(sql);
    }
    
    public List<Map<String, Object>> getTopUsersByBookings(int limit) {
        String sql = "SELECT u.id, u.name, u.email, COUNT(b.id) as total_bookings, " +
                    "COALESCE(SUM(b.total_price), 0) as total_spent " +
                    "FROM users u " +
                    "LEFT JOIN bookings b ON u.id = b.user_id " +
                    "GROUP BY u.id, u.name, u.email " +
                    "ORDER BY total_bookings DESC LIMIT ?";
        
        List<Map<String, Object>> results = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", rs.getInt("id"));
                row.put("name", rs.getString("name"));
                row.put("email", rs.getString("email"));
                row.put("totalBookings", rs.getInt("total_bookings"));
                row.put("totalSpent", rs.getDouble("total_spent"));
                results.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return results;
    }
    
    // Booking Analytics Methods
    public List<Map<String, Object>> getBookingTrends(String timeframe) {
        String sql = "";
        switch (timeframe.toLowerCase()) {
            case "daily":
                sql = "SELECT DATE(created_at) as period, COUNT(*) as bookings " +
                     "FROM bookings WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL 30 DAY) " +
                     "GROUP BY DATE(created_at) ORDER BY period";
                break;
            case "weekly":
                sql = "SELECT YEAR(created_at) as year, WEEK(created_at) as week, COUNT(*) as bookings " +
                     "FROM bookings WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL 12 WEEK) " +
                     "GROUP BY YEAR(created_at), WEEK(created_at) ORDER BY year, week";
                break;
            case "monthly":
                sql = "SELECT YEAR(created_at) as year, MONTH(created_at) as month, COUNT(*) as bookings " +
                     "FROM bookings WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL 12 MONTH) " +
                     "GROUP BY YEAR(created_at), MONTH(created_at) ORDER BY year, month";
                break;
            default:
                sql = "SELECT DATE(created_at) as period, COUNT(*) as bookings " +
                     "FROM bookings WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL 30 DAY) " +
                     "GROUP BY DATE(created_at) ORDER BY period";
        }
        return executeListQuery(sql);
    }
    
    public List<Map<String, Object>> getBookingStatusDistribution() {
        String sql = "SELECT status, COUNT(*) as count FROM bookings GROUP BY status";
        return executeListQuery(sql);
    }
    
    public List<Map<String, Object>> getPeakBookingHours() {
        String sql = "SELECT HOUR(created_at) as hour, COUNT(*) as bookings " +
                    "FROM bookings GROUP BY HOUR(created_at) ORDER BY bookings DESC";
        return executeListQuery(sql);
    }
    
    public double getAverageRentalDuration() {
        String sql = "SELECT AVG(DATEDIFF(end_date, start_date)) as avg_duration FROM bookings WHERE status = 'completed'";
        return getDoubleValue(sql, "avg_duration");
    }
    
    // Helper Methods
    private int getCount(String sql) {
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    private double getDoubleValue(String sql, String column) {
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble(column);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
    
    private Map<String, Object> executeRevenueQuery(String sql) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> data = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                ResultSetMetaData metaData = rs.getMetaData();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    row.put(metaData.getColumnName(i), rs.getObject(i));
                }
                data.add(row);
            }
            result.put("data", data);
            
        } catch (SQLException e) {
            e.printStackTrace();
            result.put("error", e.getMessage());
        }
        
        return result;
    }
    
    private List<Map<String, Object>> executeListQuery(String sql) {
        List<Map<String, Object>> results = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(metaData.getColumnName(i), rs.getObject(i));
                }
                results.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return results;
    }
    
    // Add these missing method implementations with your actual table structure
    public Map<String, Object> getBookingReportByDateRange(String startDate, String endDate) {
        Map<String, Object> report = new HashMap<>();
        String sql = "SELECT " +
                    "COUNT(*) as totalBookings, " +
                    "SUM(CASE WHEN status = 'completed' THEN 1 ELSE 0 END) as completedBookings, " +
                    "SUM(CASE WHEN status = 'cancelled' THEN 1 ELSE 0 END) as cancelledBookings, " +
                    "SUM(CASE WHEN status = 'pending' THEN 1 ELSE 0 END) as pendingBookings, " +
                    "AVG(DATEDIFF(end_date, start_date)) as avgRentalDuration " +
                    "FROM bookings " +
                    "WHERE created_at BETWEEN ? AND ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, startDate + " 00:00:00");
            stmt.setString(2, endDate + " 23:59:59");
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                report.put("totalBookings", rs.getInt("totalBookings"));
                report.put("completedBookings", rs.getInt("completedBookings"));
                report.put("cancelledBookings", rs.getInt("cancelledBookings"));
                report.put("pendingBookings", rs.getInt("pendingBookings"));
                report.put("avgRentalDuration", rs.getDouble("avgRentalDuration"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return report;
    }
    
    public Map<String, Object> getCurrentMonthBookingReport() {
        Map<String, Object> report = new HashMap<>();
        String sql = "SELECT " +
                    "COUNT(*) as totalBookings, " +
                    "SUM(CASE WHEN status = 'completed' THEN 1 ELSE 0 END) as completedBookings, " +
                    "SUM(CASE WHEN status = 'cancelled' THEN 1 ELSE 0 END) as cancelledBookings, " +
                    "SUM(CASE WHEN status = 'pending' THEN 1 ELSE 0 END) as pendingBookings, " +
                    "AVG(DATEDIFF(end_date, start_date)) as avgRentalDuration " +
                    "FROM bookings " +
                    "WHERE MONTH(created_at) = MONTH(CURDATE()) AND YEAR(created_at) = YEAR(CURDATE())";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                report.put("totalBookings", rs.getInt("totalBookings"));
                report.put("completedBookings", rs.getInt("completedBookings"));
                report.put("cancelledBookings", rs.getInt("cancelledBookings"));
                report.put("pendingBookings", rs.getInt("pendingBookings"));
                report.put("avgRentalDuration", rs.getDouble("avgRentalDuration"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return report;
    }
    
    public Map<String, Object> getUserActivitySummary() {
        Map<String, Object> summary = new HashMap<>();
        // Implementation for user activity summary
        return summary;
    }
    
    public List<Map<String, Object>> getUserStatusDistribution() {
        // Implementation for user status distribution
        return new ArrayList<>();
    }
    
    public List<Map<String, Object>> getPopularCarBrands() {
        String sql = "SELECT c.brand, COUNT(b.id) as booking_count " +
                    "FROM cars c " +
                    "JOIN bookings b ON c.id = b.car_id " +
                    "GROUP BY c.brand " +
                    "ORDER BY booking_count DESC";
        return executeListQuery(sql);
    }
    
    public List<Map<String, Object>> getCarStatusDistribution() {
        String sql = "SELECT status, COUNT(*) as count FROM cars GROUP BY status";
        return executeListQuery(sql);
    }
    
    public List<Map<String, Object>> getRevenueByCarBrand() {
        String sql = "SELECT c.brand, SUM(b.total_price) as revenue " +
                    "FROM cars c " +
                    "JOIN bookings b ON c.id = b.car_id " +
                    "WHERE b.status = 'completed' " +
                    "GROUP BY c.brand " +
                    "ORDER BY revenue DESC";
        return executeListQuery(sql);
    }
    
    public double getAverageRevenueForPeriod(String period) {
        // Implementation for average revenue calculation
        return 0.0;
    }
    
    public int getTotalTransactionsForPeriod(String period) {
        // Implementation for total transactions
        return 0;
    }
}