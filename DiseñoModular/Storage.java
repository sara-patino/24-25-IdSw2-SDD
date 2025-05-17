package DiseñoModular;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private StorageList<Book> books = new StorageList<>();
    private StorageList<Magazine> magazines = new StorageList<>();
    private StorageList<AudioVisual> audiovisuals = new StorageList<>();
    private StorageList<Material> materials = new StorageList<>();
    private StorageList<Classroom> classrooms = new StorageList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public void addMagazine(Magazine magazine) {
        magazines.add(magazine);
    }

    public void addAudiovisual(AudioVisual audiovisual) {
        audiovisuals.add(audiovisual);
    }

    public void addMaterial(Material material) {
        materials.add(material);
    }

    public void addClassroom(Classroom classroom) {
        classrooms.add(classroom);
    }

    public void eraseBook(int id) {
        books.erase(id);
    }

    public void eraseMagazine(int id) {
        magazines.erase(id);
    }

    public void eraseAudiovisual(int id) {
        audiovisuals.erase(id);
    }

    public void eraseMaterial(int id) {
        materials.erase(id);
    }

    public void eraseClassroom(int id) {
        classrooms.erase(id);
    }

    public Book getBookById(int id) {
        return books.getById(id);
    }

    public Magazine getMagazineById(int id) {
        return magazines.getById(id);
    }

    public AudioVisual getAudiovisualById(int id) {
        return audiovisuals.getById(id);
    }

    public Material getMaterialById(int id) {
        return materials.getById(id);
    }

    public Classroom getClassroomById(int id) {
        return classrooms.getById(id);
    }

    public List<Loanable> search(String keyword) {
        List<Loanable> results = new ArrayList<>();
        results.addAll(books.search(keyword));
        results.addAll(magazines.search(keyword));
        results.addAll(audiovisuals.search(keyword));
        results.addAll(materials.search(keyword));
        results.addAll(classrooms.search(keyword));
        return results;
    }

    public List<Book> getBooks() {
        return books.getAll();
    }

    public List<Magazine> getMagazines() {
        return magazines.getAll();
    }

    public List<AudioVisual> getAudiovisuals() {
        return audiovisuals.getAll();
    }

    public List<Material> getMaterials() {
        return materials.getAll();
    }

    public List<Classroom> getClassrooms() {
        return classrooms.getAll();
    }
}