package DiseñoModular;

public abstract class Document implements Loanable {
    protected int id;
    protected String title;
    protected String author;
    protected int publicationYear;
    protected boolean available;
    
    public Document(int id, String title, String author, int publicationYear) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.available = true;
    }
    
    public int getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public boolean isAvailable() {
        return available;
    }
    
    public void setAvailable(boolean available) {
        this.available = available;
    }
    
    public abstract String display();
}

