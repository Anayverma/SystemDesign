package BankingApp;

class CurrentAccount extends Account {
    private double overdraftLimit = 5000;

    public CurrentAccount(String accountNumber, String ownerName, double balance) {
        super(accountNumber, ownerName, balance);
    }

    @Override
    public void calculateInterest() {
        System.out.println("Current Accounts usually do not earn interest.");
    }

    @Override
    public void withdraw(double amount) {
        if (balance + overdraftLimit >= amount) {
            balance -= amount;
            System.out.println(amount + " withdrawn (using overdraft if needed). Balance: " + balance);
        } else {
            System.out.println("Overdraft limit exceeded!");
        }
    }
}