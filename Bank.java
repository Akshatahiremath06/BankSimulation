import java.util.HashMap;
import java.util.Map;

public class Bank {
    private final Map<String, Account> accounts = new HashMap<>();

    public Account createAccount(String owner, double initialDeposit) {
        Account a = new Account(owner, initialDeposit);
        accounts.put(a.getAccountNumber(), a);
        return a;
    }

    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    /**
     * Simple transfer: withdraw from source, deposit to target.
     * Returns true if success, false if insufficient funds.
     */
    public boolean transfer(String fromAcc, String toAcc, double amount) {
        Account from = getAccount(fromAcc);
        Account to = getAccount(toAcc);
        if (from == null || to == null) throw new IllegalArgumentException("Account not found");
        // avoid race conditions by locking both accounts consistently
        Object first = from.getAccountNumber().compareTo(to.getAccountNumber()) < 0 ? from : to;
        Object second = first == from ? to : from;
        synchronized (first) {
            synchronized (second) {
                try {
                    from.withdraw(amount, "Transfer to " + toAcc);
                    to.deposit(amount, "Transfer from " + fromAcc);
                    return true;
                } catch (IllegalArgumentException e) {
                    return false;
                }
            }
        }
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }
}