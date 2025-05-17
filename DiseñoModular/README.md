# DISEÑO MODULAR

## COHESION

## ACOPLAMIENTO

## TAMAÑO

### SELLS CODE

En cuato al tamaño, las siguientes clases presetaban smells code y aqui esta la propuesta de mejora.

USER ✅

SERVICE ✅

MATERIAL ✅

LOANABLE ✅

AUDIOVISUAL ✅

DOCUMENT ✅

LIBRARYMANAGER

```java
import java.util.ArrayList;
import java.util.List;

public class LibraryManager {
    private Storage storage;
    private List<User> users;
    private List<Loan> loans;

    public LibraryManager() {
        storage = new Storage();
        users = new ArrayList<>();
        loans = new ArrayList<>();
    }

    public void addUser(String name, String email, String type) {
        int maxItems;
        switch (type.toLowerCase()) {
            case "student":
                maxItems = 5;
                break;
            case "teacher":
                maxItems = 10;
                break;
            case "administrative":
                maxItems = 3;
                break;
            case "visitor":
                maxItems = 0;
                break;
            default:
                maxItems = 5;
        }
        users.add(new User(users.size() + 1, name, email, type, maxItems));
    }

    public Storage getStorage() {
        return storage;
    }

    public void showSearch(String keyword) {
        List<Loanable> results = storage.search(keyword);
        System.out.println("SEARCH: \"" + keyword + "\"");
        int i = 1;
        for (Loanable item : results) {
            System.out.println(i++ + ". " + item.display());
        }
    }

    public void createLoan(int userId, int itemId, String itemType, int maxDays) {
        User user = getUserById(userId);
        Loanable item = null;
        switch (itemType.toLowerCase()) {
            case "book":
                item = storage.getBookById(itemId);
                break;
            case "magazine":
                item = storage.getMagazineById(itemId);
                break;
            case "audiovisual":
                item = storage.getAudiovisualById(itemId);
                break;
            case "material":
                item = storage.getMaterialById(itemId);
                break;
            case "classroom":
                item = storage.getClassroomById(itemId);
                break;
            default:
                System.out.println("Invalid item type.");
                return;
        }
        if (user == null || item == null) {
            System.out.println("User or item not found.");
            return;
        }
        if (user.getSuspendedDays() > 0) {
            System.out.println("User suspended, cannot loan items.");
            return;
        }
        if (getUserLoanCount(user) >= user.getMaxItems()) {
            System.out.println("User " + user.display() + " has reached the loan limit.");
            return;
        }
        if (!item.isAvailable()) {
            System.out.println("Item " + item.getTitle() + " is not available.");
            return;
        }
        Loan loan = new Loan(user, item, maxDays);
        loans.add(loan);
        System.out.println("Loan created: " + loan.display());
    }

    public void processReturn(int loanIndex) {
        if (loanIndex < 0 || loanIndex >= loans.size()) {
            System.out.println("Invalid loan.");
            return;
        }
        Loan loan = loans.get(loanIndex);
        long delayDays = loan.processReturn();
        if (delayDays > 0) {
            loan.getUser().addSuspension((int) delayDays);
            System.out.println("Late return by " + delayDays + " day(s). User "
                    + loan.getUser().display() + " suspended for " + delayDays + " day(s).");
        } else {
            System.out.println("Return on time.");
        }
        loans.remove(loanIndex);
    }

    public void showInventory() {
        System.out.println("--- Inventory ---");
        System.out.println("Books:");
        for (Book b : storage.getBooks()) {
            System.out.println(b.display());
        }
        System.out.println("Magazines:");
        for (Magazine m : storage.getMagazines()) {
            System.out.println(m.display());
        }
        System.out.println("Audiovisuals:");
        for (Audiovisual a : storage.getAudiovisuals()) {
            System.out.println(a.display());
        }
        System.out.println("Materials:");
        for (Material m : storage.getMaterials()) {
            System.out.println(m.display());
        }
        System.out.println("Classrooms:");
        for (Classroom c : storage.getClassrooms()) {
            System.out.println(c.display());
        }
    }

    public void showActiveLoans() {
        System.out.println("--- Active Loans ---");
        for (Loan loan : loans) {
            System.out.println(loan.display());
        }
    }

    public void showUsers() {
        System.out.println("--- Users ---");
        for (User user : users) {
            System.out.println(user.display() + " - Suspension: " + user.getSuspendedDays() + " day(s)");
        }
    }

    public User getUserById(int userId) {
        for (User u : users) {
            if (u.getId() == userId)
                return u;
        }
        return null;
    }

    public User getUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }

    public void showActiveLoansByUser(User user) {
        System.out.println("--- Active Loans for " + user.display() + " ---");
        boolean found = false;
        for (Loan loan : loans) {
            if (loan.getUser().getId() == user.getId()) {
                System.out.println(loan.display());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No active loans for this user.");
        }
    }

    private int getUserLoanCount(User user) {
        int count = 0;
        for (Loan loan : loans) {
            if (loan.getUser().getId() == user.getId())
                count++;
        }
        return count;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void createDefualtStorage() {
        addUser("Admin", "admin@uni.edu", "admin");
        addUser("Maria Lopez", "maria@uni.edu", "student");
        addUser("John Perez", "john@uni.edu", "teacher");

        Book book1 = new Book(1, "Python for All", "Raul Gomez", 2025, "Editorial X", "Programming", "978-xxx", 3);
        getStorage().addBook(book1);

        Magazine magazine1 = new Magazine(2, "Python Code #45", "Dev Press", 2025, 45, "03/2025", "Technology");
        getStorage().addMagazine(magazine1);

        Audiovisual audiovisual1 = new Audiovisual(3, "Nature Documentary", "BBC", 2020, "MP4", 90, "Wildlife");
        getStorage().addAudiovisual(audiovisual1);

        Material material1 = new Material(4, "Canon 15x", "Canon digital camera");
        getStorage().addMaterial(material1);

        Classroom classroom1 = new Classroom(5, "Room 101", 30);
        getStorage().addClassroom(classroom1);
    }
}

```

