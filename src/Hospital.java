import java.util.Scanner;

public class Hospital {

    static Scanner reader;

    public Hospital() {
        reader = new Scanner(System.in);
    }

    public static void main(String[] args) {
        reader = new Scanner(System.in);
        Status status = new Status();
        //status.addStatus(1);

        int choice = 0;
        do {
            System.out.println("1. Login\n2. Exit");
            choice = reader.nextInt();
            if (choice == 1) {
                Staff staff = new Staff();
                if (staff.login()) 
                {
                    if (staff.getStaff_type().equals("Nurse")) {

                        Nurse nurse = new Nurse(staff);
                        nurse.showMenu();
                    } else {

                        Doctor doctor = new Doctor(staff);
                        doctor.showMenu();
                    }
                }
                else{

                    System.out.println("Invalid Login");
                }
            }
        } while (choice != 2);
    }
}
