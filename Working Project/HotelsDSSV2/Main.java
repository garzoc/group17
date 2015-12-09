package HotelsDSSV2;

import java.awt.Desktop;



import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;

import java.util.logging.Logger;


//import com.sun.java.util.jar.pack.Attribute.Layout;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.image.*;
import javafx.event.*;


public class Main extends Application{
	
	static int numShownHotels = 100;
	static int numShownReviews = 100;
	
	static public String dbAddress = "jdbc:sqlite:/Users/John/Desktop/Hotels.sqlite";

	static VBox hotelContainer[] = new VBox[numShownHotels];
	static VBox container = new VBox();
	static Scene scene, scene1, scene2, scene3, scene4, scene5, scene6, reviewScene,loginScreen;
	static Parent adminLoginScreen;
	static Parent userLoginScreen;
	static Stage theStage;
	Pane page1;
	static Scene addNew;
	static String docsUrl;
	static String filePathText;
	
	//Change
		static VBox reviewContainer[] = new VBox[numShownReviews];
		static VBox rewcontainer = new VBox();
	
	private Desktop desktop = Desktop.getDesktop();
	
	 //Textfields in JavaFX for input
	static TextField textf = new TextField();
	static TextField pricef = new TextField();
	static TextField adressf = new TextField();
	static TextField starsf = new TextField();
	
	//img
	static TextField imgPathf = new TextField();
	
	//Checkboxes in JavaFX for input
	static CheckBox poolcb = new CheckBox();
	static CheckBox barcb = new CheckBox();
	static CheckBox gymcb = new CheckBox();
	static CheckBox petscb = new CheckBox();
	
	static Stage primaryStage;
	
	
	static Stage PopupStage;
	static Stage userLoginStage;
	//test
	static Stage hotelViewerStage;
	static Parent hotelViewScene;
	
	static Stage leaveReviewStage;
	static Parent leaveReviewScene;
	static Connection connection=null;
	
	
	Image image;
	
	static GridPane grid = new GridPane(); //global
	static Pane page6;
	ScrollPane sc;
	
	static Account user = null;

	

	
	public void searchByName1 (ActionEvent searchName) {



		
		try {
			
            theStage.setScene(scene5);
            
            
            
            Button searchButton = (Button) page6.lookup("#searchButton");
           	searchButton.setOnAction(this::searchButtonEvent2);
           	
           	Button loginButton = (Button) page6.lookup("#loginButton");
           	if(user==null){
           	loginButton.setOnAction(this::loginEvent);
           	loginButton.setText("UserLogin");
           	}else{
           		loginButton.setOnAction(this::logOutEvent);
           		loginButton.setText("Logout");
           	}
           	
           	Button adminLoginButton = (Button) page6.lookup("#adminLoginButton");
           	adminLoginButton.setOnAction(this::adminLogin);
           	
           	Button startButton = (Button) page6.lookup("#startButton");
           	startButton.setOnAction(this::backToStart);
           	
           	
           textf = (TextField) scene.lookup("#searchField"); 
           	
           	Button searchByName = (Button) scene5.lookup("#searchByName");
           	searchByNameHandeler();
           	
           	try {
				searchByName.setOnAction(this::searchByName2);
			} catch (Exception e) {
				e.printStackTrace();
			}
           	}catch (Exception ex) {
		        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		        }

           	
           
		}
	