#### Solucion

Delegada correcta responsabilidades en metodos mas pequeños, eliminacion de lógica y métodos duplicados

```java
import java.util.ArrayList;
import java.util.List;

public class LibraryManager {
    private Storage storage;
    private List<User> users;
    private List<Loan> loans;

    public LibraryManager() {
        storage = new Storage();
        users = new ArrayList<>();
        loans = new ArrayList<>();
    }

    public void addUser(String name, String email, String type) {
        int maxItems = getMaxItemsForUserType(type);
        users.add(new User(users.size() + 1, name, email, type, maxItems));
    }

    private int getMaxItemsForUserType(String type) {
        switch (type.toLowerCase()) {
            case "student": return 5;
            case "teacher": return 10;
            case "administrative": return 3;
            case "visitor": return 0;
            default: return 5;
        }
    }

    public void showSearch(String keyword) {
        List<Loanable> results = storage.search(keyword);
        System.out.println("SEARCH: \"" + keyword + "\"");
        displayItems(results);
    }

    public void showInventory() {
        System.out.println("--- Inventory ---");
        displayInventory(storage.getBooks(), "Books");
        displayInventory(storage.getMagazines(), "Magazines");
        displayInventory(storage.getAudiovisuals(), "Audiovisuals");
        displayInventory(storage.getMaterials(), "Materials");
        displayInventory(storage.getClassrooms(), "Classrooms");
    }

    private <T extends Loanable> void displayInventory(List<T> items, String category) {
        System.out.println(category + ":");
        for (Loanable item : items) {
            System.out.println(item.display());
        }
    }

    public void showActiveLoans() {
        System.out.println("--- Active Loans ---");
        for (Loan loan : loans) {
            System.out.println(loan.display());
        }
    }

    public void showUsers() {
        System.out.println("--- Users ---");
        for (User user : users) {
            System.out.println(user.display() + " - Suspension: " + user.getSuspendedDays() + " day(s)");
        }
    }

    public User getUserById(int userId) {
        return users.stream().filter(u -> u.getId() == userId).findFirst().orElse(null);
    }

    public User getUserByEmail(String email) {
        return users.stream().filter(user -> user.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);
    }

    public void createLoan(int userId, int itemId, String itemType, int maxDays) {
        User user = getUserById(userId);
        Loanable item = getItemByType(itemId, itemType);
        
        if (user == null || item == null || user.getSuspendedDays() > 0 || getUserLoanCount(user) >= user.getMaxItems() || !item.isAvailable()) {
            System.out.println("Cannot create loan: Invalid user/item, user suspended, item not available, or loan limit reached.");
            return;
        }

        Loan loan = new Loan(user, item, maxDays);
        loans.add(loan);
        System.out.println("Loan created: " + loan.display());
    }

    private Loanable getItemByType(int itemId, String itemType) {
        switch (itemType.toLowerCase()) {
            case "book": return storage.getBookById(itemId);
            case "magazine": return storage.getMagazineById(itemId);
            case "audiovisual": return storage.getAudiovisualById(itemId);
            case "material": return storage.getMaterialById(itemId);
            case "classroom": return storage.getClassroomById(itemId);
            default: return null;
        }
    }

    private int getUserLoanCount(User user) {
        return (int) loans.stream().filter(loan -> loan.getUser().getId() == user.getId()).count();
    }

    public void processReturn(int loanIndex) {
        if (loanIndex < 0 || loanIndex >= loans.size()) {
            System.out.println("Invalid loan.");
            return;
        }

        Loan loan = loans.get(loanIndex);
        long delayDays = loan.processReturn();
        
        if (delayDays > 0) {
            loan.getUser().addSuspension((int) delayDays);
            System.out.println("Late return by " + delayDays + " day(s). User " + loan.getUser().display() + " suspended for " + delayDays + " day(s).");
        } else {
            System.out.println("Return on time.");
        }
        
        loans.remove(loanIndex);
    }

    public void showActiveLoansByUser(User user) {
        System.out.println("--- Active Loans for " + user.display() + " ---");
        boolean found = loans.stream().anyMatch(loan -> loan.getUser().getId() == user.getId());
        
        if (!found) {
            System.out.println("No active loans for this user.");
        } else {
            loans.stream().filter(loan -> loan.getUser().getId() == user.getId()).forEach(loan -> System.out.println(loan.display()));
        }
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void createDefaultStorage() {
        addUser("Admin", "admin@uni.edu", "admin");
        addUser("Maria Lopez", "maria@uni.edu", "student");
        addUser("John Perez", "john@uni.edu", "teacher");

        Book book1 = new Book(1, "Python for All", "Raul Gomez", 2025, "Editorial X", "Programming", "978-xxx", 3);
        storage.addBook(book1);

        Magazine magazine1 = new Magazine(2, "Python Code #45", "Dev Press", 2025, 45, "03/2025", "Technology");
        storage.addMagazine(magazine1);

        Audiovisual audiovisual1 = new Audiovisual(3, "Nature Documentary", "BBC", 2020, "MP4", 90, "Wildlife");
        storage.addAudiovisual(audiovisual1);

        Material material1 = new Material(4, "Canon 15x", "Canon digital camera");
        storage.addMaterial(material1);

        Classroom classroom1 = new Classroom(5, "Room 101", 30);
        storage.addClassroom(classroom1);
    }
}

```

