//	Author: Max Schumacher, Group W31

package application;

import database.DatabaseCheck;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Main extends Application {
	
	Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage)  throws Exception {
		
		launchLoginScreen(primaryStage);
		
	}
	
	public Stage launchLoginScreen(Stage primaryStage) {
		
		try {
			// Create login screen
			Parent root = FXMLLoader.load(getClass().getResource("/application/UserLoginPrompt.fxml"));
			Scene scene = new Scene(root,210,210);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
			primaryStage.setTitle("Login");
			this.primaryStage = primaryStage;
		} catch(LoadException e) {
			System.err.println("Error loading FXML: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return primaryStage;
	}
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
