/******************************************************************************

                            Online Java Compiler.
                Code, Compile, Run and Debug java program online.
Write your code in this editor and press "Run" button to execute it.

*******************************************************************************/

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Class to represent a bank account
class Account {
    private String accountType;
    private double balance;

    public Account(String accountType, double initialBalance) {
        this.accountType = accountType;
        this.balance = initialBalance;
    }

    public String getAccountType() {
        return accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrew: " + amount);
        } else {
            System.out.println("Invalid withdrawal amount.");
        }
    }

    public void printBalance() {
        System.out.println("Account Type: " + accountType);
        System.out.println("Current Balance: " + balance);
    }
}

// Class to represent a user
class User {
    private String username;
    private String password;
    private List<Account> accounts;
    private List<Transaction> transactions;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.accounts = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }

    public void createAccount(String accountType, double initialBalance) {
        Account account = new Account(accountType, initialBalance);
        accounts.add(account);
        System.out.println(accountType + " account created with initial balance: " + initialBalance);
    }

    public Account getAccount(String accountType) {
        for (Account account : accounts) {
            if (account.getAccountType().equals(accountType)) {
                return account;
            }
        }
        System.out.println("Account type not found.");
        return null;
    }

    public void printAccountDetails() {
        System.out.println("User: " + username);
        for (Account account : accounts) {
            account.printBalance();
            System.out.println();
        }
    }

    public void transferFunds(String fromAccountType, String toAccountType, double amount) {
        Account fromAccount = getAccount(fromAccountType);
        Account toAccount = getAccount(toAccountType);

        if (fromAccount != null && toAccount != null && amount > 0) {
            fromAccount.withdraw(amount);
            toAccount.deposit(amount);
            transactions.add(new Transaction(fromAccountType, toAccountType, amount));
            System.out.println("Transferred " + amount + " from " + fromAccountType + " to " + toAccountType);
        } else {
            System.out.println("Invalid transfer.");
        }
    }

    public void printTransactionHistory() {
        System.out.println("Transaction history for user: " + username);
        for (Transaction transaction : transactions) {
            transaction.printTransaction();
        }
    }
}

// Class to represent a transaction
class Transaction {
    private String fromAccount;
    private String toAccount;
    private double amount;

    public Transaction(String fromAccount, String toAccount, double amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }

    public void printTransaction() {
        System.out.println("From: " + fromAccount + ", To: " + toAccount + ", Amount: " + amount);
    }
}

// Class to represent loan
class Loan {
    private double loanAmount;
    private double interestRate;
    private int loanTerm; // in years
    private double monthlyPayment;

    public Loan(double loanAmount, double interestRate, int loanTerm) {
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.loanTerm = loanTerm;
        calculateMonthlyPayment();
    }

    private void calculateMonthlyPayment() {
        double monthlyInterestRate = interestRate / 12 / 100;
        int totalPayments = loanTerm * 12;

        monthlyPayment = (loanAmount * monthlyInterestRate) /
                (1 - Math.pow(1 + monthlyInterestRate, -totalPayments));
    }

    public void printLoanDetails() {
        System.out.println("Loan Amount: " + loanAmount);
        System.out.println("Interest Rate: " + interestRate + "%");
        System.out.println("Loan Term: " + loanTerm + " years");
        System.out.println("Monthly Payment: " + monthlyPayment);
    }
}

// Main class to run the banking system
public class BankingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create a user
        User user = new User("john_doe", "password123");

        // User authentication
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        if (user.authenticate(password)) {
            System.out.println("Authentication successful!");

            // Create a savings account
            user.createAccount("Savings", 1000.0);

            // Create a checking account
            user.createAccount("Checking", 500.0);

            // Perform some transactions
            user.transferFunds("Savings", "Checking", 200.0);
            user.transferFunds("Checking", "Savings", 100.0);

            // Print account details
            user.printAccountDetails();

            // Print transaction history
            user.printTransactionHistory();

            // Create a loan
            Loan loan = new Loan(10000.0, 5.0, 10); // Loan amount: $10,000, Interest rate: 5%, Term: 10 years
            loan.printLoanDetails();
        } else {
            System.out.println("Authentication failed!");
        }

        scanner.close();
    }
}