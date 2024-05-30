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
    private TextField ApplianceNameTxt;

    @FXML
    private Button GotoaddAppliance;

    @FXML
    private TableColumn<?, ?> ModelNumColumn;

    @FXML
    private TableColumn<?, ?> NameColumn;

    @FXML
    private TableColumn<?, ?> QuantityColumn;

    @FXML
    private TableColumn<?, ?> StorageNameColumn;

    @FXML
    private ComboBox<?> combStorage;

    @FXML
    private Button showAllAppliances;

    @FXML
    void handleCombStorage(ActionEvent event) {

    }

}
