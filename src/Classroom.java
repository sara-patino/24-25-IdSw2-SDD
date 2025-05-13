public class Classroom extends Service implements Loanable {
    private int capacity;

    public Classroom(int id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.available = true;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public String display() {
        return "[CLASSROOM] \"" + name + "\" - Capacity: " + capacity;
    }
}
