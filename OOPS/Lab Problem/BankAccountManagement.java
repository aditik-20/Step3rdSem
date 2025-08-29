class BankAccount {
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private static int totalAccounts = 0;

    public BankAccount(String accountHolderName, double initialDeposit) {
        if (initialDeposit < 0) {
            initialDeposit = 0;
        }
        this.accountHolderName = accountHolderName;
        this.balance = initialDeposit;
        this.accountNumber = generateAccountNumber();
        totalAccounts++;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println(amount + " deposited successfully.");
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
        } else if (amount > balance) {
            System.out.println("Insufficient balance.");
        } else {
            balance -= amount;
            System.out.println(amount + " withdrawn successfully.");
        }
    }

    public void checkBalance() {
        System.out.println("Balance for " + accountHolderName + " (" + accountNumber + "): " + balance);
    }

    public void displayAccountInfo() {
        System.out.println("-------------------------------------");
        System.out.println("Account Number   : " + accountNumber);
        System.out.println("Account Holder   : " + accountHolderName);
        System.out.println("Balance          : " + balance);
        System.out.println("-------------------------------------");
    }

    private static String generateAccountNumber() {
        return String.format("ACC%03d", totalAccounts + 1);
    }

    public static int getTotalAccounts() {
        return totalAccounts;
    }
}

public class BankAccountManagement {
    public static void main(String[] args) {
        BankAccount[] accounts = new BankAccount[3];

        accounts[0] = new BankAccount("Alice", 1000);
        accounts[1] = new BankAccount("Bob", 500);
        accounts[2] = new BankAccount("Charlie", 2000);

        accounts[0].deposit(500);
        accounts[1].withdraw(200);
        accounts[2].withdraw(2500);

        accounts[0].checkBalance();
        accounts[1].checkBalance();
        accounts[2].checkBalance();

        for (BankAccount acc : accounts) {
            acc.displayAccountInfo();
        }

        System.out.println("Total Accounts Created: " + BankAccount.getTotalAccounts());
    }
}
