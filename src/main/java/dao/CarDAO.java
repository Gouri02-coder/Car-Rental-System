package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Car;

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