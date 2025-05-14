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
                item = storage.getAudioVisualById(itemId);
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
        for (AudioVisual a : storage.getAudiovisuals()) {
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

    public void createDefaultStorage() {
        addUser("Admin", "admin@uni.edu", "admin");
        addUser("Maria Lopez", "maria@uni.edu", "student");
        addUser("John Perez", "john@uni.edu", "teacher");

        Book book1 = new Book(1, "Python for All", "Raul Gomez", 2025, "Editorial X", "Programming", "978-xxx", 3);
        getStorage().addBook(book1);

        Magazine magazine1 = new Magazine(2, "Python Code #45", "Dev Press", 2025, 45, "03/2025", "Technology");
        getStorage().addMagazine(magazine1);

        AudioVisual audiovisual1 = new AudioVisual(3, "Nature Documentary", "BBC", 2020, "MP4", 90, "Wildlife");
        getStorage().addAudioVisual(audiovisual1);

        Material material1 = new Material(4, "Canon 15x", "Canon digital camera");
        getStorage().addMaterial(material1);

        Classroom classroom1 = new Classroom(5, "Room 101", 30);
        getStorage().addClassroom(classroom1);
    }
}
