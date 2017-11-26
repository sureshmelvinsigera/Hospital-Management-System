
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Diagnosis {

    private int diagnosis_id;
    private String diagnosis_description;
    private Date diagnosis_date;
    private int staff_id;
    private ArrayList<String> diagnosisList;

    Scanner reader;

    public Diagnosis() {
        reader = new Scanner(System.in);
        diagnosisList = new ArrayList();
    }

    public Diagnosis(String diagnosis_description, Date diagnosis_date, int staff_id) {
        this.diagnosis_description = diagnosis_description;
        this.diagnosis_date = diagnosis_date;
        this.staff_id = staff_id;

    }

    public void createTable() {

        Database.executeQuery("DROP TABLE IF EXISTS Diagnosis");

        Database.executeQuery("CREATE TABLE Diagnosis (diagnosis_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " diagnosis_description char,"
                + " diagnosis_date DATE,"
                + " staff_id INTEGER,"
                + " FOREIGN KEY(staff_id) REFERENCES Staff(staff_id))");
    }

    public void insert(String diagnosis_description, Date diagnosis_date, int staff_id) {

        Database.executeQuery("INSERT INTO Diagnosis (diagnosis_description,diagnosis_date,staff_id) values('" + diagnosis_description + "','" + diagnosis_date + "'," + staff_id + ");");

    }

    public void insert() {

        Database.executeQuery("INSERT INTO Diagnosis (diagnosis_description,diagnosis_date,staff_id)  values('" + this.diagnosis_description + "','" + this.diagnosis_date + "'," + this.staff_id + ");");
    }

    public int addDiagnosis(int staff_id) {

        try {

            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

            String tempDate;

            reader = new Scanner(System.in);
            System.out.println("Please Select the Diagnosis:");
            ResultSet r = Database.getResult("SELECT * FROM DiagnosisList;");
            while(r.next())
            {
                diagnosisList.add(r.getString("type"));
            }
            for(int i=0;i<diagnosisList.size();i++)
            {
                System.out.println("  "+(i+1)+". "+diagnosisList.get(i));
            }
            int selected = reader.nextInt();reader.nextLine();
            
            diagnosis_description = diagnosisList.get(selected-1);
            
            System.out.print("Please Enter the diagnosis date:");
            tempDate = reader.nextLine();
            diagnosis_date = dateFormatter.parse(tempDate);
            this.staff_id = staff_id;

            this.insert(diagnosis_description, diagnosis_date, staff_id);

            ResultSet result = Database.getResult("SELECT * FROM Diagnosis ORDER BY diagnosis_id DESC LIMIT 1;");

            result.next();
            return result.getInt(1);

        } catch (Exception ex) {

        }

        return -1;
    }
}
