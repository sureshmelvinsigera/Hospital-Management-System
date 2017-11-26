
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Nurse {

    public static Scanner reader;
    private static int userChose;
    private Staff staff;
    
    private ArrayList<Patient> patients;

    private boolean login = false;

    public Nurse() {
        reader = new Scanner(System.in);
    }

    public Nurse(Staff objStaff) {
        reader = new Scanner(System.in);
        staff = objStaff;
        login = true;
        patients = new ArrayList<>();
    }

    public void showMenu() {

        while (login) {
            System.out.println("----------------------------------------------");
            System.out.println("-Please Choose one of the three options:     -");
            System.out.println("-1. Add New Patient.                         -");
            System.out.println("-2. List My Patient.                         -");
            System.out.println("-3. Search For A Patient                     -");
            System.out.println("-4. Log Out.                                 -");
            System.out.println("----------------------------------------------");
            userChose = reader.nextInt();reader.nextLine();
            switch (userChose) {
                case 1:
                    addPatient();
                    break;
                case 2:
                    listMypatients();
                    break;
                case 3:
                    search();
                    break;
                case 4:
                    logout();
                    break;
            }
        }
    }

    public void addPatient() {

        Patient patient = new Patient();
        patient.setStaff_id(staff.getStaff_id());
        patient.addPatient();
    }

    public void listMypatients() {
        try{
            patients.clear();
            ResultSet result = Database.getResult("SELECT * FROM Patient where staff_id="+staff.getStaff_id()+";");
            while(result.next())
            {
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
            System.out.println(String.format("%-3s", "ID")+
                   String.format("%-15s", "FirstName")+
                   String.format("%-15s", "Last Name")+
                   String.format("%-12s", "DOB")+
                   String.format("%-16s", "SSN")+
                   String.format("%-42s", "Address")+
                   String.format("%-18s", "Diagnosis")+
                   String.format("%-15s", "Treatment")+
                   String.format("%-8s", "Status")+
                   String.format("%-15s", "Staff")+
                   String.format("%-10s", "Bed ID")+
                   String.format("%-10s", "Doctor Name"));
            for(int i=0;i<patients.size();i++)
            {
                System.out.println(patients.get(i));
            }
            System.out.println();

            System.out.print("Do you want to edit details? (y/n): ");
            char ch = reader.nextLine().toLowerCase().charAt(0);
            if(ch == 'y')
            {
                System.out.print("Please Select Patient ID: ");
                int pID = reader.nextInt();reader.nextLine();
                System.out.print("Enter First Name: ");
                String fname = reader.nextLine();
                System.out.print("Enter Last Name: ");
                String lname = reader.nextLine();
                System.out.print("Enter Date of Birth: ");
                String dob = reader.next();reader.nextLine();
                System.out.print("Enter Address: ");
                String address = reader.nextLine();
                System.out.print("Enter SSN: ");
                String ssn = reader.nextLine();
                System.out.print("Enter Doctor ID: ");
                String dID = reader.next();reader.nextLine();

                String query = "UPDATE Patient SET first_name='"+fname+"', last_name='"
                        +lname+"',dob='"+dob+"', address='"+address+"', ssn='"+ssn
                        +"', doctor_id="+dID+" WHERE patient_id="+pID;
                Database.executeQuery(query);

                System.out.println("\nRecord Updated\n");
            }
        }catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        
    }

    public void search() {
        System.out.println("Search By:");
        System.out.println("   1. Patient ID");
        System.out.println("   2. First Name");
        System.out.println("   3. Last Name");
        System.out.println("   4. Bed Number");
        System.out.println("   5. SSN");
        System.out.println("   6. Doctor");
        int choice = reader.nextInt();reader.nextLine();
        String query = "";
        switch(choice)
        {
            case 1:
                System.out.print("Enter Patient ID: ");
                int pID = reader.nextInt();reader.nextLine();
                query = "SELECT * FROM Patient WHERE staff_id="+staff.getStaff_id()+" AND patient_id="+pID+";";
                break;
            case 2:
                System.out.print("Enter First Name: ");
                String fname = reader.nextLine();
                query = "SELECT * FROM Patient WHERE staff_id="+staff.getStaff_id()+" AND first_name=\""+fname+"\";";
                break;
            case 3:
                System.out.print("Enter Last Name: ");
                String lname = reader.nextLine();
                query = "SELECT * FROM Patient WHERE staff_id="+staff.getStaff_id()+" AND last_name=\""+lname+"\";";
                break;
            case 4:
                System.out.print("Enter Bed Number: ");
                int bNo = reader.nextInt();reader.nextLine();
                query = "SELECT patient_id, first_name, last_name, dob, ssn, "
                        + "address, diagnosis_id, treatment_id, status_id, "
                        + "staff_id, Patient.bed_id FROM Bed INNER JOIN Patient ON "
                        + "Bed.bed_id = Patient.patient_id WHERE staff_id="+staff.getStaff_id()
                        +" AND Bed.bed_number="+bNo+";";
                break;
            case 5:
                System.out.print("Enter SSN: ");
                String ssn = reader.nextLine();
                query = "SELECT * FROM Patient WHERE staff_id="+staff.getStaff_id()+" AND ssn=\""+ssn+"\";";
                break;
            case 6:
                System.out.print("Enter Doctor ID: ");
                int dID = reader.nextInt();reader.nextLine();
                query = "SELECT * FROM Patient WHERE doctor_id="+dID+";";
                break;
            
        }
        
        try{
        ResultSet result = Database.getResult(query);
        if(!result.isClosed())
        {
            System.out.println();
            System.out.println(String.format("%-3s", "ID")+
                   String.format("%-15s", "FirstName")+
                   String.format("%-15s", "Last Name")+
                   String.format("%-12s", "DOB")+
                   String.format("%-16s", "SSN")+
                   String.format("%-32s", "Address")+
                   String.format("%-18s", "Diagnosis")+
                   String.format("%-15s", "Treatment")+
                   String.format("%-8s", "Status")+
                   String.format("%-15s", "Staff")+
                   String.format("%-10s", "Bed ID")+
                   String.format("%-10s", "Doctor Name"));
            while(result.next())
            {
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
        }
        else
        {
            System.out.println("\nNo Record Found.\n");
        }
        }catch(SQLException ex)
        {
            ex.printStackTrace();
        }

    }

    public void logout() {
        login = false;
    }
}