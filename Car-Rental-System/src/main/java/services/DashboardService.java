package services;

import dao.BookingDAO;
import dao.CarDAO;
import dao.UserDAO;
import model.Booking;
import model.Car;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class DashboardService {
    private BookingDAO bookingDAO;
    private CarDAO carDAO;
    private UserDAO userDAO;
    
    public DashboardService() {
        this.bookingDAO = new BookingDAO();
        this.carDAO = new CarDAO();
        this.userDAO = new UserDAO();
    }
    
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        
        try {
            // Car statistics
            Map<String, Integer> carStats = carDAO.getCarStats();
            stats.put("totalCars", carStats.get("total"));
            stats.put("availableCars", carStats.get("available"));
            stats.put("rentedCars", carStats.get("rented"));
            stats.put("maintenanceCars", carStats.get("maintenance"));
            
            // Revenue statistics
            stats.put("totalRevenue", bookingDAO.getTotalRevenue());
            stats.put("monthlyRevenue", bookingDAO.getMonthlyRevenue());
            stats.put("pendingPayments", getPendingPaymentsAmount());
            
            // Booking statistics
            Map<String, Integer> bookingStats = bookingDAO.getBookingStatistics();
            stats.put("totalBookings", getTotalBookingsCount(bookingStats));
            stats.put("pendingBookings", bookingStats.getOrDefault("pending", 0));
            stats.put("confirmedBookings", bookingStats.getOrDefault("confirmed", 0));
            stats.put("activeBookings", bookingStats.getOrDefault("active", 0));
            stats.put("completedBookings", bookingStats.getOrDefault("completed", 0));
            stats.put("cancelledBookings", bookingStats.getOrDefault("cancelled", 0));
            
            // Additional stats
            stats.put("todaysBookings", bookingDAO.getTodaysBookingsCount());
            stats.put("pendingBookingsCount", bookingDAO.getPendingBookingsCount());
            
            // User statistics
            stats.put("totalUsers", userDAO.getTotalUsersCount());
            stats.put("newUsersToday", userDAO.getNewUsersTodayCount());
            
            // Rates and calculations
            stats.put("occupancyRate", calculateOccupancyRate());
            stats.put("completionRate", calculateCompletionRate());
            stats.put("avgRevenuePerBooking", calculateAverageRevenuePerBooking());
            
            // Recent data
            stats.put("recentBookings", bookingDAO.getRecentBookings(5));
            stats.put("recentCars", carDAO.getRecentlyAddedCars());
            
            // Brand statistics
            stats.put("brandStats", carDAO.getCarsByBrandStatistics());
            
            // Fleet value
            stats.put("totalFleetValue", carDAO.getTotalCarsValue());
            
        } catch (Exception e) {
            e.printStackTrace();
            setDefaultStats(stats);
        }
        
        return stats;
    }
    
    private double calculateOccupancyRate() {
        try {
            int totalCars = carDAO.getTotalCarsCount();
            int rentedCars = carDAO.getRentedCarsCount();
            return totalCars > 0 ? (double) rentedCars / totalCars * 100 : 0.0;
        } catch (Exception e) {
            return 0.0;
        }
    }
    
    private double calculateCompletionRate() {
        try {
            Map<String, Integer> stats = bookingDAO.getBookingStatistics();
            int totalBookings = getTotalBookingsCount(stats);
            int completedBookings = stats.getOrDefault("completed", 0);
            return totalBookings > 0 ? (double) completedBookings / totalBookings * 100 : 0.0;
        } catch (Exception e) {
            return 0.0;
        }
    }
    
    private double calculateAverageRevenuePerBooking() {
        try {
            double totalRevenue = bookingDAO.getTotalRevenue();
            Map<String, Integer> stats = bookingDAO.getBookingStatistics();
            int completedBookings = stats.getOrDefault("completed", 0);
            return completedBookings > 0 ? totalRevenue / completedBookings : 0.0;
        } catch (Exception e) {
            return 0.0;
        }
    }
    
    private double getPendingPaymentsAmount() {
        try {
            List<Booking> pendingBookings = bookingDAO.getBookingsByStatus("pending");
            return pendingBookings.stream()
                    .mapToDouble(Booking::getTotalPrice)
                    .sum();
        } catch (Exception e) {
            return 0.0;
        }
    }
    
    private int getTotalBookingsCount(Map<String, Integer> bookingStats) {
        return bookingStats.values().stream().mapToInt(Integer::intValue).sum();
    }
    
    private void setDefaultStats(Map<String, Object> stats) {
        stats.putIfAbsent("totalCars", 0);
        stats.putIfAbsent("availableCars", 0);
        stats.putIfAbsent("rentedCars", 0);
        stats.putIfAbsent("maintenanceCars", 0);
        stats.putIfAbsent("totalRevenue", 0.0);
        stats.putIfAbsent("monthlyRevenue", 0.0);
        stats.putIfAbsent("pendingPayments", 0.0);
        stats.putIfAbsent("totalBookings", 0);
        stats.putIfAbsent("pendingBookings", 0);
        stats.putIfAbsent("confirmedBookings", 0);
        stats.putIfAbsent("activeBookings", 0);
        stats.putIfAbsent("completedBookings", 0);
        stats.putIfAbsent("cancelledBookings", 0);
        stats.putIfAbsent("todaysBookings", 0);
        stats.putIfAbsent("pendingBookingsCount", 0);
        stats.putIfAbsent("totalUsers", 0);
        stats.putIfAbsent("newUsersToday", 0);
        stats.putIfAbsent("occupancyRate", 0.0);
        stats.putIfAbsent("completionRate", 0.0);
        stats.putIfAbsent("avgRevenuePerBooking", 0.0);
        stats.putIfAbsent("recentBookings", new ArrayList<>());
        stats.putIfAbsent("recentCars", new ArrayList<>());
        stats.putIfAbsent("brandStats", new HashMap<>());
        stats.putIfAbsent("totalFleetValue", 0.0);
    }
}