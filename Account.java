import java.util.ArrayList;
import java.util.List;

public class Account {
    private static int NEXT = 1001;
    private final String accountNumber;
    private final String owner;
    private double balance;
    private final List<Transaction> history;

    public Account(String owner, double initialDeposit) {
        this.accountNumber = String.valueOf(NEXT++);
        this.owner = owner;
        this.balance = 0.0;
        this.history = new ArrayList<>();
        if (initialDeposit > 0) deposit(initialDeposit, "Initial deposit");
    }

    public synchronized void deposit(double amount, String note) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be > 0");
        balance += amount;
        history.add(new Transaction("DEPOSIT", amount, balance, note));
    }

    public synchronized void withdraw(double amount, String note) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be > 0");
        if (amount > balance) throw new IllegalArgumentException("Insufficient funds");
        balance -= amount;
        history.add(new Transaction("WITHDRAW", amount, balance, note));
    }

    public synchronized double getBalance() { return balance; }

    public String getAccountNumber() { return accountNumber; }

    public String getOwner() { return owner; }

    public List<Transaction> getHistory() {
        return new ArrayList<>(history); // return a copy for safety
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - Balance: %.2f", accountNumber, owner, balance);
    }
}