package carinsurer.controller;

import carinsurer.entities.Customer;
import carinsurer.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;
    private static final Logger logger = Logger.getLogger(CustomerController.class.getName());

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Create a new customer
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestParam String name, @RequestParam String email) {
        logger.info("Received request to create customer with name: " + name + ", email: " + email);
        try {
            Customer customer = customerService.createCustomer(name, email);
            logger.info("Customer created with ID: " + customer.getCustomerId());
            return ResponseEntity.ok(customer);
        } catch (IllegalArgumentException e) {
            logger.warning("Failed to create customer: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // Get customer by ID
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String id) {
        logger.info("Received request to get customer with ID: " + id);
        Customer customer = customerService.getCustomerById(id);
        if (customer != null) {
            logger.info("Customer found: " + customer.getName());
            return ResponseEntity.ok(customer);
        } else {
            logger.warning("Customer not found for ID: " + id);
            return ResponseEntity.notFound().build();
        }
    }
}
