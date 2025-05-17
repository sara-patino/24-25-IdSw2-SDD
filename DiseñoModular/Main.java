package DiseñoModular;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final int OPTION_SEARCH = 1;
    private static final int OPTION_CREATE_LOAN = 2;
    private static final int OPTION_RETURN_LOAN = 3;
    private static final int OPTION_ADD_USER = 4;
    private static final int OPTION_ADD_BOOK = 5;
    private static final int OPTION_SHOW_INVENTORY = 6;
    private static final int OPTION_SHOW_USERS = 7;
    private static final int OPTION_SHOW_ACTIVE_LOANS = 8;
    private static final int OPTION_EXIT = 0;

    public static void main(String[] args) {
        LibraryManager libraryManager = new LibraryManager();
        Scanner scanner = new Scanner(System.in);
        libraryManager.createDefaultStorage();

        System.out.println("===== BIENVENIDO AL GESTOR DE BIBLIOTECA =====");
        User currentUser = handleLogin(scanner, libraryManager);
        if (currentUser == null) return;

        boolean isAdmin = currentUser.getType().equalsIgnoreCase("admin");
        boolean running = true;

        while (running) {
            showMenu(currentUser);
            int opcion = getUserOption(scanner);

            switch (opcion) {
                case OPTION_SEARCH:
                    searchMaterial(scanner, libraryManager);
                    break;

                case OPTION_CREATE_LOAN:
                    createLoan(scanner, currentUser, libraryManager);
                    break;

                case OPTION_RETURN_LOAN:
                    returnLoan(scanner, currentUser, libraryManager);
                    break;

                case OPTION_ADD_USER:
                    if (isAdmin) addUser(scanner, libraryManager);
                    break;

                case OPTION_ADD_BOOK:
                    if (isAdmin) addBook(scanner, libraryManager);
                    break;

                case OPTION_SHOW_INVENTORY:
                    if (isAdmin) libraryManager.showInventory();
                    break;

                case OPTION_SHOW_USERS:
                    if (isAdmin) libraryManager.showUsers();
                    break;

                case OPTION_SHOW_ACTIVE_LOANS:
                    libraryManager.showActiveLoansByUser(currentUser);
                    break;

                case OPTION_EXIT:
                    running = false;
                    break;

                default:
                    System.out.println("Opción no válida.");
            }

            System.out.println("\n------------------------------------------\n");
        }

        System.out.println("¡Gracias por usar el sistema!");
        scanner.close();
    }

    private static User handleLogin(Scanner scanner, LibraryManager libraryManager) {
        System.out.print("Ingrese su correo: ");
        String correoLogin = scanner.nextLine();
        User currentUser = libraryManager.getUserByEmail(correoLogin);

        if (currentUser == null) {
            System.out.println("Usuario no encontrado. Fin del programa.");
            return null;
        }
        return currentUser;
    }

    private static void showMenu(User currentUser) {
        System.out.println("\n======= MENÚ DE OPCIONES =======");
        System.out.println("Usuario activo: " + currentUser.getName() + " (" + currentUser.getType() + ")");
        System.out.println("1. Buscar material");
        System.out.println("2. Crear préstamo");
        System.out.println("3. Devolver préstamo");

        if (currentUser.getType().equalsIgnoreCase("admin")) {
            System.out.println("4. Agregar usuario");
            System.out.println("5. Agregar libro");
            System.out.println("6. Mostrar inventario");
            System.out.println("7. Mostrar usuarios");
        }

        System.out.println("8. Mostrar préstamos activos");
        System.out.println("0. Salir");
    }

    private static int getUserOption(Scanner scanner) {
        int opcion = -1;
        while (opcion < 0 || opcion > 8) {
            System.out.print("Seleccione una opción: ");
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Entrada no válida, por favor intente de nuevo.");
                scanner.nextLine();
            }
        }
        return opcion;
    }

    private static void searchMaterial(Scanner scanner, LibraryManager libraryManager) {
        System.out.print("Buscar: ");
        String termino = scanner.nextLine();
        libraryManager.showSearch(termino);
    }

    private static void createLoan(Scanner scanner, User currentUser, LibraryManager libraryManager) {
        System.out.print("ID del material: ");
        int idMaterial = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Tipo de material (book/magazine/audiovisual/material/classroom): ");
        String tipoMat = scanner.nextLine();
        System.out.print("Días de préstamo: ");
        int dias = scanner.nextInt();
        scanner.nextLine();
        libraryManager.createLoan(currentUser.getId(), idMaterial, tipoMat, dias);
    }

    private static void returnLoan(Scanner scanner, User currentUser, LibraryManager libraryManager) {
        libraryManager.showActiveLoansByUser(currentUser);
        List<Loan> activeLoans = libraryManager.getLoans();
        boolean hasLoans = false;
        for (Loan loan : activeLoans) {
            if (loan.getUser().getId() == currentUser.getId()) {
                hasLoans = true;
                break;
            }
        }
        if (!hasLoans) {
            return;
        }

        System.out.print("Índice del préstamo a devolver: ");
        int index = scanner.nextInt();
        scanner.nextLine();
        libraryManager.processReturn(index);
    }

    private static void addUser(Scanner scanner, LibraryManager libraryManager) {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Correo: ");
        String correo = scanner.nextLine();
        System.out.print("Tipo (student/teacher/visitor): ");
        String tipo = scanner.nextLine();
        libraryManager.addUser(nombre, correo, tipo);
    }

    private static void addBook(Scanner scanner, LibraryManager libraryManager) {
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Autor: ");
        String autor = scanner.nextLine();
        System.out.print("Año: ");
        int anio = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Editorial: ");
        String editorial = scanner.nextLine();
        System.out.print("Categoría: ");
        String categoria = scanner.nextLine();
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Cantidad: ");
        int cantidad = scanner.nextInt();
        scanner.nextLine();
        Book nuevoLibro = new Book(1, titulo, autor, anio, editorial, categoria, isbn, cantidad);
        libraryManager.getStorage().addBook(nuevoLibro);
    }
}
