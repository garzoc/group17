package testingdb;
import java.sql.*;

public class testingDb1 {
	
	public static void main(String[] args) {
		
		Connection connection = null;
		
		try {
			
			//Specifi the database location after jdbc:sqlite: "here"
			connection = DriverManager.getConnection("jdbc:sqlite:/Users/System/Dropbox/1mac/Hotels.sqlite"); 
				Statement statement = connection.createStatement(); //Start the connection
					statement.setQueryTimeout(30);  // set timeout to 30 sec. //Set a timeout for the connection
		  

					
					/*
					 * Here we execute the sql statements
					 */
					
					
					//To insert a statement into the database we use the falloving command
					//statement.executeUpdate("insert into person values(1, 'leo')");

					
					//return a value from the database variablename =testing here
						ResultSet testing = statement.executeQuery("SELECT * FROM Hotel Name where Rating = 5;"); 
					
					//Here we can use the variable name to specify the exact information we will display
					//In this case we will print it to the console
					while(testing.next())
			          {
			            // read the result set
			            System.out.println("name = " + testing.getString("Name"));
			            System.out.println("id = " + testing.getInt("HID"));
			          }

		
		
		
		}
		
		//This part takes care of the error if the connection fails etc.
		
		  catch(SQLException e)
	        {
	          // if the error message is "out of memory", 
	          // it probably means no database file is found
	          System.err.println(e.getMessage());
	        }
	        finally
	        {
	          try
	          {
	            if(connection != null)
	              connection.close();
	          }
	          catch(SQLException e)
	          {
	            // connection close failed.
	            System.err.println(e);
   
   }
  }
 }
}
