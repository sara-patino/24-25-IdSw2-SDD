import java.util.ArrayList;
import java.util.List;

// Clase World que maneja la inicialización
class World {
    private Library library;
    private List<Person> people;

    public World() {
        this.library = new Library();
        this.people = new ArrayList<>();
    }

    // Método para crear una persona y agregarla al mundo
    public void createPerson(String name, String email, String clientType, List<Loan> previousLoans) {
        Person person = new Person(name, email, clientType, previousLoans);
        people.add(person);
    }

    // Mostrar personas creadas
    public void showPeople() {
        for (Person person : people) {
            System.out.println(person);
        }
    }

    public static void main(String[] args) {
        World world = new World();
        
        // Creando algunas personas con datos ficticios
        world.createPerson("Alice", "alice@example.com", "Regular", new ArrayList<>());
        world.createPerson("Bob", "bob@example.com", "Premium", new ArrayList<>());
        
        // Mostrar las personas creadas
        world.showPeople();
    }
}
