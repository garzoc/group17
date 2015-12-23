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

public class SearchFunction {

	static int numShownHotels = 100;
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

	// ny
	static ImageView[] imgView = new ImageView[numShownHotels];
	static File[] file = new File[numShownHotels];
	static Image[] image = new Image[numShownHotels];

	static GridPane hotelContainer[] = new GridPane[numShownHotels];
	static GridPane container; // = new GridPane();

	/*AUTHOR: Emanuel Mellblom*/
	// This adds the Gridpane to the Search page (displays all entrys in the
	// database)
	public static void addSearchGrid(int loopLength) {

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

		for (int i = 0; i < loopLength; i++) {

			GridPane smallGrid = new GridPane();
			smallGrid.setPadding(new Insets(10, 10, 10, 10));
			smallGrid.setMinSize(510, 157);

			if (i < 5) {

				hotelContainer[i] = new GridPane();

				hotelContainer[i].setStyle("-fx-border-color: black;");
				hotelLabel[i] = new Label();
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

				smallGrid.add(imgView[i], numCol, i);
				smallGrid.add(hotelLabel[i], numCol + 1, i + 1);
				smallGrid.add(addressLabel[i], numCol + 1, i + 2);
				smallGrid.add(starsLabel[i], numCol + 1, i + 3);
				smallGrid.add(priceLabel[i], numCol + 1, i + 4);

				grid.add(smallGrid, 0, i);
				System.out.println("numCol " + numCol);
				System.out.println("i " + i);

				System.out.println("i= " + i);



			} else if (i >= 5 && i <= 10) { // && i<=6

				hotelContainer[i] = new GridPane();
				hotelContainer[i].setStyle("-fx-border-color: black;");
				hotelLabel[i] = new Label();
				addressLabel[i] = new Label();
				starsLabel[i] = new Label();
				priceLabel[i] = new Label();
				priceLabel[i].setPadding(new Insets(2, 1, 1, 1));


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

				grid.add(smallGrid, 1, i - 5); // -4
				System.out.println("else if numCol =  " + numCol);
				System.out.println("else if i =  " + i);

				System.out.println("i= " + i);


			} // else if statement ends
			numCol++;
		} // forloop ends

		sc = (ScrollPane) Main.scene5.lookup("#scroll");
		sc.setContent(grid);
		sc.setPadding(new Insets(30, 0, 0, 40));
	}// method ends

	
	/*AUTHOR: Emanuel Mellblom*/
	// This adds the data to the Gridpane to the Search page (displays all
	// entrys in the database)
	public static void addSearchData() {

		Connection connection = null;
		try {
			// Setting up the connection to the database
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection(Main.dbAddress);
			Statement statement = connection.createStatement();
			System.out.println("database connected from connect output");

			// Resultset for outputting the hotels
			ResultSet viewing = statement.executeQuery("SELECT * FROM Hotel");

			int tupleCount = 0;

			// Add text to the labels
			while (viewing.next()) { // .next();
				hotelLabel[tupleCount].setText("Name: " + viewing.getString("Name"));
				addressLabel[tupleCount].setText("Address: " + viewing.getString("Address"));
				priceLabel[tupleCount].setText("Price: " + viewing.getString("Price") + " SEK/Night");
				starsLabel[tupleCount].setText("Stars: " + viewing.getString("Stars"));

				// Add the images
				File file = new File(viewing.getString("Img"));
				String localUrl = null;
				try {
					localUrl = file.toURI().toURL().toString();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				Image localImage = new Image(localUrl, false);
				imgView[tupleCount].setImage(localImage);

				
				if (tupleCount == 10) {
					break;
				}
				tupleCount++;
				System.out.println("tuplecount= " + tupleCount);

			}
			// This belongs to the add all to one array

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
}
