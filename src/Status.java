import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Status {

	private int status_id;
	private char status_code;
	private Date date_admitted;
	private Date date_discharged;
	private int staff_id;
	private Scanner reader;
	
	public Status(){
 		reader=new Scanner(System.in);
 		
	}
	public Status(  char status_code,Date date_admitted, Date date_discharged,int staff_id)
	{
 		this.status_code=status_code;
		this.date_admitted=date_admitted;
		this.date_discharged=date_discharged;
		this.staff_id=staff_id;
		 
 	}
 
	public void createTable(){
		
		Database.executeQuery("DROP TABLE IF EXISTS Status");

		
		Database.executeQuery("CREATE TABLE Status (status_id INTEGER PRIMARY KEY AUTOINCREMENT,"
         		+ " status_code char,"
         		+ " date_admitted DATE,"
         		+ " date_discharged DATE,"
         		+ " staff_id INTEGER,"
         		+ " FOREIGN KEY(staff_id) REFERENCES Staff(staff_id))" );
	}
	
	public void insert(  char status_code,Date date_admitted, Date date_discharged,int staff_id ){
	
		Database.executeQuery("INSERT INTO Status(status_code,date_admitted,date_discharged,staff_id) values('"+status_code+"','"+date_admitted+"','"+date_discharged+"',"+staff_id+");" );
			
	}
	
	public void insert(){
		
		Database.executeQuery("INSERT INTO Status(status_code,date_admitted,date_discharged,staff_id) values('"+status_code+"','"+date_admitted+"','"+date_discharged+"',"+staff_id+");" );
	}
	
	public int addStatus(int staff_id){
		
		try{
			
		
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

        String tempDate;

		reader = new Scanner(System.in);
		System.out.print("Please Enter the status (E/O/I):");
		status_code = reader.nextLine().charAt(0);
		System.out.print("Please Enter the date admitted:");
		tempDate=reader.nextLine();
		date_admitted= dateFormatter.parse(tempDate);
		this.staff_id=staff_id;
 		
		this.insert(status_code, date_admitted, date_discharged, staff_id);


		ResultSet result= Database.getResult("SELECT * FROM Status ORDER BY status_id DESC LIMIT 1;");
		
		result.next();
		return result.getInt(1);
		
		
		}
		catch(Exception ex){
			
		}
		
		return -1;
	}
	
	
}
