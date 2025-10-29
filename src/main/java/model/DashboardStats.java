package model;

import java.util.Map;
import java.util.HashMap;

public class DashboardStats {
    // User Statistics
    private int totalUsers;
    private int activeUsers;
    private int newUsersToday;
    private int newUsersThisWeek;
    private int newUsersThisMonth;
    
    // Car Statistics
    private int totalCars;
    private int availableCars;
    private int rentedCars;
    private int maintenanceCars;
    
    // Booking Statistics
    private int totalBookings;
    private int activeBookings;
    private int pendingBookings;
    private int completedBookings;
    private int cancelledBookings;
    private int bookingsToday;
    private int bookingsThisWeek;
    private int bookingsThisMonth;
    
    // Financial Statistics
    private double totalRevenue;
    private double revenueToday;
    private double revenueThisWeek;
    private double revenueThisMonth;
    private double pendingPayments;
    private double completedPayments;
    
    // System Statistics
    private int lowAvailabilityCars; // Cars with less than 3 available
    private int overdueBookings;
    private int pendingReviews;
    
    // Constructor
    public DashboardStats() {}
    
    // Utility methods
    public double getOccupancyRate() {
        if (totalCars == 0) return 0;
        return (double) rentedCars / totalCars * 100;
    }
    
    public double getBookingCompletionRate() {
        if (totalBookings == 0) return 0;
        return (double) completedBookings / totalBookings * 100;
    }
    
    public double getAverageRevenuePerBooking() {
        if (completedBookings == 0) return 0;
        return totalRevenue / completedBookings;
    }
    
    public Map<String, Integer> getBookingStatusDistribution() {
        Map<String, Integer> distribution = new HashMap<>();
        distribution.put("active", activeBookings);
        distribution.put("pending", pendingBookings);
        distribution.put("completed", completedBookings);
        distribution.put("cancelled", cancelledBookings);
        return distribution;
    }
    
    public Map<String, Integer> getCarStatusDistribution() {
        Map<String, Integer> distribution = new HashMap<>();
        distribution.put("available", availableCars);
        distribution.put("rented", rentedCars);
        distribution.put("maintenance", maintenanceCars);
        return distribution;
    }
    
    // Getters and Setters
    public int getTotalUsers() { return totalUsers; }
    public void setTotalUsers(int totalUsers) { this.totalUsers = totalUsers; }
    
    public int getActiveUsers() { return activeUsers; }
    public void setActiveUsers(int activeUsers) { this.activeUsers = activeUsers; }
    
    public int getNewUsersToday() { return newUsersToday; }
    public void setNewUsersToday(int newUsersToday) { this.newUsersToday = newUsersToday; }
    
    public int getNewUsersThisWeek() { return newUsersThisWeek; }
    public void setNewUsersThisWeek(int newUsersThisWeek) { this.newUsersThisWeek = newUsersThisWeek; }
    
    public int getNewUsersThisMonth() { return newUsersThisMonth; }
    public void setNewUsersThisMonth(int newUsersThisMonth) { this.newUsersThisMonth = newUsersThisMonth; }
    
    public int getTotalCars() { return totalCars; }
    public void setTotalCars(int totalCars) { this.totalCars = totalCars; }
    
    public int getAvailableCars() { return availableCars; }
    public void setAvailableCars(int availableCars) { this.availableCars = availableCars; }
    
    public int getRentedCars() { return rentedCars; }
    public void setRentedCars(int rentedCars) { this.rentedCars = rentedCars; }
    
    public int getMaintenanceCars() { return maintenanceCars; }
    public void setMaintenanceCars(int maintenanceCars) { this.maintenanceCars = maintenanceCars; }
    
    public int getTotalBookings() { return totalBookings; }
    public void setTotalBookings(int totalBookings) { this.totalBookings = totalBookings; }
    
    public int getActiveBookings() { return activeBookings; }
    public void setActiveBookings(int activeBookings) { this.activeBookings = activeBookings; }
    
    public int getPendingBookings() { return pendingBookings; }
    public void setPendingBookings(int pendingBookings) { this.pendingBookings = pendingBookings; }
    
    public int getCompletedBookings() { return completedBookings; }
    public void setCompletedBookings(int completedBookings) { this.completedBookings = completedBookings; }
    
    public int getCancelledBookings() { return cancelledBookings; }
    public void setCancelledBookings(int cancelledBookings) { this.cancelledBookings = cancelledBookings; }
    
    public int getBookingsToday() { return bookingsToday; }
    public void setBookingsToday(int bookingsToday) { this.bookingsToday = bookingsToday; }
    
    public int getBookingsThisWeek() { return bookingsThisWeek; }
    public void setBookingsThisWeek(int bookingsThisWeek) { this.bookingsThisWeek = bookingsThisWeek; }
    
    public int getBookingsThisMonth() { return bookingsThisMonth; }
    public void setBookingsThisMonth(int bookingsThisMonth) { this.bookingsThisMonth = bookingsThisMonth; }
    
    public double getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(double totalRevenue) { this.totalRevenue = totalRevenue; }
    
    public double getRevenueToday() { return revenueToday; }
    public void setRevenueToday(double revenueToday) { this.revenueToday = revenueToday; }
    
    public double getRevenueThisWeek() { return revenueThisWeek; }
    public void setRevenueThisWeek(double revenueThisWeek) { this.revenueThisWeek = revenueThisWeek; }
    
    public double getRevenueThisMonth() { return revenueThisMonth; }
    public void setRevenueThisMonth(double revenueThisMonth) { this.revenueThisMonth = revenueThisMonth; }
    
    public double getPendingPayments() { return pendingPayments; }
    public void setPendingPayments(double pendingPayments) { this.pendingPayments = pendingPayments; }
    
    public double getCompletedPayments() { return completedPayments; }
    public void setCompletedPayments(double completedPayments) { this.completedPayments = completedPayments; }
    
    public int getLowAvailabilityCars() { return lowAvailabilityCars; }
    public void setLowAvailabilityCars(int lowAvailabilityCars) { this.lowAvailabilityCars = lowAvailabilityCars; }
    
    public int getOverdueBookings() { return overdueBookings; }
    public void setOverdueBookings(int overdueBookings) { this.overdueBookings = overdueBookings; }
    
    public int getPendingReviews() { return pendingReviews; }
    public void setPendingReviews(int pendingReviews) { this.pendingReviews = pendingReviews; }
}