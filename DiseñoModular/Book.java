package DiseñoModular;

public class Book extends Document implements Storable {
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
        StringBuilder sb = new StringBuilder();
        sb.append("[BOOK] \"").append(title).append("\" - Author: ").append(author)
          .append(" - Available: ").append(availableCopies).append("/").append(totalCopies).append(" copies");
        return sb.toString();
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
