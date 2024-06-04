package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private Button AppliancesInterface;

    @FXML
    private Button CompanyInterface;

    @FXML
    private Button CustomerInterface;

    @FXML
    private Button EmployeesInterface;

    @FXML
    private Button PaymentsInterface;

    @FXML
    private Button StorageInterface;

    @FXML
    private Button TransactionsInterface;

    @FXML
    void AppliancesInterface(ActionEvent event) throws IOException {
        loadFXML("Appliances.fxml");
    }

    @FXML
    void CompanyInterface(ActionEvent event) throws IOException {
        loadFXML("Company.fxml");

    }

    @FXML
    void CustomerInterface(ActionEvent event) throws IOException {
        loadFXML("Customer.fxml");
    }

    @FXML
    void EmployeesInterface(ActionEvent event) throws IOException {
         loadFXML("ShowEmployee.fxml");
    }

    @FXML
    void PaymentsInterface(ActionEvent event) throws IOException {
        loadFXML("Payment.fxml");
    }

    @FXML
    void StorageInterface(ActionEvent event) throws IOException {
        loadFXML("Storage.fxml");
    }

    @FXML
    void TransactionsInterface(ActionEvent event) throws IOException {
        loadFXML("Transactions.fxml");
    }

    private void loadFXML(String fxmlFile) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/" + fxmlFile));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
