

	import java.sql.Connection;
	import java.sql.*;
	import java.io.*;
	import org.sqlite.JDBC.*;
	import java.util.Scanner;
	import java.lang.ClassNotFoundException;

	public class Login {

		public Login(){}	//default constructor



	public static void main(String[] args) throws SQLException, IOException{
		User("hans","1234");
		
	}
	public static boolean User(String user, String pass)
	throws SQLException, IOException{

			String Username = null;
			String Password = null;
				 // dbConn db=new dbConn();
			Connection conn=null;
			PreparedStatement preparedStatement = null;
			//conn=db.ConMe();
			Scanner input = new Scanner(System.in);

			Statement st=null;

			try{
				Class.forName("org.sqlite.JDBC");
				Connection connection = DriverManager.getConnection("jdbc:sqlite:/Users/Tg/Desktop/Login.sqlite");
				Statement statement = connection.createStatement();
				System.out.println("database connected from connect output");
				System.out.println("Enter 1 if you want to add a Register or 0 if you want to view added Register");
				//This code is for adding a new person
				while (true) {
					int test = input.nextInt();
			    if(test == 1) {
			    	System.out.println("Add UserID : ");
			    	int userid = input.nextInt ();
			    	System.out.println("Add a Last name: ");
			    	String lastname = input.next();
			    	System.out.println("Add a First name: ");
			    	String firstname = input.next();
			    	System.out.println("Add  Email: ");
			    	String email = input.next();
			    	System.out.println("Add a Passward: ");
			    	String passward = input.next();
			    	System.out.println("Registerd");
			    //This sends the strings to the prepared statement att the bottom.
			    	addRegister(connection, preparedStatement, userid ,firstname , lastname , email ,passward  );

			    	System.out.println(" ");
			    	System.out.println("Enter 1 if you want to add a Register or 0 if you want to view added Register");

			    }
			  //Output everything from the database
			    else if (test == 0) {
			    	ResultSet testing = statement.executeQuery("SELECT * FROM Register");
			    	while(testing.next()){
			    		System.out.println("The id is: " + testing.getInt("UserID"));
			    		System.out.println("The first name is: " + testing.getString("First Name"));
			    		System.out.println("The last name is: " + testing.getString("Last Name"));
			    		System.out.println("The email is: " + testing.getString("Email"));
			    		System.out.println("The passward is: " + testing.getString("Passward"));

			    	}
			    	//Break the loop
			    	break;
			    }


				}
					return true;
			
			   }
			catch(ClassNotFoundException error) {
				System.out.println("Error: " + error.getMessage());
				return false;
			   }
			catch(SQLException error) {
				System.out.println("Error: " + error.getMessage());
				return false;
			   }
			finally{
				if(conn !=null) try{conn.close();} catch(SQLException ignore) {}
				if(preparedStatement !=null) try{preparedStatement.close();} catch(SQLException ignore) {}
			}


			


		     }


	private static void addRegister(Connection connection, PreparedStatement preparedStatement, int userid ,String firstname, String lastname, String email,
			String passward)throws SQLException {
		 preparedStatement = connection.prepareStatement("INSERT INTO Register (UserID, First Name, Last Name, Email, Passward)"
			+ "VALUES (?, ?, ?, ?, ?)");
		preparedStatement.setInt(1,  userid);
		preparedStatement.setString(1,  firstname);
		preparedStatement.setString(2,  lastname);
		preparedStatement.setString(3,  email);
		preparedStatement.setString(4,  passward);
		preparedStatement.executeUpdate();


	}

	   }




	

