/*	Author: Max Schumacher, Group W31
 * 	File: Controller.java
 * 	Purpose: A functional project management tool which can add, remove, and edit project data
 * 	Last modication: 04/02/23
 */

package application;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

import database.DatabaseCheck;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class LoginScreen {
	
	@FXML Button loginButton = new Button();
	@FXML Button createAccountButton;
	@FXML TextField usernameInput = new TextField();
	@FXML TextField passwordInput = new TextField();
	static boolean accessGranted = false;
	static int attempts = 0;
    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 32;
	
	public void loginUser() {
		String username = usernameInput.getText();
		String password = passwordInput.getText();
		
		if (!isValid(username, password)) {
			
			Alert alert = new Alert(Alert.AlertType.INFORMATION, "Invalid Character Entry Detected");
            alert.showAndWait();
			return;
		}
		
		if (DatabaseCheck.queryLogin(username, password)) {
			
			Stage currentStage = (Stage) loginButton.getScene().getWindow();
			currentStage.close();
			launchProjectManager(new Stage());
		} else if (attempts == 4) {
			System.out.println("Maximum Attempts Reached");
			Alert alert = new Alert(Alert.AlertType.INFORMATION, "Maximum Attempts Reached 5/5");
            alert.showAndWait();
			System.exit(5);
		} else {
			attempts++;
			Alert alert = new Alert(Alert.AlertType.INFORMATION, "Invalid Credentials. Attempt " + attempts + "/5");
	        alert.showAndWait();
		}
	}
	
	public void bypassDatabaseLogin() {
		System.out.println("Bypassing Database Login");
		Stage currentStage = (Stage) loginButton.getScene().getWindow();
		currentStage.close();
		launchProjectManager(new Stage());
	}
	
	public void createNewAccount() {
		launchCreateAccountPage(new Stage());
	}

	public static boolean checkAccess() {
		return accessGranted;
	}

	public static int getAttempts() {
		return attempts;
	}
	
	public Stage launchCreateAccountPage(Stage primaryStage) {
	    	// title of pop-up
	        primaryStage.setTitle("User Create Login");

	        // pop-up grid dimensions
	        GridPane grid = new GridPane();
	        grid.setAlignment(Pos.CENTER);
	        grid.setHgap(10);
	        grid.setVgap(10);
	        grid.setPadding(new Insets(25, 25, 25, 25));
	        
	        // Label to describe username and password requirements
	        Label messageLabel = new Label("Create a username and password that is between\n" + 
	        "8-32 characters. Use a variety of lowercase letters,\nuppercase letters, and numbers.");
	        messageLabel.setFont(Font.font("Tahoma", FontWeight.BOLD, 13));      // font detail
	        grid.add(messageLabel, 0, 0, 2, 1);                                  // placement on pop-up

	        // Label for username textbox
	        Label usernameLabel = new Label("Username:");
	        usernameLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));   // font detail
	        grid.add(usernameLabel, 0, 1);                                       // placement on pop-up

	        // Instruction in textbox for username
	        TextField usernameField = new TextField();
	        usernameField.setPromptText("Enter a username");
	        grid.add(usernameField, 1, 1);

	        // Label for password textbox
	        Label passwordLabel = new Label("Password:");
	        passwordLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
	        grid.add(passwordLabel, 0, 2);

	        // Instruction in textbox for password
	        PasswordField passwordField = new PasswordField();
	        passwordField.setPromptText("Enter a password");
	        grid.add(passwordField, 1, 2);

	        // Finished button, this will signify that the user is done creating
	        // a username and password. This will lead to checking if the requirements were met
	        Button registerButton = new Button("Create Account");
	        registerButton.setOnAction(e -> {
	        	// try-catch to check if requirements were met
	            try {
	                String username = usernameField.getText();
	                String password = passwordField.getText();
	                if (isValid(username, password)) {   // requirements met
	                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Registration successful!");
	                    alert.showAndWait();
	                    primaryStage.close();
	                } else {      // requirements not met
	                    Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid username or password.");
	                    alert.showAndWait();
	                }
	            } catch (Exception ex) {
	                Alert alert = new Alert(Alert.AlertType.ERROR, "An error occurred: " + ex.getMessage());
	                alert.showAndWait();
	            }
	        });

	        grid.add(registerButton, 1, 3);  // placement of button

	        Scene scene = new Scene(grid, 400, 250);
	        primaryStage.setScene(scene);
	        primaryStage.show();
		return primaryStage;
	}
	
    private boolean isValid(String username, String password) {
        boolean valid = true;
        if (username.length() < MIN_LENGTH || username.length() > MAX_LENGTH) {
            valid = false;
        }
        if (password.length() < MIN_LENGTH || password.length() > MAX_LENGTH) {
            valid = false;
        }
        if (!username.matches("[a-zA-Z0-9]+")) {
            valid = false;
        }
        if (!password.matches("[a-zA-Z0-9]+")) {
            valid = false;
        }
        return valid;
    }
	
	public Stage launchProjectManager(Stage primaryStage) {
		System.out.println("Launching Project Manager");
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/ProjectManager.fxml"));
			Scene scene = new Scene(root,990,505);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
			primaryStage.setTitle("Project Manager");
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
	
}
