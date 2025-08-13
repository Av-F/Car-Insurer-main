package carinsurer.services;

import carinsurer.entities.Payment;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    // add a logger
    private static Logger log = Logger.getLogger(PaymentService.class.getName());
    // add a map to store the payments
    private Map<String, Payment> payments = new HashMap<>();

    // add a customer service to get the customer details
    private CustomerService customerService = new CustomerService();
    public void setService(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Function to create payments with checks
    public Payment createPayment(String paymentId, String customerId, double amount, String paymentDate) {
        // Check if the payment details are given
        if (paymentId == null || customerId == null || amount <= 0 || paymentDate == null) {
            log.severe("Payment details cannot be empty or invalid");
            throw new IllegalArgumentException("Payment details cannot be empty or invalid");
        }
        // If so, create the payment and add it to the map
        Payment payment = new Payment(paymentId, customerId, amount, paymentDate);
        payments.put(payment.getPaymentId(), payment);
        log.info("Payment created: " + payment.getPaymentId() + " for customer: " + payment.getCustomerId() +
                " with amount: " + payment.getAmount() + " on date: " + payment.getPaymentDate());
        return payment;
    }

    // get a payment by ID with checks
    public Payment getPayment(String paymentId) {
        // Check if the payment ID is given
        if (paymentId == null) {
            log.severe("Payment ID cannot be empty");
            throw new IllegalArgumentException("Payment ID cannot be empty");
        }
        // If so, return the payment from the map
        Payment payment = payments.get(paymentId);
        if (payment == null) {
            log.severe("Payment not found: " + paymentId);
            throw new IllegalArgumentException("Payment not found");
        }
        return payment;
    }
        // get the customer ID for a payment
        public String getCustomerId(String paymentId) {
            if (paymentId == null) {
                log.severe("Payment ID cannot be empty");
                throw new IllegalArgumentException("Payment ID cannot be empty");
            }
            // Get the payment from the map
            Payment payment = getPayment(paymentId);
            // Return the customer ID
            return payment.getCustomerId();
        }
    // get the amount for a payment
    public double getAmount(String paymentId) {
        if (paymentId == null) {
            log.severe("Payment ID cannot be empty");
            throw new IllegalArgumentException("Payment ID cannot be empty");
        }
        // Get the payment from the map
        Payment payment = getPayment(paymentId);
        // Return the amount
        return payment.getAmount();
    }
    public String getPaymentDate(String paymentId) {
        if (paymentId == null) {
            log.severe("Payment ID cannot be empty");
            throw new IllegalArgumentException("Payment ID cannot be empty");
        }
        // Get the payment from the map
        Payment payment = getPayment(paymentId);
        // Return the payment date
        return payment.getPaymentDate();
    }
    @Override
    public String toString() {
        return "PaymentService{" +
                "payments=" + payments +
                '}';
    }


}