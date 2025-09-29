import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Bank bank = new Bank();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Bank Account Simulation ===");
        boolean running = true;
        while (running) {
            printMenu();
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": createAccount(); break;
                case "2": deposit(); break;
                case "3": withdraw(); break;
                case "4": transfer(); break;
                case "5": showBalance(); break;
                case "6": showHistory(); break;
                case "7": listAccounts(); break;
                case "0": running = false; break;
                default: System.out.println("Invalid choice");
            }
        }
        System.out.println("Goodbye!");
    }

    private static void printMenu() {
        System.out.println("\n1) Create account\n2) Deposit\n3) Withdraw\n4) Transfer\n5) Show balance\n6) Transaction history\n7) List accounts\n0) Exit");
        System.out.print("Choose: ");
    }

    private static void createAccount() {
        System.out.print("Owner name: ");
        String owner = sc.nextLine();
        System.out.print("Initial deposit (0 for none): ");
        double init = parseDouble(sc.nextLine());
        Account a = bank.createAccount(owner, init);
        System.out.println("Created: " + a.getAccountNumber() + " for " + a.getOwner());
    }

    private static void deposit() {
        System.out.print("Account number: "); String acc = sc.nextLine();
        Account a = bank.getAccount(acc);
        if (a == null) { System.out.println("Account not found"); return; }
        System.out.print("Amount: "); double amt = parseDouble(sc.nextLine());
        try {
            a.deposit(amt, "Deposit via CLI");
            System.out.println("New balance: " + a.getBalance());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void withdraw() {
        System.out.print("Account number: "); String acc = sc.nextLine();
        Account a = bank.getAccount(acc);
        if (a == null) { System.out.println("Account not found"); return; }
        System.out.print("Amount: "); double amt = parseDouble(sc.nextLine());
        try {
            a.withdraw(amt, "Withdraw via CLI");
            System.out.println("New balance: " + a.getBalance());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void transfer() {
        System.out.print("From account: "); String from = sc.nextLine();
        System.out.print("To account: "); String to = sc.nextLine();
        System.out.print("Amount: "); double amt = parseDouble(sc.nextLine());
        try {
            boolean ok = bank.transfer(from, to, amt);
            System.out.println(ok ? "Transfer successful" : "Transfer failed (insufficient funds)");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void showBalance() {
        System.out.print("Account number: "); String acc = sc.nextLine();
        Account a = bank.getAccount(acc);
        if (a == null) System.out.println("Account not found");
        else System.out.println(a);
    }

    private static void showHistory() {
        System.out.print("Account number: "); String acc = sc.nextLine();
        Account a = bank.getAccount(acc);
        if (a == null) { System.out.println("Account not found"); return; }
        List<Transaction> hist = a.getHistory();
        if (hist.isEmpty()) System.out.println("No transactions yet.");
        else hist.forEach(System.out::println);
    }

    private static void listAccounts() {
        if (bank.getAccounts().isEmpty()) {
            System.out.println("No accounts yet.");
        } else {
            bank.getAccounts().values().forEach(System.out::println);
        }
    }

    private static double parseDouble(String s) {
        try { return Double.parseDouble(s.trim()); } catch (Exception e) { return 0.0; }
    }
}