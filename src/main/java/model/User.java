package model;

import java.sql.Timestamp;

public class User {
    private int id;
    private String name;  // Combined first + last name
    private String email;
    private String password;
    private String phone;
    private String address;
    private String licenseNumber;
    private String role;
    private Timestamp createdAt;
    
    // Constructors
    public User() {}
    
    public User(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    
    // Full constructor with all fields
    public User(String name, String email, String password, String phone, String address, String licenseNumber, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.licenseNumber = licenseNumber;
        this.role = role;
    }
    
    // Constructor for database results (with ID and createdAt)
    public User(int id, String name, String email, String password, String phone, String address, String licenseNumber, String role, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.licenseNumber = licenseNumber;
        this.role = role;
        this.createdAt = createdAt;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    
    // Utility methods for name handling
    public String getFirstName() {
        if (name == null || name.trim().isEmpty()) {
            return "";
        }
        String[] nameParts = name.split(" ");
        return nameParts[0];
    }
    
    public String getLastName() {
        if (name == null || name.trim().isEmpty()) {
            return "";
        }
        String[] nameParts = name.split(" ");
        if (nameParts.length > 1) {
            return nameParts[1];
        }
        return "";
    }
    
    public void setFirstName(String firstName) {
        String lastName = getLastName();
        this.name = firstName + (lastName.isEmpty() ? "" : " " + lastName);
    }
    
    public void setLastName(String lastName) {
        String firstName = getFirstName();
        this.name = firstName + (lastName.isEmpty() ? "" : " " + lastName);
    }
    
    public String getFullName() {
        return name;
    }
    
    // Business logic methods
    public boolean isAdmin() {
        return "admin".equalsIgnoreCase(role);
    }
    
    public boolean isCustomer() {
        return "customer".equalsIgnoreCase(role) || "user".equalsIgnoreCase(role);
    }
    
    public boolean hasValidLicense() {
        return licenseNumber != null && !licenseNumber.trim().isEmpty();
    }
    
    // Validation methods
    public boolean isValid() {
        return name != null && !name.trim().isEmpty() &&
               email != null && !email.trim().isEmpty() &&
               password != null && !password.trim().isEmpty() &&
               phone != null && !phone.trim().isEmpty() &&
               address != null && !address.trim().isEmpty() &&
               licenseNumber != null && !licenseNumber.trim().isEmpty() &&
               role != null && !role.trim().isEmpty();
    }
    
    public String getValidationErrors() {
        StringBuilder errors = new StringBuilder();
        
        if (name == null || name.trim().isEmpty()) {
            errors.append("Name is required. ");
        }
        if (email == null || email.trim().isEmpty()) {
            errors.append("Email is required. ");
        }
        if (password == null || password.trim().isEmpty()) {
            errors.append("Password is required. ");
        }
        if (phone == null || phone.trim().isEmpty()) {
            errors.append("Phone is required. ");
        }
        if (address == null || address.trim().isEmpty()) {
            errors.append("Address is required. ");
        }
        if (licenseNumber == null || licenseNumber.trim().isEmpty()) {
            errors.append("License number is required. ");
        }
        if (role == null || role.trim().isEmpty()) {
            errors.append("Role is required. ");
        }
        
        return errors.toString().trim();
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", role='" + role + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
    
    // Equals and hashCode methods for proper object comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        User user = (User) o;
        
        if (id != user.id) return false;
        return email != null ? email.equals(user.email) : user.email == null;
    }
    
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}