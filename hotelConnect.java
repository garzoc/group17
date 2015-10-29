package hotelDbConnect;

import java.sql.*;
import org.sqlite.JDBC.*;
import java.lang.ClassNotFoundException;
import java.util.Scanner;


/*
 * Sources
 * 1: https://www.youtube.com/watch?v=kBUsaOCtqUw&list=PL-lbf_8bVpgyLJQUFQcvj_tK1p8anPT_y&index=9
 * 2: daniel liang book
 * 3: https://docs.oracle.com/cd/B10501_01/java.920/a96654/basic.htm
 * 4: https://www3.ntu.edu.sg/home/ehchua/programming/java/JDBC_Basic.html
 * 5: http://www.mkyong.com/jdbc/jdbc-preparestatement-example-select-list-of-the-records/
 * 6: https://www.youtube.com/watch?v=l7IDevUUa3A
 * 7: http://www.developer.com/java/creating-a-jdbc-gui-application.html
 * 
 */



public class hotelConnect {
	
	public static void main(String[] args) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Scanner input = new Scanner(System.in);
		
		
		
		
		try{
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:/Users/System/Dropbox/1mac/Hotels.sqlite");
			Statement statement = connection.createStatement();
			System.out.println("Enter 1 if you want to add a Hotel or 0 if you want to view added Hotels");
			
			//This code is for adding a new person
				while (true) {
					int test = input.nextInt();	
			if(test == 1) {		
			System.out.println("Add a name: ");
			String name = input.next();
				System.out.println("Add an Rating: ");
				int rating = input.nextInt();
					System.out.println("Add a price: ");
					int price = input.nextInt();
						System.out.println("write true if you have a pool otherwise write false");
						boolean pool = input.nextBoolean();
			System.out.println("Add popularity from 1 to 5");
			int popularity = input.nextInt();
				System.out.println("write true if you have a Gym otherwise write false");
				boolean gym = input.nextBoolean();
					System.out.println("write true if you have a Bar otherwise write false");
					boolean bar = input.nextBoolean();
						System.out.println("write true if you allow pets otherwise write false");
						boolean pets = input.nextBoolean();
			
			//This sends the strings to the prepared statement att the bottom.
			addHotel(connection, preparedStatement, name, rating, price, pool, popularity, gym, bar, pets);
				
				System.out.println(" ");
				System.out.println("Enter 1 if you want to add a Hotel or 0 if you want to view added Hotels");
			
			}
			//Output everything from the database
			else if (test == 0) {
				ResultSet testing = statement.executeQuery("SELECT * FROM Hotel"); 
					while(testing.next()){
					System.out.println("The id is: " + testing.getInt("HID"));
						System.out.println("The name is: " + testing.getString("Name"));
							System.out.println("The Rating is: " + testing.getInt("Rating"));
								System.out.println("The Price is: " + testing.getInt("Price"));
									System.out.println("The Hotel has a pool: " + testing.getBoolean("Pool"));
						System.out.println("The popularity of the hotel forom 1 to 5 is: " + testing.getInt("Popularity"));
							System.out.println("The hotel has a Gym: " + testing.getBoolean("Gym"));
								System.out.println("The hotel har a Bar: " + testing.getBoolean("Bar"));
									System.out.println("The hotel allows pets: " + testing.getBoolean("Pets"));
										System.out.println(" ");
					}
					//Break the loop
				break;
			}
		}
				
			}
		catch(ClassNotFoundException error) {
			System.out.println("Error: " + error.getMessage());
			}
		catch(SQLException error) {
			System.out.println("Error: " + error.getMessage());
			}
		finally{
			if(connection !=null) try{connection.close();} catch(SQLException ignore) {}
			if(preparedStatement !=null) try{preparedStatement.close();} catch(SQLException ignore) {}
		
		}
	}
	
	public static void addHotel(Connection connection, PreparedStatement preparedStatement, String name, int rating, int price, boolean pool, int popularuty, boolean gym, boolean bar, boolean pets) throws SQLException {
		
		preparedStatement = connection.prepareStatement("INSERT INTO Hotel (Name, Rating, Price, Pool, Popularity, Gym, Bar, Pets)"
														+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
		preparedStatement.setString(1,  name);
		preparedStatement.setInt(2, rating);
		preparedStatement.setInt(3, price);
		preparedStatement.setBoolean(4, pool);
		preparedStatement.setInt(5, popularuty);
		preparedStatement.setBoolean(6, gym);
		preparedStatement.setBoolean(7, bar);
		preparedStatement.setBoolean(8, pets);
		preparedStatement.executeUpdate();
	}
}

