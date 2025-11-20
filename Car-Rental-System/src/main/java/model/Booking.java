package model;

import java.sql.Date;
import java.sql.Timestamp;

public class Booking {
    private int id;
    private int userId;
    private int carId;
    private Date startDate;
    private Date endDate;
    private double totalPrice;
    private String status;
    private String adminNotes;
    private Timestamp createdAt;
    
    // Additional display fields
    private String userName;
    private String carBrand;
    private String carModel;
    
    // Constructors
    public Booking() {}
    
    public Booking(int userId, int carId, Date startDate, Date endDate, double totalPrice, String status) {
        this.userId = userId;
        this.carId = carId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.status = status;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public int getCarId() { return carId; }
    public void setCarId(int carId) { this.carId = carId; }
    
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    
    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getAdminNotes() { return adminNotes; }
    public void setAdminNotes(String adminNotes) { this.adminNotes = adminNotes; }
    
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    
    public String getCarBrand() { return carBrand; }
    public void setCarBrand(String carBrand) { this.carBrand = carBrand; }
    
    public String getCarModel() { return carModel; }
    public void setCarModel(String carModel) { this.carModel = carModel; }
    
    // Helper methods for JSP compatibility
    public String getBookingDate() {
        return createdAt != null ? createdAt.toString() : "";
    }
    
    public String getPickupDate() {
        return startDate != null ? startDate.toString() : "";
    }
    
    public String getReturnDate() {
        return endDate != null ? endDate.toString() : "";
    }
    
    public double getTotalAmount() {
        return totalPrice;
    }
    
    public int getRentalDays() {
        if (startDate != null && endDate != null) {
            long diff = endDate.getTime() - startDate.getTime();
            return (int) (diff / (1000 * 60 * 60 * 24));
        }
        return 0;
    }
}