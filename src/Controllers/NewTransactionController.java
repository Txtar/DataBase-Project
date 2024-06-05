package Controllers;

import DataBaseClasses.Transactions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class NewTransactionController implements Initializable {

    @FXML
    private Button Insert;

    @FXML
    private ComboBox<String> comboCompany;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtModel;

    @FXML
    private TextField txtPurchaseDate;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtTotalPrice;

    private ObservableList<String> companyList = FXCollections.observableArrayList();
    private Transactions transactionToUpdate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCompanyNames();
    }

    private void loadCompanyNames() {
        String query = "SELECT CompanyID, CompanyName FROM Company";
        try (Connection conn = new ConnectionToDatabase().connectToDB(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                companyList.add(rs.getString("CompanyName"));
            }
            comboCompany.setItems(companyList);
        } catch (SQLException e) {
            Message.displayMassage("Error: ", e.getMessage());
        }
    }

    @FXML
    void addTransaction(ActionEvent event) {
        try {
            int transactionID = Integer.parseInt(txtID.getText());
            String purchaseDate = txtPurchaseDate.getText();
            double amount = Double.parseDouble(txtAmount.getText());
            double totalPrice = Double.parseDouble(txtTotalPrice.getText());
            int quantity = Integer.parseInt(txtQuantity.getText());
            String companyName = comboCompany.getValue();
            String model = txtModel.getText();

            int companyID = getCompanyIDByName(companyName);

            boolean transactionExists = checkIfTransactionExists(transactionID);

            if (transactionExists) {
                String updateQuery = "UPDATE Transactions SET Purchase_Date = ?, Amount = ?, Quantity_Bought = ?, Total_Price = ?, CompanyID = ? WHERE Transaction_ID = ?";
                try (Connection conn = new ConnectionToDatabase().connectToDB(); PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                    pstmt.setString(1, purchaseDate);
                    pstmt.setDouble(2, amount);
                    pstmt.setInt(3, quantity);
                    pstmt.setDouble(4, totalPrice);
                    pstmt.setInt(5, companyID);
                    pstmt.setInt(6, transactionID);

                    pstmt.executeUpdate();
                    Message.displayMassage("Success", "Transaction updated successfully!");
                } catch (SQLException e) {
                    Message.displayMassage("Error: ", e.getMessage());
                }
            } else {
                String insertQuery = "INSERT INTO Transactions (Transaction_ID, Purchase_Date, Amount, Quantity_Bought, Total_Price, CompanyID) VALUES (?, ?, ?, ?, ?, ?)";
                try (Connection conn = new ConnectionToDatabase().connectToDB(); PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                    pstmt.setInt(1, transactionID);
                    pstmt.setString(2, purchaseDate);
                    pstmt.setDouble(3, amount);
                    pstmt.setInt(4, quantity);
                    pstmt.setDouble(5, totalPrice);
                    pstmt.setInt(6, companyID);

                    pstmt.executeUpdate();

                    String insertModelQuery = "INSERT INTO Appliances (ModelNumber, ApplianceName, CompanyID) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE ApplianceName = VALUES(ApplianceName), CompanyID = VALUES(CompanyID)";
                    try (PreparedStatement pstmtModel = conn.prepareStatement(insertModelQuery)) {
                        pstmtModel.setString(1, model);
                        pstmtModel.setString(2, model); // Assuming ApplianceName is the same as the model
                        pstmtModel.setInt(3, companyID);
                        pstmtModel.executeUpdate();
                    }
                    Message.displayMassage("Success", "Transaction and model added successfully!");
                } catch (SQLException e) {
                    Message.displayMassage("Error: ", e.getMessage());
                }
            }
        } catch (NumberFormatException e) {
            Message.displayMassage("Error", "Please enter valid data.");
        }
    }

    private boolean checkIfTransactionExists(int transactionID) {
        String query = "SELECT Transaction_ID FROM Transactions WHERE Transaction_ID = ?";
        try (Connection conn = new ConnectionToDatabase().connectToDB(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, transactionID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            Message.displayMassage("Error: ", e.getMessage());
        }
        return false;
    }

    private int getCompanyIDByName(String companyName) {
        String query = "SELECT CompanyID FROM Company WHERE CompanyName = ?";
        try (Connection conn = new ConnectionToDatabase().connectToDB(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, companyName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("CompanyID");
            }
        } catch (SQLException e) {
            Message.displayMassage("Error: ", e.getMessage());
        }
        return -1;
    }

    @FXML
    void handlecomboCompany(ActionEvent event) {
        String selectedCompany = comboCompany.getValue();
        System.out.println("Selected Company: " + selectedCompany);
    }

    public void loadTransactionData(Transactions transaction) {
        this.transactionToUpdate = transaction;
        txtID.setText(String.valueOf(transaction.getTransactionID()));
        txtPurchaseDate.setText(transaction.getPurchaseDate().toString());
        txtAmount.setText(String.valueOf(transaction.getAmount()));
        txtTotalPrice.setText(String.valueOf(transaction.getTotalPrice()));
        txtQuantity.setText(String.valueOf(transaction.getQuantityBought()));
        comboCompany.setValue(transaction.getCompanyName());
        txtModel.setText(transaction.getAppliancesModel());
    }
}
