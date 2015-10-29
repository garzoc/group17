/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 */
package application;

import java.util.logging.*;

import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;
import java.lang.*;
import javafx.scene.control.*;
import javafx.event.*;

public class Main extends Application {

    /**
     * @param args the command line arguments
     */
	
	TextField textf = new TextField();
	
    public static void main(String[] args) {
        Application.launch(Main.class, (java.lang.String[])null);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            TabPane page = (TabPane) FXMLLoader.load(Main.class.getResource("simple.fxml"));
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Hotels");
            primaryStage.show();
            
            textf = (TextField) scene.lookup("#HotelNameTextb");
            Button create = (Button) scene.lookup("#CreateButton");
            create.setOnAction(this::handleButtonAction);
            
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void handleButtonAction (ActionEvent event){
    	textf.appendText("Tjena");
    }
}







