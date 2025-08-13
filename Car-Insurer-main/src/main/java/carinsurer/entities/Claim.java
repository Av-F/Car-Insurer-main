package carinsurer.entities;

import java.util.UUID;

public class Claim {
    private final String claimId;
    private String customerId;
    private final Policy policy;
    private String description;

    private boolean approved;

    public Claim(Policy policy, String customerId, String description) {
        this.claimId = UUID.randomUUID().toString();
        this.policy = policy;
        this.customerId = customerId;
        this.description = description;
        this.approved = false;
    }

    public String getClaimId() {
        return claimId;
    }
    public String getCustomerId() {
        return customerId;
    }
    public String getDescription() {
        return description;
    }
    public boolean isApproved() {
        return approved;
    }

    public void approve() {
        this.approved = true;
    }

    @Override
    public String toString() {
        return "entities.Claim{" +
                "id='" + claimId + '\'' +
                ", policyId='" +  + '\'' +
                ", description='" + description + '\'' +
                ", approved=" + approved +
                '}';
    }
}