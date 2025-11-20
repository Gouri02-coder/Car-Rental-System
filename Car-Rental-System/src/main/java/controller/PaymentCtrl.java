package controller;

import dao.PaymentDAO;
import model.Payment;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

@WebServlet("/PaymentCtrl")
public class PaymentCtrl extends HttpServlet {
    private PaymentDAO paymentDAO;
    
    public void init() {
        paymentDAO = new PaymentDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String bookingId = req.getParameter("bookingId");
        String amountStr = req.getParameter("amount");
        String method = req.getParameter("method");

        if (bookingId == null || amountStr == null || method == null) {
            resp.sendRedirect(req.getContextPath() + "/user/payment/payment-failed.jsp");
            return;
        }

        BigDecimal amount = new BigDecimal(amountStr);

        Payment payment = new Payment();
        payment.setPaymentId("PAY-" + UUID.randomUUID().toString().substring(0, 8));
        payment.setBookingId(bookingId);
        payment.setAmount(amount);
        payment.setPaymentMethod(method);
        payment.setPaymentStatus("PENDING");
        payment.setTransactionId("TXN-" + UUID.randomUUID().toString().substring(0, 10));

        // If card, simulate card details
        if (method.equalsIgnoreCase("CREDIT") || method.equalsIgnoreCase("DEBIT")) {
            payment.setCardType(method);
            payment.setCardLastFour("1234");
        }

        boolean created = paymentDAO.createPayment(payment);

        if (!created) {
            resp.sendRedirect(req.getContextPath() + "/user/payment/payment-failed.jsp");
            return;
        }

        // Simulate delay (for prototype animation)
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

        // Simulate payment gateway response (90% success rate)
        boolean success = Math.random() > 0.1;

        if (success) {
            paymentDAO.updatePaymentStatus(payment.getPaymentId(), "SUCCESS", payment.getTransactionId());
            resp.sendRedirect(req.getContextPath() + "/user/payment/payment-success.jsp");
        } else {
            paymentDAO.updatePaymentStatus(payment.getPaymentId(), "FAILED", payment.getTransactionId());
            resp.sendRedirect(req.getContextPath() + "/user/payment/payment-failed.jsp");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/user/dashboard.jsp");
    }
}