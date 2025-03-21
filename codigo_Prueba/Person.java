// Clase Person
class Person {
    private String name;
    private String email;
    private String clientType;
    private List<Loan> previousLoans;

    public Person(String name, String email, String clientType, List<Loan> previousLoans) {
        this.name = name;
        this.email = email;
        this.clientType = clientType;
        this.previousLoans = previousLoans;
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "', email='" + email + "', clientType='" + clientType + "'}";
    }
}
