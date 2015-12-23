package HotelsDSSV2;

import java.nio.file.Path;
import java.sql.*;

public class Database {
	/*AUTHOR: John Sundling*/
	public Database() {
		Statement statement = null;
		Connection connection = null;

		try {
			Class.forName("org.sqlite.JDBC");

			connection = DriverManager
					.getConnection("jdbc:sqlite:" + Properties.getProperty("databasePath") + "/Hotels.sqlite");
			statement = connection.createStatement();
			String sql = " CREATE TABLE Hotel(" + "HID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE,"
					+ "Name TEXT," + "Address TEXT," + "Price DOUBLE," + "Stars INTEGER," + "Pool BOOL," + "Gym BOOL,"
					+ "Bar BOOL," + "Pets BOOL," + "Img STRING," + "UID INTEGER NOT NULL UNIQUE" + ");"
					+ "CREATE TABLE Users(" + "UID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE,"
					+ "Username STRING NOT NULL UNIQUE," + "Password STRING NOT NULL," + "AccountType STRING NOT NULL,"
					+ "Email STRING UNIQUE" + ");" + "CREATE TABLE Reviews("
					+ "rowid INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE," + "Content TEXT NOT NULL,"
					+ "Date DATETIME NOT NULL," + "UID INTEGER REFERENCES Users (UID) NOT NULL,"
					+ "HID INTEGER REFERENCES Hotel (HID) NOT NULL" + ");";

			statement.executeUpdate(sql);

		} catch (ClassNotFoundException error) {
		} catch (SQLException error) {
		} finally {
			if (connection != null) {
				try {
					connection.close();
					statement.close();
				} catch (SQLException ignore) {

				}

			}
		}

	}
}