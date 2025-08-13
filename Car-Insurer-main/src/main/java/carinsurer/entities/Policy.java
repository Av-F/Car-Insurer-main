package carinsurer.entities;
import java.util.UUID;

public class Policy {
    private final String policyId;
    private String  customerId;

    private String policyType;
    private double premium;

    private boolean active;

    public Policy(String customerId, String policyType, double premium) {
        this.policyId = UUID.randomUUID().toString();
        this.policyType = policyType;
        this.customerId = customerId;
        this.premium = premium;
        this.active = true;
    }

    public String getPolicyId() {
        return policyId;
    }

    public String getCustomer() {
        return customerId;
    }

    public double getPremium() {
        return premium;
    }

    public String getPolicyType() {
        return policyType;
    }

    public boolean isActive() {
        return active;
    }

    public void cancel() {
        this.active = false;
    }

    @Override
    public String toString() {
        return "entities.Policy{" +
                "id='" + policyId + '\'' +
                ", customerId=" + customerId +
                ", premium=" + premium +
                ", policy type=" + policyType +
                ", active=" + active +
                '}';
    }
}
