public abstract class Servicio {
    private static int contadorId = 0;
    private int id;
    private String nombre;
    private boolean disponible;

    public Servicio() {
        this.id = ++contadorId;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}
