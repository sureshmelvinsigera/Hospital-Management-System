
public class Specialty {

    private int specialty_id;
    private String specialty_type;
    private String specialty_description;
    private int staff_id;

    public Specialty() {
    }

    public Specialty(String specialty_type, String specialty_description, int staff_id) {
        this.specialty_type = specialty_type;
        this.specialty_description = specialty_description;
        this.staff_id = staff_id;
    }

    public void createTable() {
        Database.executeQuery("DROP TABLE IF EXISTS Specialty");
        Database.executeQuery("CREATE TABLE Specialty (specialty_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " specialty_type STRING,"
                + " specialty_description STRING,"
                + " staff_id INTEGER,"
                + " FOREIGN KEY(staff_id) REFERENCES Staff(staff_id))");
    }

    public void insert(String specialty_type, String specialty_description, int staff_id) {
        Database.executeQuery("INSERT INTO Specialty values('" + specialty_type + "','" + specialty_description + "'," + staff_id + ");");
    }

    public void insert() {
        Database.executeQuery("INSERT INTO Specialty values('" + this.specialty_type + "','" + this.specialty_description + "'," + this.staff_id + ");");
    }
}