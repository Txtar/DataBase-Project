package Controllers;

import DataBaseClasses.Storage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class NewStorageController implements Initializable {

    @FXML
    private Button btAddNewStorage;

    @FXML
    private TextField txtStorageID;

    @FXML
    private TextField txtStorageLocation;

    private ObservableList<Integer> storageIDList = FXCollections.observableArrayList();
    private ObservableList<String> storageLocationList = FXCollections.observableArrayList();

    private Storage storageToUpdate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadStorageIDs();
        loadStorageLocations();
    }

    private void loadStorageIDs() {
        String query = "SELECT StorageID FROM Storage";
        try (Connection conn = new ConnectionToDatabase().connectToDB(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                storageIDList.add(rs.getInt("StorageID"));
            }
        } catch (SQLException e) {
            Message.displayMassage("Error: ", e.getMessage());
        }
    }

    private void loadStorageLocations() {
        String query = "SELECT StorageLocation FROM Storage";
        try (Connection conn = new ConnectionToDatabase().connectToDB(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                storageLocationList.add(rs.getString("StorageLocation"));
            }
        } catch (SQLException e) {
            Message.displayMassage("Error: ", e.getMessage());
        }
    }

    public void loadStorageData(Storage storage) {
        this.storageToUpdate = storage;
        txtStorageID.setText(String.valueOf(storage.getStorageID()));
        txtStorageLocation.setText(storage.getStorageLocation());
    }

    @FXML
    public void btHandleAddNewStorage(ActionEvent event) {
        int storageID;
        try {
            storageID = Integer.parseInt(txtStorageID.getText());
        } catch (NumberFormatException e) {
            Message.displayMassage("Error", "Please enter a valid Storage ID.");
            return;
        }

        String storageLocation = txtStorageLocation.getText();

        if (storageLocation.isEmpty()) {
            Message.displayMassage("Error", "Please enter a Storage Location.");
            return;
        }

        if (storageToUpdate == null) {
            // Insert new storage
            String insertQuery = "INSERT INTO Storage (StorageID, StorageLocation) VALUES (?, ?)";
            try (Connection conn = new ConnectionToDatabase().connectToDB(); PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setInt(1, storageID);
                pstmt.setString(2, storageLocation);
                pstmt.executeUpdate();
                Message.displayMassage("Success", "New Storage added successfully!");
            } catch (SQLException e) {
                Message.displayMassage("Error: ", e.getMessage());
            }
        } else {
            // Update existing storage
            String updateQuery = "UPDATE Storage SET StorageLocation = ? WHERE StorageID = ?";
            try (Connection conn = new ConnectionToDatabase().connectToDB(); PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                pstmt.setString(1, storageLocation);
                pstmt.setInt(2, storageID);
                pstmt.executeUpdate();
                Message.displayMassage("Success", "Storage updated successfully!");
            } catch (SQLException e) {
                Message.displayMassage("Error: ", e.getMessage());
            }
        }
    }
}
