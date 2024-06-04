package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;

public class PasswordTemplate {

    @FXML
    private TextField PasswordEntry;

    @FXML
    private TextField UsernameEntry;

    @FXML
    private Button loginButton;

    @FXML
    void LoginButton(ActionEvent event) {
        // Get text from text fields
        String username = UsernameEntry.getText();
        String password = PasswordEntry.getText();

        // Create an instance of ConnectionToDatabase
        ConnectionToDatabase connectionToDatabase = new ConnectionToDatabase();

        // Set the username and password
/*
        connectionToDatabase.setCredentials(username, password);
*/

        // Connect to the database
        Connection connection = connectionToDatabase.connectToDB();

        if (connection != null) {
            try {
                // Load Main.fxml
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/main.fxml"));
                Parent root = fxmlLoader.load();

                // Get the current stage
                Stage stage = (Stage) loginButton.getScene().getWindow();

                // Set the new scene
                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Login failed!");
        }
    }
}
