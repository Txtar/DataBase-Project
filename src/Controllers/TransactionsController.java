package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

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

    }

}
