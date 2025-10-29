package controller.Admin;

import model.Car;
import services.CarService;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/cars")
public class AdminCarServlet extends HttpServlet {
    private CarService carService;
    
    @Override
    public void init() {
        carService = new CarService();
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
            default:
                listCars(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("add".equals(action)) {
            addCar(request, response);
        } else if ("update".equals(action)) {
            updateCar(request, response);
        } else if ("toggleStatus".equals(action)) {
            toggleCarStatus(request, response);
        }
    }
    
    private void listCars(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Car> cars = carService.getAllCars();
        request.setAttribute("cars", cars);
        request.getRequestDispatcher("/admin/cars.jsp").forward(request, response);
    }
    
    private void viewCar(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int carId = Integer.parseInt(request.getParameter("id"));
        Car car = carService.getCarById(carId);
        request.setAttribute("car", car);
        request.getRequestDispatcher("/admin/car-details.jsp").forward(request, response);
    }
    
    private void showAddForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/admin/add-car.jsp").forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int carId = Integer.parseInt(request.getParameter("id"));
        Car car = carService.getCarById(carId);
        request.setAttribute("car", car);
        request.getRequestDispatcher("/admin/edit-car.jsp").forward(request, response);
    }
    
    private void addCar(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        String make = request.getParameter("make");
        String model = request.getParameter("model");
        int year = Integer.parseInt(request.getParameter("year"));
        String type = request.getParameter("type");
        double dailyRate = Double.parseDouble(request.getParameter("dailyRate"));
        String registrationNumber = request.getParameter("registrationNumber");
        String color = request.getParameter("color");
        
        Car car = new Car(0, make, model, year, type, dailyRate, registrationNumber, color, true);
        boolean success = carService.addCar(car);
        
        if (success) {
            response.sendRedirect("cars?action=list&message=Car added successfully");
        } else {
            response.sendRedirect("cars?action=list&error=Failed to add car");
        }
    }
    
    private void updateCar(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        int carId = Integer.parseInt(request.getParameter("carId"));
        String make = request.getParameter("make");
        String model = request.getParameter("model");
        int year = Integer.parseInt(request.getParameter("year"));
        String type = request.getParameter("type");
        double dailyRate = Double.parseDouble(request.getParameter("dailyRate"));
        String registrationNumber = request.getParameter("registrationNumber");
        String color = request.getParameter("color");
        
        Car car = new Car(carId, make, model, year, type, dailyRate, registrationNumber, color, true);
        boolean success = carService.updateCar(car);
        
        if (success) {
            response.sendRedirect("cars?action=list&message=Car updated successfully");
        } else {
            response.sendRedirect("cars?action=list&error=Failed to update car");
        }
    }
    
    private void toggleCarStatus(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        int carId = Integer.parseInt(request.getParameter("carId"));
        boolean success = carService.toggleCarStatus(carId);
        
        if (success) {
            response.sendRedirect("cars?action=list&message=Car status updated successfully");
        } else {
            response.sendRedirect("cars?action=list&error=Failed to update car status");
        }
    }
}