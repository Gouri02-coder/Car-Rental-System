package services;

import dao.BookingDAO;
import dao.CarDAO;
import dao.UserDAO;
import model.Booking;
import model.Car;
import model.User;
import java.util.*;

public class DashboardService {
    private CarDAO carDAO;
    private BookingDAO bookingDAO;
    private UserDAO userDAO;
    
    public DashboardService() {
        this.carDAO = new CarDAO();
        this.bookingDAO = new BookingDAO();
        this.userDAO = new UserDAO();
    }
    
    public Map<String, Object> getDashboardData() {
        Map<String, Object> dashboardData = new HashMap<>();
        
        try {
            // Car statistics
            List<Car> allCars = carDAO.getAllCars();
            List<Car> availableCars = carDAO.getAvailableCars();
            
            int totalCars = allCars.size();
            int availableCount = availableCars.size();
            int rentedCount = totalCars - availableCount;
            
            dashboardData.put("totalCars", totalCars);
            dashboardData.put("availableCars", availableCount);
            dashboardData.put("rentedCars", rentedCount);
            dashboardData.put("maintenanceCars", 0);
            
            // Booking statistics
            List<Booking> allBookings = bookingDAO.getAllBookings();
            Map<String, Integer> bookingStats = bookingDAO.getBookingStatistics();
            
            dashboardData.put("totalBookings", allBookings.size());
            dashboardData.put("activeBookings", bookingStats.getOrDefault("active", 0) + bookingStats.getOrDefault("confirmed", 0));
            dashboardData.put("pendingBookings", bookingStats.getOrDefault("pending", 0));
            
            // Calculate total revenue
            double totalRevenue = 0;
            for (Booking booking : allBookings) {
                if (Arrays.asList("completed", "active", "confirmed").contains(booking.getStatus())) {
                    totalRevenue += booking.getTotalPrice();
                }
            }
            dashboardData.put("totalRevenue", totalRevenue);
            
            // Customer statistics
            List<User> allUsers = userDAO.getAllUsers();
            int customerCount = 0;
            for (User user : allUsers) {
                if ("customer".equals(user.getRole())) {
                    customerCount++;
                }
            }
            dashboardData.put("totalCustomers", customerCount);
            
            // Recent data - get last 5 cars and bookings
            List<Car> recentCars = allCars.subList(0, Math.min(allCars.size(), 5));
            List<Booking> recentBookings = allBookings.subList(0, Math.min(allBookings.size(), 5));
            
            dashboardData.put("recentCars", recentCars);
            dashboardData.put("recentBookings", recentBookings);
            
        } catch (Exception e) {
            e.printStackTrace();
            // Set default values in case of error
            dashboardData.put("totalCars", 0);
            dashboardData.put("availableCars", 0);
            dashboardData.put("rentedCars", 0);
            dashboardData.put("maintenanceCars", 0);
            dashboardData.put("totalBookings", 0);
            dashboardData.put("activeBookings", 0);
            dashboardData.put("pendingBookings", 0);
            dashboardData.put("totalRevenue", 0.0);
            dashboardData.put("totalCustomers", 0);
            dashboardData.put("recentCars", new ArrayList<Car>());
            dashboardData.put("recentBookings", new ArrayList<Booking>());
        }
        
        return dashboardData;
    }
}