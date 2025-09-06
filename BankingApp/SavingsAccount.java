package BankingApp;

class SavingsAccount extends Account {
    private final double interestRate = 0.04; 
    public SavingsAccount(String accountNumber, String ownerName, double balance) {
        super(accountNumber, ownerName, balance);
    }

    @Override
    public void calculateInterest() {
        double interest = balance * interestRate;
        System.out.println("Savings Account Interest: " + interest);
    }
}