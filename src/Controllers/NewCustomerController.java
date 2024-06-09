package Controllers;

import DataBaseClasses.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class NewCustomerController implements Initializable {

    @FXML
    private Button addCustomerButton;

    @FXML
    private ComboBox<String> combAddress;

    @FXML
    private ComboBox<String> combPaymentMethod;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtItemName;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPayment;

    @FXML
    private TextField txtPhoneNumber;

    @FXML
    private TextField txtTotalPrice;

    private ObservableList<String> addressList = FXCollections.observableArrayList();
    private ObservableList<String> paymentMethods = FXCollections.observableArrayList("Installment", "Credit", "Cash");
    private Customers customerToUpdate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAddresses();
        combPaymentMethod.setItems(paymentMethods);
    }

    private void loadAddresses() {
        String query = "SELECT DISTINCT Address FROM Customers";
        try (Connection conn = new ConnectionToDatabase().connectToDB(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                addressList.add(rs.getString("Address"));
            }
            combAddress.setItems(addressList);
        } catch (SQLException e) {
            Message.displayMassage("Error: ", e.getMessage());
        }
    }

    @FXML
    void addCustomer(ActionEvent event) {
        if (!txtName.getText().isEmpty()
                && !txtPhoneNumber.getText().isEmpty()
                && !txtPayment.getText().isEmpty()) {
            String customerName = txtName.getText();
            String phoneNumber = txtPhoneNumber.getText();
            String address = combAddress.getValue();
            String modelNumber = txtItemName.getText();
            int account = Integer.parseInt(txtPayment.getText());
            double totalPrice = Double.parseDouble(txtTotalPrice.getText());

            if (address == null) {
                Message.displayMassage("Error", "Please select an address!");
                return;
            }

            if (customerToUpdate == null) {
                String insertQuery = "INSERT INTO Customers (CustomerName, PhoneNumber, Address, ModelNumber, Account) VALUES (?, ?, ?, ?, ?)";
                try (Connection conn = new ConnectionToDatabase().connectToDB(); PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                    pstmt.setString(1, customerName);
                    pstmt.setString(2, phoneNumber);
                    pstmt.setString(3, address);
                    pstmt.setString(4, modelNumber);
                    pstmt.setInt(5, account);

                    pstmt.executeUpdate();
                    Message.displayMassage("Success", "New customer added successfully!");

                } catch (SQLException e) {
                    Message.displayMassage("Error: ", e.getMessage());
                }
            } else {
                String updateQuery = "UPDATE Customers SET CustomerName = ?, PhoneNumber = ?, Address = ?, ModelNumber = ?, Account = ? WHERE CustomerID = ?";
                try (Connection conn = new ConnectionToDatabase().connectToDB(); PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                    pstmt.setString(1, customerName);
                    pstmt.setString(2, phoneNumber);
                    pstmt.setString(3, address);
                    pstmt.setString(4, modelNumber);
                    pstmt.setInt(5, account);
                    pstmt.setInt(6, customerToUpdate.getCustomerID());

                    pstmt.executeUpdate();
                    Message.displayMassage("Success", "Customer updated successfully!");

                } catch (SQLException e) {
                    Message.displayMassage("Error: ", e.getMessage());
                }
            }
        } else {
            Message.displayMassage("Error", "Please insert all required data.");
        }
    }

    @FXML
    void handleCombAddress(ActionEvent event) {
        String selectedAddress = combAddress.getValue();
        System.out.println("Selected Address: " + selectedAddress);
    }

    @FXML
    void handlePaymentMethod(ActionEvent event) {
        String selectedMethod = combPaymentMethod.getValue();
        System.out.println("Selected Payment Method: " + selectedMethod);
    }

    public void loadCustomerData(Customers customer) {
        customerToUpdate = customer;
        txtID.setText(String.valueOf(customer.getCustomerID()));
        txtName.setText(customer.getCustomerName());
        txtPhoneNumber.setText(customer.getPhoneNumber());
        txtItemName.setText(customer.getModelNumber());
        txtPayment.setText(String.valueOf(customer.getAccount()));
        txtTotalPrice.setText(String.valueOf(customer.getTotalPrice()));
        combAddress.setValue(customer.getAddress());
    }
}
