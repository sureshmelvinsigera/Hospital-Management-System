
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Patient {

    private int patient_id;
    private String first_name;
    private String last_name;
    private String dob;
    private String ssn;
    private String address;
    private int diagnosis_id;
    private int treatment_id;
    private int status_id;
    private int staff_id;
    private int bed_id;
    private int doctor_id;
    private String staffName;
    private String doctorName;

    private Scanner reader;

    private Status statusObject;
    private Diagnosis diagnosisObject;
    private Treatment treatmentObject;

    public Patient() {
        statusObject = new Status();
        diagnosisObject = new Diagnosis();
        treatmentObject = new Treatment();

    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDiagnosis_id(int diagnosis_id) {
        this.diagnosis_id = diagnosis_id;
    }

    public void setTreatment_id(int treatment_id) {
        this.treatment_id = treatment_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public int getDoctor_id() {
		return doctor_id;
	}

	public void setDoctor_id(int doctor_id) {
		this.doctor_id = doctor_id;
	}

	public int getDiagnosis_id() {
		return diagnosis_id;
	}

	public int getStaff_id() {
		return staff_id;
	}

	public void setBed_id(int bed_id) {
        this.bed_id = bed_id;
    }

    public void setReader(Scanner reader) {
        this.reader = reader;
    }

    public void setStatusObject(Status statusObject) {
        this.statusObject = statusObject;
    }

    public void setDiagnosisObject(Diagnosis diagnosisObject) {
        this.diagnosisObject = diagnosisObject;
    }

    public void setTreatmentObject(Treatment treatmentObject) {
        this.treatmentObject = treatmentObject;
    }
    
    public void setDoctorID(int id) {
    	doctor_id = id;
    }
    
    public int getDoctorID() {
    	return doctor_id;
    }

    public String getDiagnosis() {
    	String d = "";
		try {
			ResultSet result = Database.getResult("SELECT diagnosis_description FROM Diagnosis WHERE diagnosis_id=" + diagnosis_id);
			if (!result.isClosed()) {
				while (result.next()) {
					d = result.getString(1);
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return d;
	}


	public String getTreatment() {
		String d = "";
		try {
			ResultSet result = Database.getResult("SELECT treatment_type FROM Treatment WHERE treatment_id=" + treatment_id);
			if (!result.isClosed()) {
				while (result.next()) {
					d = result.getString(1);
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return d;
	}

	public String getStatus()
	{
		String d = "";
		try {
			ResultSet result = Database.getResult("SELECT status_code FROM Status WHERE status_id=" + status_id);
			if (!result.isClosed()) {
				while (result.next()) {
					d = result.getString(1);
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return d;
	}

	public String getStaffName() {
		String name = "";
		try {
			ResultSet result = Database.getResult("SELECT first_name, last_name FROM Staff INNER JOIN Person ON\r\n"
					+ "Person.person_id = " + staff_id);
			if (!result.isClosed()) {
				while (result.next()) {
					name = result.getString(1) + " ";
					name += result.getString(2);
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return name;
	}

	
	public String getDoctorName()
	{
		String name = "";
		try {
			ResultSet result = Database.getResult("SELECT first_name, last_name FROM Staff INNER JOIN Person ON\r\n"
					+ "Person.person_id = " + doctor_id);
			if (!result.isClosed()) {
				while (result.next()) {
					name = result.getString(1) + " ";
					name += result.getString(2);
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return name;
	}
    public Patient(String first_name, String last_name, String dob, String ssn, String address, int diagnosis_id, int treatment_id, int status_id, int staff_id, int bed_id) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.dob = dob;
        this.ssn = ssn;
        this.address = address;
        this.diagnosis_id = diagnosis_id;
        this.treatment_id = treatment_id;
        this.status_id = status_id;
        this.staff_id = staff_id;
        this.bed_id = bed_id;

        statusObject = new Status();
    }

    public void addPatient() {

        try {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
            String tempDate;

            reader = new Scanner(System.in);
            System.out.print("Please Enter the patient First name:");
            first_name = reader.nextLine();
            System.out.print("Please Enter the patient Last name:");
            last_name = reader.nextLine();
            System.out.println("Please Enter the patient Date of Birth:");
            tempDate = reader.nextLine();
            dob = tempDate;

            System.out.print("Please Enter the patient Social security number:");
            ssn = reader.nextLine();
            System.out.print("Please Enter the patient Address:");
            address = reader.nextLine();
            System.out.print("Please Enter Doctor ID:");
            doctor_id = reader.nextInt();
            System.out.print("What is the patient status:");

            status_id = statusObject.addStatus(staff_id);
            System.out.println("Add Diagnosis:");

            diagnosis_id = diagnosisObject.addDiagnosis(staff_id);
            System.out.println("Add the required treatment:");

            treatment_id = treatmentObject.addTreatment(staff_id);

            Bed bed = new Bed();

            bed_id = (int) (Math.random() * 17) + 1;
            this.insert();

            System.out.println("\nPatient Successfully Added!!\n");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public void createTable() {

        Database.executeQuery("DROP TABLE IF EXISTS Patient");

        Database.executeQuery("CREATE TABLE Patient ("
                + " patient_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " first_name STRING,"
                + " last_name STRING,"
                + " dob STRING,"
                + " SSN STRING,"
                + " address STRING,"
                + " diagnosis_id INTEGER,"
                + " treatment_id INTEGER,"
                + " status_id INTEGER,"
                + " staff_id INTEGER,"
                + " bed_id INTEGER,"
                + " doctor_id INTEGER)");
    }
    

    public void insert(String first_name, String last_name, Date dob, String ssn, String address, int diagnosis_id, int treatment_id, int status_id, int staff_id, int bed_id, int doctor_id) {

        Database.executeQuery("INSERT INTO Patient (first_name, last_name, dob, SSN, address, diagnosis_id, treatment_id, status_id, staff_id, bed_id, doctor_id) values('" + first_name + "','" + last_name + "','" + dob + "','" + ssn + "','" + address + "'," + diagnosis_id + "," + treatment_id + "," + status_id + "," + staff_id + "," + bed_id + "," + doctor_id+")");
    }

    public void insert() {

        Database.executeQuery("INSERT INTO Patient (first_name, last_name, dob, SSN, address, diagnosis_id, treatment_id, status_id, staff_id, bed_id, doctor_id) values('" + this.first_name + "','" + this.last_name + "','" + this.dob + "','" + this.ssn + "','" + this.address + "'," + this.diagnosis_id + "," + this.treatment_id + "," + this.status_id + "," + this.staff_id + "," + this.bed_id + ","+ doctor_id+")");

    }

    public String toString()
    {
        return String.format("%-3d", patient_id)+
               String.format("%-15s", first_name)+
               String.format("%-15s", last_name)+
               String.format("%-12s", dob)+
               String.format("%-16s", ssn)+
               String.format("%-32s", address)+
               String.format("%-18s", getDiagnosis())+
               String.format("%-17s", getTreatment())+
               String.format("%-6s", getStatus())+
               String.format("%-16s", getStaffName())+
               String.format("%-9s", bed_id)+
               String.format("%-15s", getDoctorName());
    }
}
