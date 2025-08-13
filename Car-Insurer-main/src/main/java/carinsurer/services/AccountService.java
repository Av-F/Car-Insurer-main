package carinsurer.services;

import carinsurer.entities.Account;
import carinsurer.entities.Customer;

import java.util.logging.Logger;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final CustomerService customerService;

    private static final Logger log = Logger.getLogger(AccountService.class.getName());
    private final Map<String, Account> accounts = new HashMap<>();

    // Constructor injection — Spring will inject the right instance
    public AccountService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public Account createAccount(String accountName, String accountHolderID, double initialBalance) {
        if (accountName == null) {
            log.severe("Account name cannot be empty");
            throw new IllegalArgumentException("Account name cannot be empty");
        }
        if (accountHolderID == null) {
            log.severe("Account holder ID cannot be empty");
            throw new IllegalArgumentException("Account holder ID cannot be empty");
        }
        if (initialBalance < 0) {
            log.severe("Initial balance cannot be negative");
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }

        Customer c = customerService.getCustomerById(accountHolderID);
        if (c == null) {
            log.severe("No customer found with ID: " + accountHolderID);
            throw new IllegalArgumentException("Customer not found for ID: " + accountHolderID);
        }

        Account account = new Account(accountName, accountHolderID, initialBalance);
        accounts.put(account.getAccountName(), account);
        c.addAccount(account);

        log.info("Account created: " + account.getAccountName() + " for " +
                account.getAccountHolderID() + " with initial balance: " + initialBalance);
        return account;
    }

    public Account getAccount(String accountName) {
        // Check if the account number is given
        if (accountName == null) {
            log.severe("Account number cannot be empty");
            throw new IllegalArgumentException("Account number cannot be empty");
        }
        // If so, return the account from the map
        Account account = accounts.get(accountName);
        if (account == null) {
            log.severe("Account not found: " + accountName);
            throw new IllegalArgumentException("Account not found");
        }
        return account;
    }
    public String getAccountHolderID(String accountName) {
        if(accountName == null) {
            log.severe("Account number cannot be empty");
            throw new IllegalArgumentException("Account number cannot be empty");
        }
        // Get the account from the map
        Account account = getAccount(accountName);
        // Return the account holder name
        return account.getAccountHolderID();
    }

    public double getBalance(String accountName) {
        if(accountName == null) {
            log.severe("Account number cannot be empty");
            throw new IllegalArgumentException("Account number cannot be empty");
        }
        // Get the account from the map
        Account account = getAccount(accountName);
        // Return the balance of the account
        return account.getBalance();
    }
    public void deposit(String accountName, double amount) {
        if(amount > 0) {
            // Get the account from the map
            Account account = getAccount(accountName);
            // Deposit the amount to the account
            account.deposit(amount);
            log.info("Deposited " + amount + " to account " + accountName +
                    ". New balance: " + account.getBalance());
        } else {
            log.severe("Deposit amount must be positive");
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
    }

    public void withdraw(String accountName, double amount) {
        // Get the account from the map
        Account account = getAccount(accountName);
        // Check if the balance has sufficient funds for a withdrawal
        if (account.getBalance() < amount) {
            log.severe("Insufficient balance for withdrawal from account " + accountName +
                    ". Current balance: " + account.getBalance() + ", attempted withdrawal: " + amount);
            throw new IllegalArgumentException("Insufficient balance");
        } else {
            // Withdraw the amount from the account
            account.withdraw(amount);
            log.info("Withdrew " + amount + " from account " + accountName +
                    ". New balance: " + account.getBalance());
        }
    }
    public void transfer(String startNumber, String endNumber, double amount) {
        // Get the accounts from the map
        Account fromAccount = getAccount(startNumber);
        Account toAccount = getAccount(endNumber);
        // Withdraw the amount from the from account if there is sufficient balance
        if(fromAccount.getBalance() < amount) {
            log.severe("Insufficient balance for transfer from account " + startNumber +
                    ". Current balance: " + fromAccount.getBalance() + ", attempted transfer: " + amount);
            throw new IllegalArgumentException("Insufficient balance");
        } else {
            log.info("Transferring " + amount + " from account " + startNumber +
                    " to account " + endNumber + ".");
            fromAccount.withdraw(amount);
            // Deposit the amount to the to account
            toAccount.deposit(amount);
        }
    }
}
