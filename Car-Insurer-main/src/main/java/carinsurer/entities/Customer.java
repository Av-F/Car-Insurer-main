package carinsurer.entities;
import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String customerId;
    private String name;
    private String email;

    private List<Account> accounts = new ArrayList<>();

    private List<Claim> claims = new ArrayList<>();

    private List<Policy> policies = new ArrayList<>();

    public Customer(String name, String email) {
        this.customerId = java.util.UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
    }

    public String getCustomerId() {

        return customerId;
    }

    public String getName() {

        return name;
    }

    public List<Claim> getClaims() {
        return claims;
    }
    public void addClaim(Claim claim) {
        if (claim != null) {
            claims.add(claim);
        }
    }
    public void approveClaim(String claimId) {
        for (Claim claim : claims) {
            if (claim.getClaimId().equals(claimId)) {
                claim.approve();
                return;
            }
        }
        throw new IllegalArgumentException("Claim with ID " + claimId + " not found.");
    }
    public List<Policy> getPolicies() {
        return policies;
    }
    public void addPolicy(Policy policy) {
        if (policy != null) {
            policies.add(policy);
        }
    }
    public void addAccount(Account account) {
        if (account != null) {
            accounts.add(account);
        }
    }
    @Override
    public String toString() {
        return "entities.Customer{" +
                "customerId='" + customerId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}