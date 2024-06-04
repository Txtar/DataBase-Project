package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class PaymentDetailsController {
    @FXML
    private TableColumn<?, ?> cmApplianceName;

    @FXML
    private TableColumn<?, ?> cmCustomerID;

    @FXML
    private TableColumn<?, ?> cmModelNumber;

    @FXML
    private TableColumn<?, ?> cmQuantity;

    @FXML
    private ComboBox<?> combPaymentMethod;

    @FXML
    private TableView<?> tablePaymentDetails;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtPaymentDate;

    @FXML
    private TextField txtPaymentId;

    @FXML
    void handlePaymentMethod(ActionEvent event) {

    }
}
