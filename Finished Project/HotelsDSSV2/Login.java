package HotelsDSSV2;

import java.sql.*;
import java.io.*;
import org.sqlite.JDBC.*;
import java.util.Scanner;
import java.lang.ClassNotFoundException;

public class Login {

	public Login() {
	} // default constructor

	/*AUTHOR: Tigistu Anebo*/
	/*Contributors: Emanuel Mellblom*/
	/*Contributors: John Sundling*/
	public static Account User(String username, String password) throws SQLException, IOException {

		String Username = null;
		String Password = null;
		Main.connection = null;
		PreparedStatement preparedStatement = null;
		Scanner input = new Scanner(System.in);

		Statement st = null;
		Statement statement = null;
		try {
			Class.forName("org.sqlite.JDBC");
			Main.connection = DriverManager.getConnection(Main.dbAddress);
			statement = Main.connection.createStatement();

			ResultSet testing = statement.executeQuery(
					"SELECT * FROM Users WHERE Password == '" + password + "' AND Username == '" + username + "'");
			if (testing.next()) {
				return new Account(testing.getString("Username"), testing.getString("Password"),
						testing.getString("AccountType"), testing.getInt("UID"));
			} else {
				return new Account(null, null);

			}

		} catch (ClassNotFoundException error) {
			System.out.println("Error: " + error.getMessage());
			return new Account(null, null);
		} catch (SQLException error) {
			System.out.println("Error: " + error.getMessage());
			return new Account(null, null);
		} finally {
			if (Main.connection != null)
				try {
					Main.connection.close();
				} catch (SQLException ignore) {
				}
			if (preparedStatement != null)
				try {
					preparedStatement.close();
				} catch (SQLException ignore) {
				}
			try {
				statement.close();
			} catch (SQLException e) {
			}
		}

	}

}
