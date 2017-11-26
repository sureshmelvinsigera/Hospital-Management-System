import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    static Connection connection = null;
    static Statement statement = null;
    static boolean connected = false;

    public Database() {

    }

    private static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:hospital_management_system.db");

            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            connected = true;
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static void executeQuery(String query) {
        try {
            if (!connected) {
                connect();
            }
            
            statement.executeUpdate(query);
            
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public static void initialize() {
        Person person = new Person();
        Bed bed = new Bed();
        Diagnosis diagnosis = new Diagnosis();
        Room room = new Room();
        Specialty speciality = new Specialty();
        Staff staff = new Staff();
        Status status = new Status();
        Treatment treamtment = new Treatment();
        Patient patient = new Patient();

        person.createTable();
        bed.createTable();
        diagnosis.createTable();
        room.createTable();
        speciality.createTable();
        staff.createTable();
        status.createTable();
        treamtment.createTable();
        patient.createTable();
    }

    public static ResultSet getResult(String query) {
        try {
            if (!connected) {
                connect();
            }
            ResultSet resultSet = statement.executeQuery(query);


            return resultSet;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    //public static void main(String[] args) throws ClassNotFoundException 
    {
        
    }
}