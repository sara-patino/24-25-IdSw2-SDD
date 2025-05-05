
public class Book extends Document {
    private String publisher;
    private String genre;
    private String isbn;
    private int totalCopies;
    private int availableCopies;
    
    public Book(int id, String title, String author, int publicationYear, String publisher, String genre, String isbn, int totalCopies) {
        super(id, title, author, publicationYear);
        this.publisher = publisher;
        this.genre = genre;
        this.isbn = isbn;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
        this.available = totalCopies > 0;
    }
    
    @Override
    public String display() {
        return "[BOOK] \"" + title + "\" - Author: " + author + " - Available: " 
                + availableCopies + "/" + totalCopies + " copies";
    }
    
    public boolean canBeLent() {
        return availableCopies > 0;
    }
    
    public void lend() {
        if (canBeLent()) {
            availableCopies--;
            if (availableCopies == 0) {
                available = false;
            }
        }
    }
    
    public void returnItem() {
        if (availableCopies < totalCopies) {
            availableCopies++;
            available = true;
        }
    }
}
