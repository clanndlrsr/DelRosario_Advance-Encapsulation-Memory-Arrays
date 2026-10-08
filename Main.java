import java.util.Scanner;

public class Main {
    private static final int MAX_ACCOUNTS = 5;

    public static void main(String[] args) {
        BankAccount[] accounts = new BankAccount[MAX_ACCOUNTS];
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            displayMenu();
            int choice = readInt(scanner, "Choose an option: ");

            switch (choice) {
                case 1:
                    createAccount(scanner, accounts);
                    break;
                case 2:
                    processDeposit(scanner, accounts);
                    break;
                case 3:
                    processWithdrawal(scanner, accounts);
                    break;
                case 4:
                    searchAndDisplayAccount(scanner, accounts);
                    break;
                case 5:
                    displayMetrics();
                    break;
                case 6:
                    running = false;
                    System.out.println("System closed.");
                    break;
                default:
                    System.out.println("Invalid option. Select a number from 1 to 6.");
            }
        }

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n=== Console-Based Banking System ===");
        System.out.println("[1] Create Account");
        System.out.println("[2] Deposit Money");
        System.out.println("[3] Withdraw Money");
        System.out.println("[4] Search & Display Account Details");
        System.out.println("[5] View Global Bank Metrics");
        System.out.println("[6] Exit System");
    }

    private static void createAccount(Scanner scanner, BankAccount[] accounts) {
        int freeSlot = findFreeSlot(accounts);
        if (freeSlot == -1) {
            System.out.println("The account array is full. No more accounts can be created.");
            return;
        }

        System.out.print("Account holder name: ");
        String holder = scanner.nextLine().trim();
        double initialDeposit = readDouble(scanner, "Initial deposit (minimum Php 500.00): Php ");

        try {
            BankAccount account = new BankAccount(holder, initialDeposit);
            accounts[freeSlot] = account;
            System.out.println("Account created successfully. Account number: " + account.getAccountNumber());
        } catch (IllegalArgumentException exception) {
            System.out.println("Account creation failed: " + exception.getMessage());
        }
    }

    private static void processDeposit(Scanner scanner, BankAccount[] accounts) {
        BankAccount account = promptForAccount(scanner, accounts);
        if (account == null) {
            return;
        }

        double amount = readDouble(scanner, "Deposit amount: Php ");
        if (account.deposit(amount)) {
            System.out.printf("Deposit successful. New balance: Php %.2f%n", account.getBalance());
        } else {
            System.out.println("Deposit rejected. Enter a finite amount greater than zero.");
        }
    }

    private static void processWithdrawal(Scanner scanner, BankAccount[] accounts) {
        BankAccount account = promptForAccount(scanner, accounts);
        if (account == null) {
            return;
        }

        double amount = readDouble(scanner, "Withdrawal amount: Php ");
        if (account.withdraw(amount)) {
            System.out.printf("Withdrawal successful. New balance: Php %.2f%n", account.getBalance());
        } else {
            System.out.println("Withdrawal rejected. Amount must be positive and the remaining balance must be at least Php 100.00.");
        }
    }

    private static void searchAndDisplayAccount(Scanner scanner, BankAccount[] accounts) {
        BankAccount account = promptForAccount(scanner, accounts);
        if (account != null) {
            displayAccount(account);
        }
    }

    private static BankAccount promptForAccount(Scanner scanner, BankAccount[] accounts) {
        System.out.print("Account number: ");
        String accountNumber = scanner.nextLine().trim();
        BankAccount account = findAccount(accounts, accountNumber);

        if (account == null) {
            System.out.println("No account found with number " + accountNumber + ".");
        }
        return account;
    }

    private static BankAccount findAccount(BankAccount[] accounts, String accountNumber) {
        for (BankAccount account : accounts) {
            if (account != null && account.getAccountNumber().equalsIgnoreCase(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    private static int findFreeSlot(BankAccount[] accounts) {
        for (int index = 0; index < accounts.length; index++) {
            if (accounts[index] == null) {
                return index;
            }
        }
        return -1;
    }

    private static void displayAccount(BankAccount account) {
        System.out.println("\nAccount details");
        System.out.println("Account number: " + account.getAccountNumber());
        System.out.println("Account holder: " + account.getAccountHolder());
        System.out.printf("Balance: Php %.2f%n", account.getBalance());
    }

    private static void displayMetrics() {
        System.out.println("\nGlobal bank metrics");
        System.out.println("Account counter: " + BankAccount.getTotalAccounts());
        System.out.printf("Total bank liquidity: Php %.2f%n", BankAccount.getTotalBankLiquidity());
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException exception) {
                System.out.println("Enter a whole number.");
            }
        }
    }

    private static double readDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException exception) {
                System.out.println("Enter a valid numeric amount.");
            }
        }
    }
}