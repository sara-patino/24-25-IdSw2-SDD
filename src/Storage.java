import java.util.ArrayList;
import java.util.List;

public class Storage {
    private List<Book> books;
    private List<Magazine> magazines;
    private List<Audiovisual> audiovisuals;
    private List<Material> materials;
    private List<Classroom> classrooms;

    public Storage() {
        books = new ArrayList<>();
        magazines = new ArrayList<>();
        audiovisuals = new ArrayList<>();
        materials = new ArrayList<>();
        classrooms = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void addMagazine(Magazine magazine) {
        magazines.add(magazine);
    }

    public void addAudiovisual(Audiovisual audiovisual) {
        audiovisuals.add(audiovisual);
    }

    public void addMaterial(Material material) {
        materials.add(material);
    }

    public void addClassroom(Classroom classroom) {
        classrooms.add(classroom);
    }

    public void eraseBook(int id) {
        books.removeIf(b -> b.getId() == id);
    }

    public void eraseMagazine(int id) {
        magazines.removeIf(m -> m.getId() == id);
    }

    public void eraseAudiovisual(int id) {
        audiovisuals.removeIf(a -> a.getId() == id);
    }

    public void eraseMaterial(int id) {
        materials.removeIf(m -> m.getId() == id);
    }

    public void eraseClassroom(int id) {
        classrooms.removeIf(c -> c.getId() == id);
    }

    public Book getBookById(int id) {
        for (Book b : books) {
            if (b.getId() == id)
                return b;
        }
        return null;
    }

    public Magazine getMagazineById(int id) {
        for (Magazine m : magazines) {
            if (m.getId() == id)
                return m;
        }
        return null;
    }

    public Audiovisual getAudiovisualById(int id) {
        for (Audiovisual a : audiovisuals) {
            if (a.getId() == id)
                return a;
        }
        return null;
    }

    public Material getMaterialById(int id) {
        for (Material m : materials) {
            if (m.getId() == id)
                return m;
        }
        return null;
    }

    public Classroom getClassroomById(int id) {
        for (Classroom c : classrooms) {
            if (c.getId() == id)
                return c;
        }
        return null;
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<Magazine> getMagazines() {
        return magazines;
    }

    public List<Audiovisual> getAudiovisuals() {
        return audiovisuals;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public List<Classroom> getClassrooms() {
        return classrooms;
    }

    public List<Loanable> search(String keyword) {
        List<Loanable> results = new ArrayList<>();
        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                results.add(b);
        }
        for (Magazine m : magazines) {
            if (m.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                results.add(m);
        }
        for (Audiovisual a : audiovisuals) {
            if (a.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                results.add(a);
        }
        for (Material m : materials) {
            if (m.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                results.add(m);
        }
        for (Classroom c : classrooms) {
            if (c.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                results.add(c);
        }
        return results;
    }
}
