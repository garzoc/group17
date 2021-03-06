package HotelsDSSV2;

import java.io.File;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class TestSample {

	static int numShownHotels = 1000;
	static ScrollPane sc;
	static ScrollPane sc3;

	static Label[] hotelLabel = new Label[numShownHotels];
	static Label[] addressLabel = new Label[numShownHotels];
	static Label[] starsLabel = new Label[numShownHotels];
	static Label[] poolLabel = new Label[numShownHotels];
	static Label[] gymLabel = new Label[numShownHotels];
	static Label[] petsLabel = new Label[numShownHotels];
	static Label[] barLabel = new Label[numShownHotels];
	static Label[] priceLabel = new Label[numShownHotels];
	static Label[] ratingLabel = new Label[numShownHotels];

	// ny
	static ImageView[] imgView = new ImageView[numShownHotels];
	static File[] file = new File[numShownHotels];
	static Image[] image = new Image[numShownHotels];

	static GridPane hotelContainer[] = new GridPane[numShownHotels];
	static GridPane container; // = new GridPane();

	/* AUTHOR: Emanuel Mellblom */
	// This adds the Gridpane to the Start page
	public static void addSampleGrid() {

		GridPane grid = new GridPane();
		// padding from outer frame
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setStyle("-fx-border-color: black;");
		// Padding
		grid.setVgap(10);
		grid.setHgap(10);
		// Grid lines visible
		grid.setGridLinesVisible(true);
		grid.setMinSize(1050, 510);
		// grid.setAlignment(Pos.CENTER);

		int numCol = 0;

		for (int i = 0; i < 6; i++) {

			GridPane smallGrid = new GridPane();
			smallGrid.setPadding(new Insets(10, 10, 10, 10));
			smallGrid.setMinSize(510, 157);
			if (i < 3) {
				hotelContainer[i] = new GridPane();
				// hotelContainer[i].setPrefHeight(156);
				// hotelContainer[i].setLayoutY(i*156);
				hotelContainer[i].setStyle("-fx-border-color: black;");
				hotelLabel[i] = new Label();
				// hotelLabel[i].setStyle("-fx-border-color: black;");
				addressLabel[i] = new Label();
				starsLabel[i] = new Label();

				priceLabel[i] = new Label();

				// ny
				imgView[i] = new ImageView();
				imgView[i].setFitHeight(150);
				imgView[i].setFitWidth(120);
				smallGrid.setHalignment(imgView[i], HPos.LEFT);
				smallGrid.setColumnSpan(imgView[i], 1);
				smallGrid.setRowSpan(imgView[i], 6);

				// ny
				smallGrid.add(imgView[i], numCol, i);
				smallGrid.add(hotelLabel[i], numCol + 1, i + 1);
				smallGrid.add(addressLabel[i], numCol + 1, i + 2);
				smallGrid.add(starsLabel[i], numCol + 1, i + 3);

				smallGrid.add(priceLabel[i], numCol + 1, i + 4);

				grid.add(smallGrid, 0, i);
			} else if (i >= 3 && i <= 6) { // && i<=6

				hotelContainer[i] = new GridPane();
				hotelContainer[i].setStyle("-fx-border-color: black;");
				hotelLabel[i] = new Label();
				// hotelLabel[i].setStyle("-fx-border-color: black;");
				addressLabel[i] = new Label();
				starsLabel[i] = new Label();

				priceLabel[i] = new Label();
				priceLabel[i].setPadding(new Insets(2, 1, 1, 1));

				// ny

				imgView[i] = new ImageView();
				imgView[i].setFitHeight(150);
				imgView[i].setFitWidth(120);
				// Shadow on image
				// imgView[i].setStyle("-fx-effect: dropshadow( gaussian , gray,
				// 2,2,2,2 )");
				smallGrid.setHalignment(imgView[i], HPos.LEFT);
				smallGrid.setColumnSpan(imgView[i], 1);
				smallGrid.setRowSpan(imgView[i], 6);

				// ny
				smallGrid.add(imgView[i], numCol, i);

				// smallGrid.add(imv, numCol, i);

				smallGrid.add(hotelLabel[i], numCol + 1, i + 1);
				smallGrid.add(addressLabel[i], numCol + 1, i + 2);
				smallGrid.add(starsLabel[i], numCol + 1, i + 3);
				smallGrid.add(priceLabel[i], numCol + 1, i + 4);

				grid.add(smallGrid, 1, i - 3); // -4
			} // else if statement ends
			numCol++;

		} // forloop ends
		sc = (ScrollPane) Main.scene.lookup("#scroll");
		sc.setContent(grid);
		sc.setPadding(new Insets(30, 0, 0, 40));
	}

	/* AUTHOR: Emanuel Mellblom */
	// This adds data to the 6 GridPanes in the start page
	public static void addSampleData() {

		Main.connection = null;
		Statement statement = null;
		try {
			// Setting up the connection to the database
			Class.forName("org.sqlite.JDBC");
			Main.connection = DriverManager.getConnection(Main.dbAddress);
			statement = Main.connection.createStatement();
			// System.out.println("database connected from connect output");

			// Resultset for outputting the hotels
			ResultSet viewing = statement.executeQuery("SELECT * FROM Hotel");

			int tupleCount = 0;

			// Add text to the labels
			while (viewing.next()) { // .next();
				hotelLabel[tupleCount].setText("Name: " + viewing.getString("Name"));
				hotelLabel[tupleCount].setPadding(new Insets(0, 0, 0, 5));
				addressLabel[tupleCount].setText("Address: " + viewing.getString("Address"));
				addressLabel[tupleCount].setPadding(new Insets(0, 0, 0, 5));
				priceLabel[tupleCount].setText("Price: " + viewing.getDouble("Price") + " SEK/Night"); // String
				priceLabel[tupleCount].setPadding(new Insets(0, 0, 0, 5));
				starsLabel[tupleCount].setText("Stars: " + viewing.getInt("Stars")); // String
				starsLabel[tupleCount].setPadding(new Insets(0, 0, 0, 5));

				// Add the images
				File file = new File(Properties.getProperty("imgPath") + viewing.getString("Img"));
				String localUrl = null;
				try {
					localUrl = file.toURI().toURL().toString();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Image localImage = new Image(localUrl, false);
				imgView[tupleCount].setImage(localImage);
				imgView[tupleCount].setStyle("-fx-effect: dropshadow( gaussian , black, 0,0,2,2 )");

				// ny
				if (tupleCount == 5) {
					break;
				}
				tupleCount++;

			}

		} catch (ClassNotFoundException error) {
			System.out.println("Error: " + error.getMessage());
		} catch (SQLException error) {
			System.out.println("Error: " + error.getMessage());
		} finally {
			if (Main.connection != null)
				try {
					Main.connection.close();
				} catch (SQLException ignore) {
				}
			try {
				statement.close();
			} catch (SQLException e) {

			} catch (NullPointerException e) {

			}
		}

	}

	/* AUTHOR: Emanuel Mellblom */
	// This is connected to the start button event
	public static void addSampleGridToStart() {

		GridPane grid = new GridPane();
		// padding from outer frame
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setStyle("-fx-border-color: black;");
		// Padding
		grid.setVgap(10);
		grid.setHgap(10);
		// Grid lines visible
		grid.setGridLinesVisible(true);
		grid.setMinSize(1050, 510);
		// grid.setAlignment(Pos.CENTER);
		int numCol = 0;

		for (int i = 0; i < 6; i++) {

			GridPane smallGrid = new GridPane();
			smallGrid.setPadding(new Insets(10, 10, 10, 10));
			smallGrid.setMinSize(510, 157);

			if (i < 3) {
				hotelContainer[i] = new GridPane();
				hotelContainer[i].setStyle("-fx-border-color: black;");
				hotelLabel[i] = new Label();
				addressLabel[i] = new Label();
				starsLabel[i] = new Label();

				priceLabel[i] = new Label();

				imgView[i] = new ImageView();
				imgView[i].setFitHeight(150);
				imgView[i].setFitWidth(120);
				smallGrid.setHalignment(imgView[i], HPos.RIGHT);
				smallGrid.setColumnSpan(imgView[i], 1);
				smallGrid.setRowSpan(imgView[i], 6);

				smallGrid.add(imgView[i], numCol, i);

				// smallGrid.add(imv, numCol, i);
				smallGrid.add(hotelLabel[i], numCol + 1, i + 1);
				smallGrid.add(addressLabel[i], numCol + 1, i + 2);
				smallGrid.add(starsLabel[i], numCol + 1, i + 3);
				smallGrid.add(priceLabel[i], numCol + 1, i + 4);

				grid.add(smallGrid, 0, i);

			} // if statement ends

			else if (i >= 3 && i <= 6) { // && i<=6

				hotelContainer[i] = new GridPane();

				hotelContainer[i].setStyle("-fx-border-color: black;");
				hotelLabel[i] = new Label();
				addressLabel[i] = new Label();
				starsLabel[i] = new Label();

				priceLabel[i] = new Label();

				imgView[i] = new ImageView();
				imgView[i].setFitHeight(150);
				imgView[i].setFitWidth(120);
				smallGrid.setHalignment(imgView[i], HPos.LEFT);
				smallGrid.setColumnSpan(imgView[i], 1);
				smallGrid.setRowSpan(imgView[i], 6);
				smallGrid.add(imgView[i], numCol, i);
				smallGrid.add(hotelLabel[i], numCol + 1, i + 1);
				smallGrid.add(addressLabel[i], numCol + 1, i + 2);
				smallGrid.add(starsLabel[i], numCol + 1, i + 3);

				smallGrid.add(priceLabel[i], numCol + 1, i + 4);

				grid.add(smallGrid, 1, i - 3); // -4

			} // else if statement ends
			numCol++;
		} // forloop ends

		sc3 = (ScrollPane) Main.scene3.lookup("#scroll");
		sc3.setContent(grid);
		sc3.setPadding(new Insets(30, 0, 0, 40));
	}// method ends
}
