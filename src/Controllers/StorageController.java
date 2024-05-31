package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

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
    private ComboBox<?> combAppliance;

    @FXML
    private ComboBox<?> combStorage;

    @FXML
    void handleBtRefresh(ActionEvent event) {

    }

    @FXML
    void handleCombSAppliance(ActionEvent event) {

    }

    @FXML
    void handleCombStorage(ActionEvent event) {

    }

}
