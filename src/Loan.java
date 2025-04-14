import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Loan {
    private User user;
    private Loanable item;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private String loanCode;
    
    public Loan(User user, Loanable item, int maxDays) {
        this.user = user;
        this.item = item;
        this.loanDate = LocalDate.now();
        this.dueDate = loanDate.plusDays(maxDays);
        this.loanCode = generateLoanCode();
        
        // For a Book, call its specialized lend() method; for other items, simply mark them unavailable.
        if (item instanceof Book) {
            ((Book) item).lend();
        } else {
            item.setAvailable(false);
        }
    }
    
    private String generateLoanCode() {
        String prefix = item.getTitle().substring(0, Math.min(3, item.getTitle().length())).toUpperCase();
        return prefix + "-LOAN-" + user.getId();
    }
    
    public long processReturn() {
        LocalDate returnDate = LocalDate.now();
        long delay = ChronoUnit.DAYS.between(dueDate, returnDate);
        if (item instanceof Book) {
            ((Book) item).returnItem();
        } else {
            item.setAvailable(true);
        }
        return delay > 0 ? delay : 0;
    }
    
    public String display() {
        return "Code: " + loanCode + " | User: " + user.display() +
               " | Item: " + item.getTitle() + " | Due Date: " + dueDate;
    }
    
    public User getUser() {
        return user;
    }
}
