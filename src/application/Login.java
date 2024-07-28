/*
 * Title: User Create Login
 * Class Name: P1.java
 * Author: Demeter Ross
 * Description: Program that allows users to create a username and
 * password with a specific set of requirements. These requirements
 * are a security measure that reduces risk by applying multi-layered security.
 */

package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Login extends Application {
	// declare variables
    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 32;

    @Override
    public void start(Stage loginStage) {
    	// title of pop-up
        loginStage.setTitle("User Create Login");

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
        loginStage.setScene(scene);
        loginStage.show();
    }

    // username and password requirements
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

}
