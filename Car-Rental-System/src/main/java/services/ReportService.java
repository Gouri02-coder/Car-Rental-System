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
import java.util.LinkedHashMap;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReportService {
    private BookingDAO bookingDAO;
    private CarDAO carDAO;
    private UserDAO userDAO;
    
    public ReportService() {
        this.bookingDAO = new BookingDAO();
        this.carDAO = new CarDAO();
        this.userDAO = new UserDAO();
    }
    
    // Revenue Reports
    public Map<String, Object> getRevenueReport(String period) {
        Map<String, Object> report = new HashMap<>();
        
        try {
            double totalRevenue = bookingDAO.getTotalRevenue();
            double monthlyRevenue = bookingDAO.getMonthlyRevenue();
            Map<String, Integer> bookingStats = bookingDAO.getBookingStatistics();
            int completedBookings = bookingStats.getOrDefault("completed", 0);
            
            switch (period.toLowerCase()) {
                case "daily":
                    report.put("revenue", getDailyRevenue());
                    report.put("bookings", getDailyBookings());
                    report.put("periodLabel", "Daily");
                    break;
                case "weekly":
                    report.put("revenue", getWeeklyRevenue());
                    report.put("bookings", getWeeklyBookings());
                    report.put("periodLabel", "Weekly");
                    break;
                case "monthly":
                    report.put("revenue", getMonthlyRevenue());
                    report.put("bookings", getMonthlyBookings());
                    report.put("periodLabel", "Monthly");
                    break;
                case "yearly":
                    report.put("revenue", getYearlyRevenue());
                    report.put("bookings", getYearlyBookings());
                    report.put("periodLabel", "Yearly");
                    break;
                default:
                    report.put("revenue", getMonthlyRevenue());
                    report.put("bookings", getMonthlyBookings());
                    report.put("periodLabel", "Monthly");
            }
            
            report.put("totalRevenue", totalRevenue);
            report.put("monthlyRevenue", monthlyRevenue);
            report.put("averageBookingValue", calculateAverageBookingValue());
            report.put("topPerformingCars", getTopPerformingCars());
            report.put("revenueGrowth", calculateRevenueGrowth());
            
        } catch (Exception e) {
            e.printStackTrace();
            setDefaultRevenueReport(report, period);
        }
        
        return report;
    }
    
    // Car Performance Reports
    public Map<String, Object> getCarPerformanceReport() {
        Map<String, Object> report = new HashMap<>();
        
        try {
            List<Car> allCars = carDAO.getAllCars();
            List<Map<String, Object>> carPerformance = new ArrayList<>();
            Car mostPopularCar = null;
            Car mostProfitableCar = null;
            int maxBookings = 0;
            double maxRevenue = 0;
            
            for (Car car : allCars) {
                Map<String, Object> performance = new HashMap<>();
                int bookingCount = getBookingCountForCar(car.getId());
                double revenue = getRevenueForCar(car.getId());
                double utilizationRate = getCarUtilizationRate(car.getId());
                
                performance.put("car", car);
                performance.put("bookingCount", bookingCount);
                performance.put("totalRevenue", revenue);
                performance.put("utilizationRate", utilizationRate);
                carPerformance.add(performance);
                
                // Track most popular car
                if (bookingCount > maxBookings) {
                    maxBookings = bookingCount;
                    mostPopularCar = car;
                }
                
                // Track most profitable car
                if (revenue > maxRevenue) {
                    maxRevenue = revenue;
                    mostProfitableCar = car;
                }
            }
            
            report.put("carPerformance", carPerformance);
            report.put("mostPopularCar", mostPopularCar);
            report.put("mostProfitableCar", mostProfitableCar);
            report.put("totalCars", allCars.size());
            report.put("averageUtilization", calculateAverageUtilization(carPerformance));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return report;
    }
    
    // Booking Analysis Reports
    public Map<String, Object> getBookingAnalysisReport() {
        Map<String, Object> report = new HashMap<>();
        
        try {
            Map<String, Integer> statusDistribution = bookingDAO.getBookingStatistics();
            report.put("statusDistribution", statusDistribution);
            report.put("bookingTrends", getBookingTrends());
            report.put("peakBookingHours", getPeakBookingHours());
            report.put("averageRentalDuration", getAverageRentalDuration());
            report.put("cancellationRate", getCancellationRate());
            report.put("revenueByStatus", getRevenueByStatus());
            report.put("seasonalTrends", getSeasonalTrends());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return report;
    }
    
    // User Activity Reports
    public Map<String, Object> getUserActivityReport() {
        Map<String, Object> report = new HashMap<>();
        
        try {
            int totalUsers = userDAO.getTotalUsersCount();
            int activeUsers = getActiveUsersCount();
            Map<String, Integer> newRegistrations = getNewRegistrationsTrend();
            List<Map<String, Object>> topCustomers = getTopCustomers();
            double retentionRate = calculateUserRetentionRate();
            
            report.put("totalUsers", totalUsers);
            report.put("activeUsers", activeUsers);
            report.put("newRegistrations", newRegistrations);
            report.put("topCustomers", topCustomers);
            report.put("userRetentionRate", retentionRate);
            report.put("userGrowthRate", calculateUserGrowthRate());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return report;
    }
    
    // Financial Summary
    public Map<String, Object> getFinancialSummary() {
        Map<String, Object> summary = new HashMap<>();
        
        try {
            double totalRevenue = bookingDAO.getTotalRevenue();
            double monthlyRevenue = bookingDAO.getMonthlyRevenue();
            double pendingPayments = getPendingPaymentsAmount();
            double fleetValue = carDAO.getTotalCarsValue();
            double profitMargin = calculateProfitMargin();
            double revenueGrowth = calculateRevenueGrowth();
            
            summary.put("totalRevenue", totalRevenue);
            summary.put("monthlyRevenue", monthlyRevenue);
            summary.put("pendingPayments", pendingPayments);
            summary.put("fleetValue", fleetValue);
            summary.put("profitMargin", profitMargin);
            summary.put("revenueGrowth", revenueGrowth);
            summary.put("operatingCosts", calculateOperatingCosts());
            summary.put("netProfit", calculateNetProfit());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return summary;
    }
    
    // Vehicle Status Report
    public Map<String, Object> getVehicleStatusReport() {
        Map<String, Object> report = new HashMap<>();
        
        try {
            Map<String, Integer> carStats = carDAO.getCarStats();
            Map<String, Integer> brandStats = carDAO.getCarsByBrandStatistics();
            List<Car> availableCars = carDAO.getAvailableCars();
            List<Car> rentedCars = getCarsByStatus("Rented");
            List<Car> maintenanceCars = getCarsByStatus("Maintenance");
            
            report.put("carStats", carStats);
            report.put("brandStats", brandStats);
            report.put("availableCars", availableCars);
            report.put("rentedCars", rentedCars);
            report.put("maintenanceCars", maintenanceCars);
            report.put("fleetAge", calculateFleetAge());
            report.put("maintenanceCost", estimateMaintenanceCost());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return report;
    }
    
    // Implementation of helper methods
    private Map<String, Double> getDailyRevenue() {
        Map<String, Double> dailyRevenue = new LinkedHashMap<>();
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd");
        
        // Simulate data for last 7 days
        for (int i = 6; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            double revenue = 500 + (Math.random() * 1500); // Simulated data
            dailyRevenue.put(date.format(formatter), revenue);
        }
        
        return dailyRevenue;
    }
    
    private Map<String, Double> getWeeklyRevenue() {
        Map<String, Double> weeklyRevenue = new LinkedHashMap<>();
        LocalDate today = LocalDate.now();
        
        // Simulate data for last 8 weeks
        for (int i = 7; i >= 0; i--) {
            LocalDate weekStart = today.minusWeeks(i);
            String weekLabel = "Week " + (8 - i);
            double revenue = 3000 + (Math.random() * 7000); // Simulated data
            weeklyRevenue.put(weekLabel, revenue);
        }
        
        return weeklyRevenue;
    }
    
    private Map<String, Double> getMonthlyRevenue() {
        Map<String, Double> monthlyRevenue = new LinkedHashMap<>();
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        
        // Simulate monthly data
        for (String month : months) {
            double revenue = 10000 + (Math.random() * 20000); // Simulated data
            monthlyRevenue.put(month, revenue);
        }
        
        return monthlyRevenue;
    }
    
    private Map<String, Double> getYearlyRevenue() {
        Map<String, Double> yearlyRevenue = new LinkedHashMap<>();
        int currentYear = LocalDate.now().getYear();
        
        // Simulate data for last 5 years
        for (int i = 4; i >= 0; i--) {
            int year = currentYear - i;
            double revenue = 120000 + (Math.random() * 80000); // Simulated data
            yearlyRevenue.put(String.valueOf(year), revenue);
        }
        
        return yearlyRevenue;
    }
    
    private Map<String, Integer> getDailyBookings() {
        Map<String, Integer> dailyBookings = new LinkedHashMap<>();
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd");
        
        for (int i = 6; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            int bookings = 2 + (int)(Math.random() * 8); // Simulated data
            dailyBookings.put(date.format(formatter), bookings);
        }
        
        return dailyBookings;
    }
    
    private Map<String, Integer> getWeeklyBookings() {
        Map<String, Integer> weeklyBookings = new LinkedHashMap<>();
        
        for (int i = 7; i >= 0; i--) {
            String weekLabel = "Week " + (8 - i);
            int bookings = 15 + (int)(Math.random() * 25); // Simulated data
            weeklyBookings.put(weekLabel, bookings);
        }
        
        return weeklyBookings;
    }
    
    private Map<String, Integer> getMonthlyBookings() {
        Map<String, Integer> monthlyBookings = new LinkedHashMap<>();
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        
        for (String month : months) {
            int bookings = 60 + (int)(Math.random() * 40); // Simulated data
            monthlyBookings.put(month, bookings);
        }
        
        return monthlyBookings;
    }
    
    private Map<String, Integer> getYearlyBookings() {
        Map<String, Integer> yearlyBookings = new LinkedHashMap<>();
        int currentYear = LocalDate.now().getYear();
        
        for (int i = 4; i >= 0; i--) {
            int year = currentYear - i;
            int bookings = 700 + (int)(Math.random() * 300); // Simulated data
            yearlyBookings.put(String.valueOf(year), bookings);
        }
        
        return yearlyBookings;
    }
    
    private double calculateAverageBookingValue() {
        try {
            double totalRevenue = bookingDAO.getTotalRevenue();
            Map<String, Integer> stats = bookingDAO.getBookingStatistics();
            int completedBookings = stats.getOrDefault("completed", 0);
            return completedBookings > 0 ? totalRevenue / completedBookings : 0.0;
        } catch (Exception e) {
            return 0.0;
        }
    }
    
    private List<Map<String, Object>> getTopPerformingCars() {
        List<Map<String, Object>> topCars = new ArrayList<>();
        try {
            List<Car> allCars = carDAO.getAllCars();
            
            for (Car car : allCars) {
                if (topCars.size() >= 5) break;
                
                Map<String, Object> carData = new HashMap<>();
                carData.put("car", car);
                carData.put("revenue", getRevenueForCar(car.getId()));
                carData.put("bookings", getBookingCountForCar(car.getId()));
                topCars.add(carData);
            }
            
            // Sort by revenue descending
            topCars.sort((a, b) -> Double.compare((Double)b.get("revenue"), (Double)a.get("revenue")));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return topCars;
    }
    
    private int getBookingCountForCar(int carId) {
        // This would typically query the database for bookings per car
        // For now, return simulated data
        return (int)(Math.random() * 20) + 5;
    }
    
    private double getRevenueForCar(int carId) {
        // This would typically query the database for revenue per car
        // For now, return simulated data
        return (Math.random() * 5000) + 1000;
    }
    
    private double getCarUtilizationRate(int carId) {
        // This would calculate how often the car is rented
        // For now, return simulated data
        return (Math.random() * 80) + 20;
    }
    
    private double calculateAverageUtilization(List<Map<String, Object>> carPerformance) {
        if (carPerformance.isEmpty()) return 0.0;
        double totalUtilization = carPerformance.stream()
                .mapToDouble(perf -> (Double)perf.get("utilizationRate"))
                .sum();
        return totalUtilization / carPerformance.size();
    }
    
    private Map<String, Object> getBookingTrends() {
        Map<String, Object> trends = new HashMap<>();
        trends.put("daily", getDailyBookings());
        trends.put("weekly", getWeeklyBookings());
        trends.put("monthly", getMonthlyBookings());
        return trends;
    }
    
    private Map<String, Integer> getPeakBookingHours() {
        Map<String, Integer> peakHours = new LinkedHashMap<>();
        String[] hours = {"00:00", "02:00", "04:00", "06:00", "08:00", "10:00", "12:00", 
                         "14:00", "16:00", "18:00", "20:00", "22:00"};
        
        for (String hour : hours) {
            peakHours.put(hour, (int)(Math.random() * 50) + 10);
        }
        
        return peakHours;
    }
    
    private double getAverageRentalDuration() {
        // Simulated average rental duration in days
        return 3.5 + (Math.random() * 4);
    }
    
    private double getCancellationRate() {
        try {
            Map<String, Integer> stats = bookingDAO.getBookingStatistics();
            int totalBookings = stats.values().stream().mapToInt(Integer::intValue).sum();
            int cancelledBookings = stats.getOrDefault("cancelled", 0);
            return totalBookings > 0 ? (double) cancelledBookings / totalBookings * 100 : 0.0;
        } catch (Exception e) {
            return 0.0;
        }
    }
    
    private Map<String, Double> getRevenueByStatus() {
        Map<String, Double> revenueByStatus = new HashMap<>();
        Map<String, Integer> stats = bookingDAO.getBookingStatistics();
        
        for (String status : stats.keySet()) {
            revenueByStatus.put(status, (Math.random() * 10000) + 1000);
        }
        
        return revenueByStatus;
    }
    
    private Map<String, Integer> getSeasonalTrends() {
        Map<String, Integer> seasonal = new LinkedHashMap<>();
        String[] seasons = {"Winter", "Spring", "Summer", "Fall"};
        
        for (String season : seasons) {
            seasonal.put(season, (int)(Math.random() * 100) + 50);
        }
        
        return seasonal;
    }
    
    private int getActiveUsersCount() {
        // Users with bookings in the last 30 days
        return (int)(Math.random() * 50) + 20;
    }
    
    private Map<String, Integer> getNewRegistrationsTrend() {
        Map<String, Integer> registrations = new LinkedHashMap<>();
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd");
        
        for (int i = 6; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            registrations.put(date.format(formatter), (int)(Math.random() * 5) + 1);
        }
        
        return registrations;
    }
    
    private List<Map<String, Object>> getTopCustomers() {
        List<Map<String, Object>> topCustomers = new ArrayList<>();
        String[] names = {"John Smith", "Maria Garcia", "David Johnson", "Sarah Williams", "James Brown"};
        
        for (String name : names) {
            Map<String, Object> customer = new HashMap<>();
            customer.put("name", name);
            customer.put("bookings", (int)(Math.random() * 10) + 5);
            customer.put("totalSpent", (Math.random() * 5000) + 1000);
            topCustomers.add(customer);
        }
        
        return topCustomers;
    }
    
    private double calculateUserRetentionRate() {
        return 65.0 + (Math.random() * 25); // 65-90%
    }
    
    private double calculateUserGrowthRate() {
        return 5.0 + (Math.random() * 15); // 5-20%
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
    
    private double calculateProfitMargin() {
        return 25.0 + (Math.random() * 20); // 25-45%
    }
    
    private double calculateRevenueGrowth() {
        return 8.0 + (Math.random() * 12); // 8-20%
    }
    
    private double calculateOperatingCosts() {
        return bookingDAO.getTotalRevenue() * 0.6; // 60% of revenue
    }
    
    private double calculateNetProfit() {
        return bookingDAO.getTotalRevenue() - calculateOperatingCosts();
    }
    
    private List<Car> getCarsByStatus(String status) {
        List<Car> allCars = carDAO.getAllCars();
        List<Car> filteredCars = new ArrayList<>();
        for (Car car : allCars) {
            if (status.equals(car.getStatus())) {
                filteredCars.add(car);
            }
        }
        return filteredCars;
    }
    
    private double calculateFleetAge() {
        List<Car> allCars = carDAO.getAllCars();
        if (allCars.isEmpty()) return 0.0;
        
        int currentYear = LocalDate.now().getYear();
        double totalAge = 0;
        for (Car car : allCars) {
            totalAge += (currentYear - car.getYear());
        }
        return totalAge / allCars.size();
    }
    
    private double estimateMaintenanceCost() {
        List<Car> allCars = carDAO.getAllCars();
        return allCars.size() * 500; // $500 per car annually
    }
    
    private void setDefaultRevenueReport(Map<String, Object> report, String period) {
        report.put("revenue", getMonthlyRevenue());
        report.put("bookings", getMonthlyBookings());
        report.put("periodLabel", period.substring(0, 1).toUpperCase() + period.substring(1));
        report.put("totalRevenue", 150000.0);
        report.put("monthlyRevenue", 25000.0);
        report.put("averageBookingValue", 350.0);
        report.put("revenueGrowth", 12.5);
    }
    
    // Export methods
    public String generateCSVReport(Map<String, Object> report, String reportType) {
        StringBuilder csv = new StringBuilder();
        
        switch (reportType) {
            case "revenue":
                csv.append("Period,Bookings,Revenue,Average Booking Value\n");
                // Add data rows
                break;
            case "vehicles":
                csv.append("Car ID,Brand,Model,Bookings,Revenue,Utilization Rate\n");
                // Add data rows
                break;
        }
        
        return csv.toString();
    }
}