package HotelsDSSV2;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
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
		static TextField[] imagePathLabel=new TextField[numShownHotels];
		
		//Img
		static ImageView[]imgView = new ImageView[numShownHotels];
		static File[] file = new File[numShownHotels];
		static Image[] image = new Image[numShownHotels];
		
		//Buttons
		static Button []edit = new Button[numShownHotels];
		static Button []delete = new Button[numShownHotels];
		static Button []save = new Button[numShownHotels];
		
		 //ID
		static int[] hotelId = new int[numShownHotels];
		static boolean[] underEdit = new boolean[numShownHotels];
		
		
		
		
		static ScrollPane sc;
		static VBox container = (VBox) Main.addNew.lookup("#hotelDataContainer");
		
		/*AUTHOR: Emanuel Mellblom*/
		/*Contributors: John Sundling*/
		//Add hotel method
	public static void addHotel(Connection connection, PreparedStatement preparedStatement, String name, String address,
			double price, int stars, boolean pool, boolean gym, boolean bar, boolean pets, String imgPath, int userId)
					throws SQLException {

		// deleted Rating, Popularity. Added= Adress,
		String imageName = "";
		if (imgPath.lastIndexOf("/") > -1) {
			imageName = imgPath.substring(imgPath.lastIndexOf("/") + 1, imgPath.length());
		} else {
			imageName = imgPath;
		}

		if (imageName.lastIndexOf("\\") > -1) {
			imageName = imageName.substring(imageName.lastIndexOf("\\") + 1, imageName.length());
		}

		preparedStatement = connection
				.prepareStatement("INSERT INTO Hotel (Name, Address, Price, Stars, Pool, Gym, Bar, Pets, Img, UID)"
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)");
		preparedStatement.setString(1, name); // name
		preparedStatement.setString(2, address); // rating
		preparedStatement.setDouble(3, price);
		preparedStatement.setInt(4, stars);
		preparedStatement.setBoolean(5, pool);
		preparedStatement.setBoolean(6, gym);
		preparedStatement.setBoolean(7, bar);
		preparedStatement.setBoolean(8, pets);
		preparedStatement.setString(9, imageName);
		preparedStatement.setInt(10, userId);
		preparedStatement.executeUpdate();
	}
		
	/*AUTHOR: Emanuel Mellblom*/
	// Input hotel method
	public static void connectInputDB(Hotel hotel) {

		Statement statement = null;
		try {
			// Setting up the connection to the database
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection(dbAddress);

			statement = connection.createStatement();
			// System.out.println("database connected from connect input");

			// here the hotel is added to the database
			// System.out.println(Main.user);
			DBConnection.addHotel(connection, preparedStatement, hotel.getHotelName(), hotel.getHotelAddress(),
					hotel.getPrice(), hotel.getStars(), hotel.getPool(), hotel.getGym(), hotel.getBar(),
					hotel.getPets(), hotel.getImgPath(), Main.user.getUserId()); // Hotel.getHotelName,
																					// etc...
																					// //rating=deleted
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
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
	}
		
	/*AUTHOR: Emanuel Mellblom*/
	/*Contributors: John Sundling*/
	public static void secureLogout(ActionEvent logout) {
		user = null;
		for (int i = 0; i < underEdit.length; i++) {
			if (underEdit[i]) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Warning");
				alert.setHeaderText("Changes has been made to Hotel " + hotelLabel[i].getText().toString());
				alert.setContentText("Do you want to save these changes?");

				ButtonType yes = new ButtonType("Yes");
				ButtonType no = new ButtonType("No");

				alert.getButtonTypes().setAll(yes, no);
				Optional<ButtonType> result = alert.showAndWait();
				// if (result.get() == ButtonType.OK){
				Statement statement = null;
				if (result.get() == yes) {
					try {
						// Setting up the connection to the database
						Class.forName("org.sqlite.JDBC");
						connection = DriverManager.getConnection(dbAddress);
						statement = connection.createStatement();
						String imgPath = imagePathLabel[i].getText().toString();
						String imageName = "";
						if (imgPath.lastIndexOf("/") > -1) {
							imageName = imgPath.substring(imgPath.lastIndexOf("/") + 1, imgPath.length());
						} else {
							imageName = imgPath;
						}

						if (imageName.lastIndexOf("\\") > -1) {
							imageName = imageName.substring(imageName.lastIndexOf("\\") + 1, imageName.length());
						}

						int pool = (poolLabel[i].isSelected()) ? 1 : 0;

						int bar = (barLabel[i].isSelected()) ? 1 : 0;

						int gym = (gymLabel[i].isSelected()) ? 1 : 0;

						int pets = (petsLabel[i].isSelected()) ? 1 : 0;
						String update = "Update Hotel SET Name = '" + hotelLabel[i].getText().toString()
								+ "',Address = '" + addressLabel[i].getText().toString() + "',Stars = '"
								+ Integer.parseInt(starsLabel[i].getText().toString()) + "',Pool = '" + pool
								+ "',Gym = '" + gym + "',Pets = '" + pets + "',Bar = '" + bar + "',Price = '"
								+ Double.parseDouble(priceLabel[i].getText().toString()) + "', Img = '" + imageName
								+ "' where HID== '" + hotelId[i] + "'";
						statement.executeUpdate(update);

						underEdit[i] = false;
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
						try {
							statement.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							// e.printStackTrace();
						}
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
	
	/*AUTHOR: Emanuel Mellblom*/
	/*Contributors: John Sundling*/
		//Output Hotel method
	public static void connectOutputDb() {

		Connection connection = null;
		Statement statement = null;
		try {
			// Setting up the connection to the database
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection(dbAddress);
			statement = connection.createStatement();

			// Run the forloop
			addTheBox(Main.addNew); // Main.addNew)

			// Resultset for outputting the hotels
			ResultSet viewing = statement
					.executeQuery("SELECT * FROM Hotel where UID == '" + Main.user.getUserId() + "'");

			/**
			 * With this you add all items into one array
			 */

			int tupleCount = 0;
			while (viewing.next()) {
				hotelId[tupleCount] = viewing.getInt("HID");
				hotelLabel[tupleCount].setText(viewing.getString("Name"));
				addressLabel[tupleCount].setText(viewing.getString("Address"));
				priceLabel[tupleCount].setText("" + viewing.getDouble("Price"));
				starsLabel[tupleCount].setText("" + viewing.getInt("Stars"));
				imagePathLabel[tupleCount].setText(viewing.getString("Img"));
				// Set the boolean values

				if (viewing.getBoolean("Pool") == true) {
					poolLabel[tupleCount].setText("Pool: " + "yes");
					poolLabel[tupleCount].setSelected(true);
				} else {
					poolLabel[tupleCount].setText("Pool: " + "No");
					poolLabel[tupleCount].setSelected(false);
				}

				if (viewing.getBoolean("Gym") == true) {
					gymLabel[tupleCount].setText("Gym: " + "Yes");
					gymLabel[tupleCount].setSelected(true);
				} else {
					gymLabel[tupleCount].setText("Gym: " + "No");
					gymLabel[tupleCount].setSelected(false);
				}
				if (viewing.getBoolean("Pets") == true) {
					petsLabel[tupleCount].setText("Pets: " + "Yes");
					petsLabel[tupleCount].setSelected(true);
				} else {
					petsLabel[tupleCount].setText("Pets: " + "No");
					petsLabel[tupleCount].setSelected(false);
				}
				if (viewing.getBoolean("Bar") == true) {
					barLabel[tupleCount].setText("Bar: " + "Yes");
					barLabel[tupleCount].setSelected(true);
				} else {
					barLabel[tupleCount].setText("Bar: " + "No");
					barLabel[tupleCount].setSelected(false);
				}
				File file = new File(Properties.getProperty("imgPath") + viewing.getString("Img"));

				String localUrl = null;
				try {
					localUrl = file.toURI().toURL().toString();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				Image localImage = new Image(localUrl, false);
				imgView[tupleCount].setImage(localImage);
				imgView[tupleCount].setStyle("-fx-effect: dropshadow( gaussian , black, 0,0,2,2 )");

				tupleCount++;

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
			try {
				statement.close();
			} catch (SQLException e) {
			}
		}
	}

	/*AUTHOR: Emanuel Mellblom*/
	/*Contributors: John Sundling*/
		//Database Connection method
	public static int databaseTupleCounter() {

		connection = null;
		Statement statement = null;
		int magicNum = 0;
		try {
			// Setting up the connection to the database
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection(dbAddress);
			statement = connection.createStatement();
			// Statement state = connection.createStatement();
			// System.out.println("database connected from tuple counter");

			// Resultset for outputting the hotels
			ResultSet viewing = statement
					.executeQuery("SELECT * FROM Hotel where UID == '" + Main.user.getUserId() + "'");

			while (viewing.next()) {

				magicNum++;
			}
			return magicNum;

		} catch (ClassNotFoundException error) {
			System.out.println("Error: " + error.getMessage());
			return magicNum;
		} catch (SQLException error) {
			System.out.println("Error: " + error.getMessage());
			return magicNum;
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException ignore) {
				}
			try {
				statement.close();
			} catch (SQLException e) {
			}

		}
	}
		
	/*AUTHOR: Emanuel Mellblom*/
	/*Contributors: John Sundling*/
		//Add new Vboxes
	public static void addTheBox(Scene addNew) {

		int loopLength = 0;

		if (numShownHotels > databaseTupleCounter()) {
			loopLength = databaseTupleCounter();
		} else {
			loopLength = numShownHotels;
		}

		for (int i = 0; i < loopLength; i++) {
			hotelContainer[i] = new VBox();
			hotelContainer[i].setPrefHeight(156);
			hotelContainer[i].setLayoutY(i * 156);
			hotelContainer[i].setStyle("-fx-border-color: black;" + "align:left;");

			// Labels
			Label name = new Label("Name:");
			hotelLabel[i] = new TextField();
			hotelLabel[i].setStyle("-fx-border-color: black;");
			hotelLabel[i].setMaxWidth(600);
			hotelLabel[i].setEditable(false);
			hotelLabel[i].setStyle("-fx-background-color:rgba(255,255,255,0);" + "align:left;");

			Label address = new Label("Address:");
			addressLabel[i] = new TextField();
			addressLabel[i].setEditable(false);
			addressLabel[i].setMaxWidth(600);
			addressLabel[i].setStyle("-fx-background-color:rgba(255,255,255,0);" + "");

			Label stars = new Label("Stars:");
			starsLabel[i] = new TextField();
			starsLabel[i].setEditable(false);
			starsLabel[i].setMaxWidth(50);
			starsLabel[i].setStyle("-fx-background-color:rgba(255,255,255,0);" + "");

			poolLabel[i] = new CheckBox();
			// poolLabel[i].setEditable(false);
			poolLabel[i].setMaxWidth(600);
			poolLabel[i].setDisable(true);

			gymLabel[i] = new CheckBox();
			// gymLabel[i].setEditable(false);
			gymLabel[i].setMaxWidth(600);
			gymLabel[i].setDisable(true);

			petsLabel[i] = new CheckBox();
			// petsLabel[i].setEditable(false);
			petsLabel[i].setMaxWidth(600);
			petsLabel[i].setDisable(true);

			barLabel[i] = new CheckBox();
			// barLabel[i].setEditable(false);
			barLabel[i].setMaxWidth(600);
			barLabel[i].setDisable(true);

			Label price = new Label("Price:");
			priceLabel[i] = new TextField();
			priceLabel[i].setEditable(false);
			priceLabel[i].setMaxWidth(100);
			priceLabel[i].setStyle("-fx-background-color:rgba(255,255,255,0);");
			// Img
			imgView[i] = new ImageView();
			imgView[i].setFitHeight(150);
			imgView[i].setFitWidth(120);

			imagePathLabel[i] = new TextField();
			imagePathLabel[i].setEditable(false);
			imagePathLabel[i].setMaxWidth(600);
			imagePathLabel[i].setStyle("-fx-background-color:rgba(255,255,255,0);");

			// Buttons
			int posAti = i;
			edit[i] = new Button("Edit");
			// save[i] = new Button("Save");
			delete[i] = new Button("Delete hotel");
			delete[i].setVisible(false);
			int num = i;
			delete[i].setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(final ActionEvent e) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Delate");
					alert.setHeaderText("Warning");
					alert.setContentText("You're about to delete an entry press yes to continue");
					ButtonType yes = new ButtonType("Yes");
					ButtonType no = new ButtonType("No");
					alert.getButtonTypes().setAll(yes, no);
					Optional<ButtonType> result = alert.showAndWait();

					if (result.get() == yes) {
						Statement statement = null;
						try {
							// Setting up the connection to the database
							Class.forName("org.sqlite.JDBC");
							connection = DriverManager.getConnection(dbAddress);
							statement = connection.createStatement();
							String update = "DELETE FROM Hotel where HID=='" + hotelId[num] + "'";
							statement.executeUpdate(update);

							// for(int i=0;i<hotelLabel.length;i++){
							//
							// }
							hotelLabel = new TextField[numShownHotels];
							addressLabel = new TextField[numShownHotels];
							starsLabel = new TextField[numShownHotels];
							poolLabel = new CheckBox[numShownHotels];
							gymLabel = new CheckBox[numShownHotels];
							petsLabel = new CheckBox[numShownHotels];
							barLabel = new CheckBox[numShownHotels];
							priceLabel = new TextField[numShownHotels];
							imagePathLabel = new TextField[numShownHotels];
							hotelId = new int[numShownHotels];
							// Img
							imgView = new ImageView[numShownHotels];
							file = new File[numShownHotels];
							image = new Image[numShownHotels];

							// statement.executeUpdate(update);
						} catch (ClassNotFoundException error) {
						} catch (SQLException error) {
						} finally {
							if (connection != null) {
								try {
									connection.close();
								} catch (SQLException ignore) {

								}
								try {
									statement.close();
								} catch (SQLException er) {
									// TODO Auto-generated catch block
									// e.printStackTrace();
								}
							}

						}
						edit[num].arm();
						edit[num].fire();
					}
				}
			});

			edit[i].setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(final ActionEvent e) {
					hotelLabel[num].setEditable(true);
					hotelLabel[num]
							.setStyle("-fx-background-color:rgba(255,230,230,0.7);" + "-fx-border-style:dashed;");
					addressLabel[num].setEditable(true);
					addressLabel[num]
							.setStyle("-fx-background-color:rgba(255,230,230,0.7);" + "-fx-border-style:dashed;");
					starsLabel[num].setEditable(true);
					starsLabel[num]
							.setStyle("-fx-background-color:rgba(255,230,230,0.7);" + "-fx-border-style:dashed;");
					poolLabel[num].setDisable(false);
					gymLabel[num].setDisable(false);
					petsLabel[num].setDisable(false);
					barLabel[num].setDisable(false);
					priceLabel[num].setEditable(true);
					priceLabel[num]
							.setStyle("-fx-background-color:rgba(255,230,230,0.7);" + "-fx-border-style:dashed;");
					imagePathLabel[num]
							.setStyle("-fx-background-color:rgba(255,230,230,0.7);" + "-fx-border-style:dashed;");
					edit[num].setText("Save");
					delete[num].setVisible(true);
					underEdit[num] = true;

					imgView[num].addEventFilter(MouseEvent.MOUSE_CLICKED, x -> {
						FileChooser fileChooser = new FileChooser();
						File file = fileChooser.showOpenDialog(theStage);
						if (file != null) {
							try {
								Desktop desktop = Desktop.getDesktop();
								desktop.open(file);
							} catch (IOException ex) {
								Logger.getLogger(FileChooser.class.getName()).log(Level.SEVERE, null, ex);
							}
							filePathText = file.toString();
							imgPathf.setText(file.getAbsolutePath());
							docsUrl = file.toURI().toString();
							docsUrl = docsUrl.substring(docsUrl.indexOf(':') + 1, docsUrl.length());
							filePathText = docsUrl;
							if (ImageTransfer.createNewImageTransfer(filePathText)) {
								imagePathLabel[num].setText(ImageTransfer.imagePath);
							}
						}

					});

					edit[num].setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(final ActionEvent e) {
							Statement statement = null;
							try {
								// Setting up the connection to the database
								Class.forName("org.sqlite.JDBC");
								connection = DriverManager.getConnection(dbAddress);
								statement = connection.createStatement();
								for (int i = 0; hotelLabel[i] != null; i++) {
									// System.out.println(poolLabel[i]);
									String imgPath = imagePathLabel[i].getText().toString();
									String imageName = "";
									if (imgPath.lastIndexOf("/") > -1) {
										imageName = imgPath.substring(imgPath.lastIndexOf("/") + 1, imgPath.length());
									} else {
										imageName = imgPath;
									}
									if (imageName.lastIndexOf("\\") > -1) {
										imageName = imageName.substring(imageName.lastIndexOf("\\") + 1,
												imageName.length());
									}
									int pool = (poolLabel[i].isSelected()) ? 1 : 0;

									int bar = (barLabel[i].isSelected()) ? 1 : 0;

									int gym = (gymLabel[i].isSelected()) ? 1 : 0;

									int pets = (petsLabel[i].isSelected()) ? 1 : 0;

									String update = "Update Hotel SET Name = '" + hotelLabel[i].getText().toString()
											+ "',Address = '" + addressLabel[i].getText().toString() + "',Stars = '"
											+ Integer.parseInt(starsLabel[i].getText().toString()) + "',Pool = '" + pool
											+ "',Gym = '" + gym + "',Pets = '" + pets + "',Bar = '" + bar
											+ "',Price = '" + Double.parseDouble(priceLabel[i].getText().toString())
											+ "',Img = '" + imageName + "' where HID== '" + hotelId[i] + "'";
									statement.executeUpdate(update);
								}
								Button button = (Button) Main.addNew.lookup("#loadButton");
								button.arm();
								button.fire();

								underEdit[num] = false;

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
								try {
									statement.close();
								} catch (SQLException er) {
									// TODO Auto-generated catch block
									// e.printStackTrace();
								}
							}
						}

					});
				}
			});

			hotelContainer[i].getChildren().addAll(name, hotelLabel[i], address, addressLabel[i], stars, starsLabel[i],
					poolLabel[i], gymLabel[i], petsLabel[i], barLabel[i], price, priceLabel[i], imgView[i],
					imagePathLabel[i], edit[i], delete[i]);
			container.getChildren().addAll(hotelContainer[i]);

			// System.out.println("i= " + i);

			container.setPrefHeight(156 * loopLength);// loopLength

			sc = (ScrollPane) addNew.lookup("#Scrolling");
			sc.setContent(container);
		}
	}

	// Clears the Output screen
	public static void clearOutput() {

		container.getChildren().clear();

	}
		
}
		
		



		




