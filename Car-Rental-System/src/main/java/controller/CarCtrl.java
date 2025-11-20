package controller;


import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CarDAO;
import model.Car;

@WebServlet("/CarServlet")
public class CarCtrl extends HttpServlet {
    private CarDAO carDAO;
    
    public void init() {
        carDAO = new CarDAO();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("list".equals(action)) {
            listCars(request, response);
        } else if ("view".equals(action)) {
            viewCar(request, response);
        } else if ("available".equals(action)) {
            listAvailableCars(request, response);
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("add".equals(action)) {
            addCar(request, response);
        }
    }
    
    private void listCars(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<Car> cars = carDAO.getAllCars();
        request.setAttribute("cars", cars);
        request.getRequestDispatcher("cars/list.jsp").forward(request, response);
    }
    
    private void listAvailableCars(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<Car> cars = carDAO.getAvailableCars();
        request.setAttribute("cars", cars);
        request.getRequestDispatcher("cars/available.jsp").forward(request, response);
    }
    
    private void viewCar(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        int carId = Integer.parseInt(request.getParameter("id"));
        Car car = carDAO.getCarById(carId);
        
        if (car != null) {
            request.setAttribute("car", car);
            request.getRequestDispatcher("cars/details.jsp").forward(request, response);
        } else {
            response.sendRedirect("cars/list.jsp?error=Car not found");
        }
    }
    
    private void addCar(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String brand = request.getParameter("brand");
        String model = request.getParameter("model");
        int year = Integer.parseInt(request.getParameter("year"));
        String color = request.getParameter("color");
        String licensePlate = request.getParameter("licensePlate");
        double pricePerDay = Double.parseDouble(request.getParameter("pricePerDay"));
        String description = request.getParameter("description");
        
        Car car = new Car();
        car.setBrand(brand);
        car.setModel(model);
        car.setYear(year);
        car.setColor(color);
        car.setLicensePlate(licensePlate);
        car.setPricePerDay(pricePerDay);
        car.setDescription(description);
        car.setAvailable(true);
        
        if (carDAO.addCar(car)) {
            response.sendRedirect("CarServlet?action=list&success=Car added successfully");
        } else {
            response.sendRedirect("admin/add-car.jsp?error=Failed to add car");
        }
    }
}