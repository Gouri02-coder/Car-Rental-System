package controller.Admin;

import dao.CarDAO;
import model.Car;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@WebServlet("/admin/cars")
public class AdminCarServlet extends HttpServlet {
    private CarDAO carDAO;
    
    @Override
    public void init() {
        carDAO = new CarDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "list";
        }
        
        switch (action) {
            case "list":
                listCars(request, response);
                break;
            case "view":
                viewCar(request, response);
                break;
            case "add":
                showAddForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteCar(request, response);
                break;
            case "toggleStatus":
                toggleCarStatus(request, response);
                break;
            case "stats":
                showStats(request, response);
                break;
            default:
                listCars(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            response.sendRedirect("cars?action=list");
            return;
        }
        
        switch (action) {
            case "add":
                addCar(request, response);
                break;
            case "update":
                updateCar(request, response);
                break;
            case "delete":
                deleteCar(request, response);
                break;
            case "toggleStatus":
                toggleCarStatus(request, response);
                break;
            default:
                response.sendRedirect("cars?action=list");
        }
    }
    
    private void listCars(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String search = request.getParameter("search");
            String status = request.getParameter("status");
            String type = request.getParameter("type");
            
            List<Car> cars;
            if ((search != null && !search.trim().isEmpty()) || 
                (status != null && !status.trim().isEmpty()) ||
                (type != null && !type.trim().isEmpty())) {
                cars = carDAO.searchCars(search, type, status);
            } else {
                cars = carDAO.getAllCars();
            }
            
            Map<String, Integer> stats = carDAO.getCarStats();
            Map<String, Integer> brandStats = carDAO.getCarsByBrandStatistics();
            double totalValue = carDAO.getTotalCarsValue();
            
            request.setAttribute("cars", cars);
            request.setAttribute("stats", stats);
            request.setAttribute("brandStats", brandStats);
            request.setAttribute("totalValue", totalValue);
            request.setAttribute("searchQuery", search);
            request.setAttribute("selectedStatus", status);
            request.setAttribute("selectedType", type);
            
            request.getRequestDispatcher("/admin/cars/manage-cars.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("cars?error=Error loading cars");
        }
    }
    
