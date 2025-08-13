package carinsurer.services;

import carinsurer.entities.Claim;
import carinsurer.entities.Policy;

import java.util.*;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

@Service
public class ClaimService {
    // Create logger and initialize claims storage
    private static final Logger log = Logger.getLogger(ClaimService.class.getName());
    private final Map<String, Claim> claims = new HashMap<>();
    CustomerService customerService = new CustomerService();
    public void setService(CustomerService customerService) {
        this.customerService = customerService;
    }
    // create a claim
    public Claim createClaim(Policy policy, String customerId, String description) {
        Claim claim = new Claim(policy,customerId, description);
        claims.put(claim.getClaimId(), claim);
        customerService.getCustomerById(policy.getCustomer()).addClaim(claim);
        System.out.println("Claim created successfully with the id of " + claim.getClaimId());
        log.info("Created claim: " + claim);
        return claim;
    }
    // Getters and methods for claim management
    public Claim getClaim(String claimId) {
        Claim claim = claims.get(claimId);
        if (claim == null) {
            log.warning("Claim not found: " + claimId);
            return null;
        }
        log.info("Retrieved claim: " + claim);
        return claim;
    }

    // Function to approve/deny claims
    public boolean approveClaim(String claimId) {
        Claim claim = claims.get(claimId);
        if (claim == null || claim.isApproved()) {
            log.warning("Claim not found for approval or is already true: " + claimId);
            return false;
        }
        claim.approve();
        customerService.getCustomerById(claim.getCustomerId()).approveClaim(claimId);
        log.info("Approved claim: " + claim);
        return true;
    }
}
