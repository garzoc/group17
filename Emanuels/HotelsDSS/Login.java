package HotelsDSS;

	import java.sql.Connection;
	import java.sql.*;
	import java.io.*;
	import org.sqlite.JDBC.*;
	import java.util.Scanner;
	import java.lang.ClassNotFoundException;

	public class Login {

		public Login(){}	//default constructor




	public static Account User(String username, String password)
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
				Connection connection = DriverManager.getConnection("jdbc:sqlite:/Users/System/Dropbox/1mac/Hotels.sqlite");
				Statement statement = connection.createStatement();


			    	ResultSet testing = statement.executeQuery("SELECT * FROM Users WHERE Password == '" + password + "' AND Username == '" + username + "'" );
			    	//System.out.println(username + " " + password);
			    	if(testing.next()){
			    		//System.out.println("loggin");
			    		return new Account(testing.getString("Username"), testing.getString("Password"), testing.getString("AccountType"), testing.getInt("UID") );
			    	}else{
			    		return new Account(null, null);
			    		
			    	}

			
			   }
			catch(ClassNotFoundException error) {
				System.out.println("Error: " + error.getMessage());
				return new Account(null, null);
			   }
			catch(SQLException error) {
				System.out.println("Error: " + error.getMessage());
				return new Account(null, null);
			   }
			finally{
				if(conn !=null) try{conn.close();} catch(SQLException ignore) {}
				if(preparedStatement !=null) try{preparedStatement.close();} catch(SQLException ignore) {}
			}


			


		     }



	   }




	

