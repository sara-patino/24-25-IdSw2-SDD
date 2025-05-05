import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LibraryManager libraryManager = new LibraryManager();

        Scanner scanner = new Scanner(System.in);

        // Cargar un administrador por defecto (por ejemplo)
        libraryManager.addUser("Admin", "admin@uni.edu", "admin");
        libraryManager.addUser("Maria Lopez", "maria@uni.edu", "student");
        libraryManager.addUser("John Perez", "john@uni.edu", "teacher");

        Book book1 = new Book(1, "Python for All", "Raul Gomez", 2025, "Editorial X", "Programming", "978-xxx", 3);
        libraryManager.getStorage().addBook(book1);

        Magazine magazine1 = new Magazine(2, "Python Code #45", "Dev Press", 2025, 45, "03/2025", "Technology");
        libraryManager.getStorage().addMagazine(magazine1);

        Audiovisual audiovisual1 = new Audiovisual(3, "Nature Documentary", "BBC", 2020, "MP4", 90, "Wildlife");
        libraryManager.getStorage().addAudiovisual(audiovisual1);

        Material material1 = new Material(4, "Canon 15x", "Canon digital camera");
        libraryManager.getStorage().addMaterial(material1);

        System.out.println("===== BIENVENIDO AL GESTOR DE BIBLIOTECA =====");
        System.out.print("Ingrese su correo: ");
        String correoLogin = scanner.nextLine();

        User currentUser = libraryManager.getUserByEmail(correoLogin);

        if (currentUser == null) {
            System.out.println("Usuario no encontrado. Fin del programa.");
            return;
        }

        boolean isAdmin = currentUser.getType().equalsIgnoreCase("admin");
        boolean running = true;

        while (running) {
            System.out.println("\n======= MENÚ DE OPCIONES =======");
            System.out.println("Usuario activo: " + currentUser.getName() + " (" + currentUser.getType() + ")");
            System.out.println("1. Buscar material");
            System.out.println("2. Crear préstamo");
            System.out.println("3. Devolver préstamo");

            if (isAdmin) {
                System.out.println("4. Agregar usuario");
                System.out.println("5. Agregar libro");
                System.out.println("6. Mostrar inventario");
                System.out.println("7. Mostrar usuarios");
            }

            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.print("Buscar: ");
                    String termino = scanner.nextLine();
                    libraryManager.showSearch(termino);
                    break;

                case 2:
                    System.out.print("ID del material: ");
                    int idMaterial = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Tipo de material (book/magazine/audiovisual/material): ");
                    String tipoMat = scanner.nextLine();
                    System.out.print("Días de préstamo: ");
                    int dias = scanner.nextInt();
                    scanner.nextLine();
                    libraryManager.createLoan(currentUser.getId(), idMaterial, tipoMat, dias);
                    break;

                case 3:
                    libraryManager.showActiveLoansByUser(currentUser);
                    System.out.print("Índice del préstamo a devolver: ");
                    int index = scanner.nextInt();
                    scanner.nextLine();
                    libraryManager.processReturn(index);
                    break;

                case 4:
                    if (!isAdmin)
                        break;
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Correo: ");
                    String correo = scanner.nextLine();
                    System.out.print("Tipo (student/teacher/visitor): ");
                    String tipo = scanner.nextLine();
                    libraryManager.addUser(nombre, correo, tipo);
                    break;

                case 5:
                    if (!isAdmin)
                        break;
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
                    break;

                case 6:
                    if (isAdmin)
                        libraryManager.showInventory();
                    break;

                case 7:
                    if (isAdmin)
                        libraryManager.showUsers();
                    break;

                case 0:
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
}
