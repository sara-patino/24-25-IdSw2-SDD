package DiseñoModular;

public class Classroom implements Loanable, Storable {
    private final int id;
    private final String name;
    private int capacity;
    private boolean available;

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
        return String.format("[CLASSROOM] \"%s\" - Capacity: %d", name, capacity);
    }

    public int getId() {
        return id;
    }
}
