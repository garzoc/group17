package HotelsDSSV2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Rating {
	
	static Connection connection = null;
	static PreparedStatement preparedStatement = null;
	
	public static double getRatings(int hotelId){
		
		double rating = 0.0;
		try{
		Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection(Main.dbAddress);
		Statement statement = connection.createStatement();

		ResultSet ratingavg = statement.executeQuery("SELECT avg(Rating) FROM Reviews where HID == '" +  hotelId + "'");

		double rate = (ratingavg.getDouble(1));
		
		rating = Math.round(rate*100)/100D;
		
	}
		
	//database error handling 
	catch(ClassNotFoundException err) {
		System.out.println("Error: " + err.getMessage());
	}
	catch(SQLException error) {
		System.out.println("Error: " + error.getMessage());
	}
	finally{
		if(connection !=null) try{connection.close();} catch(SQLException ignore) {}
		if(preparedStatement !=null) try{preparedStatement.close();} catch(SQLException ignore) {}
	}
		return rating;

}
}
