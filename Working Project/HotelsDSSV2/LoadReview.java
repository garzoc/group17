package HotelsDSSV2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoadReview extends Main{
	
	static ScrollPane scroll;
	
	private static int hotelId;
	
	//Labels for storing the review data
	static Label []dateLabel = new Label[numShownReviews]; 
	static Label []ratingLabel = new Label[numShownReviews]; 
	static Label []textLabel = new Label[numShownReviews]; 
	
	
	//Add review info in the VBoxes
	public static void loadReviews(int hotelId){


		Connection connection = null;
    	try{
			//Setting up the connection to the database
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection(Main.dbAddress);
			Statement statement = connection.createStatement();

			//Call the method for adding the boxes
			addReviewBox(hotelViewScene, hotelId); //Main.addNew)
			
			ResultSet reviewvs = statement.executeQuery("SELECT * FROM Reviews where HID == '" + hotelId + "'");
				
			//Loop for adding info to labels
			int rewCount = 0;
			while (reviewvs.next()){
				dateLabel[rewCount].setText(reviewvs.getString("Date"));
				ratingLabel[rewCount].setText("" +reviewvs.getInt("Rating"));
				textLabel[rewCount].setText(reviewvs.getString("Content"));
				rewCount++;
			}
			
			}catch(ClassNotFoundException error) {
				System.out.println("Error: " + error.getMessage());
			}
			catch(SQLException error) {
				System.out.println("Error: " + error.getMessage());
			}
			finally{
				if(connection !=null) try{connection.close();} catch(SQLException ignore) {}
							
			}
	}
	
	//Add the VBoxes for the reviews
	public static void addReviewBox(Parent hotelViewScene, int hotelId){

		
		int loopLength = 0; 
		
		if(numShownReviews > counter(hotelId)){
			loopLength = counter(hotelId);
		}else{
			loopLength=numShownReviews;
		}
		
		for(int i = 0; i<loopLength; i++){
			reviewContainer[i] = new VBox();
			reviewContainer[i].setPrefHeight(150);
			reviewContainer[i].setPrefWidth(950);
			reviewContainer[i].setStyle("-fx-border-color: black;"
          			+ "align:left;");
			
			Label date = new Label("Date: ");
			date.setStyle("-fx-border-radius: 5 5 5 5;"
		        			+ "-fx-font-family:Times New Roman;"
		        			+ "-fx-font-weight: bold;"
		        			+ "-fx-font-style: italic;"
		        			+ "-fx-font-size: 16;");
			dateLabel[i] = new Label(); 
			dateLabel[i].setStyle("-fx-font-size: 14;");
			
			Label rating = new Label("Rating: ");
			rating.setStyle("-fx-border-radius: 5 5 5 5;"
		        			+ "-fx-font-family:Times New Roman;"
		        			+ "-fx-font-weight: bold;"
		        			+ "-fx-font-style: italic;"
		        			+ "-fx-font-size: 16;");
			ratingLabel[i] = new Label();
			ratingLabel[i].setStyle("-fx-font-size: 14;");
			
			Label text = new Label("Text:");
			text.setStyle("-fx-border-radius: 5 5 5 5;"
		        			+ "-fx-font-family:Times New Roman;"
		        			+ "-fx-font-weight: bold;"
		        			+ "-fx-font-style: italic;"
		        			+ "-fx-font-size: 16;");
			textLabel[i] = new Label();
			textLabel[i].setStyle("-fx-font-size: 14;");
			
			reviewContainer[i].getChildren().addAll(date, dateLabel[i], rating,ratingLabel[i], text, textLabel[i]);
			rewcontainer.getChildren().addAll(reviewContainer[i]);
			scroll = (ScrollPane) hotelViewScene.lookup("#veiwScroll");
			scroll.setContent(rewcontainer);
		
		}
	}
	
	//Counter for how many reviews there is for a selected hotel
	public static int counter(int hotelId){


		Connection connection = null;
		int numReviews = 0;
		try{
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection(Main.dbAddress);
			Statement statement = connection.createStatement();

			ResultSet viewing = statement.executeQuery("SELECT * FROM Reviews where HID == '" + hotelId + "'");

			while (viewing.next()){
				
				numReviews++;
				
			}
			return numReviews;
    	}
		 catch(ClassNotFoundException error) {
				System.out.println("Error: " + error.getMessage());
				return numReviews;
				}
				catch(SQLException error) {
				System.out.println("Error: " + error.getMessage());
				return numReviews;
				}
				finally{
				if(connection !=null) try{connection.close();} catch(SQLException ignore) {}
						
		 
    }	
	}
	
	//Clear the output
	public static void clearReviewOutput(){

		rewcontainer.getChildren().clear();
		
	}

}
