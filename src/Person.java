
public class Person {

    private int person_id;
    private String first_name;
    private String last_name;
    private String ssn;
    private String address;
    private String email;
    private String telephone_number;

    public Person() {

    }

    public Person(String first_name, String last_name, String ssn, String address, String email, String telephone_number) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.ssn = ssn;
        this.address = address;
        this.email = email;
        this.telephone_number = telephone_number;
    }

    public void createTable() {

        Database.executeQuery("DROP TABLE IF EXISTS Person");

        Database.executeQuery("CREATE TABLE Person ("
                + " person_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " first_name STRING,"
                + " last_name STRING,"
                + " SSN STRING,"
                + " addresss STRING,"
                + " email STRING,"
                + " telephone_number STRING)");
    }

    public void insert(String first_name, String last_name, String ssn, String address, String email, String telephone_number) {
        Database.executeQuery("INSERT INTO Person values('" + first_name + "','" + last_name + "','" + ssn + "','" + address + "','" + email + "','" + telephone_number + "')");
    }

    public void insert() {
        Database.executeQuery("INSERT INTO Person values('" + this.first_name + "','" + this.last_name + "','" + this.ssn + "','" + this.address + "','" + this.email + "','" + this.telephone_number + "')");
    }
}