LOAN

```java
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Loan {
    private User user;
    private Loanable item;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private String loanCode;
    
    public Loan(User user, Loanable item, int maxDays) {
        this.user = user;
        this.item = item;
        this.loanDate = LocalDate.now();
        this.dueDate = loanDate.plusDays(maxDays);
        this.loanCode = generateLoanCode();
        
        // For a Book, call its specialized lend() method; for other items, simply mark them unavailable.
        if (item instanceof Book) {
            ((Book) item).lend();
        } else {
            item.setAvailable(false);
        }
    }
    
    private String generateLoanCode() {
        String prefix = item.getTitle().substring(0, Math.min(3, item.getTitle().length())).toUpperCase();
        return prefix + "-LOAN-" + user.getId();
    }
    
    public long processReturn() {
        LocalDate returnDate = LocalDate.now();
        long delay = ChronoUnit.DAYS.between(dueDate, returnDate);
        if (item instanceof Book) {
            ((Book) item).returnItem();
        } else {
            item.setAvailable(true);
        }
        return delay > 0 ? delay : 0;
    }
    
    public String display() {
        return "Code: " + loanCode + " | User: " + user.display() +
               " | Item: " + item.getTitle() + " | Due Date: " + dueDate;
    }
    
    public User getUser() {
        return user;
    }
}

```

#### Solución

```java
package DiseñoModular;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Loan {
    private User user;
    private Loanable item;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private String loanCode;
    
    public Loan(User user, Loanable item, int maxDays) {
        this.user = user;
        this.item = item;
        this.loanDate = LocalDate.now();
        this.dueDate = loanDate.plusDays(maxDays);
        this.loanCode = generateLoanCode();
        
        markItemAsUnavailable(item);
    }
    
    private void markItemAsUnavailable(Loanable item) {
        if (item instanceof Book) {
            lendBook((Book) item);
        } else {
            item.setAvailable(false);
        }
    }

    private void lendBook(Book book) {
        book.lend();
    }

    private String generateLoanCode() {
        String prefix = item.getTitle().substring(0, Math.min(3, item.getTitle().length())).toUpperCase();
        return prefix + "-LOAN-" + user.getId();
    }

    public long processReturn() {
        long delay = calculateDelay();
        returnItem();
        return delay;
    }

    private long calculateDelay() {
        LocalDate returnDate = LocalDate.now();
        return ChronoUnit.DAYS.between(dueDate, returnDate);
    }

    private void returnItem() {
        if (item instanceof Book) {
            ((Book) item).returnItem();
        } else {
            item.setAvailable(true);
        }
    }

    public String display() {
        return "Code: " + loanCode + " | User: " + user.display() +
               " | Item: " + item.getTitle() + " | Due Date: " + dueDate;
    }

    public User getUser() {
        return user;
    }
}


```

CLASSROOM

```java
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

```

#### Solucion

