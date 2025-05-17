package DiseñoModular;

public class LoanManager {

    public void lendBook(Book book) {
        if (book.canBeLent()) {
            book.lend();
        } else {
            System.out.println("The book is not available for lending.");
        }
    }

    public void returnBook(Book book) {
        book.returnItem();
    }
}
