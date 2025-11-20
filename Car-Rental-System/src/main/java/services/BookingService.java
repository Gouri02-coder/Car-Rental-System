package services;

import dao.BookingDAO;
import model.Booking;
import java.util.List;

public class BookingService {
    private BookingDAO bookingDAO;
    
    public BookingService() {
        this.bookingDAO = new BookingDAO();
    }
    
    public List<Booking> getAllBookings() {
        return bookingDAO.getAllBookings();
    }
    
    public Booking getBookingById(int id) {
        return bookingDAO.getBookingById(id);
    }
    
    public boolean updateBookingStatus(int bookingId, String status, String adminNotes) {
        return bookingDAO.updateBookingStatus(bookingId, status, adminNotes);
    }
    
    public boolean cancelBooking(int bookingId, String reason) {
        return bookingDAO.cancelBooking(bookingId, reason);
    }
}