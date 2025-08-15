package carinsurer.entities;

public class Payment {
    private String paymentId;
    private String customerId;
    private double amount;
    private String paymentDate;

    public Payment(String paymentId, String customerId, double amount, String paymentDate) {
        this.paymentId = paymentId;
        this.customerId = customerId;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    @Override
    public String toString() {
        return "entities.Payment{" +
                "paymentId='" + paymentId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", amount=" + amount +
                ", paymentDate='" + paymentDate + '\'' +
                '}';
    }
}