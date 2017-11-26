
import java.sql.ResultSet;
import java.util.Scanner;

public class Staff {
    private int staff_id;
    private String staff_password;
    private String staff_type;
    private String department_type;
    private int person_id;
    Scanner reader;
    
    private boolean login = false;

    public Staff() {
        reader = new Scanner(System.in);
    }

    public boolean login() {
        boolean validLogin = false;
        try {
            reader = new Scanner(System.in);
            System.out.print("Please Enter Your Staff ID : ");
            staff_id = reader.nextInt();
            System.out.print("Please Enter Your Password : ");
            staff_password = reader.next();
            ResultSet result = Database.getResult("Select * from Staff where staff_id=" + staff_id + " and staff_password='" + staff_password + "'");
            while (result.next()) {
                validLogin = true;
                staff_type = result.getString("staff_type");
                department_type = result.getString("department_type");
                person_id = result.getInt("person_id");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        login = true;
        return validLogin;
    }

    public Staff(String staff_password, String staff_type, String department_type, int person_id) {
        this.staff_password = staff_password;
        this.staff_type = staff_type;
        this.department_type = department_type;
        this.person_id = person_id;
    }

    public void createTable() {
        Database.executeQuery("DROP TABLE IF EXISTS Staff");
        Database.executeQuery("CREATE TABLE Staff (staff_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " staff_password STRING,"
                + " staff_type INTEGER,"
                + " department_type INTEGER,"
                + " person_id INTEGER,"
                + " FOREIGN KEY(person_id) REFERENCES Person(person_id))");
    }

    public void insert(String staff_password, int staff_type, int department_type, int person_id) {
        Database.executeQuery("INSERT INTO Staff values('" + staff_password + "'," + staff_type + "," + department_type + "," + person_id + ");");
    }

    public void insert() {
        Database.executeQuery("INSERT INTO Staff values('" + this.staff_password + "'," + this.staff_type + "," + this.department_type + "," + this.person_id + ");");
    }

    public String getStaff_type() {

        return staff_type;
    }

    public int getStaff_id() {
        return staff_id;
    }
    
    public void logout()
    {
        login = false;
    }
    
    public boolean loggedIn()
    {
        return login;
    }
}