package application;
import java.sql.*;
import java.io.*;
import org.sqlite.JDBC.*;
import java.util.Scanner;
import java.lang.ClassNotFoundException;

public class Login {

	public Login(){}	//default constructor





public static boolean User(String user, String pass)
throws SQLException, IOException{

		String Username = null;
		String Password = null;
			 // dbConn db=new dbConn();
		Connection conn=null;
		PreparedStatement preparedStatement = null;
		//conn=db.ConMe();
		Scanner input = new Scanner(System.in);

		Statement st=conn.createStatement();

		try{
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/Users/System/Dropbox/1mac/Hotel.sqlite");
			Statement statement = connection.createStatement();
			System.out.println("database connected from connect output");

		   }
		catch(ClassNotFoundException error) {
			System.out.println("Error: " + error.getMessage());
		   }
		catch(SQLException error) {
			System.out.println("Error: " + error.getMessage());
		   }
		finally{
			if(conn !=null) try{conn.close();} catch(SQLException ignore) {}
			if(preparedStatement !=null) try{preparedStatement.close();} catch(SQLException ignore) {}
		}



String sql = "SELECT firstname, password FROM Register" + "WHERE firstname='" + user + "'" + "AND password='" + pass + "'";
ResultSet rs = st.executeQuery(sql);
if(rs.next()){
Username = rs.getString("name");
Password = rs.getString("password");
}
if(Username != null && Password != null && user.equals(Username) && pass.equals(Password)){

return true;
}
else{
return false;
}


	     }

   }



