package DiseñoModular;
import java.util.ArrayList;
import java.util.List;

public class StorageList<T extends Storable> {
    private List<T> items = new ArrayList<>();

    public void add(T item) {
        items.add(item);
    }

    public void erase(int id) {
        items.removeIf(i -> i.getId() == id);
    }

    public T getById(int id) {
        for (T item : items) {
            if (item.getId() == id) return item;
        }
        return null;
    }

    public List<T> search(String keyword) {
        List<T> results = new ArrayList<>();
        for (T item : items) {
            if (item.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(item);
            }
        }
        return results;
    }

    public List<T> getAll() {
        return items;
    }
}
