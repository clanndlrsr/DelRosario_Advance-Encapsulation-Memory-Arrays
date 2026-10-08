public class BankAccount {
    private static int totalAccounts = 1000;
    private static double totalBankLiquidity = 0.0;

    private final String accountNumber;
    private final String accountHolder;
    private double balance;

    public BankAccount(String accountHolder, double initialBalance) {
        if (accountHolder == null || accountHolder.trim().isEmpty()) {
            throw new IllegalArgumentException("Account holder name cannot be empty.");
        }
        if (!Double.isFinite(initialBalance) || initialBalance < 500.00) {
            throw new IllegalArgumentException("The initial deposit must be at least Php 500.00.");
        }

        this.accountNumber = "ACC-" + totalAccounts;
        this.accountHolder = accountHolder.trim();
        this.balance = initialBalance;
        totalAccounts++;
        totalBankLiquidity += initialBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public boolean deposit(double amount) {
        if (!Double.isFinite(amount) || amount <= 0.0) {
            return false;
        }

        balance += amount;
        totalBankLiquidity += amount;
        return true;
    }

    public boolean withdraw(double amount) {
        if (!Double.isFinite(amount) || amount <= 0.0 || balance - amount < 100.00) {
            return false;
        }

        balance -= amount;
        totalBankLiquidity -= amount;
        return true;
    }

    public static int getTotalAccounts() {
        return totalAccounts;
    }

    public static double getTotalBankLiquidity() {
        return totalBankLiquidity;
    }
}