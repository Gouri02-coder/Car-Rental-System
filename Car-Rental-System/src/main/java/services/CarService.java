package services;

import dao.CarDAO;
import model.Car;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class CarService {
    private CarDAO carDAO;
    
    public CarService() {
        this.carDAO = new CarDAO();
    }
    
    // Basic CRUD Operations - UPDATED WITH DEBUG LOGGING
    public List<Car> getAllCars() {
        System.out.println("DEBUG: Fetching all cars from database...");
        List<Car> cars = carDAO.getAllCars();
        System.out.println("DEBUG: Total cars found: " + (cars != null ? cars.size() : "null"));
        
        // Debug: Print first few cars if available
        if (cars != null && !cars.isEmpty()) {
            for (int i = 0; i < Math.min(cars.size(), 3); i++) {
                Car car = cars.get(i);
                System.out.println("DEBUG: Car " + (i+1) + ": ID=" + car.getId() + 
                                 ", Brand=" + car.getBrand() + 
                                 ", Model=" + car.getModel() +
                                 ", RegNo=" + car.getRegistrationNumber());
            }
        } else {
            System.out.println("DEBUG: No cars found or cars list is null");
        }
        
        return cars;
    }
    
    public List<Car> getAvailableCars() {
        return carDAO.getAvailableCars();
    }
    
    public Car getCarById(int carId) {
        System.out.println("DEBUG: Getting car by ID: " + carId);
        Car car = carDAO.getCarById(carId);
        System.out.println("DEBUG: Car found: " + (car != null ? car.getBrand() + " " + car.getModel() : "null"));
        return car;
    }
    
    public boolean addCar(Car car) {
        if (car == null || !isCarValid(car)) {
            System.out.println("Car validation failed");
            return false;
        }
        
        // Check if registration number already exists
        if (carDAO.isRegistrationNumberExists(car.getRegistrationNumber())) {
            System.out.println("Registration number already exists: " + car.getRegistrationNumber());
            return false;
        }
        
        return carDAO.addCar(car);
    }
    
    public boolean updateCar(Car car) {
        if (car == null || car.getId() <= 0 || !isCarValid(car)) {
            System.out.println("Car validation failed");
            return false;
        }
        
        // Check if registration number already exists for another car
        if (carDAO.isRegistrationNumberExists(car.getRegistrationNumber(), car.getId())) {
            System.out.println("Registration number already exists for another car: " + car.getRegistrationNumber());
            return false;
        }
        
        return carDAO.updateCar(car);
    }
    
    public boolean deleteCar(int carId) {
        if (carId <= 0) {
            return false;
        }
        return carDAO.deleteCar(carId);
    }
    
    // Availability Management
    public boolean updateCarAvailability(int carId, String status) {
        if (carId <= 0 || status == null) {
            return false;
        }
        return carDAO.updateCarAvailability(carId, status);
    }
    
    public boolean toggleCarStatus(int carId) {
        if (carId <= 0) {
            return false;
        }
        return carDAO.toggleCarStatus(carId);
    }
    
    // Search and Filter Operations
    public List<Car> searchCars(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getAllCars();
        }
        return carDAO.searchCars(searchTerm.trim());
    }
    
    public List<Car> searchCars(String searchQuery, String type, String status) {
        System.out.println("DEBUG: Searching cars - Query: " + searchQuery + ", Type: " + type + ", Status: " + status);
        List<Car> cars = carDAO.searchCars(searchQuery, type, status);
        System.out.println("DEBUG: Search found: " + (cars != null ? cars.size() : "null") + " cars");
        return cars;
    }
    
    // Statistics and Dashboard Methods
    public Map<String, Integer> getCarStats() {
        System.out.println("DEBUG: Getting car statistics...");
        Map<String, Integer> stats = carDAO.getCarStats();
        System.out.println("DEBUG: Car stats - Total: " + stats.getOrDefault("total", 0) + 
                         ", Available: " + stats.getOrDefault("available", 0) +
                         ", Rented: " + stats.getOrDefault("rented", 0) +
                         ", Maintenance: " + stats.getOrDefault("maintenance", 0));
        return stats;
    }
    
    // Validation Methods
    private boolean isCarValid(Car car) {
        if (car == null) {
            return false;
        }
        
        // Required fields validation
        if (car.getBrand() == null || car.getBrand().trim().isEmpty()) {
            return false;
        }
        
        if (car.getModel() == null || car.getModel().trim().isEmpty()) {
            return false;
        }
        
        if (car.getRegistrationNumber() == null || car.getRegistrationNumber().trim().isEmpty()) {
            return false;
        }
        
        if (car.getYear() < 1900 || car.getYear() > java.time.Year.now().getValue() + 1) {
            return false;
        }
        
        if (car.getDailyRate() == null || car.getDailyRate().compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        
        if (car.getSeatingCapacity() <= 0) {
            return false;
        }
        
        return true;
    }
    
    // Keep your existing methods as they are
    public List<Car> getCarsByBrand(String brand) {
        if (brand == null || brand.trim().isEmpty()) {
            return getAllCars();
        }
        return carDAO.getCarsByBrand(brand.trim());
    }
    
    public int getTotalCarsCount() {
        return carDAO.getTotalCarsCount();
    }
    
    public int getAvailableCarsCount() {
        return carDAO.getAvailableCarsCount();
    }
    
    public int getRentedCarsCount() {
        return carDAO.getRentedCarsCount();
    }
    
    public int getMaintenanceCarsCount() {
        return carDAO.getMaintenanceCarsCount();
    }
    
    public boolean isRegistrationNumberExists(String registrationNumber) {
        return carDAO.isRegistrationNumberExists(registrationNumber);
    }
    
    public boolean isRegistrationNumberExists(String registrationNumber, int excludeCarId) {
        return carDAO.isRegistrationNumberExists(registrationNumber, excludeCarId);
    }
    
    // ... rest of your existing methods
}