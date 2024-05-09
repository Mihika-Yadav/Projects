// Program to create the Patients class and establish the connection b/w mysql

package hospitalManagementSystem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patients {
	//use access modifier and encapsulation in this project
	private Connection connection;
	private Scanner scanner;
	
	//create constructor
	public Patients(Connection connection,Scanner scanner)
	{
		this.connection=connection;
		this.scanner=scanner;
	}
	
	public void addPatients()
	{
		System.out.print("Enter Patient name: ");
		String name=scanner.next();
		System.out.print("Enter Patient Age: ");
		int age=scanner.nextInt();
		System.out.print("Enter Patient Gender: ");
		String gender=scanner.next();
		
		// use try catch to get the exception of SQL whenever we connect it to database
		try
		{
			String query="INSERT INTO patients(name,age,gender) VALUES (?, ? , ?)";
			PreparedStatement preparedStatement=connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, age);
			preparedStatement.setString(3, gender);
			
			int affectedRows=preparedStatement.executeUpdate();
			
			if(affectedRows>0)
			{
				System.out.println("Patient added successfully!! ");
			}
			else
			{
				System.out.println("Failed to add patient!! ");
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	// view patient
	
	public void viewPatient()
	{
		String query="select * from patients";
		try
		{
			PreparedStatement preparedStatement=connection.prepareStatement(query);
			ResultSet resultSet=preparedStatement.executeQuery();
			System.out.println("Patients: ");
			System.out.println("+-------------+---------------+-------+------------+");
			System.out.println("| Patient ID  | Name          | Age   | Gender     |");
			System.out.println("+-------------+---------------+-------+------------+");
			while(resultSet.next())
			{
				// right hand side are sql variables and left ones are java variables
				int id=resultSet.getInt("id");
				String name=resultSet.getString("name");
				int age=resultSet.getInt("age");
				String gender=resultSet.getString("gender");
				System.out.printf("|% -12s |% -14s |%-8s|%-13s |\n",id,name,age,gender);
				System.out.println("+-------------+---------------+-------+------------+");
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean getPatientById(int id)
	{
		String query="Select * from patients where id=?";
		try 
		{
			PreparedStatement preparedStatement=connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultset=preparedStatement.executeQuery();
			if(resultset.next())
			{
				return true;
			}
			else
			{
				return false;
			}
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}

}
