package HotelsDSSV2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.Scanner;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CreateAccount {
	/* AUTHOR: Tigistu Anebo */
	/* Contributors: Emanuel Mellblom */
	/* Contributors: John Sundling */
	public static void newRegistrer() throws SQLException {
		Connection connection = null;
		Statement statement = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection(Main.dbAddress);
			statement = connection.createStatement();
			addRegister(connection);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				statement.close();
			} catch (SQLException e) {

			}
		}

	}

	/* AUTHOR: Tigistu Anebo */
	/* Contributors: Emanuel Mellblom */
	/* Contributors: John Sundling */
	private static void addRegister(Connection connection) throws SQLException {
		Scanner scan = new Scanner(System.in);
		PreparedStatement preparedStatement = null;
		TextField username = (TextField) Main.userLoginScreen.lookup("#username"); // loginScreen
		TextField email = (TextField) Main.userLoginScreen.lookup("#email");
		PasswordField password = (PasswordField) Main.userLoginScreen.lookup("#password");
		PasswordField confirmPassword = (PasswordField) Main.userLoginScreen.lookup("#confirmPassword");
		Label error = (Label) Main.userLoginScreen.lookup("#errorMessage");
		// error.setVisible(false);

		if (password.getText().toString().equals(confirmPassword.getText().toString())) {

			try {

				preparedStatement = connection.prepareStatement(
						"INSERT INTO Users (Username, Password, AccountType, Email)" + "VALUES (?, ?, ?, ?)");
				// preparedStatement.setInt(1, userid);
				// System.out.println("Username");
				preparedStatement.setString(1, username.getText().toString());
				// System.out.println("Password");
				preparedStatement.setString(2, password.getText().toString());
				preparedStatement.setString(3, "U");
				// System.out.println("Email");
				preparedStatement.setString(4, email.getText().toString());
				preparedStatement.executeUpdate();
				error.setVisible(false);
			} catch (Exception e) {
				error.setText("Username or Email already in use");
				error.setVisible(true);
			} finally {
				try {
					connection.close();
					preparedStatement.close();
				} catch (SQLException e) {

				}
			}
		}
	}

}
