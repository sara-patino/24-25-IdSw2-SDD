package DiseñoModular;

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
        
        markItemAsUnavailable(item);
    }
    
    private void markItemAsUnavailable(Loanable item) {
        if (item instanceof Book) {
            lendBook((Book) item);
        } else {
            item.setAvailable(false);
        }
    }

    private void lendBook(Book book) {
        book.lend();
    }

    private String generateLoanCode() {
        String prefix = item.getTitle().substring(0, Math.min(3, item.getTitle().length())).toUpperCase();
        return prefix + "-LOAN-" + user.getId();
    }

    public long processReturn() {
        long delay = calculateDelay();
        returnItem();
        return delay;
    }

    private long calculateDelay() {
        LocalDate returnDate = LocalDate.now();
        return ChronoUnit.DAYS.between(dueDate, returnDate);
    }

    private void returnItem() {
        if (item instanceof Book) {
            ((Book) item).returnItem();
        } else {
            item.setAvailable(true);
        }
    }

    public String display() {
        return "Code: " + loanCode + " | User: " + user.display() +
               " | Item: " + item.getTitle() + " | Due Date: " + dueDate;
    }

    public User getUser() {
        return user;
    }
}

