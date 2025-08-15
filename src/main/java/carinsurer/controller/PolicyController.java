package carinsurer.controller;

import carinsurer.entities.Policy;
import carinsurer.services.PolicyService;
import carinsurer.services.CustomerService;

import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {

    private final PolicyService policyService;
    private final CustomerService customerService;
    private static final Logger log = Logger.getLogger(PolicyController.class.getName());

    public PolicyController(PolicyService policyService, CustomerService customerService) {
        this.policyService = policyService;
        this.customerService = customerService;
        this.policyService.setService(customerService);
    }

    @PostMapping("/create")
    public Policy createPolicy(@RequestParam String customerId,
                               @RequestParam String policyType,
                               @RequestParam double premium) {
        log.info("Creating policy for customerId=" + customerId + ", type=" + policyType);
        Policy policy = policyService.createPolicy(customerId, policyType, premium);
        if (policy == null) {
            throw new IllegalArgumentException("Policy creation failed due to invalid input");
        }
        return policy;
    }

    @GetMapping("/{policyId}")
    public Policy getPolicyById(@PathVariable String policyId) {
        Policy policy = policyService.getPolicyById(policyId);
        if (policy == null) {
            throw new IllegalArgumentException("Policy not found with ID: " + policyId);
        }
        return policy;
    }

    @PostMapping("/{policyId}/cancel")
    public String cancelPolicy(@PathVariable String policyId) {
        boolean success = policyService.cancelPolicy(policyId);
        return success ? "Policy cancelled: " + policyId : "Policy not found: " + policyId;
    }

    @GetMapping("/active")
    public Map<String, Policy> getActivePolicies() {
        return policyService.getActivePolicies();
    }
}

