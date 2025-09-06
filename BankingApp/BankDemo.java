package BankingApp;

public class BankDemo {
    public static void main(String[] args) {
        Account acc1 = new SavingsAccount("SA101", "Anay", 10000);
        Account acc2 = new CurrentAccount("CA202", "Verma", 20000);

        acc1.deposit(2000);
        acc1.calculateInterest();   

        acc2.withdraw(25000);       
        acc2.calculateInterest();   
    }
}