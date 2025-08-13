package carinsurer.controller;

import carinsurer.entities.Payment;
import carinsurer.services.PaymentService;
import carinsurer.services.CustomerService;

import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final CustomerService customerService;
    private static final Logger log = Logger.getLogger(PaymentController.class.getName());

    public PaymentController(PaymentService paymentService, CustomerService customerService) {
        this.paymentService = paymentService;
        this.customerService = customerService;

        // Inject customer service into payment service
        this.paymentService.setService(customerService);
    }

    // Create a payment
    @PostMapping("/create")
    public Payment createPayment(@RequestParam String paymentId,
                                 @RequestParam String customerId,
                                 @RequestParam double amount,
                                 @RequestParam String paymentDate) {
        log.info("Creating payment: paymentId=" + paymentId + ", customerId=" + customerId +
                ", amount=" + amount + ", date=" + paymentDate);
        return paymentService.createPayment(paymentId, customerId, amount, paymentDate);
    }

    // Get payment details by payment ID
    @GetMapping("/{paymentId}")
    public Payment getPayment(@PathVariable String paymentId) {
        return paymentService.getPayment(paymentId);
    }

    // Get customer ID for a payment
    @GetMapping("/{paymentId}/customer")
    public String getCustomerId(@PathVariable String paymentId) {
        return paymentService.getCustomerId(paymentId);
    }

    // Get amount for a payment
    @GetMapping("/{paymentId}/amount")
    public double getAmount(@PathVariable String paymentId) {
        return paymentService.getAmount(paymentId);
    }

    // Get payment date for a payment
    @GetMapping("/{paymentId}/date")
    public String getPaymentDate(@PathVariable String paymentId) {
        return paymentService.getPaymentDate(paymentId);
    }
}
