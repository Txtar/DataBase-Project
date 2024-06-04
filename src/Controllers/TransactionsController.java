package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class TransactionsController {

    @FXML
    private Button AddnewTransaction;

    @FXML
    private TableColumn<?, ?> ApplianceModelColumn;

    @FXML
    private TableColumn<?, ?> Company;

    @FXML
    private Button DeleteTransaction;

    @FXML
    private TableColumn<?, ?> PurchaseDate;

    @FXML
    private TableColumn<?, ?> QuantityBoughtColumn;

    @FXML
    private TableColumn<?, ?> TotalPriceColumn;

    @FXML
    private TableColumn<?, ?> TransactionAmountColumn;

    @FXML
    private TableColumn<?, ?> TransactionIDColumn;

    @FXML
    private Button UpdateTransaction;

    @FXML
    private Button btRefresh;

    @FXML
    private Button btSearch;

    @FXML
    private TextField txtSearch;

    @FXML
    void AddTransaction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxml/NewTransaction.fxml")));
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Insert New Transaction");
            window.setScene(new Scene(root));
            window.setResizable(false);
            window.show();
        } catch (IOException exception) {
            Message.displayMassage("Warning", exception.getMessage());
        }
    }

    @FXML
    void DeleteTransaction(ActionEvent event) {

    }

    @FXML
    void UpdateTransaction(ActionEvent event) {

    }

    @FXML
    void handleBtRefresh(ActionEvent event) {

    }

    @FXML
    void handleBtSearch(ActionEvent event) {

//        if (!this.txtSearch.getText().trim().isEmpty()) {
//            if (!Methods.isNumber(this.txtSearch.getText().trim())) {
//                Message.displayMassage("Warning", " Employee id is invalid ");
//                this.txtSearch.clear();
//                return;
//            }
//
//
//            this.execute(" where E.employeeID=" + Integer.parseInt(this.txtSearch.getText().trim()));
//        }

    }

}
