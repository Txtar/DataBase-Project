package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

import javafx.scene.control.ComboBox;

public class StorageController {

    @FXML
    private TextField ApplianceModelTxt;

    @FXML
    private TableColumn<?, ?> ModelNumColumn;

    @FXML
    private TableColumn<?, ?> NameColumn;

    @FXML
    private TableColumn<?, ?> QuantityColumn;

    @FXML
    private TableColumn<?, ?> StorageNameColumn;

    @FXML
    private Button btDelete;

    @FXML
    private Button btNewAppliance;

    @FXML
    private Button btRefresh;

    @FXML
    private Button btSearch;

    @FXML
    private Button btUpdate;

    @FXML
    private ComboBox<String> combAppliance;

    @FXML
    private ComboBox<String> combStorage;

    @FXML
    void handleBtDelete(ActionEvent event) {
        // Handle delete button action
    }

    @FXML
    void handleBtNewAppliance(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxml/NewAppliances.fxml")));

            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Insert New Employee");
            window.setScene(new Scene(root));
            window.setResizable(false);
            window.show();
        } catch (IOException exception) {
            Message.displayMassage("Warning", exception.getMessage());
        }
    }

    @FXML
    void handleBtRefresh(ActionEvent event) {
        // Handle refresh button action
    }

    @FXML
    void handleBtUpdate(ActionEvent event) {
        // Handle update button action
    }


    @FXML
    void handleCombSAppliance(ActionEvent event) {
    ObservableList<String> suggestions = FXCollections.observableArrayList(
        "TV", "Refrigerator", "Freezer", "Dishwasher", "Microwave", "Toaster"
    );
    combAppliance.setItems(suggestions);
}



@FXML
void handleCombStorage(ActionEvent event) {
    ObservableList<String> suggestions = FXCollections.observableArrayList(
        "مخزن البلد", "مخزن المحل", "مخزن انور", "مخزن جوني", "مخزن متيا"
    );
    combStorage.setItems(suggestions);
}



    @FXML
    void handleSearchBtR(ActionEvent event) {
        // Handle search button action
    }
}
