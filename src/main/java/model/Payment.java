package model;

public class Payment {
    private String bookingId;
    private double amount;
    private String paymentMethod;
    private String status;

    // Getters & Setters
    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Payment{" +
                "bookingId='" + bookingId + '\'' +
                ", amount=" + amount +
                ", method='" + paymentMethod + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