    private void showStats(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            Map<String, Integer> stats = carDAO.getCarStats();
            Map<String, Integer> brandStats = carDAO.getCarsByBrandStatistics();
            double totalValue = carDAO.getTotalCarsValue();
            List<Car> recentCars = carDAO.getRecentlyAddedCars();
            
            request.setAttribute("stats", stats);
            request.setAttribute("brandStats", brandStats);
            request.setAttribute("totalValue", totalValue);
            request.setAttribute("recentCars", recentCars);
            
            request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("cars?error=Error loading statistics");
        }
    }
    
    private void viewCar(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            int carId = Integer.parseInt(request.getParameter("id"));
            Car car = carDAO.getCarById(carId);
            
            if (car != null) {
                request.setAttribute("car", car);
                request.getRequestDispatcher("/admin/cars/car-details.jsp").forward(request, response);
            } else {
                response.sendRedirect("cars?error=Car not found");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("cars?error=Invalid car ID");
        }
    }
    
    private void showAddForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/admin/cars/add-car.jsp").forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            int carId = Integer.parseInt(request.getParameter("id"));
            Car car = carDAO.getCarById(carId);
            
            if (car != null) {
                request.setAttribute("car", car);
                request.getRequestDispatcher("/admin/cars/edit-car.jsp").forward(request, response);
            } else {
                response.sendRedirect("cars?error=Car not found");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("cars?error=Invalid car ID");
        }
    }
    
    private void addCar(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        try {
            String brand = request.getParameter("brand");
            String model = request.getParameter("model");
            int year = Integer.parseInt(request.getParameter("year"));
            String type = request.getParameter("type");
            String color = request.getParameter("color");
            String registrationNumber = request.getParameter("registrationNumber");
            int seatingCapacity = Integer.parseInt(request.getParameter("seatingCapacity"));
            String fuelType = request.getParameter("fuelType");
            BigDecimal dailyRate = new BigDecimal(request.getParameter("dailyRate"));
            String status = request.getParameter("status");
            String description = request.getParameter("description");
            String features = request.getParameter("features");
            String imagePath = request.getParameter("imagePath");
            
            // Check if registration number already exists
            if (carDAO.isRegistrationNumberExists(registrationNumber)) {
                response.sendRedirect("cars?action=add&error=Registration number already exists");
                return;
            }
            
            Car car = new Car();
            car.setBrand(brand);
            car.setModel(model);
            car.setYear(year);
            car.setType(type);
            car.setColor(color);
            car.setRegistrationNumber(registrationNumber);
            car.setSeatingCapacity(seatingCapacity);
            car.setFuelType(fuelType);
            car.setDailyRate(dailyRate);
            car.setStatus(status != null ? status : "Available");
            car.setDescription(description);
            car.setFeatures(features);
            car.setImagePath(imagePath);
            
            if (carDAO.addCar(car)) {
                response.sendRedirect("cars?success=Car added successfully");
            } else {
                response.sendRedirect("cars?action=add&error=Failed to add car");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("cars?action=add&error=Invalid input data");
        }
    }
    
    private void updateCar(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        try {
            int carId = Integer.parseInt(request.getParameter("id"));
            String brand = request.getParameter("brand");
            String model = request.getParameter("model");
            int year = Integer.parseInt(request.getParameter("year"));
            String type = request.getParameter("type");
            String color = request.getParameter("color");
            String registrationNumber = request.getParameter("registrationNumber");
            int seatingCapacity = Integer.parseInt(request.getParameter("seatingCapacity"));
            String fuelType = request.getParameter("fuelType");
            BigDecimal dailyRate = new BigDecimal(request.getParameter("dailyRate"));
            String status = request.getParameter("status");
            String description = request.getParameter("description");
            String features = request.getParameter("features");
            String imagePath = request.getParameter("imagePath");
            
            // Check if registration number already exists (excluding current car)
            if (carDAO.isRegistrationNumberExists(registrationNumber, carId)) {
                response.sendRedirect("cars?action=edit&id=" + carId + "&error=Registration number already exists");
                return;
            }
            
            Car car = carDAO.getCarById(carId);
            if (car != null) {
                car.setBrand(brand);
                car.setModel(model);
                car.setYear(year);
                car.setType(type);
                car.setColor(color);
                car.setRegistrationNumber(registrationNumber);
                car.setSeatingCapacity(seatingCapacity);
                car.setFuelType(fuelType);
                car.setDailyRate(dailyRate);
                car.setStatus(status);
                car.setDescription(description);
                car.setFeatures(features);
                car.setImagePath(imagePath);
                
                if (carDAO.updateCar(car)) {
                    response.sendRedirect("cars?success=Car updated successfully");
                } else {
                    response.sendRedirect("cars?action=edit&id=" + carId + "&error=Failed to update car");
                }
            } else {
                response.sendRedirect("cars?error=Car not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("cars?error=Invalid input data");
        }
    }
    
    private void deleteCar(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        try {
            int carId = Integer.parseInt(request.getParameter("id"));
            
            if (carDAO.deleteCar(carId)) {
                response.sendRedirect("cars?success=Car deleted successfully");
            } else {
                response.sendRedirect("cars?error=Failed to delete car");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("cars?error=Invalid car ID");
        }
    }
    
    private void toggleCarStatus(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        try {
            int carId = Integer.parseInt(request.getParameter("id"));
            
            if (carDAO.toggleCarStatus(carId)) {
                response.sendRedirect("cars?success=Car status updated");
            } else {
                response.sendRedirect("cars?error=Failed to update car status");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("cars?error=Invalid car ID");
        }
    }
}