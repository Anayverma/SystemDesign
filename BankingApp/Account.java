package BankingApp;

abstract class Account {
    private String accountNumber;   // Encapsulation
    private String ownerName;
    protected double balance;

    public Account(String accountNumber, String ownerName, double balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
    }

    // Encapsulation: controlled access
    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    public double getBalance() { return balance; }

    public void deposit(double amount) {
        balance += amount;
        System.out.println(amount + " deposited. New balance: " + balance);
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println(amount + " withdrawn. Remaining balance: " + balance);
        } else {
            System.out.println("Insufficient balance!");
        }
    }

    public abstract void calculateInterest();
}