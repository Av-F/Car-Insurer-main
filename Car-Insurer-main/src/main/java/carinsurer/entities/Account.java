package carinsurer.entities;

public class Account {
    private String accountName;
    private String accountHolderID;
    private double balance;

    public Account(String accountNumber, String accountHolderID, double initialBalance) {
        this.accountName = accountNumber;
        this.accountHolderID = accountHolderID;
        this.balance = initialBalance;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getAccountHolderID() {
        return accountHolderID;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        } else {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
    }
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        } else {
            throw new IllegalArgumentException("Invalid withdrawal amount");
        }
    }
}