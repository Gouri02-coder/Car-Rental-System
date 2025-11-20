package services;

import dao.UserDAO;
import model.User;
import java.util.List;
import java.util.Map;

public class UserService {
    private UserDAO userDAO;
    
    public UserService() {
        this.userDAO = new UserDAO();
    }
    
    // Authentication and Registration
    public User authenticateUser(String email, String password) {
        return userDAO.authenticateUser(email, password);
    }
    
    public boolean registerUser(User user) {
        // Additional validation before registration
        if (user == null || !user.isValid()) {
            System.out.println("User validation failed: " + user.getValidationErrors());
            return false;
        }
        
        // Check if email already exists
        if (userDAO.isEmailExists(user.getEmail())) {
            System.out.println("Email already exists: " + user.getEmail());
            return false;
        }
        
        // Check if license number already exists
        if (userDAO.isLicenseExists(user.getLicenseNumber())) {
            System.out.println("License number already exists: " + user.getLicenseNumber());
            return false;
        }
        
        return userDAO.registerUser(user);
    }
    
    // User Management
    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }
    
    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }
    
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
    
    public List<User> getUsersByRole(String role) {
        return userDAO.getUsersByRole(role);
    }
    
    public List<User> searchUsers(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getAllUsers();
        }
        return userDAO.searchUsers(searchTerm.trim());
    }
    
    // Update Operations
    public boolean updateUserProfile(User user) {
        if (user == null || user.getId() <= 0) {
            return false;
        }
        
        // Basic validation for profile update
        if (user.getName() == null || user.getName().trim().isEmpty() ||
            user.getPhone() == null || user.getPhone().trim().isEmpty()) {
            return false;
        }
        
        return userDAO.updateUserProfile(user);
    }
    
    public boolean updateUser(User user) {
        if (user == null || user.getId() <= 0 || !user.isValid()) {
            return false;
        }
        
        // Check if email is being changed and if it already exists for another user
        User existingUser = getUserById(user.getId());
        if (existingUser != null && !existingUser.getEmail().equals(user.getEmail())) {
            if (userDAO.isEmailExists(user.getEmail())) {
                return false; // Email already taken by another user
            }
        }
        
        return userDAO.updateUser(user);
    }
    
    public boolean updateUser(int userId, String email, String phone, String licenseNumber) {
        // Validate inputs
        if (userId <= 0 || email == null || email.trim().isEmpty() || 
            phone == null || phone.trim().isEmpty() || 
            licenseNumber == null || licenseNumber.trim().isEmpty()) {
            return false;
        }
        
        // Check if email is being changed and if it already exists for another user
        User existingUser = getUserById(userId);
        if (existingUser != null && !existingUser.getEmail().equals(email.trim())) {
            if (userDAO.isEmailExists(email.trim())) {
                return false; // Email already taken by another user
            }
        }
        
        return userDAO.updateUser(userId, email.trim(), phone.trim(), licenseNumber.trim());
    }
    
    // Status Management
    public boolean toggleUserStatus(int userId) {
        if (userId <= 0) {
            return false;
        }
        return userDAO.toggleUserStatus(userId);
    }
    
    public boolean activateUser(int userId) {
        User user = getUserById(userId);
        if (user != null && !user.isActive()) {
            user.setActive(true);
            return updateUserRole(userId, "user"); // Or implement specific activate method
        }
        return false;
    }
    
    public boolean deactivateUser(int userId) {
        User user = getUserById(userId);
        if (user != null && user.isActive()) {
            user.setActive(false);
            return updateUserRole(userId, "inactive"); // Or implement specific deactivate method
        }
        return false;
    }
    
    // Password Management
    public boolean updatePassword(int userId, String newPassword) {
        if (userId <= 0 || newPassword == null || newPassword.trim().isEmpty()) {
            return false;
        }
        
        // Add password strength validation if needed
        if (newPassword.length() < 6) {
            System.out.println("Password must be at least 6 characters long");
            return false;
        }
        
        return userDAO.updateUserPassword(userId, newPassword.trim());
    }
    
    public boolean changePassword(int userId, String currentPassword, String newPassword) {
        User user = getUserById(userId);
        if (user == null) {
            return false;
        }
        
        // Verify current password
        if (!userDAO.verifyPassword(user.getEmail(), currentPassword)) {
            System.out.println("Current password is incorrect");
            return false;
        }
        
        return updatePassword(userId, newPassword);
    }
    
    public boolean verifyPassword(String email, String password) {
        return userDAO.verifyPassword(email, password);
    }
    
    // Role Management
    public boolean updateUserRole(int userId, String role) {
        if (userId <= 0 || role == null || role.trim().isEmpty()) {
            return false;
        }
        
        // Validate role
        String validRole = role.trim().toLowerCase();
        if (!validRole.equals("admin") && !validRole.equals("user") && !validRole.equals("customer") && !validRole.equals("inactive")) {
            System.out.println("Invalid role: " + role);
            return false;
        }
        
        return userDAO.updateUserRole(userId, validRole);
    }
    
    public boolean isAdmin(int userId) {
        return userDAO.isAdmin(userId);
    }
    
    // Validation Methods
    public boolean isEmailExists(String email) {
        return userDAO.isEmailExists(email);
    }
    
    public boolean isLicenseExists(String licenseNumber) {
        return userDAO.isLicenseExists(licenseNumber);
    }
    
    // Delete Operations
    public boolean deleteUser(int userId) {
        if (userId <= 0) {
            return false;
        }
        
        // Prevent deletion of admin users or add other business rules
        User user = getUserById(userId);
        if (user != null && user.isAdmin()) {
            System.out.println("Cannot delete admin user");
            return false;
        }
        
        return userDAO.deleteUser(userId);
    }
    
    // Statistics and Reporting
    public int getTotalUsersCount() {
        return userDAO.getTotalUsersCount();
    }
    
    public int getActiveUsersCount() {
        return userDAO.getActiveUsersCount();
    }
    
    public int getInactiveUsersCount() {
        return getTotalUsersCount() - getActiveUsersCount();
    }
    
    public Map<String, Integer> getUserStatistics() {
        return userDAO.getUserStatistics();
    }
    
    // Business Logic Methods
    public boolean canUserRentVehicle(int userId) {
        User user = getUserById(userId);
        if (user == null) {
            return false;
        }
        
        // Business rules for vehicle rental eligibility
        return user.isUserActive() && 
               user.hasValidLicense() && 
               !user.isAdmin(); // Assuming admins don't rent vehicles
    }
    
    public boolean validateUserForBooking(int userId) {
        User user = getUserById(userId);
        if (user == null) {
            return false;
        }
        
        return user.isUserActive() && 
               user.hasValidLicense() && 
               user.isCustomer();
    }
    
    // Bulk Operations
    public boolean bulkUpdateUserStatus(List<Integer> userIds, boolean activate) {
        boolean allSuccess = true;
        
        for (int userId : userIds) {
            boolean success = activate ? activateUser(userId) : deactivateUser(userId);
            if (!success) {
                allSuccess = false;
                System.out.println("Failed to update status for user ID: " + userId);
            }
        }
        
        return allSuccess;
    }
    
    // Search and Filter with advanced options
    public List<User> searchUsersWithFilters(String searchTerm, String role, Boolean active) {
        List<User> users = searchUsers(searchTerm);
        
        // Apply role filter
        if (role != null && !role.trim().isEmpty()) {
            users.removeIf(user -> !user.getRole().equalsIgnoreCase(role.trim()));
        }
        
        // Apply active status filter
        if (active != null) {
            users.removeIf(user -> user.isActive() != active);
        }
        
        return users;
    }
    
    // User Creation with business validation
    public boolean createUserWithValidation(User user) {
        // Comprehensive validation
        if (user == null) {
            System.out.println("User cannot be null");
            return false;
        }
        
        String validationErrors = user.getValidationErrors();
        if (!validationErrors.isEmpty()) {
            System.out.println("Validation errors: " + validationErrors);
            return false;
        }
        
        // Check for duplicate email
        if (isEmailExists(user.getEmail())) {
            System.out.println("Email already exists: " + user.getEmail());
            return false;
        }
        
        // Check for duplicate license number
        if (isLicenseExists(user.getLicenseNumber())) {
            System.out.println("License number already exists: " + user.getLicenseNumber());
            return false;
        }
        
        // Set default values if not provided
        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            user.setRole("user");
        }
        
        user.setActive(true); // New users are active by default
        
        return userDAO.registerUser(user);
    }
}