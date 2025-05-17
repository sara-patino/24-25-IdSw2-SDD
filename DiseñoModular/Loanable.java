package DiseñoModular;

public interface Loanable {
    boolean isAvailable();
    void setAvailable(boolean available);
    String getTitle();
    String display();
}

