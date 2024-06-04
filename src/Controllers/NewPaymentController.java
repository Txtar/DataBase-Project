package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class NewPaymentController {

    @FXML
    private Button addNewPayment;

    @FXML
    private ComboBox<?> combPaymentMethod;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtAppliancesName;

    @FXML
    private TextField txtCustomerID;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtModelNumber;

    @FXML
    private TextField txtQuantity;

    @FXML
    void addNewPayment(ActionEvent event) {

    }

    @FXML
    void handleCombPaymentMethod(ActionEvent event) {

    }
}
