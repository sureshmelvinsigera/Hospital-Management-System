
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Doctor {

    public static Scanner reader;
    private static int userChose;
    private Staff staff;

    private boolean login = false;

    private ArrayList<Patient> patients;

    public Doctor() {
        login = true;
        patients = new ArrayList<Patient>();
    }

    public Doctor(Staff objStaff) {
        staff = objStaff;
        login = true;
        patients = new ArrayList<Patient>();
    }

    public void showMenu() {
        while (login) {
            reader = new Scanner(System.in);
            System.out.println("----------------------------------------------");
            System.out.println("-Please Choose one of the options:            -");
            System.out.println("-1. List My Patient.                          -");
            System.out.println("-2. Search For A Patient                      -");
            System.out.println("-3. Log Out.                                  -");
            System.out.println("----------------------------------------------");
            userChose = reader.nextInt();
            reader.nextLine();
            switch (userChose) {
                case 1:
                    listMypatients();
                    break;
                case 2:
                    search();
                    break;
                case 3:
                    logout();
                    break;
            }
        }
    }

    public void logout() {
        login = false;
    }

    private void listMypatients() {
        try {
            patients.clear();
            ResultSet result = Database.getResult("SELECT * FROM Patient WHERE doctor_id=" + staff.getStaff_id() + ";");
            while (result.next()) {
                Patient p = new Patient();
                p.setPatient_id(result.getInt(1));
                p.setFirst_name(result.getString(2));
                p.setLast_name(result.getString(3));
                p.setDob(result.getString(4));
                p.setSsn(result.getString(5));
                p.setAddress(result.getString(6));
                p.setDiagnosis_id(result.getInt(7));
                p.setTreatment_id(result.getInt(8));
                p.setStatus_id(result.getInt(9));
                p.setStaff_id(result.getInt(10));
                p.setBed_id(result.getInt(11));
                p.setDoctorID(result.getInt(12));

                patients.add(p);
            }

            System.out.println();
            System.out.println(String.format("%-3s", "ID") +
                    String.format("%-15s", "FirstName") +
                    String.format("%-15s", "Last Name") +
                    String.format("%-12s", "DOB") +
                    String.format("%-16s", "SSN") +
                    String.format("%-32s", "Address") +
                    String.format("%-18s", "Diagnosis") +
                    String.format("%-15s", "Treatment") +
                    String.format("%-8s", "Status") +
                    String.format("%-15s", "Staff") +
                    String.format("%-10s", "Bed ID") +
                    String.format("%-10s", "Doctor Name"));
            for (int i = 0; i < patients.size(); i++) {
                System.out.println(patients.get(i));
            }
            System.out.println();

            System.out.print("Do you want to edit details? (y/n): ");
            char ch = reader.next().toLowerCase().charAt(0);
            if (ch == 'y') {
                System.out.print("Please Select Patient ID: ");
                int pID = reader.nextInt();
                System.out.print("Enter First Name: ");
                String fname = reader.next();
                System.out.print("Enter Last Name: ");
                String lname = reader.next();
                System.out.print("Enter Date of Birth: ");
                String dob = reader.next();
                reader.nextLine();
                System.out.print("Enter Address: ");
                String address = reader.nextLine();
                System.out.print("Enter SSN: ");
                String ssn = reader.next();

                String query = "UPDATE Patient SET first_name='" + fname + "', last_name='"
                        + lname + "',dob='" + dob + "', address='" + address + "', ssn='" + ssn
                        + "' WHERE patient_id=" + pID;
                Database.executeQuery(query);

                System.out.println("\nRecord Updated\n");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void search() {
        System.out.println("Search By:");
        System.out.println("   1. Patient ID");
        System.out.println("   2. First Name");
        System.out.println("   3. Last Name");
        System.out.println("   4. Bed Number");
        System.out.println("   5. SSN");
        System.out.println("   6. Nurse");
        int choice = reader.nextInt();
        String query = "";
        switch (choice) {
            case 1:
                System.out.print("Enter Patient ID: ");
                int pID = reader.nextInt();
                query = "SELECT * FROM Patient WHERE patient_id=" + pID + " AND doctor_id=" + staff.getStaff_id() + ";";
                break;
            case 2:
                System.out.print("Enter First Name: ");
                String fname = reader.nextLine();
                query = "SELECT * FROM Patient WHERE first_name=\"" + fname + "\" AND doctor_id=" + staff.getStaff_id() + ";";
                break;
            case 3:
                System.out.print("Enter Last Name: ");
                String lname = reader.nextLine();
                query = "SELECT * FROM Patient WHERE last_name=\"" + lname + "\" AND doctor_id=" + staff.getStaff_id() + ";";
                break;
            case 4:
                System.out.print("Enter Bed Number: ");
                int bNo = reader.nextInt();
                reader.nextLine();
                query = "SELECT patient_id, first_name, last_name, dob, ssn, "
                        + "address, diagnosis_id, treatment_id, status_id, "
                        + "staff_id, Patient.bed_id FROM Bed INNER JOIN Patient ON "
                        + "Bed.bed_id = Patient.patient_id WHERE Bed.bed_number=" + bNo + ";";
                break;
            case 5:
                System.out.print("Enter SSN: ");
                String ssn = reader.nextLine();
                query = "SELECT * FROM Patient WHERE ssn=\"" + ssn + "\" AND doctor_id=" + staff.getStaff_id() + ";";
                break;
            case 6:
                System.out.print("Enter Nurse ID: ");
                int dID = reader.nextInt();
                reader.nextLine();
                query = "SELECT * FROM Patient WHERE staff_id=" + dID + " AND doctor_id=" + staff.getStaff_id() + ";";
                break;

        }

        try {
            ResultSet result = Database.getResult(query);
            if (!result.isClosed()) {
                System.out.println();
                System.out.println(String.format("%-3s", "ID") +
                        String.format("%-15s", "FirstName") +
                        String.format("%-15s", "Last Name") +
                        String.format("%-12s", "DOB") +
                        String.format("%-16s", "SSN") +
                        String.format("%-32s", "Address") +
                        String.format("%-18s", "Diagnosis") +
                        String.format("%-15s", "Treatment") +
                        String.format("%-8s", "Status") +
                        String.format("%-15s", "Staff") +
                        String.format("%-10s", "Bed ID") +
                        String.format("%-10s", "Doctor Name"));
                while (result.next()) {
                    Patient p = new Patient();
                    p.setPatient_id(result.getInt(1));
                    p.setFirst_name(result.getString(2));
                    p.setLast_name(result.getString(3));
                    p.setDob(result.getString(4));
                    p.setSsn(result.getString(5));
                    p.setAddress(result.getString(6));
                    p.setDiagnosis_id(result.getInt(7));
                    p.setTreatment_id(result.getInt(8));
                    p.setStatus_id(result.getInt(9));
                    p.setStaff_id(result.getInt(10));
                    p.setBed_id(result.getInt(11));
                    p.setDoctorID(result.getInt(12));

                    System.out.println(p);
                }
            } else {
                System.out.println("\nNo Record Found.\n");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
