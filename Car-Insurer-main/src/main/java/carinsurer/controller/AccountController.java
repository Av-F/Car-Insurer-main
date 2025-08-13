package carinsurer.controller;

import carinsurer.entities.Account;
import carinsurer.services.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;
    private static final Logger log = Logger.getLogger(AccountController.class.getName());

    // Inject AccountService via constructor (Spring will handle it)
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Create an account
    @PostMapping("/create")
    public Account createAccount(
            @RequestParam String accountName,
            @RequestParam String accountHolderID,
            @RequestParam double initialBalance) {
        log.info("Creating account: " + accountName);
        return accountService.createAccount(accountName, accountHolderID, initialBalance);
    }

    // Get account details by account name
    @GetMapping("/{accountName}")
    public Account getAccount(@PathVariable String accountName) {
        return accountService.getAccount(accountName);
    }

    // Get account balance
    @GetMapping("/{accountName}/balance")
    public double getBalance(@PathVariable String accountName) {
        return accountService.getBalance(accountName);
    }

    // Deposit money into account
    @PostMapping("/{accountName}/deposit")
    public String deposit(@PathVariable String accountName, @RequestParam double amount) {
        accountService.deposit(accountName, amount);
        return "Deposited " + amount + " to account " + accountName;
    }

    // Withdraw money from account
    @PostMapping("/{accountName}/withdraw")
    public String withdraw(@PathVariable String accountName, @RequestParam double amount) {
        accountService.withdraw(accountName, amount);
        return "Withdrew " + amount + " from account " + accountName;
    }

    // Transfer money between accounts
    @PostMapping("/transfer")
    public String transfer(@RequestParam String fromAccountName,
                           @RequestParam String toAccountName,
                           @RequestParam double amount) {
        accountService.transfer(fromAccountName, toAccountName, amount);
        return "Transferred " + amount + " from " + fromAccountName + " to " + toAccountName;
    }
}
