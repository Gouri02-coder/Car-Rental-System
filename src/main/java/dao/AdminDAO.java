package dao;

import model.Admin;
import util.DatabaseConnection;
import java.sql.*;

public class AdminDAO {
    
    public Admin authenticateAdmin(String username, String password) {
        Admin admin = null;
        String query = "SELECT * FROM admins WHERE username = ? AND password = ? AND is_active = true";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                // Update last login
                updateLastLogin(rs.getInt("admin_id"));
                
                // Create and return Admin object
                admin = extractAdminFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }
    
    private void updateLastLogin(int adminId) {
        String query = "UPDATE admins SET last_login = CURRENT_TIMESTAMP WHERE admin_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, adminId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private Admin extractAdminFromResultSet(ResultSet rs) throws SQLException {
        Admin admin = new Admin();
        admin.setAdminId(rs.getInt("admin_id"));
        admin.setUsername(rs.getString("username"));
        admin.setPassword(rs.getString("password"));
        admin.setEmail(rs.getString("email"));
        admin.setFullName(rs.getString("full_name"));
        admin.setActive(rs.getBoolean("is_active"));
        admin.setCreatedAt(rs.getTimestamp("created_at"));
        admin.setLastLogin(rs.getTimestamp("last_login"));
        return admin;
    }
}