// Main.java
public class Main {
    public static void main(String[] args) {
        LibraryManager libraryManager = new LibraryManager();
        
        // Add users
        libraryManager.addUser("Maria Lopez", "maria@uni.edu", "student");
        libraryManager.addUser("John Perez", "john@uni.edu", "teacher");
        
        // Add items to storage
        
        // Book: "Python for All"
        Book book1 = new Book(1, "Python for All", "Raul Gomez", 2025, "Editorial X", "Programming", "978-xxx", 3);
        libraryManager.getStorage().addBook(book1);
        
        // Magazine: "Python Code #45"
        Magazine magazine1 = new Magazine(2, "Python Code #45", "Dev Press", 2025, 45, "03/2025", "Technology");
        libraryManager.getStorage().addMagazine(magazine1);
        
        // Audiovisual: "Nature Documentary"
        Audiovisual audiovisual1 = new Audiovisual(3, "Nature Documentary", "BBC", 2020, "MP4", 90, "Wildlife");
        libraryManager.getStorage().addAudiovisual(audiovisual1);
        
        // Material: Canon 15x camera
        Material material1 = new Material(4, "Canon 15x", "Canon digital camera");
        libraryManager.getStorage().addMaterial(material1);
        
        // Display a clean header for the search results.
        System.out.println("========== SEARCH RESULTS ==========");
        libraryManager.showSearch("Python");
        System.out.println("\n====================================\n");
        
        // Active user information for Maria
        User maria = libraryManager.getUserById(1);
        System.out.println("ACTIVE USER: " + maria.display() + "\n");
        
        // Create loans:
        // Loan a book (max 15 days) for Maria
        libraryManager.createLoan(maria.getId(), 1, "book", 15);
        
        // Loan a material (Canon 15x camera, max 7 days) for John Perez
        User john = libraryManager.getUserById(2);
        libraryManager.createLoan(john.getId(), 4, "material", 7);
        
        // Display inventory
        System.out.println("\n========== CURRENT INVENTORY ==========");
        libraryManager.showInventory();
        System.out.println("=======================================\n");
        
        // Display active loans
        System.out.println("========== ACTIVE LOANS ==========");
        libraryManager.showActiveLoans();
        System.out.println("==================================\n");
        
        // Process return for the first loan (for demonstration, assume on time)
        if (!libraryManager.getLoans().isEmpty()) {
            libraryManager.processReturn(0);
        }
        
        // Display results after processing the return
        System.out.println("\n========== LOANS AFTER RETURN ==========");
        libraryManager.showActiveLoans();
        System.out.println("========================================\n");
        
        System.out.println("========== USERS ==========");
        libraryManager.showUsers();
        System.out.println("================================\n");
    }
}
