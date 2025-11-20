package services;

import model.Payment;
import java.util.ArrayList;
import java.util.List;

public class PaymentService {
    // Temporary in-memory list for prototype
    private static List<Payment> paymentList = new ArrayList<>();

    public void savePayment(Payment payment) {
        paymentList.add(payment);
        System.out.println("Payment Saved: " + payment);
    }

    public List<Payment> getAllPayments() {
        return paymentList;
    }
}
