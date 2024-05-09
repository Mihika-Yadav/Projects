// Doctor class of hospital management system

package hospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Doctors {
	//use access modifier and encapsulation in this project
		private Connection connection;
		
		//create constructor
		public Doctors(Connection connection)
		{
			this.connection=connection;
			
		}
// we add doctors through the database, so don't use add doctors methods
		
		// view patient
		
		public void viewDoctors()
		{
			String query="select * from doctors";
			try
			{
				PreparedStatement preparedStatement=connection.prepareStatement(query);
				ResultSet resultSet=preparedStatement.executeQuery();
				System.out.println("Doctors: ");
				System.out.println("+------------+---------------+-------------------+");
				System.out.println("| Doctor ID  |      Name     |  Specialization   |");
				System.out.println("+------------+---------------+-------------------+");
				while(resultSet.next())
				{
					// right hand side are sql variables and left ones are java variables
					int id=resultSet.getInt("id");
					String name=resultSet.getString("name");
					String specialization=resultSet.getString("specialization");
					System.out.printf("|%-13s|%-14s|%-20s|\n",id,name,specialization);
					System.out.println("+------------+---------------+-------------------+");
				}
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		
		public boolean getDoctorById(int id)
		{
			String query="Select * from doctors where id=?";
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
