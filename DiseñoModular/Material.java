package DiseñoModular;

public class Material extends Service implements Loanable, Storable {
    private String description;
    
    public Material(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = true;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
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
        return "[MATERIAL] \"" + name + "\" - Description: " + description;
    }
}
