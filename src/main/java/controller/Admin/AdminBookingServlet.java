package controller.Admin;

import model.Booking;
import services.BookingService;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/bookings")
public class AdminBookingServlet extends HttpServlet {
    private BookingService bookingService;
    
    @Override
    public void init() {
        bookingService = new BookingService();
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
                listBookings(request, response);
                break;
            case "view":
                viewBooking(request, response);
                break;
            case "update":
                showUpdateForm(request, response);
                break;
            default:
                listBookings(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("update".equals(action)) {
            updateBooking(request, response);
        } else if ("cancel".equals(action)) {
            cancelBooking(request, response);
        }
    }
    
    private void listBookings(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Booking> bookings = bookingService.getAllBookings();
        request.setAttribute("bookings", bookings);
        request.getRequestDispatcher("/admin/bookings.jsp").forward(request, response);
    }
    
    private void viewBooking(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int bookingId = Integer.parseInt(request.getParameter("id"));
        Booking booking = bookingService.getBookingById(bookingId);
        request.setAttribute("booking", booking);
        request.getRequestDispatcher("/admin/booking-details.jsp").forward(request, response);
    }
    
    private void showUpdateForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int bookingId = Integer.parseInt(request.getParameter("id"));
        Booking booking = bookingService.getBookingById(bookingId);
        request.setAttribute("booking", booking);
        request.getRequestDispatcher("/admin/update-booking.jsp").forward(request, response);
    }
    
    private void updateBooking(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        String status = request.getParameter("status");
        String adminNotes = request.getParameter("adminNotes");
        
        boolean success = bookingService.updateBookingStatus(bookingId, status, adminNotes);
        
        if (success) {
            response.sendRedirect("bookings?action=list&message=Booking updated successfully");
        } else {
            response.sendRedirect("bookings?action=list&error=Failed to update booking");
        }
    }
    
    private void cancelBooking(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        String reason = request.getParameter("reason");
        
        boolean success = bookingService.cancelBooking(bookingId, reason);
        
        if (success) {
            response.sendRedirect("bookings?action=list&message=Booking cancelled successfully");
        } else {
            response.sendRedirect("bookings?action=list&error=Failed to cancel booking");
        }
    }
}