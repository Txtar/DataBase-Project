package Controllers;

import DataBaseClasses.Storage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class StorageController {

    @FXML
    private TextField ApplianceModelTxt;

    @FXML
    private TableView<Storage> storageTable;

    @FXML
    private TableColumn<Storage, Integer> StorageIDColumn;

    @FXML
    private TableColumn<Storage, String> StorageLocationColumn;

    @FXML
    private Button btDelete;

    @FXML
    private Button btNewStorage;

    @FXML
    private Button btRefresh;

    @FXML
    private Button btSearch;

    @FXML
    private Button btUpdate;

    @FXML
    private ComboBox<String> combStorage;

    private ObservableList<Storage> storageList;

    @FXML
    public void initialize() {
        storageList = FXCollections.observableArrayList();

        // Set up the columns in the table
        StorageIDColumn.setCellValueFactory(new PropertyValueFactory<>("storageID"));
        StorageLocationColumn.setCellValueFactory(new PropertyValueFactory<>("storageLocation"));

        // Bind the ObservableList to the TableView
        storageTable.setItems(storageList);

        // Load initial data (if any)
        loadStorageData();
    }

    public void loadStorageData() {
        String query = "SELECT * FROM Storage";

        try (Connection conn = new ConnectionToDatabase().connectToDB();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            storageList.clear();
            while (rs.next()) {
                Storage storage = new Storage(
                        rs.getInt("StorageID"),
                        rs.getString("StorageLocation")
                );
                storageList.add(storage);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @FXML
    void handleBtDelete(ActionEvent event) {
        Storage selectedStorage = storageTable.getSelectionModel().getSelectedItem();
        if (selectedStorage == null) {
            showAlert("Error", "No storage selected!", "");
            return;
        }

        String deleteQuery = "DELETE FROM Storage WHERE StorageID = ?";

        try (Connection conn = new ConnectionToDatabase().connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
            pstmt.setInt(1, selectedStorage.getStorageID());
            pstmt.executeUpdate();
            storageList.remove(selectedStorage);
            showAlert("Success", "Storage deleted successfully!", "");
        } catch (SQLException e) {
            showAlert("Error:", "", e.getMessage());
        }
    }

    @FXML
    void handleBtNewStorage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxml/NewStorage.fxml")));
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Insert New Appliances");
            window.setScene(new Scene(root));
            window.setResizable(false);
            window.show();
        } catch (IOException exception) {
            System.out.println("Warning: " + exception.getMessage());
        }
    }

    @FXML
    void handleBtRefresh(ActionEvent event) {
        loadStorageData();
    }

    @FXML
    void handleBtUpdate(ActionEvent event) {
        Storage selectedStorage = storageTable.getSelectionModel().getSelectedItem();
        if (selectedStorage == null) {
            showAlert("Error", "No storage selected!", "");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/NewStorage.fxml"));
            Parent root = loader.load();

            NewStorageController controller = loader.getController();
            controller.loadStorageData(selectedStorage);

            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Update Storage");
            window.setScene(new Scene(root));
            window.setResizable(false);
            window.show();
        } catch (IOException exception) {
            showAlert("Error", "An error occurred while loading the update form.", "");
        }
    }

    @FXML
    void handleSearchBtR(ActionEvent event) {
        String searchQuery = ApplianceModelTxt.getText().toLowerCase();
        ObservableList<Storage> filteredList = FXCollections.observableArrayList();
        for (Storage storage : storageList) {
            if (storage.getStorageLocation().toLowerCase().contains(searchQuery) ||
                    String.valueOf(storage.getStorageID()).contains(searchQuery)) {
                filteredList.add(storage);
            }
        }
        storageTable.setItems(filteredList);
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
