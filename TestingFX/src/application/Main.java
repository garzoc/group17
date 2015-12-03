package application;
	
import java.util.logging.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class Main extends Application {
	
	public static void main(String[] args) {
        Application.launch(Main.class, (java.lang.String[])null);
    }
	
	public void start(Stage primaryStage) {
	        try {
	            TitledPane page = (TitledPane) FXMLLoader.load(Main.class.getResource("HotelViewerGUI.fxml"));
	            Scene scene1 = new Scene(page);
//	            scene1.getStylesheets().add("application/application.css");
	            primaryStage.setScene(scene1);
	            primaryStage.setTitle("Review");
	            primaryStage.show();
	            
	        } catch (Exception ex) {
	            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }
}
