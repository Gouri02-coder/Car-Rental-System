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
        String sql = "SELECT * FROM cars WHERE available = TRUE ORDER BY created_at DESC";
        
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
        String sql = "INSERT INTO cars (brand, model, year, color, license_plate, price_per_day, available, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, car.getBrand());
            stmt.setString(2, car.getModel());
            stmt.setInt(3, car.getYear());
            stmt.setString(4, car.getColor());
            stmt.setString(5, car.getLicensePlate());
            stmt.setDouble(6, car.getPricePerDay());
            stmt.setBoolean(7, car.isAvailable());
            stmt.setString(8, car.getDescription());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateCarAvailability(int carId, boolean available) {
        String sql = "UPDATE cars SET available = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setBoolean(1, available);
            stmt.setInt(2, carId);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateCar(Car car) {
        String sql = "UPDATE cars SET brand = ?, model = ?, year = ?, color = ?, license_plate = ?, price_per_day = ?, available = ?, description = ?, image_path = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, car.getBrand());
            stmt.setString(2, car.getModel());
            stmt.setInt(3, car.getYear());
            stmt.setString(4, car.getColor());
            stmt.setString(5, car.getLicensePlate());
            stmt.setDouble(6, car.getPricePerDay());
            stmt.setBoolean(7, car.isAvailable());
            stmt.setString(8, car.getDescription());
            stmt.setString(9, car.getImagePath());
            stmt.setInt(10, car.getId());
            
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
    
    // NEW METHODS FOR DASHBOARD
    
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
        String query = "SELECT COUNT(*) FROM cars WHERE available = TRUE";
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
        String query = "SELECT COUNT(*) FROM cars WHERE available = FALSE";
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
        // If you have a maintenance status column, use it here
        // For now, returning 0 as your current schema doesn't have maintenance status
        return 0;
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
        String sql = "SELECT * FROM cars WHERE brand LIKE ? OR model LIKE ? OR color LIKE ? OR license_plate LIKE ? ORDER BY created_at DESC";
        
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
    
    public boolean isLicensePlateExists(String licensePlate) {
        String sql = "SELECT id FROM cars WHERE license_plate = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, licensePlate);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean isLicensePlateExists(String licensePlate, int excludeCarId) {
        String sql = "SELECT id FROM cars WHERE license_plate = ? AND id != ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, licensePlate);
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
        String sql = "SELECT * FROM cars WHERE price_per_day BETWEEN ? AND ? AND available = TRUE ORDER BY price_per_day";
        
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
        String query = "SELECT COALESCE(SUM(price_per_day * 30), 0) FROM cars"; // Approximate monthly value
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
        String sql = "SELECT COUNT(*) FROM cars WHERE brand LIKE ? OR model LIKE ? OR color LIKE ? OR license_plate LIKE ?";
        
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
        car.setColor(rs.getString("color"));
        car.setLicensePlate(rs.getString("license_plate"));
        car.setPricePerDay(rs.getDouble("price_per_day"));
        car.setAvailable(rs.getBoolean("available"));
        car.setDescription(rs.getString("description"));
        car.setImagePath(rs.getString("image_path"));
        car.setCreatedAt(rs.getTimestamp("created_at"));
        return car;
    }
}