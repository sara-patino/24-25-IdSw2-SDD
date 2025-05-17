package DiseñoModular;

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

    public int getId() {
        return id;
    }

    public String display() {
        return name + " (" + type + ")";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMaxItems(int maxItems) {
        this.maxItems = maxItems;
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