	public void searchByName2 (ActionEvent searchName2) {





		try {
         	textf = (TextField) scene5.lookup("#searchField"); 
         
           	Button searchByName = (Button) scene5.lookup("#searchByName");

           	
           	searchByNameHandeler();
         	
           	try {
           		
				searchByName.setOnAction(this::searchByName2); //1
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           	
           	
			} catch (Exception ex) {
	        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	        }
		}
	
	public void searchByNameHandeler(){




		
		try {
           // System.out.println("start searching");
            grid.getChildren().clear();
       		Hotel[] hotels = SearchItem.locateItems(textf.getText().toString());
       		//addFirstGrid();
       		addSearchGrid2(hotels);
            } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
}
	
	public void addSearchGrid2(Hotel[] hotels) throws SQLException{

		int numCol = 0;
       	for(int y = 0; y < hotels.length; y++){
       		if(hotels[y] !=null){
       			for(int x =0 ; x<2; x++){
       				if(hotels[numCol] !=null){
       				GridPane smallGrid = new GridPane();
    		   		smallGrid.setPadding(new Insets(10,10,10,10));
    		    	smallGrid.setMinSize(510, 157); 

    		    	//Clicked event on hotel
    		    	int psudoNumCol = numCol;
    		    	Hotel[] psudoHotel = hotels;
    		    	smallGrid.addEventFilter(MouseEvent.MOUSE_CLICKED,  e -> {
    		    		//System.out.println(psudoNumCol);
    		    		
    		    		//Set the hotel Viewer Scene when a user clicks on a hotel
    		    		try {	
    		    			hotelViewerStage = new Stage();
    		    			hotelViewScene = FXMLLoader.load(
    		                    Main.class.getResource("HotelViewerGUI.fxml"));
    		                 hotelViewerStage.setScene(new Scene(hotelViewScene));
    		                 hotelViewerStage.setTitle("Hotel Info");
    		                 hotelViewerStage.initModality(Modality.WINDOW_MODAL);
    		                 hotelViewerStage.initOwner(
    		                    ((Node)e.getSource()).getScene().getWindow() );   		                 
    		               
    		                 hotelViewerStage.setOpacity(1.0);
    		                 hotelViewerStage.show();
    		                 
    		                 /** 
    		                 * Reviews AnchorPane ID = "#reviewsAnchorPane"
    		                 */
    		                 
    		                 //Hotel Name
    		                 Label hotelNameLabel = (Label) hotelViewScene.lookup("#HotelNameLabel");
    		                 hotelNameLabel.setText(hotels[psudoNumCol].getHotelName());
    		                 
    		                 //Hotel Address
    		                 Label hotelAddressLabel = (Label) hotelViewScene.lookup("#AddressLabel");
    		                 hotelAddressLabel.setText(hotels[psudoNumCol].getHotelAddress());
    		                 
    		                 //Stars
    		                 Label starsLabel = (Label) hotelViewScene.lookup("#StarsNumberLabel");
    		                 starsLabel.setText(""+hotels[psudoNumCol].getStars());
    		                 
    		                 //Price
    		                 Label priceLabel = (Label) hotelViewScene.lookup("#PriceLabel");
    		                 priceLabel.setText(""+hotels[psudoNumCol].getPrice());
    		                 
    		                 //Pets
    		                 Label petsLabel = (Label) hotelViewScene.lookup("#PetsLabel");
    		                 if(hotels[psudoNumCol].getPets() == true){
    		                	 petsLabel.setText("Yes");
    		                 } else{
    		                	 petsLabel.setText("No");
    		                 }
    		                 
    		                 //Gym
    		                 Label gymLabel = (Label) hotelViewScene.lookup("#GymLabel");
    		                 if(hotels[psudoNumCol].getGym() == true){
    		                	 gymLabel.setText("Yes");
    		                 } else{
    		                	 gymLabel.setText("No");
    		                 }
    		                 
    		                 //Pool
    		                 Label poolLabel = (Label) hotelViewScene.lookup("#PoolLabel");
    		                 if(hotels[psudoNumCol].getPool()== true){
    		                	 poolLabel.setText("Yes");
    		                 } else{
    		                	 poolLabel.setText("No");
    		                 }
    		                 
    		                 //Bar
    		                 Label barLabel = (Label) hotelViewScene.lookup("#BarLabel");
    		                 if(hotels[psudoNumCol].getPool()== true){
    		                	 barLabel.setText("Yes");
    		                 } else{
    		                	 barLabel.setText("No");
    		                 }
    		                 
    		                 //Image
    		                 File file = new File(hotels[psudoNumCol].getImgPath());
    				   	    	String localUrl = null;
    				   			try {
    				   				localUrl = file.toURI().toURL().toString();
    				   			} catch (MalformedURLException eve) {
    				   				// TODO Auto-generated catch block
    				   				eve.printStackTrace();
    				   			}
    				   	    Image localImage = new Image(localUrl, false);
    		                ImageView image = (ImageView) hotelViewScene.lookup("#HotelImageBox");
    		                image.setImage(localImage);
    		                
    		                /**
    		                 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Code for loading in the reviews goes here!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    		                 */
    		              //Load the reviews to the veiw screen for selected hotel
    		                LoadReview.clearReviewOutput();
    		                LoadReview.counter(hotels[psudoNumCol].getHotelId());
    		                LoadReview.loadReviews(hotels[psudoNumCol].getHotelId());
    		                
    		                
    		                Button leaveReview = (Button) hotelViewScene.lookup("#leaveReview");
    		                leaveReview.setVisible(false);
   		               
    		                //Check if the user is logged in and in that case set the review button visible.
    		                if(user==null || !user.userExist()){
    		                	System.out.println("User exists");
    		                	leaveReview.setVisible(false);
    		                }else if(user.userExist()){
    		                	System.out.println("user not logged in");
    		                	leaveReview.setVisible(true);
    		                }
    		                
    		                //Event for when the user is logged in and press the leave review button.
    		                leaveReview.addEventFilter(MouseEvent.MOUSE_CLICKED,  leaveReveiw -> {
    		                	//Leave reveiw event goes here
    		                	System.out.println("leave a review");
    		                	try {	
    		                		
    		                		leaveReviewStage = new Stage();
    		                		leaveReviewScene = FXMLLoader.load(
    	    		                    Main.class.getResource("ReviewInputGUI.fxml"));
    	    		    			leaveReviewStage.setScene(new Scene(leaveReviewScene));
    	    		    			leaveReviewStage.setTitle("Leave a Rewiev");
    	    		    			leaveReviewStage.initModality(Modality.WINDOW_MODAL);
    	    		    			leaveReviewStage.initOwner(
    	    		                    ((Node)leaveReveiw.getSource()).getScene().getWindow() );   		                 
    	    		    			leaveReviewStage.setOpacity(0.85);
    	    		    			leaveReviewStage.show();
    	    		    			
    	    		    			RadioButton Radiobutton5 = (RadioButton) leaveReviewScene.lookup("#Radiobutton5");
    	    		    			RadioButton Radiobutton4 = (RadioButton) leaveReviewScene.lookup("#Radiobutton4");
    	    		    			RadioButton Radiobutton3 = (RadioButton) leaveReviewScene.lookup("#Radiobutton3");
    	    		    			RadioButton Radiobutton2 = (RadioButton) leaveReviewScene.lookup("#Radiobutton2");
    	    		    			RadioButton Radiobutton1 = (RadioButton) leaveReviewScene.lookup("#Radiobutton1");
    	    		    			
    	    		    			TextArea ReviewTextarea = (TextArea) leaveReviewScene.lookup("#ReviewTextarea");
    	    		    				    		    		
    	    		    			Button cancelButton = (Button) leaveReviewScene.lookup("#CancelButton");
    	    		    			cancelButton.addEventFilter(MouseEvent.MOUSE_CLICKED,  cancelEvent -> {
    	    		    				leaveReviewStage.close();
    	    		    			});
    	    		    			
    	    		    			Button leaveReviewButton = (Button) leaveReviewScene.lookup("#PostButton");
    	    		    			leaveReviewButton.addEventFilter(MouseEvent.MOUSE_CLICKED,  leaveEvent -> {
    	    		    				
    	    		    				/**
    	    		    				 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Code for storing a review goes here!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    	    		    				 */
    	    		    				int reviewRating = 0;
    	    		    				String reveiwText =null;
    	    		    				
    	    		    				if(Radiobutton5.isSelected()){
    	    		    					reviewRating = 5;
    	    		    				}else if (Radiobutton4.isSelected()){
    	    		    					reviewRating = 4;
    	    		    				}else if(Radiobutton3.isSelected()){
    	    		    					reviewRating = 3;
    	    		    				}else if (Radiobutton2.isSelected()){
    	    		    					reviewRating = 2;
    	    		    				}else if (Radiobutton1.isSelected()){
    	    		    					reviewRating = 1;
    	    		    				}
    	    		    				
    	    		    				reveiwText = ReviewTextarea.getText().toString();
    	    		    				
    	    		    				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    	    		    				Calendar cal = Calendar.getInstance();
    	    		    				String date = dateFormat.format(cal.getTime());

    	    		    				//Add the review to the database
    	    		    				Review review = new Review(user.getUserId(), reveiwText, date, reviewRating, hotels[psudoNumCol].getHotelId());
    	    		    				Review.storeReview(review);

    	    		    				leaveReviewStage.close();
    	    		    				
    	    		    				
    	    		    			});
    	    		    			
    	    		    			
    	    		    			
    		                		} catch (Exception ex) {
    	    		    	        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
    	    		    	        }	
    		                });
    		                
    		                
    		                Button backToSearch = (Button) hotelViewScene.lookup("#returnToSearch");
    		               backToSearch.addEventFilter(MouseEvent.MOUSE_CLICKED,  backToSearchevent -> {
    		                	//Close and go back to the searchresult
    		            	   hotelViewerStage.close();
    		                	
    		                });
    		                
    		    			} catch (Exception ex) {
    		    	        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
    		    	        }	

    		    	});

    		    	
		    		TestSample.hotelContainer[numCol] = new GridPane();

		    		TestSample.hotelContainer[numCol].setStyle("-fx-border-color: black;");

		    		//Name
		    		TestSample.hotelLabel[numCol] = new Label();
					TestSample.hotelLabel[numCol].setText(hotels[numCol].getHotelName());
					TestSample.hotelLabel[numCol].setPadding(new Insets( 0,0,0,5));
					//Address
					TestSample.addressLabel[numCol] = new Label();
					TestSample.addressLabel[numCol].setText(hotels[numCol].getHotelAddress());
					TestSample.addressLabel[numCol].setPadding(new Insets( 0,0,0,5));
					//Price
					TestSample.priceLabel[numCol] = new Label();
					TestSample.priceLabel[numCol].setText("Price: "+""+hotels[numCol].getPrice()); //String
					TestSample.priceLabel[numCol].setPadding(new Insets( 0,0,0,5));
					//Stars
					TestSample.starsLabel[numCol] = new Label();
			        TestSample.starsLabel[numCol].setText("Stars: "+""+hotels[numCol].getStars()); //String
			        TestSample.starsLabel[numCol].setPadding(new Insets( 0,0,0,5));
			        //Rating
			        TestSample.ratingLabel[numCol] = new Label();
			        TestSample.ratingLabel[numCol].setText("Rating: "+"" + Rating.getRatings(hotels[numCol].getHotelId()));
			        TestSample.ratingLabel[numCol].setPadding(new Insets( 0,0,0,5));
			        
			        
			        //Image
			        File file = new File(Properties.getProperty("imgPath")+hotels[numCol].getImgPath());
		   	    	String localUrl = null;
		   			try {
		   				localUrl = file.toURI().toURL().toString();
		   			} catch (MalformedURLException e) {
		   				// TODO Auto-generated catch block
		   				e.printStackTrace();
		   			}
		   	    	Image localImage = new Image(localUrl, false);
		   	    	TestSample.imgView[numCol] = new ImageView();
			        TestSample.imgView[numCol].setImage(localImage);
		          	TestSample.imgView[numCol].setFitHeight(150);
		          	TestSample.imgView[numCol].setFitWidth(120);
		          	TestSample.imgView[numCol].setStyle("-fx-effect: dropshadow( gaussian , black, 0,0,2,2 )");
		          	smallGrid.setHalignment(TestSample.imgView[numCol], HPos.LEFT);
			    	smallGrid.setColumnSpan(TestSample.imgView[numCol], 1);
			    	smallGrid.setRowSpan(TestSample.imgView[numCol], 6);

		          	//Add to the Small Grid
		          	smallGrid.add(TestSample.imgView[numCol],x, y);
		          	smallGrid.add(TestSample.hotelLabel [numCol], x+1, y+1);
		        	smallGrid.add(TestSample.addressLabel [numCol], x+1, y+2);
		        	smallGrid.add(TestSample.starsLabel[numCol], x+1, y+3);
		        	smallGrid.add(TestSample.priceLabel[numCol], x+1, y+4);
		        	
		        	smallGrid.add(TestSample.ratingLabel[numCol], x+1, y+5);
		        	smallGrid.setStyle("-fx-border-style: solid;"
		        			+ "-fx-border-radius: 5 5 5 5;"
		        			+ "-fx-font-family:Times New Roman;"
		        			+ "-fx-font-weight: bold;"
		        			+ "-fx-font-style: italic;"
		        			+ "-fx-font-size: 16;"
		        			+ "-fx-background-color:white;");
		        	//smallGrid.setStyle("-fx-background-color:black;");
		          	numCol++;
    		    	grid.add(smallGrid, x, y);
    		    	
    		    	
       			}
       			}
       		}
       		sc = (ScrollPane) Main.scene5.lookup("#scroll");
       		sc.setContent(grid);
			sc.setPadding(new Insets(30,0,0,40));
       	} 
			
			
      
	
	}
	
	public void addFirstGrid(){




		//GridPane grid = new GridPane(); //global
       	//padding from outer frame
       		grid.setPadding(new Insets(10,10,10,10));
       		grid.setStyle("-fx-border-color: black;"
       				+ "-fx-border-radius: 5 5 5 5;"
       				+ "-fx-background-color: linear-gradient(#cdfefa,#FFFFFF);");
        //Padding
       		grid.setVgap(10);
       		grid.setHgap(10);
       	//Grid lines visible
       		grid.setGridLinesVisible(true);
       		grid.setMinSize(1050, 510);
       //grid.setAlignment(Pos.CENTER);
       		ScrollPane sc = (ScrollPane) Main.scene5.lookup("#scroll");
		sc.setContent(grid);
		sc.setPadding(new Insets(30,0,0,40));
	}
	
	//Start method FirstPage
	@Override
 	
	public void start(Stage primaryStage) throws Exception {


		theStage = primaryStage;
			
		try {
            page1 = (Pane) FXMLLoader.load(Main.class.getResource("Output.fxml"));
           scene = new Scene(page1);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Hotels DSS");
           	primaryStage.show();
           	
           	Button searchButton = (Button) scene.lookup("#searchButton");
           	searchButton.setOnAction(this::searchButtonEvent1);
           	
           	Button loginButton = (Button) scene.lookup("#loginButton");
           	loginButton.setOnAction(this::loginEvent);
           	
           	Button adminLoginButton = (Button) scene.lookup("#adminLoginButton");
           	adminLoginButton.setOnAction(this::adminLogin);
           	
           	Button searchByName = (Button) scene.lookup("#searchByName");
           	
           	
           	
           	try {
				searchByName.setOnAction(this::searchByName1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           	
           	
           	if(!Properties.propertyExists("imgPath")||!Properties.propertyExists("databasePath")){
    			firstTimeSetUp();
    		}
           	
           	
           	//Add 6 gridpanes with sample hotels
             page6 = (Pane) FXMLLoader.load(Main.class.getResource("searchWindow.fxml"));
            scene5 = new Scene(page6);
           	addFirstGrid(); //Ny
           TestSample.addSampleGrid();
           TestSample.addSampleData();
           	
	
	} catch (Exception ex) {
        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
	

}

		
	//Event when searchbutton is pressed should take the user to a new screen 
 	private void searchButtonEvent1 (ActionEvent event){	



		try {
            //Pane page5 = (Pane) FXMLLoader.load(Main.class.getResource("searchWindow.fxml"));
            //scene5 = new Scene(page6);
            theStage.setScene(scene5);
            
            Button searchButton = (Button) page6.lookup("#searchButton");
        
            Button loginButton = (Button) page6.lookup("#loginButton");
           	if(user==null){
           	loginButton.setOnAction(this::loginEvent);
           	loginButton.setText("UserLogin");
           	}else{
           		loginButton.setOnAction(this::logOutEvent);
           		loginButton.setText("Logout");
           	}
           	
           	
           	Button adminLoginButton = (Button) page6.lookup("#adminLoginButton");
           	adminLoginButton.setOnAction(this::adminLogin);
           	
           	Button startButton = (Button) page6.lookup("#startButton");
           	startButton.setOnAction(this::backToStart);
           	
           	TextField searchField=(TextField) scene5.lookup("#searchField");
           	TextField searchField2=(TextField) scene.lookup("#searchField");
           	searchField.setText(searchField2.getText());
          
           	//Checkbox
           	CheckBox barCheckBox = (CheckBox) scene5.lookup("#barCheck");
           	CheckBox barCheckBox2 = (CheckBox) scene.lookup("#barCheck");
           	barCheckBox.setSelected(barCheckBox2.isSelected());
           	
           	CheckBox gymCheckBox = (CheckBox) scene5.lookup("#gymCheck");
           	CheckBox gymCheckBox2 = (CheckBox) scene.lookup("#gymCheck");
           	gymCheckBox.setSelected(gymCheckBox2.isSelected());
           	
        	CheckBox poolCheckBox = (CheckBox) scene5.lookup("#poolCheck");
           	CheckBox poolCheckBox2 = (CheckBox) scene.lookup("#poolCheck");
           	poolCheckBox.setSelected(poolCheckBox2.isSelected());
           	
        	CheckBox petsCheckBox = (CheckBox) scene5.lookup("#petsCheck");
           	CheckBox petsCheckBox2 = (CheckBox) scene.lookup("#petsCheck");
           	petsCheckBox.setSelected(petsCheckBox2.isSelected());
           	
           	//Price Textareas
           	TextField priceFrom = (TextField) scene5.lookup("#priceFrom");
           	TextField priceFrom2 = (TextField) scene.lookup("#priceFrom");
           	priceFrom.setText(priceFrom2.getText().toString());
          
           	
           	
           	TextField priceTo = (TextField) scene5.lookup("#PriceTo");
        	TextField priceTo2 = (TextField) scene.lookup("#PriceTo");
           	priceTo.setText(priceTo2.getText().toString());
           	//System.out.println(priceTo2.getText().toString());
        	
           	//Slider for stars
           	Slider starsSlide = (Slider) scene5.lookup("#starSlide");
           	Slider starsSlide2 = (Slider) scene.lookup("#starSlide");
           	starsSlide.setValue(starsSlide2.getValue());
           	
           	//textField for hotelname
           	textf = (TextField) scene.lookup("#searchField"); 
           	
           	
           	//sätt vars
           	
           	
           	searchButtonEventHandeler();
           	
           	try {
				searchButton.setOnAction(this::searchButtonEvent2);
			} catch (Exception e) {
				e.printStackTrace();
			}
           	}catch (Exception ex) {
		        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
           	}
 	}
 	
 	private void searchButtonEvent2 (ActionEvent event){	



		try {
            //Pane page5 = (Pane) FXMLLoader.load(Main.class.getResource("searchWindow.fxml"));
            //scene5 = new Scene(page6);
           // theStage.setScene(scene5);
            
            //Button searchButton = (Button) page6.lookup("#searchButton");
//           	searchButton.setOnAction(this::searchButtonEvent1);
//           	
//           	Button loginButton = (Button) page6.lookup("#loginButton");
//           	loginButton.setOnAction(this::loginEvent);
//           	
//           	Button adminLoginButton = (Button) page6.lookup("#adminLoginButton");
//           	adminLoginButton.setOnAction(this::adminLogin);
//           	
//           	Button startButton = (Button) page6.lookup("#startButton");
//           	startButton.setOnAction(this::backToStart);
           	
           	textf = (TextField) scene5.lookup("#searchField"); 
           	
           	//sätt vars
           	
           	
           	searchButtonEventHandeler();
           	
           	
           	}catch (Exception ex) {
		        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
           	}
 	}
 	
 	public void searchButtonEventHandeler(){

 	
 		grid.getChildren().clear();
 	  	//Checkbox
       	CheckBox barCheckBox = (CheckBox) scene5.lookup("#barCheck");

       	CheckBox gymCheckBox = (CheckBox) scene5.lookup("#gymCheck");

    	CheckBox poolCheckBox = (CheckBox) scene5.lookup("#poolCheck");

    	CheckBox petsCheckBox = (CheckBox) scene5.lookup("#petsCheck");

       	//Price Textareas
       	TextField priceFrom = (TextField) scene5.lookup("#priceFrom");

       	TextField priceTo = (TextField) scene5.lookup("#PriceTo");
       	
       	//System.out.println(textf.getText().toString());

       	//Slider for stars
       	Slider starSlide = (Slider) scene5.lookup("#starSlide");
//       	Slider starSlide2 = (Slider) scene.lookup("#starSlide");
//       	starSlide.setValue(starSlide2.getValue());
       	
       	try{
 		try {
			Hotel[] hotels = HotelSearch.searchHotelsBy(Double.parseDouble(priceFrom.getText().toString()), Double.parseDouble(priceTo.getText().toString()), (int)Math.round(starSlide.getValue()), barCheckBox.isSelected(),  poolCheckBox.isSelected(),  gymCheckBox.isSelected(), petsCheckBox.isSelected(), textf.getText().toString());
			
			addSearchGrid2(hotels);
 		} catch (SQLException e) {
			e.printStackTrace();
		}
 				
       	}catch(NumberFormatException e){
       		System.out.println("problem");
       	}
 	}
 		
	//Event when login butten is pressed from main stage
	private void loginEvent(ActionEvent userLogin){	

		try {
			
			//Show the login Screen
            userLoginStage = new Stage();
            userLoginScreen = FXMLLoader.load(
                Main.class.getResource("ProjectLoginSystem.fxml"));
            userLoginStage.setScene(new Scene(userLoginScreen));
            userLoginStage.setTitle("Login");
            userLoginStage.initModality(Modality.WINDOW_MODAL);
            userLoginStage.initOwner(
                ((Node)userLogin.getSource()).getScene().getWindow() );
            userLoginStage.show();  
            
            Button regButton = (Button) userLoginScreen.lookup("#registerUser");
            regButton.setOnAction(this::regUser);
          
            Button loggedInButton = (Button) userLoginScreen.lookup("#loggedInButton");
            loggedInButton.setOnAction(this::loggedInEvent);
            
			} catch (Exception ex) {
	        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	        }	
	}
	
	//Event when the user login button on the login screen is pressed (Show the add new screen)
	
	//User Logging in
	private void loggedInEvent(ActionEvent login){

		try {      	
           	TextField username = (TextField) userLoginScreen.lookup("#loginUserName");
           
           	PasswordField password = (PasswordField) userLoginScreen.lookup("#loginPassword");
 	
           	user = Login.User(username.getText().toString(), password.getText().toString());
           	
           	Label loginError = (Label) userLoginScreen.lookup("#errorLoginMessage"); //Main.
           	if(user.userExist()){
           		
           		loginError.setVisible(false);
           		userLoginStage.close();
           		theStage.setScene(scene);
           		Button loginButton = (Button) scene.lookup("#loginButton");
               	loginButton.setOnAction(this::logOutEvent);
               	loginButton.setText("Logout");
               	
           	}else{
           		
           		loginError.setVisible(true);
           		loginError.setText("Incorrect username or password");
           	} 	
		} catch (Exception ex) {
        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
		
	}
	
	//User Logout event
	private void logOutEvent(ActionEvent UserLogout){


		/**
		 * This event will be runned when the logged in 
		 * User press the "LogOut Button"
		 */
		user = null;
		 theStage.setScene(scene);
		Button loginButton = (Button) scene.lookup("#loginButton");
       	loginButton.setOnAction(this::loginEvent);
       	loginButton.setText("UserLogin");
       	
	}
	
	//Register a new User
	private void regUser(ActionEvent regUser) {
		try {
			CreateAccount.newRegistrer();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Create a new Hotel as admin
	//Button event for grabbig the text in the textfields and add it to the database
	private void createNewHotel(ActionEvent CreateNew){



		try{

			Integer.parseInt(starsf.getText());
			  if(ImageTransfer.createNewImageTransfer(imgPathf.getText())){
			    	
			    
			Hotel hotel = new Hotel(textf.getText(), adressf.getText(), Double.parseDouble(pricef.getText()), poolcb.isSelected(), gymcb.isSelected(), barcb.isSelected(), petscb.isSelected(), Integer.parseInt(starsf.getText()),ImageTransfer.imagePath);
	    	DBConnection.connectInputDB(hotel);
			}
			  
	    	Button update = (Button) addNew.lookup("#loadButton");
	    	update.arm();
	    	update.fire();
	    	
		}catch(NumberFormatException e){
	    		
	    }
		
		
	
	}
	
	//ButtonEvent for loading in all hotels that is in the database
	//Load hotels as a logged in admin
	private void loadHotels(ActionEvent loadHotels){



			//Clear
		   DBConnection.clearOutput();
		   
		   //Run the output
		   DBConnection.connectOutputDb();
	}
	
	//Method for going back to the startpage
	//Go back to the start page when start button is pressed
	private void backToStart(ActionEvent backToStart){

		/**
		 * Event for when the start button is pressed
		 */

		try {
			
            theStage.setScene(scene);
            
	      
		} catch (Exception ex) {
        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
		}
	
	//Method for when the admin has pressed the login button
	//Log in as admin
	private void adminLogin(ActionEvent adminLogin){



		/**
		 * Event for when the admin login button is pressed
		 * Sets the scene to adminLogin.fxml
		 */

		try {	
            PopupStage = new Stage();
             adminLoginScreen = FXMLLoader.load(
                Main.class.getResource("adminLogin.fxml"));
            PopupStage.setScene(new Scene(adminLoginScreen));
            PopupStage.setTitle("Admin Login");
            PopupStage.initModality(Modality.WINDOW_MODAL);
            PopupStage.initOwner(
                ((Node)adminLogin.getSource()).getScene().getWindow() );
            PopupStage.show();
            
            Button loggedInButton = (Button) adminLoginScreen.lookup("#loggedInButton"); //page4
            loggedInButton.setOnAction(this::admin);
            
			} catch (Exception ex) {
	        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	        }	
	}
	
	//Method that displays the "Add new hotel" view
	//Set the scene to the one where the admin can add/view hotels
	private void admin(ActionEvent admin){



		try {      	
	           
           	TextField username = (TextField) adminLoginScreen.lookup("#loginUserName");
           
           	PasswordField password = (PasswordField) adminLoginScreen.lookup("#loginPassword");
 	
           	user = Login.User(username.getText().toString(), password.getText().toString());
           	
           	Text loginError = (Text) adminLoginScreen.lookup("#errorLoginMessage");
           	if(user.userExist() && user.getUserType().equals("A")){
           		
               	try {
        			
        			//Show the Input of Hotel Screen.
                    Pane page2 = (Pane) FXMLLoader.load(Main.class.getResource("Simple.fxml"));
                    addNew = new Scene(page2);
                    theStage.setScene(addNew);
                    PopupStage.close();
                    

                    //Button for creating a new hotel
                    Button create = (Button) page2.lookup("#CreateButton");
                    create.setOnAction(this::createNewHotel);
                    
                    //Button for loading the existing hotels gets clicked automatically
                    Button load = (Button) page2.lookup("#loadButton");
                    load.setOnAction(this::loadHotels);
                    load.arm();
                    load.fire();
                    load.setVisible(false);
                    
                   /**
                    *  Add an image to the entry
                    */
                    imgPathf = (TextField) page2.lookup("#filePath");
                    
                    FileChooser fileChooser = new FileChooser();
                    Button selectImage = (Button) page2.lookup("#selectImage");
            		selectImage.setOnAction(
            				new EventHandler<ActionEvent>() {
            				@Override
            					public void handle(final ActionEvent e) {
            					File file = fileChooser.showOpenDialog(theStage);
            						if(file != null){
            							openFile(file);
            							filePathText = file.toString();
            							imgPathf.setText(file.getAbsolutePath());
            						    docsUrl = file.toURI().toString();
            						    docsUrl=docsUrl.substring(0,docsUrl.lastIndexOf('/') + 1);
            						    filePathText = docsUrl;
            						  
            						}
            					}
            				});

                   	//Button for logging out and return to main page
                   	Button logOutButton = (Button) page2.lookup("#logOutButton");
                   	logOutButton.setOnAction(DBConnection::secureLogout); 
                    
                    //Textfields for input
                    textf = (TextField) page2.lookup("#HotelNameTextb");
                    pricef = (TextField) page2.lookup("#PriceTextb");
                    adressf = (TextField) page2.lookup("#AddressTextb");
                    starsf = (TextField) page2.lookup("#StarsTextb");
                    
                    //Checkboxes for input
                    poolcb = (CheckBox) page2.lookup("#PoolCheck");
                    barcb = (CheckBox) page2.lookup("#BarCheck");
                    gymcb = (CheckBox) page2.lookup("#GymCheck");
                    petscb = (CheckBox) page2.lookup("#PetsCheck");

        			} catch (Exception ex) {
        	        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        	        }
           	}else{
           		
           		loginError.setVisible(true);
           		loginError.setText("Incorrect username or password");
           	}

           	
		} catch (Exception ex) {
        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		

		/**
		 * Event for showing the Screen where a administrator can add a new hotel
		 * And view the hotels that belongs to that administrator.
		 */
		
	}

	//Open and select an image to the hotel entry
	//Open the dialog window to choose a file
	
	//Event for adding a picture to the hotel entry
	private void openFile(File file) {



		/**
		 * Method for adding a file to the hotel entry
		 */

        try {
            desktop.open(file);
        } catch (IOException ex) {
            Logger.getLogger(
            FileChooser.class.getName()).log(
            Level.SEVERE, null, ex
            );
        	}
    }

	
	//Main Method
	//Main method
	//Main method
	static public boolean firstTimeSetUp(){
		
		//FileChooser fileChooser = new FileChooser();
		DirectoryChooser dirChooser=new DirectoryChooser();
		
		if(!Properties.propertyExists("imgPath")){
			File file = dirChooser.showDialog(theStage);
			dirChooser.setInitialDirectory(file);
			String relative=null;
			if(file != null){
				Desktop desktop = Desktop.getDesktop(); 	
				// desktop.open(file);
				docsUrl=file.getAbsolutePath();
				String baseUrl=new File("").getAbsolutePath();
				relative = new File(baseUrl).toURI().relativize(new File(docsUrl).toURI()).getPath();
				//System.out.println(relative);
				Properties.addProperty("imgPath", relative);
			}else{
				System.exit(0);
			}
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("First Setup");
			alert.setHeaderText("Import");
			alert.setContentText("Do you want to import images?");
			
			ButtonType yes = new ButtonType("Yes");
			ButtonType no = new ButtonType("No");			
			alert.getButtonTypes().setAll(yes, no);
			Optional<ButtonType> result = alert.showAndWait();	
			if(result.get() == yes){
				FileChooser fileChooser=new FileChooser();
				File[] fileList=new File[100];
				List<File> test=fileChooser.showOpenMultipleDialog(theStage);
				for(int i=0;i<test.size();i++){
					//System.out.println(test.toArray()[i].toString());
					try {
						new ImageTransfer(test.toArray()[i].toString(),Properties.getProperty("imgPath"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		}
		
		if(!Properties.propertyExists("databasePath")){
			File file = dirChooser.showDialog(theStage);
			dirChooser.setInitialDirectory(file);
			if(file != null){
				Desktop desktop = Desktop.getDesktop(); 	
				// desktop.open(file);
				docsUrl=file.getAbsolutePath();
				String baseUrl=new File("").getAbsolutePath();
				String relative = new File(baseUrl).toURI().relativize(new File(docsUrl).toURI()).getPath();
				//System.out.println(relative);
				Properties.addProperty("databasePath", relative);
				
			}else{
				System.exit(0);
			}
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("First Setup");
			alert.setHeaderText("Import");
			alert.setContentText("Do you want to import a database?");
			
			ButtonType yes = new ButtonType("Yes");
			ButtonType no = new ButtonType("No");			
			alert.getButtonTypes().setAll(yes, no);
			Optional<ButtonType> result = alert.showAndWait();	
			if(result.get() == yes){
				FileChooser fileChooser=new FileChooser();
				file=fileChooser.showOpenDialog(theStage);
				try {
					new ImageTransfer(file.getAbsolutePath(),Properties.getProperty("imgPath"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}else{
				//new Database();
			}
		}
		
		
		
		
		
//		System.out.println("First time setup");
//		Scanner scan=new Scanner(System.in);
//		System.out.println("select where to store your images");
//		Properties.addProperty("imgPath", filePathText);
//		Path path=Paths.get(Properties.getProperty("imgPath"));
//		String pathBuilder="";
//		for(int i=0;i<path.toString().split("/").length;i++){
//			pathBuilder=pathBuilder+path.toString().split("/")[i].toString()+"/";
//			if(Files.notExists(Paths.get(pathBuilder))){
//				new File(pathBuilder).mkdir();
//			}else{
//				break;
//			}		
//		}
//
//		System.out.println("select your database location");
//		Properties.addProperty("databasePath", scan.next());
//		path=Paths.get(Properties.getProperty("databasePath"));
//		pathBuilder="";
//		for(int i=0;i<path.toString().split("/").length;i++){
//			pathBuilder=pathBuilder+path.toString().split("/")[i].toString()+"/";
//			if(Files.notExists(Paths.get(pathBuilder))){
//				new File(pathBuilder).mkdir();
//			}else{
//				break;
//			}
//			new Database(Paths.get(pathBuilder));
//		}
		
		dbAddress="jdbc:sqlite:"+Properties.getProperty("databasePath")+"Hotels.sqlite";

		return false;
	}
	
	public static void main(String[] args) throws  Exception {
		
		dbAddress="jdbc:sqlite:"+Properties.getProperty("databasePath")+"Hotels.sqlite";
		
		Application.launch(Main.class, (java.lang.String[])null);
		   
		   
	   	}

}
