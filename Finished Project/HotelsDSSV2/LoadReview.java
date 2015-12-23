package HotelsDSSV2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoadReview extends Main {

	static ScrollPane scroll;

	private static int hotelId;

	// Labels for storing the review data
	static Label[] dateLabel = new Label[numShownReviews];
	static Label[] ratingLabel = new Label[numShownReviews];
	static TextArea[] textLabel = new TextArea[numShownReviews];

	/*AUTHOR: Emanuel Mellblom*/
	/*Contributors: John Sundling*/
	// Add review info in the VBoxes
	public static void loadReviews(int hotelId) {

		Connection connection = null;
		try {
			// Setting up the connection to the database
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection(Main.dbAddress);
			Statement statement = connection.createStatement();

			// Call the method for adding the boxes
			addReviewBox(hotelViewScene, hotelId); // Main.addNew)

			ResultSet reviewvs = statement.executeQuery("SELECT * FROM Reviews where HID == '" + hotelId + "'");

			// Loop for adding info to labels
			int rewCount = 0;
			Integer userIds[] = new Integer[Main.numShownHotels];
			while (reviewvs.next()) {
				dateLabel[rewCount].setText("Date: " + reviewvs.getString("Date"));
				ratingLabel[rewCount].setText("Rating: " + reviewvs.getInt("Rating"));
				textLabel[rewCount].setText(reviewvs.getString("Content"));
				userIds[rewCount] = reviewvs.getInt("UID");
				rewCount++;
			}
			rewCount = 0;
			reviewvs = statement.executeQuery("SELECT * FROM Users where UID == '" + userIds[0] + "'");
			while (reviewvs.next()) {
				if (userIds[rewCount] != null) {
					dateLabel[rewCount].setText(dateLabel[rewCount].getText() + "\t\t\t\t\t\t\t\t\t\t User: "
							+ reviewvs.getString("Username"));
					rewCount++;
					if (userIds[rewCount] != null) {
						reviewvs = statement
								.executeQuery("SELECT * FROM Users where UID == '" + userIds[rewCount] + "'");
					} else {
						reviewvs.close();
						break;
					}
				}

			}

		} catch (ClassNotFoundException error) {
			System.out.println("Error: " + error.getMessage());
		} catch (SQLException error) {
			System.out.println("Error: " + error.getMessage());
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException ignore) {
				}

		}
	}

	
	/*AUTHOR: Emanuel Mellblom*/
	/*Contributors: John Sundling*/
	// Add the VBoxes for the reviews
	public static void addReviewBox(Parent hotelViewScene, int hotelId) {

		int loopLength = 0;

		if (numShownReviews > counter(hotelId)) {
			loopLength = counter(hotelId);
		} else {
			loopLength = numShownReviews;
		}
		for (int i = 0; i < loopLength; i++) {
			reviewContainer[i] = new VBox();
			reviewContainer[i].setPrefHeight(150);
			reviewContainer[i].setPrefWidth(950);
			reviewContainer[i].setPadding(new Insets(10, 10, 10, 10));

			reviewContainer[i].setStyle("-fx-border-color: black;"
					+ "-fx-background-color: linear-gradient(#cdfefa,#FFFFFF);" + "-fx-border-radius: 5 5 5 5;");

			dateLabel[i] = new Label("");
			dateLabel[i].setPadding(new Insets(0, 0, 10, 0));
			dateLabel[i].setStyle("-fx-border-radius: 5 5 5 5;" + "-fx-font-family:Times New Roman;"
					+ "-fx-font-weight: bold;" + "-fx-font-style: italic;" + "-fx-font-size: 16;");

			ratingLabel[i] = new Label();
			ratingLabel[i].setPadding(new Insets(0, 0, 20, 0));
			ratingLabel[i].setStyle("-fx-border-radius: 5 5 5 5;" + "-fx-font-family:Times New Roman;"
					+ "-fx-font-weight: bold;" + "-fx-font-style: italic;" + "-fx-font-size: 16;");

			textLabel[i] = new TextArea();
			textLabel[i].setDisable(true);
			textLabel[i].setLayoutX(900);
			textLabel[i].setWrapText(true);

			textLabel[i].setMaxWidth(500);
			textLabel[i].setMaxHeight(4000);

			textLabel[i].setPrefHeight(3500);
			textLabel[i].minHeight(2800);
			textLabel[i].minWidth(800);
			textLabel[i].setStyle("-fx-border-radius: 5 5 5 5;" + "-fx-opacity: 1.0;"
					+ "-fx-font-family:Times New Roman;" + "-fx-font-weight: bold;" + "-fx-font-style: italic;"
					+ "-fx-font-size: 16;" + "-fx-text-fill:red;" + "-fx-border-color: black;"
					+ "-fx-background-color:white;" + "-fx-max-height:700;");

			reviewContainer[i].getChildren().addAll(dateLabel[i], ratingLabel[i], textLabel[i]);
			rewcontainer.getChildren().addAll(reviewContainer[i]);
			rewcontainer.setMargin(reviewContainer[i], new Insets(0, 0, 10, 0));
			rewcontainer.setStyle("-fx-background-color:rgba(210,210,210,0.2);");
			scroll = (ScrollPane) hotelViewScene.lookup("#veiwScroll");
			scroll.setContent(rewcontainer);

		}
	}

	/*AUTHOR: Emanuel Mellblom*/
	// Counter for how many reviews there is for a selected hotel
	public static int counter(int hotelId) {

		Connection connection = null;
		int numReviews = 0;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection(Main.dbAddress);
			Statement statement = connection.createStatement();

			ResultSet viewing = statement.executeQuery("SELECT * FROM Reviews where HID == '" + hotelId + "'");

			while (viewing.next()) {

				numReviews++;

			}
			return numReviews;
		} catch (ClassNotFoundException error) {
			System.out.println("Error: " + error.getMessage());
			return numReviews;
		} catch (SQLException error) {
			System.out.println("Error: " + error.getMessage());
			return numReviews;
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException ignore) {
				}

		}
	}

	/*AUTHOR: Emanuel Mellblom*/
	// Clear the output
	public static void clearReviewOutput() {

		rewcontainer.getChildren().clear();

	}

}
