package carinsurer.services;

import carinsurer.entities.Customer;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;
import java.util.*;

@Service
public class CustomerService {
    // create a logger
    private static final Logger log = Logger.getLogger(CustomerService.class.getName());

    // use a map to store customers by their ID
    private final Map<String, Customer> customers = new HashMap<>();

    // Function to create a new customer with check for email
    public Customer createCustomer(String name, String email) {
        // Check if the name exists
        if (name == null || name.trim().isEmpty()) {
            log.severe("Customer name cannot be empty");
            throw new IllegalArgumentException("Customer name cannot be empty");
        }
        // Check if there is an email given
        if (email == null || email.trim().isEmpty()) {
            log.severe("Customer email cannot be empty");
            throw new IllegalArgumentException("Customer email cannot be empty");
        }
        // If passed, create the customer and put into the list of customers
        Customer customer = new Customer(name, email);
        customers.put(customer.getCustomerId(), customer);
        // Log the creation of the customer
        log.info(String.format("Created customer: ID=%s, Name='%s', Email='%s'",
                customer.getCustomerId(), name, email));
        // Return the created customer
        System.out.println("Customer created successfully: " + customer.getName() +
                " (ID: " + customer.getCustomerId() + ")");
        return customer;
    }
    // Function to get a customer by their ID
    public Customer getCustomerById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer ID cannot be empty");
        }
        return customers.get(id); // Returns customer if customer ID is found
    }
    // Function to get a customer by their name
    public String getCustomerIDByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty");
        }
        for (Customer customer : customers.values()) {
            if (customer.getName().equalsIgnoreCase(name)) {
                return customer.getCustomerId(); // Returns the ID
            }
        }
        log.warning("No customer found with name: " + name);
        return null; // Returns null if no customer is found
    }

    // Function to get customer claims
    public List<String> getCustomerClaims(String customerId) {
        if (customerId == null || customerId.trim().isEmpty()) {
            log.warning("Customer ID cannot be empty when retrieving claims");
            throw new IllegalArgumentException("Customer ID cannot be empty");
        }
        Customer customer = customers.get(customerId);
        if (customer == null) {
            log.warning("No customer found with ID: " + customerId);
            return Collections.emptyList(); // Return empty list if no customer found
        }
        List<String> claimIds = new ArrayList<>();
        for (var claim : customer.getClaims()) {
            claimIds.add(claim.getClaimId());
        }
        log.info("Retrieved claims for customer ID=" + customerId + ", Claims count: " + claimIds.size());
        return claimIds;
    }
    // Function to get customer policies
    public List<String> getCustomerPolicies(String customerId) {
        if (customerId == null || customerId.trim().isEmpty()) {
            log.warning("Customer ID cannot be empty when retrieving policies");
            throw new IllegalArgumentException("Customer ID cannot be empty");
        }
        Customer customer = customers.get(customerId);
        if (customer == null) {
            log.warning("No customer found with ID: " + customerId);
            return Collections.emptyList(); // Return empty list if no customer found
        }
        List<String> policyIds = new ArrayList<>();
        for (var policy : customer.getPolicies()) {
            policyIds.add(policy.getPolicyId());
        }
        log.info("Retrieved policies for customer ID=" + customerId + ", Policies count: " + policyIds.size());
        return policyIds;
    }
}