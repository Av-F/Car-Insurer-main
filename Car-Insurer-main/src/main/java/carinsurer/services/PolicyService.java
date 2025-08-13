package carinsurer.services;

import carinsurer.entities.Customer;
import carinsurer.entities.Policy;

import java.util.Scanner;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class PolicyService {
    // Add a map for storage and logger for logging
    private final Map<String, Policy> policies = new HashMap<>();
    private static final Logger logger = Logger.getLogger(PolicyService.class.getName());

    CustomerService customerService = new CustomerService();
    public void setService(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Function to create a new policy
    public Policy createPolicy(String customerID, String policyType, double premium) {
        if(customerID == null || policyType == null || policyType.trim().isEmpty() || premium <= 0) {
            logger.warning("Invalid input for creating policy");
            return null;
        }
        Policy policy = new Policy(customerID, policyType, premium);
        policies.put(policy.getPolicyId(), policy);
        Customer customer = customerService.getCustomerById(customerID);
        if (customer != null) {
            customer.addPolicy(policy);
        } else {
            logger.warning("Cannot add policy to customer: Customer with ID " + customerID + " does not exist");
        }
        logger.info("Created policy: " + policy);
        return policy;
    }

    // Function to cancel a policy
    public boolean cancelPolicy(String policyId) {
        // Check if the policy exists
        Policy policy = policies.get(policyId);
        if (policy == null) {
            logger.warning("Cannot cancel policy: Policy with ID " + policyId + " does not exist");
            return false;
        }
        // If it exists, cancel the policy
        policy.cancel();
        logger.info("Cancelled policy: " + policy);
        return true;
    }
    // Function to get a policy by ID
    public Policy getPolicyById(String policyId) {
        // Check if the policy exists
        Policy policy = policies.get(policyId);
        if (policy == null) {
            logger.warning("Policy with ID " + policyId + " does not exist");
            return null;
        }
        logger.info("Retrieved policy: " + policy);
        return policy;
    }
    public Policy getPolicyByCustomer(Customer customer) {
        if (customer == null) {
            logger.warning("Cannot retrieve policy: Customer is null");
            return null;
        }
        for (Policy policy : policies.values()) {
            if (policy.getCustomer().equals(customer.getCustomerId())) {
                logger.info("Retrieved policy for customer: " + customer);
                return policy;
            }
        }
        logger.warning("No policy found for customer: " + customer);
        return null;
    }

    // Function to get all active policies
    public Map<String, Policy> getActivePolicies() {
        Map<String, Policy> activePolicies = new HashMap<>();
        for (Policy policy : policies.values()) {
            if (policy.isActive()) {
                activePolicies.put(policy.getPolicyId(), policy);
            }
        }
        logger.info("Retrieved all active policies");
        return activePolicies;
    }
}
