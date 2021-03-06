package HotelsDSS;


import org.omg.CORBA.INITIALIZE;
import org.sqlite.JDBC.*;

import com.sun.javafx.collections.ImmutableObservableList;
import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.File;
import java.lang.ClassNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;

public class DBConnection extends Main{
	
		@FXML
		private static TableColumn hotelname;
		@FXML
		private static TableColumn hoteladress;
		@FXML
		private static TableColumn price;
		@FXML
		private static TableColumn stars;
		@FXML
		private static TableColumn pool;
		@FXML
		private static TableColumn gym;
		@FXML
		private static TableColumn bar;
		@FXML
		private static TableColumn pets;
		@FXML
		private static TableView hoteltable;
		static List<String> list = new ArrayList<String>();
		
		public static ObservableList<String> observableList;
		public static ObservableList<String> searchList;
		
		static Connection connection = null;
		static PreparedStatement preparedStatement = null;
		
		//static Label []hotelLabel = new Label[numShownHotels];
//		static Label []addressLabel = new Label[numShownHotels];
//		static Label []starsLabel = new Label[numShownHotels];
//		static Label []poolLabel = new Label[numShownHotels];
//		static Label []gymLabel = new Label[numShownHotels];
//		static Label []petsLabel = new Label[numShownHotels];
//		static Label []barLabel = new Label[numShownHotels];
//		static Label []priceLabel = new Label[numShownHotels];
		
		//Ny test med text field
		static TextField []hotelLabel = new TextField[numShownHotels];
		static TextField []addressLabel = new TextField[numShownHotels];
		static TextField []starsLabel = new TextField[numShownHotels];
		static CheckBox []poolLabel = new CheckBox[numShownHotels];
		static CheckBox []gymLabel = new CheckBox[numShownHotels];
		static CheckBox []petsLabel = new CheckBox[numShownHotels];
		static CheckBox []barLabel = new CheckBox[numShownHotels];
		static TextField []priceLabel = new TextField[numShownHotels];
		
		//Img
		static ImageView[]imgView = new ImageView[numShownHotels];
		static File[] file = new File[numShownHotels];
		static Image[] image = new Image[numShownHotels];
		
		//Buttons
		static Button []edit = new Button[numShownHotels];
		static Button []save = new Button[numShownHotels];
		
		 //ID
		static int hotelId[] = new int[numShownHotels];
		static boolean underEdit[] = new boolean[numShownHotels];
     	
		
		
		
		static ScrollPane sc;
		static VBox container = (VBox) Main.addNew.lookup("#hotelDataContainer");

		//Add hotel method
		public static void addHotel(Connection connection, PreparedStatement preparedStatement, String name, String address, double price, int stars, boolean pool, boolean gym, boolean bar, boolean pets,String imgPath, int userId) throws SQLException {
			
			//deleted Rating, Popularity. Added= Adress, 
			preparedStatement = connection.prepareStatement("INSERT INTO Hotel (Name, Address, Price, Stars, Pool, Gym, Bar, Pets, Img, UID)"
															+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)");
			preparedStatement.setString(1, name); //name
			preparedStatement.setString(2, address); //rating
			preparedStatement.setDouble(3, price);
			preparedStatement.setInt(4, stars);
			preparedStatement.setBoolean(5, pool);
			preparedStatement.setBoolean(6, gym);
			preparedStatement.setBoolean(7, bar);
			preparedStatement.setBoolean(8, pets);
			preparedStatement.setString(9, imgPath);
			preparedStatement.setInt(10, userId);
			preparedStatement.executeUpdate();
		}
		
