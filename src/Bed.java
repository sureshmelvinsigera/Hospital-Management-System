import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Bed {

    private int bed_id;
    private String bed_number;
    private int room_id;

    Scanner reader;

    public Bed() {
        reader = new Scanner(System.in);
    }

    public Bed(String bed_number, int room_id) {
        this.bed_number = bed_number;
        this.room_id = room_id;
    }

    public void createTable() {
        Database.executeQuery("DROP TABLE IF EXISTS Bed");
        Database.executeQuery("CREATE TABLE Bed (bed_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " bed_number STRING,"
                + " room_id INTEGER,"
                + " FOREIGN KEY(room_id) REFERENCES Room(room_id))");
    }

    public void insert(String bed_number, int room_id) {
        Database.executeQuery("INSERT INTO Bed values('" + bed_number + "'," + room_id + ");");
    }

    public void insert() {
        Database.executeQuery("INSERT INTO Bed values('" + this.bed_number + "'," + this.room_id + ");");
    }

    public int addBed() {
        return -1;
    }
}
