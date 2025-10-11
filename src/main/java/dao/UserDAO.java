package dao;

import java.sql.*;
import model.User;
import util.DatabaseConnection;

public class UserDAO {
    
    public User authenticateUser(String email, String password) {
        User user = null;
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                
                // Combine first_name and last_name into name field
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                user.setName(firstName + " " + lastName);
                
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setLicenseNumber(rs.getString("license_number"));
                user.setRole(rs.getString("role"));
                user.setCreatedAt(rs.getTimestamp("created_at"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    
    public boolean registerUser(User user) {
        // Use first_name and last_name columns instead of name
        String sql = "INSERT INTO users (first_name, last_name, email, password, phone, address, license_number, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Split the full name into first and last name
            String[] nameParts = user.getName().split(" ");
            String firstName = nameParts[0];
            String lastName = nameParts.length > 1 ? nameParts[1] : "";
            
            // VALIDATION: Ensure first name is not empty
            if (firstName == null || firstName.trim().isEmpty()) {
                System.out.println("ERROR: First name cannot be empty");
                return false;
            }
            
            // VALIDATION: Ensure last name is not empty
            if (lastName == null || lastName.trim().isEmpty()) {
                System.out.println("ERROR: Last name cannot be empty");
                return false;
            }
            
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getPhone());
            stmt.setString(6, user.getAddress());
            stmt.setString(7, user.getLicenseNumber());
            stmt.setString(8, user.getRole());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("SQL Error in registerUser: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean isEmailExists(String email) {
        String sql = "SELECT id FROM users WHERE email = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean isLicenseExists(String licenseNumber) {
        String sql = "SELECT id FROM users WHERE license_number = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, licenseNumber);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public User getUserById(int userId) {
        User user = null;
        String sql = "SELECT * FROM users WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                
                // Combine first_name and last_name into name field
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                user.setName(firstName + " " + lastName);
                
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setLicenseNumber(rs.getString("license_number"));
                user.setRole(rs.getString("role"));
                user.setCreatedAt(rs.getTimestamp("created_at"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    
    // Additional method to update user profile
    public boolean updateUser(User user) {
        String sql = "UPDATE users SET first_name = ?, last_name = ?, email = ?, phone = ?, address = ?, license_number = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Split the full name into first and last name
            String[] nameParts = user.getName().split(" ");
            String firstName = nameParts[0];
            String lastName = nameParts.length > 1 ? nameParts[1] : "";
            
            // VALIDATION: Ensure first name is not empty
            if (firstName == null || firstName.trim().isEmpty()) {
                System.out.println("ERROR: First name cannot be empty");
                return false;
            }
            
            // VALIDATION: Ensure last name is not empty
            if (lastName == null || lastName.trim().isEmpty()) {
                System.out.println("ERROR: Last name cannot be empty");
                return false;
            }
            
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getAddress());
            stmt.setString(6, user.getLicenseNumber());
            stmt.setInt(7, user.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Method to change password
    public boolean changePassword(int userId, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, newPassword);
            stmt.setInt(2, userId);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Method to get all users (for admin)
    public java.util.List<User> getAllUsers() {
        java.util.List<User> users = new java.util.ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY created_at DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                
                // Combine first_name and last_name into name field
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                user.setName(firstName + " " + lastName);
                
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setLicenseNumber(rs.getString("license_number"));
                user.setRole(rs.getString("role"));
                user.setCreatedAt(rs.getTimestamp("created_at"));
                users.add(user);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    
    // Method to delete user (for admin)
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Helper method to check if user is admin
    public boolean isAdmin(int userId) {
        String sql = "SELECT role FROM users WHERE id = ? AND role = 'admin'";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}