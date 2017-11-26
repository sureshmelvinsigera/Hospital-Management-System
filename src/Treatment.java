
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Treatment {

    private int treatment_id;
    private String treatment_type;
    private String treatment_description;
    private Date treatment_date;
    private int staff_id;
    Scanner reader;

    public Treatment() {
        reader = new Scanner(System.in);
    }

    public Treatment(String treatment_type, String treatment_description, Date treatment_date, int staff_id) {
        this.treatment_type = treatment_type;
        this.treatment_description = treatment_description;
        this.treatment_date = treatment_date;
        this.staff_id = staff_id;
    }

    public void createTable() {
        Database.executeQuery("DROP TABLE IF EXISTS Treatment");
        Database.executeQuery("CREATE TABLE Treatment (treatment_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " treatment_type STRING,"
                + " treatment_description STRING,"
                + " treatment_date DATE,"
                + " staff_id INTEGER,"
                + " FOREIGN KEY(staff_id) REFERENCES Staff(staff_id))");
    }

    public void insert(String treatment_type, String treatment_description, Date treatment_date, int staff_id) {
        Database.executeQuery("INSERT INTO Treatment (treatment_type,treatment_description,treatment_date,staff_id) values('" + treatment_type + "','" + treatment_description + "','" + treatment_date + "'," + staff_id + ");");
    }

    public void insert() {
        Database.executeQuery("INSERT INTO Treatment (treatment_type,treatment_description,treatment_date,staff_id) values('" + treatment_type + "','" + treatment_description + "','" + treatment_date + "'," + staff_id + ");");
    }

    public int addTreatment(int staff_id) {
        try {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
            String tempDate;
            reader = new Scanner(System.in);
            System.out.print("Please Enter the Treatment type:");
            treatment_type = reader.nextLine();
            System.out.print("Please Enter the Treatment description:");
            treatment_description = reader.nextLine();
            System.out.print("Please Enter the Treatment date:");
            tempDate = reader.nextLine();
            treatment_date = dateFormatter.parse(tempDate);
            this.staff_id = staff_id;
            this.insert(treatment_type, treatment_description, treatment_date, staff_id);
            ResultSet result = Database.getResult("SELECT * FROM Treatment ORDER BY treatment_id DESC LIMIT 1;");
            result.next();
            return result.getInt(1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }
}