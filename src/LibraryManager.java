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
            if (u.getId() == userId) return u;
        }
        return null;
    }
    
    private int getUserLoanCount(User user) {
        int count = 0;
        for (Loan loan : loans) {
            if (loan.getUser().getId() == user.getId())
                count++;
        }
        return count;
    }
    
    // (Optional) Getter for the loans list.
    public List<Loan> getLoans() {
        return loans;
    }
}
