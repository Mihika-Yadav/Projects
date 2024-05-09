// This is the driver class of Hospital Management System

package hospitalManagementSystem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalSystem {
	private static final String url="jdbc:mysql://localhost:3306/hospital";
	private static final String username="root";
	private static final String password="mihika1920ydv";
	public static void main(String[] args) {
		// load mandate class to connect database
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		// use another try catch to establish connection
		try 
		{
			Scanner scanner=new Scanner(System.in);
			Connection connection=DriverManager.getConnection(url, username, password);
			Patients patient=new Patients(connection,scanner);
			Doctors doctor=new Doctors(connection);
			while(true)
			{
				System.out.println(" HOSPITAL MANAGEMENT SYSTEM ");
				System.out.println("1. Add Patients");
				System.out.println("2. View Patients");
				System.out.println("3. View Doctors");
				System.out.println("4. Book Appointment");
				System.out.println("5. Exit");
				System.out.println("Enter your Choice: ");
				int choice=scanner.nextInt();
				// switch case
				switch(choice)
				{
				case 1:
					//add patients
					patient.addPatients();
					System.out.println();
					break;
				case 2:
					// view patients
					patient.viewPatient();
					System.out.println();
					break;
				case 3:
			
		// view doctors
					doctor.viewDoctors();
					System.out.println();
					break;
				case 4:
					// book appointment
					bookAppointment(patient,doctor,connection,scanner);
					System.out.println();
					break;
				case 5:
					return;
				default:
					System.out.println("Enter valid choice!!! ");
				}



			}
		}

		catch(SQLException e)	
		{
			e.printStackTrace();	
		}

	}
	public static void bookAppointment(Patients patient,Doctors doctor,Connection connection,Scanner scanner)
	{
		System.out.print("Enter Patient Id: ");
		int patientId=scanner.nextInt();
		System.out.println("Enter Doctor Id: ");
		int doctorId=scanner.nextInt();
		System.out.println("Enter appointment date (YYYY-MM-DD)");
		String appointmentDates=scanner.next();
		if(patient.getPatientById(patientId) && doctor.getDoctorById(doctorId))
		{
			if(checkDoctorAvailability(doctorId,appointmentDates,connection))
			{
				String appointmentQuery="INSERT INTO appointments(patient_id,doctor_id,appointment_date) VALUES(?,?,?)";
				try
				{
					PreparedStatement preparedStatement=connection.prepareStatement(appointmentQuery);
					preparedStatement.setInt(1, patientId);
					preparedStatement.setInt(2, doctorId);
					preparedStatement.setString(3, appointmentDates);
					int rowsAffected=preparedStatement.executeUpdate();
					if(rowsAffected>0)
					{
						System.out.println("Appointment Booked");
					}
					else
					{
						System.out.println("Failed to book appointment! ");
					}
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				System.out.println("Doctor not available. ");
			}
		}
		else
		{
			System.out.println("Either patient or doctor doesn't exist!!");
		}
	}
	public static boolean checkDoctorAvailability(int doctorId,String appointmentDate,Connection connection)
	{
		String query="SELECT COUNT(*) FROM appointments where doctor_id=? and appointment_date=?";
		try
		{
			PreparedStatement preparedStatement=connection.prepareStatement(query);
			preparedStatement.setInt(1, doctorId);
			preparedStatement.setString(2,appointmentDate);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next())
			{
				int count=resultSet.getInt(1);
				if(count==0)
				{
					return true;
				}
				else
				{
					return false;
				}
			}

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;


	}
}