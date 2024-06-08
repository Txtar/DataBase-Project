package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;

public class PasswordTemplate {

    @FXML
    private TextField UsernameEntry;

    @FXML
    private PasswordField PasswordEntry;

    @FXML
    private Button loginButton;

    private PasswordUser passwordUser;

    @FXML
    void LoginButton(ActionEvent event) {
        String username = UsernameEntry.getText();
        String password = PasswordEntry.getText();

        passwordUser = new PasswordUser(username, password);

        attemptDatabaseConnection();
    }

    private void attemptDatabaseConnection() {
        ConnectionToDatabase connectionToDatabase = new ConnectionToDatabase();

        connectionToDatabase.setCredentials(passwordUser.getUsername(), passwordUser.getPassword());

        Connection connection = connectionToDatabase.connectToDB();

        if (connection != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/main.fxml"));
                Parent root = fxmlLoader.load();

                Stage stage = (Stage) loginButton.getScene().getWindow();

                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
                Message.displayMassage("Loading Error", "Failed to load the main page.");
            }
        } else {
            Message.displayMassage("Login Failed", "Incorrect username or password.");
        }
    }


}
