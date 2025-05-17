package DiseñoModular;

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
    
    private void displayItems(List<Loanable> items) {
        if (items.isEmpty()) {
            System.out.println("No results found.");
        } else {
            for (Loanable item : items) {
                System.out.println(item.display());
            }
        }
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
    
    public Storage getStorage() {
        return storage;
    }
    

    public void createDefaultStorage() {
        addUser("Admin", "admin@uni.edu", "admin");
        addUser("Maria Lopez", "maria@uni.edu", "student");
        addUser("John Perez", "john@uni.edu", "teacher");

        Book book1 = new Book(1, "Python for All", "Raul Gomez", 2025, "Editorial X", "Programming", "978-xxx", 3);
        storage.addBook(book1);

        Magazine magazine1 = new Magazine.Builder()
        .setId(2)
        .setTitle("Python Code #45")
        .setPublisher("Dev Press")
        .setPublicationYear(2025)
        .setIssueNumber(45)
        .setMonthYear("03/2025")
        .setTheme("Technology")
        .build();
    
        storage.addMagazine(magazine1);

        AudioVisual audiovisual1 = new AudioVisual(3, "Nature Documentary", "BBC", 2020, "MP4", 90, "Wildlife");
        storage.addAudiovisual(audiovisual1);

        Material material1 = new Material(4, "Canon 15x", "Canon digital camera");
        storage.addMaterial(material1);

        Classroom classroom1 = new Classroom(5, "Room 101", 30);
        storage.addClassroom(classroom1);
    }
}

