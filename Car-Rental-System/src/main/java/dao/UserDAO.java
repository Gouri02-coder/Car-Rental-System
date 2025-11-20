package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
    
    // Update user profile (name, phone, address only)
    public boolean updateUserProfile(User user) {
        String sql = "UPDATE users SET first_name = ?, last_name = ?, phone = ?, address = ? WHERE id = ?";
        
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
            stmt.setString(3, user.getPhone());
            stmt.setString(4, user.getAddress());
            stmt.setInt(5, user.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Update user with all fields
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
    
    // Update user information (for admin - specific parameters from your image)
    public boolean updateUser(int userId, String email, String phone, String licenseNumber) {
        String sql = "UPDATE users SET email = ?, phone = ?, license_number = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            stmt.setString(2, phone);
            stmt.setString(3, licenseNumber);
            stmt.setInt(4, userId);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Toggle user active status (for admin)
    public boolean toggleUserStatus(int userId) {
        // First get current user to check if active field exists
        User user = getUserById(userId);
        if (user == null) {
            return false;
        }
        
        // Check if is_active column exists, if not use a different approach
        String sql = "UPDATE users SET is_active = NOT is_active WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            // If is_active column doesn't exist, use role-based approach
            System.out.println("is_active column not found, using role-based approach");
            return toggleUserStatusByRole(userId);
        }
    }
    
    // Alternative method if is_active column doesn't exist
    private boolean toggleUserStatusByRole(int userId) {
        User user = getUserById(userId);
        if (user == null) {
            return false;
        }
        
        String newRole = "user".equals(user.getRole()) ? "inactive" : "user";
        String sql = "UPDATE users SET role = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, newRole);
            stmt.setInt(2, userId);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Update user password
    public boolean updateUserPassword(int userId, String newPassword) {
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
    
    // Verify current password
    public boolean verifyPassword(String email, String password) {
        String sql = "SELECT password FROM users WHERE email = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                return storedPassword.equals(password);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Method to change password (alias for updateUserPassword)
    public boolean changePassword(int userId, String newPassword) {
        return updateUserPassword(userId, newPassword);
    }
    
    // Method to get all users (for admin)
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
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
    
    // Get user by email
    public User getUserByEmail(String email) {
        User user = null;
        String sql = "SELECT * FROM users WHERE email = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
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
    
    // Update user role (for admin)
    public boolean updateUserRole(int userId, String role) {
        String sql = "UPDATE users SET role = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, role);
            stmt.setInt(2, userId);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Get users by role
    public List<User> getUsersByRole(String role) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = ? ORDER BY created_at DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, role);
            ResultSet rs = stmt.executeQuery();
            
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
    
    // Search users by name or email
    public List<User> searchUsers(String searchTerm) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE first_name LIKE ? OR last_name LIKE ? OR email LIKE ? ORDER BY created_at DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String likeTerm = "%" + searchTerm + "%";
            stmt.setString(1, likeTerm);
            stmt.setString(2, likeTerm);
            stmt.setString(3, likeTerm);
            
            ResultSet rs = stmt.executeQuery();
            
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
    
    // Get total users count
    public int getTotalUsersCount() {
        String sql = "SELECT COUNT(*) as count FROM users";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    // Get active users count
    public int getActiveUsersCount() {
        // Try to use is_active column first
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) as count FROM users WHERE is_active = true");
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            // If is_active doesn't exist, count users with role 'user'
            String sql = "SELECT COUNT(*) as count FROM users WHERE role = 'user'";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                
                if (rs.next()) {
                    return rs.getInt("count");
                }
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return 0;
    }
    
    // Get user statistics for admin dashboard
    public java.util.Map<String, Integer> getUserStatistics() {
        java.util.Map<String, Integer> stats = new java.util.HashMap<>();
        
        // Total users
        stats.put("totalUsers", getTotalUsersCount());
        
        // Active users
        stats.put("activeUsers", getActiveUsersCount());
        
        // Users by role
        String[] roles = {"admin", "user", "inactive"};
        for (String role : roles) {
            String sql = "SELECT COUNT(*) as count FROM users WHERE role = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                
                stmt.setString(1, role);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    stats.put(role + "Users", rs.getInt("count"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return stats;
    }
    public int getTotalUsersCount1() {
        String sql = "SELECT COUNT(*) FROM users";
        return getCount(sql);
    }
    
    public int getNewUsersTodayCount() {
        String sql = "SELECT COUNT(*) FROM users WHERE DATE(created_at) = CURDATE()";
        return getCount(sql);
    }
    
    public int getActiveUsersCount1() {
        String sql = "SELECT COUNT(*) FROM users WHERE status = 'active'";
        return getCount(sql);
    }
    
    private int getCount(String sql) {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}