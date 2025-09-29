import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private final LocalDateTime timestamp;
    private final String type;
    private final double amount;
    private final double balanceAfter;
    private final String note;

    public Transaction(String type, double amount, double balanceAfter, String note) {
        this.timestamp = LocalDateTime.now();
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.note = note == null ? "" : note;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("%s | %-8s | %10.2f | Balance: %10.2f | %s",
                timestamp.format(fmt), type, amount, balanceAfter, note);
    }
}