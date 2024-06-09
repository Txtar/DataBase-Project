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
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String query = "SELECT CompanyName FROM Company";
        try (Connection conn = new ConnectionToDatabase().connectToDB();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                companyList.add(rs.getString("CompanyName"));
            }
            comboCompany.setItems(companyList);
        } catch (SQLException e) {
            Message.displayMassage("Error", e.getMessage());
        }
    }

    @FXML
    void addTransaction(ActionEvent event) {
        if (validateFields()) {
            try {
                int transactionID = Integer.parseInt(txtID.getText());
                String purchaseDate = txtPurchaseDate.getText();
                double amount = Double.parseDouble(txtAmount.getText());
                double totalPrice = Double.parseDouble(txtTotalPrice.getText());
                int quantity = Integer.parseInt(txtQuantity.getText());
                String companyName = comboCompany.getValue();
                String model = txtModel.getText();
                int companyID = getCompanyIDByName(companyName);

                if (checkIfTransactionExists(transactionID)) {
                    updateTransaction(transactionID, purchaseDate, amount, totalPrice, quantity, companyID, model);
                } else {
                    insertTransaction(transactionID, purchaseDate, amount, totalPrice, quantity, companyID, model);
                }

                closeWindow();
            } catch (NumberFormatException e) {
                Message.displayMassage("Error", "Please enter valid data.");
            }
        }
    }

    private boolean validateFields() {
        if (txtID.getText().isEmpty() || txtPurchaseDate.getText().isEmpty() || txtAmount.getText().isEmpty() ||
                txtTotalPrice.getText().isEmpty() || txtQuantity.getText().isEmpty() || comboCompany.getValue() == null || txtModel.getText().isEmpty()) {
            Message.displayMassage("Error", "All fields must be filled out.");
            return false;
        }

        try {
            Double.parseDouble(txtAmount.getText());
            Double.parseDouble(txtTotalPrice.getText());
            Integer.parseInt(txtQuantity.getText());
        } catch (NumberFormatException e) {
            Message.displayMassage("Error", "Invalid number format.");
            return false;
        }
        return true;
    }

    private boolean checkIfTransactionExists(int transactionID) {
        String query = "SELECT Transaction_ID FROM Transactions WHERE Transaction_ID = ?";
        try (Connection conn = new ConnectionToDatabase().connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, transactionID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            Message.displayMassage("Error", e.getMessage());
        }
        return false;
    }

    private int getCompanyIDByName(String companyName) {
        String query = "SELECT CompanyID FROM Company WHERE CompanyName = ?";
        try (Connection conn = new ConnectionToDatabase().connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, companyName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("CompanyID");
            }
        } catch (SQLException e) {
            Message.displayMassage("Error", e.getMessage());
        }
        return -1;
    }

    private void insertTransaction(int transactionID, String purchaseDate, double amount, double totalPrice, int quantity, int companyID, String model) {
        String insertQuery = "INSERT INTO Transactions (Transaction_ID, Purchase_Date, Amount, Quantity_Bought, Total_Price, CompanyID, ModelNumber) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = new ConnectionToDatabase().connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
            pstmt.setInt(1, transactionID);
            pstmt.setString(2, purchaseDate);
            pstmt.setDouble(3, amount);
            pstmt.setInt(4, quantity);
            pstmt.setDouble(5, totalPrice);
            pstmt.setInt(6, companyID);
            pstmt.setString(7, model);
            pstmt.executeUpdate();
            updateModelQuantity(model, quantity);
            Message.displayMassage("Success", "Transaction added successfully!");
        } catch (SQLException e) {
            Message.displayMassage("Error", e.getMessage());
        }
    }

    private void updateTransaction(int transactionID, String purchaseDate, double amount, double totalPrice, int quantity, int companyID, String model) {
        String updateQuery = "UPDATE Transactions SET Purchase_Date = ?, Amount = ?, Quantity_Bought = ?, Total_Price = ?, CompanyID = ?, ModelNumber = ? WHERE Transaction_ID = ?";
        try (Connection conn = new ConnectionToDatabase().connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setString(1, purchaseDate);
            pstmt.setDouble(2, amount);
            pstmt.setInt(3, quantity);
            pstmt.setDouble(4, totalPrice);
            pstmt.setInt(5, companyID);
            pstmt.setString(6, model);
            pstmt.setInt(7, transactionID);
            pstmt.executeUpdate();
            updateModelQuantity(model, quantity);
            Message.displayMassage("Success", "Transaction updated successfully!");
        } catch (SQLException e) {
            Message.displayMassage("Error", e.getMessage());
        }
    }

    private void updateModelQuantity(String model, int quantity) {
        String updateModelQuery = "UPDATE Appliances SET Quantity = Quantity + ? WHERE ModelNumber = ?";
        try (Connection conn = new ConnectionToDatabase().connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(updateModelQuery)) {
            pstmt.setInt(1, quantity);
            pstmt.setString(2, model);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Message.displayMassage("Error", e.getMessage());
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) Insert.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handlecomboCompany(ActionEvent event) {
        String selectedCompany = comboCompany.getValue();
        if (selectedCompany != null) {
            System.out.println("Selected Company: " + selectedCompany);
        }
    }

    public void loadTransactionData(Transactions transaction) {
        this.transactionToUpdate = transaction;
        txtID.setText(String.valueOf(transaction.getTransactionID()));
        txtPurchaseDate.setText(transaction.getPurchaseDate().toString());
        txtAmount.setText(String.valueOf(transaction.getAmount()));
        txtTotalPrice.setText(String.valueOf(transaction.getTotalPrice()));
        txtQuantity.setText(String.valueOf(transaction.getQuantityBought()));
        comboCompany.setValue(transaction.getCompanyName());
        txtModel.setText(transaction.getModelNumber());
    }
}
