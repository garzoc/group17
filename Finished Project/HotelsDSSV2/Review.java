package HotelsDSSV2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Review {

	static Connection connection = null;
	static PreparedStatement preparedStatement = null;

	// date...
	private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	private Calendar cal = Calendar.getInstance();
	private String date = dateFormat.format(cal.getTime());

	// private final Account owner;
	private String text;
	private int rating;
	private int hotelId;
	private int userId;

	/* AUTHOR: Emanuel Mellblom */
	// Reviews Constructor
	public Review(int userId, String text, String date, int rating, int hotelId) {
		this.userId = userId;
		this.date = date;
		this.text = text.replace("\n", " ");
		this.rating = rating;
		this.hotelId = hotelId;
	}

	/* AUTHOR: Emanuel Mellblom */
	public String getDate() {
		return this.date;
	}

	/* AUTHOR: Emanuel Mellblom */
	public String getText() {
		return this.text;
	}

	/* AUTHOR: Emanuel Mellblom */
	public int getRating() {
		return this.rating;
	}

	/* AUTHOR: Emanuel Mellblom */
	public int getuserId() {
		return this.userId;
	}

	/* AUTHOR: Emanuel Mellblom */
	public int gethotelId() {
		return this.hotelId;
	}

	/* AUTHOR: Emanuel Mellblom */
	// Prepared statement for storing a review to database
	public static void prepareReview(Connection connection, PreparedStatement preparedStatement, int userId,
			String text, String date, double rating, int hotelId) throws SQLException {
		preparedStatement = connection
				.prepareStatement("INSERT INTO Reviews (UID, HID, Content, Date, Rating)" + "VALUES (?, ?, ?, ?, ?)");
		preparedStatement.setInt(1, userId); // name
		preparedStatement.setInt(2, hotelId); // rating
		preparedStatement.setString(3, text);
		preparedStatement.setString(4, date);
		preparedStatement.setDouble(5, rating);
		preparedStatement.executeUpdate();
	}

	/*AUTHOR: Emanuel Mellblom*/
	/*Contributors: John Sundling*/
	// method for storing the review
	public static void storeReview(Review review) { // Pass in values here

		Statement statement = null;
		try {
			// Setting up the connection to the database
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection(Main.dbAddress);
			statement = connection.createStatement();

			prepareReview(connection, preparedStatement, review.getuserId(), review.getText(), review.getDate(),
					review.getRating(), review.gethotelId());

		}
		// database error handling
		catch (ClassNotFoundException error) {
			System.out.println("Error: " + error.getMessage());
		} catch (SQLException error) {
			System.out.println("Error: " + error.getMessage());
		} finally {
			if (connection != null)
				try {
					connection.close();
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

			} catch (NullPointerException w) {

			}
		}
	}
}
