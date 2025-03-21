class Library {
    private List<Material> materials;
    private List<Loan> loans;
  
    public Library() {
        this.materials = new ArrayList<>();
        this.loans = new ArrayList<>();
        System.out.println("Library initialized.");
    }
  // Agregar material a la biblioteca
    public void addMaterial(Material material) {
        materials.add(material);
    }

    // Mostrar todos los materiales (disponibles o no)
    public void showMaterials() {
        for (Material material : materials) {
            System.out.println(material);
        }
    }
  // Manejar préstamos
  public boolean lendMaterial(Person person, Material material) {
        if (!material.isAvailable()) {
            System.out.println("Material not available: " + material);
            return false;
        }
        
        int maxItems = 0;
        int maxDays = 0;
        
        switch (person.getClientType()) {
            case "Estudiante":
                maxItems = 5;
                maxDays = 15;
                break;
            case "Profesor":
                maxItems = 10;
                maxDays = 30;
                break;
            case "Administrativo":
                maxItems = 3;
                maxDays = 10;
                break;
            case "Visitante":
                System.out.println("Visitantes solo pueden consultar en sala.");
                return false;
            default:
                System.out.println("Tipo de cliente no reconocido.");
                return false;
        }
        
        if (person.getActiveLoans().size() >= maxItems) {
            System.out.println("El usuario ha alcanzado el límite de préstamos.");
            return false;
        }
        
        Loan loan = new Loan(person, material, maxDays);
        loans.add(loan);
        person.addLoan(loan);
        material.setAvailable(false);
        System.out.println("Loan granted: " + loan);
        return true;
    }

    // Verificar préstamos vencidos
    public void checkOverdueLoans() {
        LocalDate today = LocalDate.now();
        for (Loan loan : loans) {
            if (loan.isOverdue(today)) {
                System.out.println("Overdue loan: " + loan);
            }
        }
    }
}

