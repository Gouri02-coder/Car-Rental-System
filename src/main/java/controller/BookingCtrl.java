package controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BookingDAO;
import dao.CarDAO;
import model.Booking;
import model.Car;

@WebServlet("/BookingServlet")
public class BookingCtrl extends HttpServlet {
    private BookingDAO bookingDAO;
    private CarDAO carDAO;
    
    public void init() {
        bookingDAO = new BookingDAO();
        carDAO = new CarDAO();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        if ("history".equals(action)) {
            showBookingHistory(request, response, userId);
        } else if ("view".equals(action)) {
            viewBooking(request, response);
        } else if ("all".equals(action)) {
            showAllBookings(request, response);
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        if ("create".equals(action)) {
            createBooking(request, response, userId);
        } else if ("update".equals(action)) {
            updateBookingStatus(request, response);
        }
    }
    
    private void showBookingHistory(HttpServletRequest request, HttpServletResponse response, int userId) 
            throws ServletException, IOException {
        
        List<Booking> bookings = bookingDAO.getBookingsByUserId(userId);
        request.setAttribute("bookings", bookings);
        request.getRequestDispatcher("user/booking-history.jsp").forward(request, response);
    }
    
    private void showAllBookings(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<Booking> bookings = bookingDAO.getAllBookings();
        request.setAttribute("bookings", bookings);
        request.getRequestDispatcher("admin/manage-bookings.jsp").forward(request, response);
    }
    
    private void viewBooking(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        int bookingId = Integer.parseInt(request.getParameter("id"));
        Booking booking = bookingDAO.getBookingById(bookingId);
        
        if (booking != null) {
            request.setAttribute("booking", booking);
            request.getRequestDispatcher("booking-details.jsp").forward(request, response);
        } else {
            response.sendRedirect("user/dashboard.jsp?error=Booking not found");
        }
    }
    
    private void createBooking(HttpServletRequest request, HttpServletResponse response, int userId) 
            throws ServletException, IOException {
        
        int carId = Integer.parseInt(request.getParameter("carId"));
        Date startDate = Date.valueOf(request.getParameter("startDate"));
        Date endDate = Date.valueOf(request.getParameter("endDate"));
        
        // Calculate total price
        Car car = carDAO.getCarById(carId);
        long days = (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24);
        double totalPrice = days * car.getPricePerDay();
        
        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setCarId(carId);
        booking.setStartDate(startDate);
        booking.setEndDate(endDate);
        booking.setTotalPrice(totalPrice);
        
        if (bookingDAO.createBooking(booking)) {
            // Update car availability
            carDAO.updateCarAvailability(carId, false);
            response.sendRedirect("user/dashboard.jsp?success=Booking created successfully");
        } else {
            response.sendRedirect("cars/details.jsp?id=" + carId + "&error=Failed to create booking");
        }
    }
    
    private void updateBookingStatus(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        String status = request.getParameter("status");
        
        if (bookingDAO.updateBookingStatus(bookingId, status)) {
            // If booking is completed or cancelled, make car available again
            if ("completed".equals(status) || "cancelled".equals(status)) {
                Booking booking = bookingDAO.getBookingById(bookingId);
                carDAO.updateCarAvailability(booking.getCarId(), true);
            }
            response.sendRedirect("BookingServlet?action=all&success=Booking status updated");
        } else {
            response.sendRedirect("BookingServlet?action=all&error=Failed to update booking status");
        }
    }
}