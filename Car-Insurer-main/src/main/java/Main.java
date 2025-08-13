import carinsurer.services.*;
import carinsurer.entities.Claim;
import carinsurer.entities.Customer;
import carinsurer.entities.Policy;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Create scanner for user input
        Scanner scanner = new Scanner(System.in);
        // give an option for isRunning
        boolean isRunning = true;
        //Introduce the system
        System.out.println("Welcome to Gotham Insurance Management System!");
        System.out.println("Please select an option:");
        // Do while loop for the menu options
        CustomerService customerService = new CustomerService();
        PolicyService policyService = new PolicyService();
        ClaimService claimService = new ClaimService();
        PaymentService paymentService = new PaymentService();
        AccountService accountService = new AccountService(customerService);

        policyService.setService(customerService);
        claimService.setService(customerService);
        paymentService.setService(customerService);

        
        // Loop through the menu options
        do {
            System.out.println(
                "1. Create Customer\n" +
                "2. Create Policy\n" +
                "3. Create Claim\n" +
                "4. View Customer\n" +
                "5. View Policy\n" +
                "6. View Claim\n" +
                "7. Exit"
            );
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline character
            // Trying out something new, switch cases
            switch (option) {
                case 1:
                    // Call method to create customer
                    System.out.println("Creating Customer...");
                    System.out.print("Enter Customer Name: ");
                    String customerName = scanner.nextLine();
                    System.out.print("Enter Customer Email: ");
                    String customerEmail = scanner.nextLine();
                    customerService.createCustomer(customerName, customerEmail);
                    System.out.println("Add in an account for the customer too");
                    System.out.print("Enter Account Name: ");
                    String accountName = scanner.nextLine();
                    System.out.print("Enter Account Balance: ");
                    double accountBalance = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline character
                    String customerID = customerService.getCustomerIDByName(customerName);
                    accountService.createAccount(accountName, customerID, accountBalance);
                    System.out.println("Account created successfully for customer: " + customerName);
                    break;
                case 2:
                    // Call method to create policy
                    System.out.print("Enter Customer ID: ");
                    String customerId = scanner.nextLine();
                    System.out.print("Enter Policy Amount: ");
                    double premium = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline character
                    System.out.println("Enter Policy Type (e.g., Health, Vehicle, Home): ");
                    String polytype = scanner.nextLine();
                    policyService.createPolicy(customerId, polytype, premium);
                    System.out.println("Policy created successfully!");
                    break;
                case 3:
                    // Call method to create claim
                    System.out.println("Creating Claim...");
                    System.out.print("Enter customer ID: ");
                    String IDForClaim = scanner.nextLine();
                    System.out.println("Enter claim description: ");
                    String claimDescription = scanner.nextLine();
                    Customer c = customerService.getCustomerById(IDForClaim);
                    c.getPolicies().forEach(p -> System.out.println("Policy ID: " + p.getPolicyId() + ", Type: " + p.getPolicyType()));
                    System.out.println("Enter policy ID from the above list: ");
                    String policyIDForClaim = scanner.nextLine();
                    Policy policyForClaim = policyService.getPolicyById(policyIDForClaim);

                    if (policyService.getPolicyById(policyIDForClaim) != null) {
                        claimService.createClaim(policyService.getPolicyById(policyIDForClaim), IDForClaim, claimDescription);
                        System.out.println("Claim created successfully for policy: " + policyForClaim.getPolicyId());
                    } else {
                        System.out.println("No policy found for customer: " + IDForClaim);
                    }
                    claimService.createClaim(policyForClaim, IDForClaim, claimDescription);
                    System.out.println("Claim created successfully!\n");
                    break;
                case 4:
                    // Call method to view customer
                    System.out.println("Viewing Customer...");
                    System.out.println("Enter customer id:");
                    String customerIdToView = scanner.nextLine();
                    Customer customer = customerService.getCustomerById(customerIdToView);
                    if (customer != null) {
                        System.out.println("Customer Details: " + customer);
                    } else {
                        System.out.println("Customer not found with ID: " + customerIdToView);
                    }
                    break;
                case 5:
                    // Call method to view policy
                    System.out.println("Viewing Policy...");
                    System.out.println("Enter customer id:");
                    String cIdForPolicy = scanner.nextLine();
                    Customer custForPolicy = customerService.getCustomerById(cIdForPolicy);
                    Policy policy = policyService.getPolicyByCustomer(custForPolicy);
                    if (policy != null) {
                        System.out.println("Policy Details: " + policy);
                    } else {
                        System.out.println("No policy found for customer ID: " + cIdForPolicy);
                    }
                    break;
                case 6:
                    // Call method to view claim
                    System.out.println("Viewing Claim...");
                    System.out.println("Enter claim id:");
                    String claimIdToView = scanner.nextLine();
                    Claim claim = claimService.getClaim(claimIdToView);
                    System.out.println("Claim Details: " + claim);
                    if(!claim.isApproved()) {
                        System.out.println("Would you like to approve the claim? Y or N");
                        String approve = scanner.nextLine();
                        if (approve.equalsIgnoreCase("Y")) {

                            claimService.approveClaim(claimIdToView);
                            System.out.println("Claim approved successfully!");
                        } else {
                            System.out.println("Claim not approved.");
                        }
                    }
                    break;
                case 7:
                    isRunning = false;
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (isRunning);
    }

}
