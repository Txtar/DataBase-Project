package Controllers;

import DataBaseClasses.Appliances;
import DataBaseClasses.Storage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
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
        String query = "SELECT storageID FROM Storage";
        try (Connection conn = new ConnectionToDatabase().connectToDB(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                storageIDList.add(rs.getInt("storageID"));
            }
        } catch (SQLException e) {
            Message.displayMassage("Error: ", e.getMessage());
        }
    }

    private void loadStorageLocations() {
        String query = "SELECT storageLocation FROM Storage";
        try (Connection conn = new ConnectionToDatabase().connectToDB(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                storageLocationList.add(rs.getString("storageLocation"));
            }
        } catch (SQLException e) {
            Message.displayMassage("Error: ", e.getMessage());
        }
    }


    public void loadStorageData(Storage storage) {
    // Set the text of the txtStorageID and txtStorageLocation fields
    txtStorageID.setText(String.valueOf(storage.getStorageID()));
    txtStorageLocation.setText(storage.getStorageLocation());
}



    @FXML
   public void btHandleAddNewStorage(ActionEvent event) {

        int StorageID = txtStorageID.anchorProperty().getValue();
        String StorageLocation = txtStorageLocation.getText();


        if (storageToUpdate == null) {
            // Insert new appliance
            String insertQuery = "INSERT INTO Storage (StorageID, StorageLocation) VALUES (?, ?)";

            try (Connection conn = new ConnectionToDatabase().connectToDB(); PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setInt(1,StorageID);
                pstmt.setString(2, StorageLocation);

                pstmt.executeUpdate();
                Message.displayMassage("success", "New Storage added successfully!");

            } catch (SQLException e) {
                Message.displayMassage("Error: ", e.getMessage());
            }
        } else {
            String updateQuery = "UPDATE Storage SET StorageID = ?, StorageLocation = ?";

            try (Connection conn = new ConnectionToDatabase().connectToDB(); PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                pstmt.setInt(1,StorageID);
                pstmt.setString(2, StorageLocation);

                pstmt.executeUpdate();

                Message.displayMassage("success", "Appliance updated successfully!");

            } catch (SQLException e) {
                Message.displayMassage("Error", e.getMessage());

            }
        }
    }



}
