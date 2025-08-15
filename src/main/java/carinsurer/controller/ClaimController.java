package carinsurer.controller;

import carinsurer.entities.Claim;
import carinsurer.entities.Policy;
import carinsurer.services.ClaimService;
import carinsurer.services.CustomerService;
import carinsurer.services.PolicyService;

import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {

    private final ClaimService claimService;
    private final CustomerService customerService;
    private final PolicyService policyService;
    private static final Logger log = Logger.getLogger(ClaimController.class.getName());

    public ClaimController(ClaimService claimService, CustomerService customerService, PolicyService policyService) {
        this.claimService = claimService;
        this.customerService = customerService;
        this.policyService = policyService;

        // Inject customer service into claimService
        this.claimService.setService(customerService);
    }

    // Create a claim with customer ID, policy ID, and description
    @PostMapping("/create")
    public Claim createClaim(@RequestParam String policyId,
                             @RequestParam String customerId,
                             @RequestParam String description) {
        log.info("Creating claim for policyId=" + policyId + ", customerId=" + customerId);

        // Fetch policy from policy service
        Policy policy = policyService.getPolicyById(policyId);
        if (policy == null) {
            throw new IllegalArgumentException("Policy not found with ID: " + policyId);
        }

        Claim claim = claimService.createClaim(policy, customerId, description);
        if (claim == null) {
            throw new IllegalArgumentException("Failed to create claim");
        }
        return claim;
    }

    // Get a claim by its ID
    @GetMapping("/{claimId}")
    public Claim getClaim(@PathVariable String claimId) {
        Claim claim = claimService.getClaim(claimId);
        if (claim == null) {
            throw new IllegalArgumentException("Claim not found with ID: " + claimId);
        }
        return claim;
    }

    // Approve a claim by its ID
    @PostMapping("/{claimId}/approve")
    public String approveClaim(@PathVariable String claimId) {
        boolean success = claimService.approveClaim(claimId);
        return success ? "Claim approved: " + claimId : "Claim not found or already approved: " + claimId;
    }
}
