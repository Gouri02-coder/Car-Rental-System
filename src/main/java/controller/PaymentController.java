package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import services.PaymentService;
import model.Payment;

@WebServlet("/PaymentController")
public class PaymentController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PaymentService paymentService = new PaymentService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String paymentMethod = request.getParameter("paymentMethod");
        String bookingId = request.getParameter("bookingId");
        double amount = Double.parseDouble(request.getParameter("amount"));

        // Create mock payment for prototype
        Payment payment = new Payment();
        payment.setBookingId(bookingId);
        payment.setAmount(amount);
        payment.setPaymentMethod(paymentMethod);
        payment.setStatus("PENDING");

        boolean isPaid = false;

        // Prototype simulation
        if (paymentMethod.equalsIgnoreCase("upi")) {
            // Assume user scans QR and confirms
            isPaid = true;
        } else if (paymentMethod.equalsIgnoreCase("card")) {
            // Assume successful for prototype
            isPaid = true;
        }

        if (isPaid) {
            payment.setStatus("SUCCESS");
            paymentService.savePayment(payment);
            response.sendRedirect("users/payment-success.jsp");
        } else {
            payment.setStatus("FAILED");
            paymentService.savePayment(payment);
            response.sendRedirect("users/payment-cancel.jsp");
        }
    }
}