Eliminar erencia con Service porque no se implementan metodos ni funciones, mejorado display con StringBuilder

```java
public class Classroom implements Loanable {
    private int id;
    private String name;
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
        StringBuilder sb = new StringBuilder();
        sb.append("[CLASSROOM] \"").append(name).append("\" - Capacity: ").append(capacity);
        return sb.toString();
    }
}

```

BOOK

```java

public class Book extends Document {
    private String publisher;
    private String genre;
    private String isbn;
    private int totalCopies;
    private int availableCopies;
    
    public Book(int id, String title, String author, int publicationYear, String publisher, String genre, String isbn, int totalCopies) {
        super(id, title, author, publicationYear);
        this.publisher = publisher;
        this.genre = genre;
        this.isbn = isbn;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
        this.available = totalCopies > 0;
    }
    
    @Override
    public String display() {
        return "[BOOK] \"" + title + "\" - Author: " + author + " - Available: " 
                + availableCopies + "/" + totalCopies + " copies";
    }
    
    public boolean canBeLent() {
        return availableCopies > 0;
    }
    
    public void lend() {
        if (canBeLent()) {
            availableCopies--;
            if (availableCopies == 0) {
                available = false;
            }
        }
    }
    
    public void returnItem() {
        if (availableCopies < totalCopies) {
            availableCopies++;
            available = true;
        }
    }
}

```

#### Solucion

Creo LoanManager que maneja lógica de prestamo y devolución

```java
public class LoanManager {

    public void lendBook(Book book) {
        if (book.canBeLent()) {
            book.lend();
        } else {
            System.out.println("The book is not available for lending.");
        }
    }

    public void returnBook(Book book) {
        book.returnItem();
    }
}

```

Refactorizo la clase Book

```java
public class Book extends Document {
    private String publisher;
    private String genre;
    private String isbn;
    private int totalCopies;
    private int availableCopies;
    
    public Book(int id, String title, String author, int publicationYear, String publisher, String genre, String isbn, int totalCopies) {
        super(id, title, author, publicationYear);
        this.publisher = publisher;
        this.genre = genre;
        this.isbn = isbn;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
        this.available = totalCopies > 0;
    }
    
    @Override
    public String display() {
        StringBuilder sb = new StringBuilder();
        sb.append("[BOOK] \"").append(title).append("\" - Author: ").append(author)
          .append(" - Available: ").append(availableCopies).append("/").append(totalCopies).append(" copies");
        return sb.toString();
    }
    
    public boolean canBeLent() {
        return availableCopies > 0;
    }

    public void lend() {
        if (canBeLent()) {
            availableCopies--;
            if (availableCopies == 0) {
                available = false;
            }
        }
    }

    public void returnItem() {
        if (availableCopies < totalCopies) {
            availableCopies++;
            available = true;
        }
    }
}

```


MAGAZINE

```java

public class Magazine extends Document {
    private String publisher;
    private int issueNumber;
    private String monthYear;
    private String theme;
    
    public Magazine(int id, String title, String publisher, int publicationYear, int issueNumber, String monthYear, String theme) {
        super(id, title, null, publicationYear);
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

```

#### Solucion

Crear clase MagazineData

```java
public class MagazineData {
    public int id;
    public String title;
    public String publisher;
    public int publicationYear;
    public int issueNumber;
    public String monthYear;
    public String theme;
}
```

Refactorizar Magazine

```java
public class Magazine extends Document {
    private String publisher;
    private int issueNumber;
    private String monthYear;
    private String theme;

    public Magazine(MagazineData data) {
        super(data.id, data.title, null, data.publicationYear);
        this.publisher = data.publisher;
        this.issueNumber = data.issueNumber;
        this.monthYear = data.monthYear;
        this.theme = data.theme;
    }

    @Override
    public String display() {
        return "[MAGAZINE] \"" + title + "\" - Publisher: " + publisher;
    }
}
```

STORAGE 

```java
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

```

#### Solucion

Crear clase Storable que actua como interface:

```java
public interface Storable {
    int getId();
    String getTitle();
}

```

Crear clase StorageList para manejar las listas:

```java
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
```

Refactorizar clase Storage

```java
public class Storage {
    private StorageList<Book> books = new StorageList<>();
    private StorageList<Magazine> magazines = new StorageList<>();
    private StorageList<Audiovisual> audiovisuals = new StorageList<>();
    private StorageList<Material> materials = new StorageList<>();
    private StorageList<Classroom> classrooms = new StorageList<>();

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

    public Audiovisual getAudiovisualById(int id) {
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

    public List<Audiovisual> getAudiovisuals() {
        return audiovisuals.getAll();
    }

    public List<Material> getMaterials() {
        return materials.getAll();
    }

    public List<Classroom> getClassrooms() {
        return classrooms.getAll();
    }
}

```


