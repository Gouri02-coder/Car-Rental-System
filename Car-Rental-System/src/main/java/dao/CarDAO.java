package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Car;
import util.DatabaseConnection;

public class CarDAO {
    
    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars ORDER BY created_at DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Car car = extractCarFromResultSet(rs);
                cars.add(car);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }
    
    public List<Car> getAvailableCars() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars WHERE status = 'Available' ORDER BY created_at DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Car car = extractCarFromResultSet(rs);
                cars.add(car);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }
    
    public Car getCarById(int carId) {
        Car car = null;
        String sql = "SELECT * FROM cars WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, carId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                car = extractCarFromResultSet(rs);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return car;
    }
    
    public boolean addCar(Car car) {
        String sql = "INSERT INTO cars (brand, model, year, type, color, registration_number, seating_capacity, fuel_type, daily_rate, status, description, features, image_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, car.getBrand());
            stmt.setString(2, car.getModel());
            stmt.setInt(3, car.getYear());
            stmt.setString(4, car.getType());
            stmt.setString(5, car.getColor());
            stmt.setString(6, car.getRegistrationNumber());
            stmt.setInt(7, car.getSeatingCapacity());
            stmt.setString(8, car.getFuelType());
            stmt.setBigDecimal(9, car.getDailyRate());
            stmt.setString(10, car.getStatus());
            stmt.setString(11, car.getDescription());
            stmt.setString(12, car.getFeatures());
            stmt.setString(13, car.getImagePath());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateCarAvailability(int carId, String status) {
        String sql = "UPDATE cars SET status = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            stmt.setInt(2, carId);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateCar(Car car) {
        String sql = "UPDATE cars SET brand = ?, model = ?, year = ?, type = ?, color = ?, registration_number = ?, seating_capacity = ?, fuel_type = ?, daily_rate = ?, status = ?, description = ?, features = ?, image_path = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, car.getBrand());
            stmt.setString(2, car.getModel());
            stmt.setInt(3, car.getYear());
            stmt.setString(4, car.getType());
            stmt.setString(5, car.getColor());
            stmt.setString(6, car.getRegistrationNumber());
            stmt.setInt(7, car.getSeatingCapacity());
            stmt.setString(8, car.getFuelType());
            stmt.setBigDecimal(9, car.getDailyRate());
            stmt.setString(10, car.getStatus());
            stmt.setString(11, car.getDescription());
            stmt.setString(12, car.getFeatures());
            stmt.setString(13, car.getImagePath());
            stmt.setInt(14, car.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteCar(int carId) {
        String sql = "DELETE FROM cars WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, carId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Toggle car status (for admin)
    public boolean toggleCarStatus(int carId) {
        Car car = getCarById(carId);
        if (car == null) {
            return false;
        }
        
        String newStatus = "Available".equals(car.getStatus()) ? "Rented" : "Available";
        return updateCarAvailability(carId, newStatus);
    }
    
    // Search cars with multiple criteria
    public List<Car> searchCars(String searchQuery, String type, String status) {
        List<Car> cars = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM cars WHERE 1=1");
        List<Object> params = new ArrayList<>();
        
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            sql.append(" AND (brand LIKE ? OR model LIKE ? OR registration_number LIKE ?)");
            String likeQuery = "%" + searchQuery + "%";
            params.add(likeQuery);
            params.add(likeQuery);
            params.add(likeQuery);
        }
        
        if (type != null && !type.trim().isEmpty()) {
            sql.append(" AND type = ?");
            params.add(type);
        }
        
        if (status != null && !status.trim().isEmpty()) {
            sql.append(" AND status = ?");
            params.add(status);
        }
        
        sql.append(" ORDER BY created_at DESC");
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    cars.add(extractCarFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }
    
    // Get car statistics for admin dashboard
    public Map<String, Integer> getCarStats() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("total", getTotalCarsCount());
        stats.put("available", getAvailableCarsCount());
        stats.put("rented", getRentedCarsCount());
        stats.put("maintenance", getMaintenanceCarsCount());
        return stats;
    }
    
    public int getTotalCarsCount() {
        String query = "SELECT COUNT(*) FROM cars";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public int getAvailableCarsCount() {
        String query = "SELECT COUNT(*) FROM cars WHERE status = 'Available'";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public int getRentedCarsCount() {
        String query = "SELECT COUNT(*) FROM cars WHERE status = 'Rented'";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public int getMaintenanceCarsCount() {
        String query = "SELECT COUNT(*) FROM cars WHERE status = 'Maintenance'";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public List<Car> getRecentlyAddedCars() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars ORDER BY created_at DESC LIMIT 5";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Car car = extractCarFromResultSet(rs);
                cars.add(car);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }
    
    public List<Car> getCarsByBrand(String brand) {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars WHERE brand LIKE ? ORDER BY model";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + brand + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Car car = extractCarFromResultSet(rs);
                cars.add(car);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }
    
    public List<Car> searchCars(String searchTerm) {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars WHERE brand LIKE ? OR model LIKE ? OR color LIKE ? OR registration_number LIKE ? ORDER BY created_at DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String likeTerm = "%" + searchTerm + "%";
            stmt.setString(1, likeTerm);
            stmt.setString(2, likeTerm);
            stmt.setString(3, likeTerm);
            stmt.setString(4, likeTerm);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Car car = extractCarFromResultSet(rs);
                cars.add(car);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }
    
    public boolean isRegistrationNumberExists(String registrationNumber) {
        String sql = "SELECT id FROM cars WHERE registration_number = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, registrationNumber);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean isRegistrationNumberExists(String registrationNumber, int excludeCarId) {
        String sql = "SELECT id FROM cars WHERE registration_number = ? AND id != ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, registrationNumber);
            stmt.setInt(2, excludeCarId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Car> getCarsByPriceRange(double minPrice, double maxPrice) {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars WHERE daily_rate BETWEEN ? AND ? AND status = 'Available' ORDER BY daily_rate";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDouble(1, minPrice);
            stmt.setDouble(2, maxPrice);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Car car = extractCarFromResultSet(rs);
                cars.add(car);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }
    
    public double getTotalCarsValue() {
        String query = "SELECT COALESCE(SUM(daily_rate * 30), 0) FROM cars";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            return rs.next() ? rs.getDouble(1) : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public Map<String, Integer> getCarsByBrandStatistics() {
        Map<String, Integer> brandStats = new HashMap<>();
        String sql = "SELECT brand, COUNT(*) as count FROM cars GROUP BY brand";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                brandStats.put(rs.getString("brand"), rs.getInt("count"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return brandStats;
    }
    
    public List<Car> getCarsWithPagination(int offset, int limit) {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars ORDER BY created_at DESC LIMIT ? OFFSET ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Car car = extractCarFromResultSet(rs);
                cars.add(car);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }
    
    public int getTotalCarsCountForSearch(String searchTerm) {
        String sql = "SELECT COUNT(*) FROM cars WHERE brand LIKE ? OR model LIKE ? OR color LIKE ? OR registration_number LIKE ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String likeTerm = "%" + searchTerm + "%";
            stmt.setString(1, likeTerm);
            stmt.setString(2, likeTerm);
            stmt.setString(3, likeTerm);
            stmt.setString(4, likeTerm);
            
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    // Helper method to extract Car from ResultSet
    private Car extractCarFromResultSet(ResultSet rs) throws SQLException {
        Car car = new Car();
        car.setId(rs.getInt("id"));
        car.setBrand(rs.getString("brand")); 
        car.setModel(rs.getString("model"));
        car.setYear(rs.getInt("year"));
        car.setType(rs.getString("type"));
        car.setColor(rs.getString("color"));
        car.setRegistrationNumber(rs.getString("registration_number"));
        car.setSeatingCapacity(rs.getInt("seating_capacity"));
        car.setFuelType(rs.getString("fuel_type"));
        car.setDailyRate(rs.getBigDecimal("daily_rate"));
        car.setStatus(rs.getString("status"));
        car.setDescription(rs.getString("description"));
        car.setFeatures(rs.getString("features"));
        car.setImagePath(rs.getString("image_path"));
        car.setCreatedAt(rs.getTimestamp("created_at"));
        car.setUpdatedAt(rs.getTimestamp("updated_at"));
        return car;
    }
}