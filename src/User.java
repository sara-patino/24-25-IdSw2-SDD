public class User {
    private int id;
    private String name;
    private String email;
    private String type;
    private int maxItems;
    private int suspendedDays;
    
    public User(int id, String name, String email, String type, int maxItems) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.type = type;
        this.maxItems = maxItems;
        this.suspendedDays = 0;
    }
    
    public String display() {
        return name + " (" + type + ")";
    }
    
    public int getId() {
        return id;
    }
    
    public int getMaxItems() {
        return maxItems;
    }
    
    public void addSuspension(int days) {
        suspendedDays += days;
    }
    
    public int getSuspendedDays() {
        return suspendedDays;
    }
}