		//Input hotel method
		public static void connectInputDB(Hotel hotel){



				try{
					//Setting up the connection to the database
					Class.forName("org.sqlite.JDBC");
					connection = DriverManager.getConnection("jdbc:sqlite:/Users/System/Dropbox/1mac/Hotels.sqlite");
					Statement statement = connection.createStatement();
					//System.out.println("database connected from connect input");
				
					//here the hotel is added to the database
					System.out.println(Main.user);
					DBConnection.addHotel(connection, preparedStatement, hotel.getHotelName(), hotel.getHotelAddress(), hotel.getPrice(), hotel.getStars(), hotel.getPool(), hotel.getGym(), hotel.getBar(), hotel.getPets(), hotel.getImgPath(), Main.user.getUserId());  //Hotel.getHotelName, etc... //rating=deleted
				}
				//database error handling 
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
		
		public static void secureLogout(ActionEvent logout){
			for(int i = 0; i< underEdit.length; i++){
				if(underEdit[i]){
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Warning");
					alert.setHeaderText("Changes has been made to Hotel " + hotelLabel[i].getText().toString());
					alert.setContentText("Do you want to save these changes?");
					
					ButtonType yes = new ButtonType("Yes");
					ButtonType no = new ButtonType("No");
					
					alert.getButtonTypes().setAll(yes, no);
					Optional<ButtonType> result = alert.showAndWait();
					//if (result.get() == ButtonType.OK){
						if(result.get() == yes){
						try{
	        				//Setting up the connection to the database
	        				Class.forName("org.sqlite.JDBC");
	        				connection = DriverManager.getConnection("jdbc:sqlite:/Users/System/Dropbox/1mac/Hotels.sqlite");
	        				Statement statement = connection.createStatement();
	        				
	        				String update = "Update Hotel SET Name = '" +hotelLabel[i].getText().toString() + "' where HID== '" + hotelId[i]  + "'";
	        				statement.executeUpdate(update);
	        				update = "Update Hotel SET Address = '" +addressLabel[i].getText().toString() + "' where HID== '" + hotelId[i]  + "'";
	        				statement.executeUpdate(update);
	        				update = "Update Hotel SET Stars = '" +Integer.parseInt(starsLabel[i].getText().toString()) + "' where HID== '" + hotelId[i]  + "'";
	        				statement.executeUpdate(update);
	        				update = "Update Hotel SET Pool = '" +poolLabel[i].isSelected() + "' where HID== '" + hotelId[i]  + "'";
	        				statement.executeUpdate(update);
	        				update = "Update Hotel SET Gym = '" +gymLabel[i].isSelected() + "' where HID== '" + hotelId[i]  + "'";
	        				statement.executeUpdate(update);
	        				update = "Update Hotel SET Pets = '" +petsLabel[i].isSelected() + "' where HID== '" + hotelId[i]  + "'";
	        				statement.executeUpdate(update);
	        				update = "Update Hotel SET Bar = '" +barLabel[i].isSelected() + "' where HID== '" + hotelId[i]  + "'";
	        				statement.executeUpdate(update);
	        				update = "Update Hotel SET Price = '" +Double.parseDouble(priceLabel[i].getText().toString()) + "' where HID== '" + hotelId[i]  + "'";
	        				statement.executeUpdate(update);
	        	            underEdit[i] = false;			
	        	    	}
	        			catch(ClassNotFoundException error) {
	        					System.out.println("Error: " + error.getMessage());
	        			}
	        			catch(SQLException error) {
	        			System.out.println("Error: " + error.getMessage());
	        			}
	        			finally{
	        			if(connection !=null) try{connection.close();} catch(SQLException ignore) {}
	        							
	        			 }	
					} else {
					    
					}
				}
			}
			try {
				
	            theStage.setScene(scene);
	            
		      
			} catch (Exception ex) {
	        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	        }
		}
		//Output Hotel method
		public static void connectOutputDb(){

			




			Connection connection = null;
	    	try{
				//Setting up the connection to the database
				Class.forName("org.sqlite.JDBC");
				connection = DriverManager.getConnection("jdbc:sqlite:/Users/System/Dropbox/1mac/Hotels.sqlite");
				Statement statement = connection.createStatement();
				//System.out.println("database connected from connect output");
				
				//Run the forloop
				addTheBox(Main.addNew); //Main.addNew)
				
				//Resultset for outputting the hotels
				ResultSet viewing = statement.executeQuery("SELECT * FROM Hotel where UID == '" + Main.user.getUserId() + "'");
				
				/**
				 * With this you add all items into one array
				 */

				 
				
		        int tupleCount = 0;
				while (viewing.next()){
					hotelId[tupleCount] = viewing.getInt("HID");
					//ObservableList<String> row = FXCollections.observableArrayList();
					hotelLabel[tupleCount].setText(viewing.getString("Name"));
					//observableList.add(viewing.getString("Name"));
					addressLabel[tupleCount].setText(viewing.getString("Address"));
					//observableList.add(viewing.getString("Address"));
					priceLabel[tupleCount].setText(""+viewing.getDouble("Price"));
					//observableList.add(viewing.getString("Price"));
			        starsLabel[tupleCount].setText(""+viewing.getInt("Stars"));
			      //  observableList.add(viewing.getString("Stars"));
			        
			        //Set the boolean values
			        
			        if(viewing.getBoolean("Pool") == true){ 
			        	poolLabel[tupleCount].setText("Pool: " + "yes");
			        	poolLabel[tupleCount].setSelected(true);
			        	//observableList.add("Yes");
			        }
			        else{
			        	//poolLabel[tupleCount].setText("Pool: "+"No");
			        	//observableList.add("No");
			        	poolLabel[tupleCount].setText("Pool: " + "No");
			        	poolLabel[tupleCount].setSelected(false);
			        }
			        
			        if(viewing.getBoolean("Gym") == true){
			        	gymLabel[tupleCount].setText("Gym: "+"Yes");
			        	gymLabel[tupleCount].setSelected(true);
			        	//observableList.add("Yes");
			        }
			        else{
			        	gymLabel[tupleCount].setText("Gym: "+ "No");
			        	gymLabel[tupleCount].setSelected(false);
			        	//observableList.add("No");
			        }
			        if(viewing.getBoolean("Pets") == true){
			        	petsLabel[tupleCount].setText("Pets: "+"Yes");
			        	petsLabel[tupleCount].setSelected(true);
			        	//observableList.add("Yes");
			        }
			        else{
			        	petsLabel[tupleCount].setText("Pets: "+"No");
			        	petsLabel[tupleCount].setSelected(false);
			        	//observableList.add("No");
			        }
			        if(viewing.getBoolean("Bar") == true){
			        	barLabel[tupleCount].setText("Bar: "+"Yes");
			        	barLabel[tupleCount].setSelected(true);
			        	//observableList.add("Yes");
			        }
			        else{
			        	barLabel[tupleCount].setText("Bar: "+"No");
			        	barLabel[tupleCount].setSelected(false);
			        	//observableList.add("No");
			        }
			        File file = new File(viewing.getString("Img"));
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
			        
			        
			       
			        tupleCount++;
			       // System.out.println("tuplecount= " + tupleCount);
			     
				}
				//This belongs to the add all to one array
//				System.out.println("Size: "+observableList.size());
				//System.out.println("the item: " + observableList.toString());
				//searchList = observableList;
			        
			}
			 catch(ClassNotFoundException error) {
				System.out.println("Error: " + error.getMessage());
			}
			catch(SQLException error) {
				System.out.println("Error: " + error.getMessage());
			}
			finally{
				if(connection !=null) try{connection.close();} catch(SQLException ignore) {}
							
			}
		}

		//Database Connection method
		public static int databaseTupleCounter(){






			
			Connection connection = null;
			int magicNum = 0;
			try{
				//Setting up the connection to the database
				Class.forName("org.sqlite.JDBC");
				connection = DriverManager.getConnection("jdbc:sqlite:/Users/System/Dropbox/1mac/Hotels.sqlite");
				Statement statement = connection.createStatement();
				//Statement state = connection.createStatement();
				//System.out.println("database connected from tuple counter");
				
				
				//Resultset for outputting the hotels
				ResultSet viewing = statement.executeQuery("SELECT * FROM Hotel where UID == '" + Main.user.getUserId() + "'");

				
				
				while (viewing.next()){
					
					magicNum++;
				}
				return magicNum;

				
	    	}
			 catch(ClassNotFoundException error) {
					System.out.println("Error: " + error.getMessage());
					return magicNum;
					}
					catch(SQLException error) {
					System.out.println("Error: " + error.getMessage());
					return magicNum;
						}
						finally{
							if(connection !=null) try{connection.close();} catch(SQLException ignore) {}
							
			 }	
	    }
		
		//Add new Vboxes
		public static void addTheBox(Scene addNew){ 



			int loopLength = 0; 
		
			if(numShownHotels > databaseTupleCounter()){
				loopLength = databaseTupleCounter();
			}else{
				loopLength=numShownHotels;
			}

			for(int i = 0; i<loopLength; i++){
				hotelContainer[i] = new VBox();
				hotelContainer[i].setPrefHeight(156);
				hotelContainer[i].setLayoutY(i*156);
	          	hotelContainer[i].setStyle("-fx-border-color: black;"
	          			+ "align:left;");
	          	
	          	//Labels
	          	Label name = new Label("Name:");
	          	hotelLabel[i]= new TextField();
	          	hotelLabel[i].setStyle("-fx-border-color: black;");
	          	hotelLabel[i].setMaxWidth(600);
	          	hotelLabel[i].setEditable(false);
	          	hotelLabel[i].setStyle("-fx-background-color:rgba(255,255,255,0);"
	          			+ "align:left;");
	          	
	          	Label address = new Label("Address:");
	          	addressLabel[i]= new TextField();
	          	addressLabel[i].setEditable(false);
	          	addressLabel[i].setMaxWidth(600);
	          	addressLabel[i].setStyle("-fx-background-color:rgba(255,255,255,0);"
	          			+ "");
	          	
	          	Label stars = new Label("Stars:");
	          	starsLabel[i] = new TextField();
	          	starsLabel[i].setEditable(false);
	          	starsLabel[i].setMaxWidth(50);
	          	starsLabel[i].setStyle("-fx-background-color:rgba(255,255,255,0);"
	          			+ "");
	          	
	          	poolLabel[i] = new CheckBox();
	          	//poolLabel[i].setEditable(false); 
	          	poolLabel[i].setMaxWidth(600);
	          	poolLabel[i].setDisable(true);
	          	
	          	gymLabel[i] = new CheckBox();
	          	//gymLabel[i].setEditable(false);
	          	gymLabel[i].setMaxWidth(600);
	          	gymLabel[i].setDisable(true);
	          	
	          	
	          	petsLabel[i] = new CheckBox();
	          	//petsLabel[i].setEditable(false);
	          	petsLabel[i].setMaxWidth(600);
	          	petsLabel[i].setDisable(true);
	          	
	          	barLabel[i] = new CheckBox();
	          	//barLabel[i].setEditable(false);
	          	barLabel[i].setMaxWidth(600);
	          	barLabel[i].setDisable(true);
	          	
	          	Label price = new Label("Price:");
	          	priceLabel[i] = new TextField();
	          	priceLabel[i].setEditable(false);
	          	priceLabel[i].setMaxWidth(100);
	          	priceLabel[i].setStyle("-fx-background-color:rgba(255,255,255,0);");
	          	//Img
	          	imgView[i] = new ImageView();
	         	imgView[i].setFitHeight(150);
	         	imgView[i].setFitWidth(120);
	         	//Buttons
	         	int posAti = i;
	         	edit[i] = new Button("Edit");
	         	//save[i] = new Button("Save");
	         	
	         	int num = i;
	         	
	         	edit[i].setOnAction(new EventHandler<ActionEvent>() {
	         		@Override
					public void handle(final ActionEvent e) {		
	         			hotelLabel[num].setEditable(true);
	         			hotelLabel[num].setStyle("-fx-background-color:rgba(255,230,230,0.7);"
	         					+ "-fx-border-style:dashed;");
	         			addressLabel[num].setEditable(true);
	         			addressLabel[num].setStyle("-fx-background-color:rgba(255,230,230,0.7);"
	         					+ "-fx-border-style:dashed;");
	         			starsLabel[num].setEditable(true);
	         			starsLabel[num].setStyle("-fx-background-color:rgba(255,230,230,0.7);"
	         					+ "-fx-border-style:dashed;");
	         			poolLabel[num].setDisable(false);
	         			gymLabel[num].setDisable(false);
	         			petsLabel[num].setDisable(false);
	         			barLabel[num].setDisable(false);
	         			priceLabel[num].setEditable(true);
	         			priceLabel[num].setStyle("-fx-background-color:rgba(255,230,230,0.7);"
	         					+ "-fx-border-style:dashed;");
	         			edit[num].setText("Save");
	         			underEdit[num] = true;
	         			
	         			edit[num].setOnAction(new EventHandler<ActionEvent>() {
	         				@Override
	    					public void handle(final ActionEvent e) {
	         					try{
	    	        				//Setting up the connection to the database
	    	        				Class.forName("org.sqlite.JDBC");
	    	        				connection = DriverManager.getConnection("jdbc:sqlite:/Users/System/Dropbox/1mac/Hotels.sqlite");
	    	        				Statement statement = connection.createStatement();
	    	        				for(int i=0;hotelLabel[i]!=null;i++){
	    	        					String update = "Update Hotel SET Name = '" +hotelLabel[i].getText().toString() + "' where HID== '" + hotelId[i]  + "'";
	    	        					statement.executeUpdate(update);
	    	        					update = "Update Hotel SET Address = '" +addressLabel[i].getText().toString() + "' where HID== '" + hotelId[i]  + "'";
	    	        					statement.executeUpdate(update);
	    	        					update = "Update Hotel SET Stars = '" +Integer.parseInt(starsLabel[i].getText().toString()) + "' where HID== '" + hotelId[i]  + "'";
	    	        					statement.executeUpdate(update);
	    	        					update = "Update Hotel SET Pool = '" +poolLabel[i].isSelected() + "' where HID== '" + hotelId[i]  + "'";
	    	        					statement.executeUpdate(update);
	    	        					update = "Update Hotel SET Gym = '" +gymLabel[i].isSelected() + "' where HID== '" + hotelId[i]  + "'";
	    	        					statement.executeUpdate(update);
	    	        					update = "Update Hotel SET Pets = '" +petsLabel[i].isSelected() + "' where HID== '" + hotelId[i]  + "'";
	    	        					statement.executeUpdate(update);
	    	        					update = "Update Hotel SET Bar = '" +barLabel[i].isSelected() + "' where HID== '" + hotelId[i]  + "'";
	    	        					statement.executeUpdate(update);
	    	        					update = "Update Hotel SET Price = '" +Double.parseDouble(priceLabel[i].getText().toString()) + "' where HID== '" + hotelId[i]  + "'";
	    	        					statement.executeUpdate(update);
	    	        				}
	    	        				Button button = (Button) Main.addNew.lookup("#loadButton");
	    	        				button.arm();
	    	        	            button.fire();
	    	        				
	    	        	            underEdit[num] = false;
	    	        				
	    	        				
	    	        	    	}
	    	        			catch(ClassNotFoundException error) {
	    	        					System.out.println("Error: " + error.getMessage());
	    	        			}
	    	        			catch(SQLException error) {
	    	        			System.out.println("Error: " + error.getMessage());
	    	        			}
	    	        			finally{
	    	        			if(connection !=null) try{connection.close();} catch(SQLException ignore) {}
	    	        							
	    	        			 }	
	    	        	    }
	         				
	         				});
					}
	         	});
//	         	save[i].setOnAction(new EventHandler<ActionEvent>() {
//	         		@Override
//					public void handle(final ActionEvent e) {
//	         			//System.out.println("save" + posAti);
//	         			//Save function goes here
//	         			//hotelLabel[num].getText().toString();
//	         			System.out.println(hotelLabel[num].getText().toString());
//	         			Connection connection = null;
//	        			int magicNum = 0;
//	        			
//	        			try{
//	        				//Setting up the connection to the database
//	        				Class.forName("org.sqlite.JDBC");
//	        				connection = DriverManager.getConnection("jdbc:sqlite:/Users/System/Dropbox/1mac/Hotels.sqlite");
//	        				
//	        				String update = "Update Hotel SET Name = '" +hotelLabel[num].getText().toString() + "' where == '" + hotels[num].getText().toString() + "'";
//	        				Statement statement = connection.createStatement();
//	        				statement.executeUpdate(update);
//	        				
//	        				//Statement update = connection.
//	        				//Statement state = connection.createStatement();
//	        				//System.out.println("database connected from tuple counter");
//	        				
//	        				
//	        				//Resultset for outputting the hotels
//	        				//ResultSet viewing = statement.executeQuery("SELECT * FROM Hotel");
//
//	        				
//	        				
//	        	    	}
//	        			 catch(ClassNotFoundException error) {
//	        					System.out.println("Error: " + error.getMessage());
//	        					
//	        					}
//	        					catch(SQLException error) {
//	        					System.out.println("Error: " + error.getMessage());
//	        				
//	        						}
//	        						finally{
//	        							if(connection !=null) try{connection.close();} catch(SQLException ignore) {}
//	        							
//	        			 }	
//	        	    }
//	         			
//					
//	         	});
	         	
	        
	          	hotelContainer[i].getChildren().addAll(name,hotelLabel [i], address,addressLabel [i],stars, starsLabel[i], poolLabel[i],gymLabel[i],petsLabel[i], barLabel[i], price, priceLabel[i], imgView[i], edit[i]);
	          	container.getChildren().addAll(hotelContainer[i]);
	          
	          	//System.out.println("i= " + i);
	          
	          	container.setPrefHeight(156*loopLength);//loopLength

	          	sc = (ScrollPane) addNew.lookup("#Scrolling");
	          	sc.setContent(container);
			}
		}
		
		//Clears the Output screen
		public static void clearOutput(){

			container.getChildren().clear();
			
		}
		
	}
		
		



		




