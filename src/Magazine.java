// Magazine.java
public class Magazine extends Document {
    private String publisher;
    private int issueNumber;
    private String monthYear;
    private String theme;
    
    public Magazine(int id, String title, String publisher, int publicationYear, int issueNumber, String monthYear, String theme) {
        super(id, title, null, publicationYear); // author can be null or "Editorial Team"
        this.publisher = publisher;
        this.issueNumber = issueNumber;
        this.monthYear = monthYear;
        this.theme = theme;
    }
    
    @Override
    public String display() {
        return "[MAGAZINE] \"" + title + "\" - Publisher: " + publisher;
    }
}
