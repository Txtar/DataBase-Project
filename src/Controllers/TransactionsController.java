package Controllers;

import DataBaseClasses.Transactions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;

public class TransactionsController implements Initializable {

    @FXML
    private Button AddnewTransaction;

    @FXML
    private TableColumn<Transactions, Integer> TransactionIDColumn;

    @FXML
    private TableColumn<Transactions, String> PurchaseDateColumn;

    @FXML
    private TableColumn<Transactions, Integer> QuantityBoughtColumn;

    @FXML
    private TableColumn<Transactions, Double> TotalPriceColumn;

    @FXML
    private TableColumn<Transactions, Double> TransactionAmountColumn;

    @FXML
    private TableColumn<Transactions, String> CompanyColumn;

    @FXML
    private TableColumn<Transactions, String> ApplianceModelColumn;

    @FXML
    private Button DeleteTransaction;

    @FXML
    private Button UpdateTransaction;

    @FXML
    private Button btRefresh;

    @FXML
    private Button btSearch;

    @FXML
    private TextField txtSearch;

    @FXML
    private TableView<Transactions> tableTransactions;

    private ObservableList<Transactions> transactionsList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TransactionIDColumn.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
        PurchaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("purchaseDate"));
        QuantityBoughtColumn.setCellValueFactory(new PropertyValueFactory<>("quantityBought"));
        TotalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        TransactionAmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        CompanyColumn.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        ApplianceModelColumn.setCellValueFactory(new PropertyValueFactory<>("appliancesModel"));

        loadTransactionsData();
    }

    private void loadTransactionsData() {
        String query = "SELECT t.Transaction_ID, t.Purchase_Date, t.Quantity_Bought, t.Total_Price, t.Amount, t.CompanyID, " +
                "(SELECT c.CompanyName FROM Company c WHERE c.CompanyID = t.CompanyID) AS CompanyName, " +
                "(SELECT COALESCE(a.ModelNumber, 'Unknown Model') FROM Appliances a WHERE a.ModelNumber = " +
                "(SELECT eact.ModelNumber FROM EmployeeAppliancesCustomersTransactions eact WHERE eact.employeeID = t.Transaction_ID LIMIT 1)) AS AppliancesModel " +
                "FROM Transactions t " +
                "ORDER BY t.Transaction_ID";

        try (Connection conn = new ConnectionToDatabase().connectToDB();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            transactionsList.clear();
            while (rs.next()) {
                Transactions transaction = new Transactions(
                        rs.getInt("Transaction_ID"),
                        rs.getDate("Purchase_Date"),
                        rs.getDouble("Amount"),
                        rs.getInt("Quantity_Bought"),
                        rs.getDouble("Total_Price"),
                        rs.getInt("CompanyID"),
                        rs.getString("CompanyName"),
                        rs.getString("AppliancesModel") == null ? "Unknown Model" : rs.getString("AppliancesModel")
                );
                transactionsList.add(transaction);
            }
            tableTransactions.setItems(transactionsList);

        } catch (SQLException e) {
            Message.displayMassage("Error: " , e.getMessage());
        }
    }


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
        Transactions selectedTransaction = tableTransactions.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null) {
            String deleteQuery = "DELETE FROM Transactions WHERE Transaction_ID = ?";
            try (Connection conn = new ConnectionToDatabase().connectToDB(); PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
                pstmt.setInt(1, selectedTransaction.getTransactionID());
                pstmt.executeUpdate();
                transactionsList.remove(selectedTransaction);
                Message.displayMassage("Success", "Transaction deleted successfully!");
            } catch (SQLException e) {
                Message.displayMassage("Error", e.getMessage());
            }
        } else {
            Message.displayMassage("Error", "No transaction selected!");
        }
    }

    @FXML
    void UpdateTransaction(ActionEvent event) {
        Transactions selectedTransaction = tableTransactions.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/NewTransaction.fxml"));
                Parent root = loader.load();
                NewTransactionController controller = loader.getController();
                controller.loadTransactionData(selectedTransaction);
                Stage window = new Stage();
                window.initModality(Modality.APPLICATION_MODAL);
                window.setTitle("Update Transaction");
                window.setScene(new Scene(root));
                window.setResizable(false);
                window.show();
            } catch (IOException exception) {
                Message.displayMassage("Warning", exception.getMessage());
            }
        } else {
            Message.displayMassage("Error", "No transaction selected!");
        }
    }

    @FXML
    void handleBtRefresh(ActionEvent event) {
        loadTransactionsData();
    }

    @FXML
    void handleBtSearch(ActionEvent event) {
        String search = txtSearch.getText();
        if (search == null || search.trim().isEmpty()) {
            loadTransactionsData();
            return;
        }

        String query = "SELECT t.Transaction_ID, t.Purchase_Date, t.Quantity_Bought, t.Total_Price, t.Amount, t.CompanyID, " +
                "(SELECT c.CompanyName FROM Company c WHERE c.CompanyID = t.CompanyID) AS CompanyName, " +
                "(SELECT IFNULL(a.ModelNumber, 'Unknown Model') FROM Appliances a WHERE a.ModelNumber = " +
                "(SELECT eact.ModelNumber FROM EmployeeAppliancesCustomersTransactions eact WHERE eact.employeeID = t.Transaction_ID LIMIT 1)) AS AppliancesModel " +
                "FROM Transactions t " +
                "WHERE t.Transaction_ID = " + search;

        try (Connection conn = new ConnectionToDatabase().connectToDB(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            transactionsList.clear();
            while (rs.next()) {
                Transactions transaction = new Transactions(
                        rs.getInt("Transaction_ID"),
                        rs.getDate("Purchase_Date"),
                        rs.getDouble("Amount"),
                        rs.getInt("Quantity_Bought"),
                        rs.getDouble("Total_Price"),
                        rs.getInt("CompanyID"),
                        rs.getString("CompanyName"),
                        rs.getString("AppliancesModel")
                );
                transactionsList.add(transaction);
            }
            tableTransactions.setItems(transactionsList);
        } catch (SQLException e) {
            Message.displayMassage("Error: " ,e.getMessage());
        }
    }
}
