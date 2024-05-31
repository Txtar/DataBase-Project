package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class CustomerController {

    @FXML
    private Button addNewCustomerButton;

    @FXML
    private TableColumn<?, ?> cmAccount;

    @FXML
    private TableColumn<?, ?> cmAddress;

    @FXML
    private TableColumn<?, ?> cmID;

    @FXML
    private TableColumn<?, ?> cmName;

    @FXML
    private TableColumn<?, ?> cmPhonenumber;

    @FXML
    private TableColumn<?, ?> cmTotalPrice;

    @FXML
    private TextField customerIDTxt1;

    @FXML
    private TextField customerNameTxt1;

    @FXML
    private AnchorPane outputPane;

    @FXML
    private Button searchButton;

    @FXML
    private Button showCustomersButton;

    @FXML
    private Button updateCustomersButton;

    @FXML
    void addNewCustomerButton(ActionEvent event) {

    }

    @FXML
    void searchButton(ActionEvent event) {

    }

    @FXML
    void showCustomersButton(ActionEvent event) {

    }

    @FXML
    void updateCustomersButton(ActionEvent event) {

    }

}
