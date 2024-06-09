package Controllers;

import DataBaseClasses.Payment;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class PaymentController {

    @FXML
    private Button btDetails, btRefresh, btSearch, deletePayment, insertNewPayment, updatePayment;
    @FXML
    private TableColumn<Payment, Integer> cmPaymentID;
    @FXML
    private TableColumn<Payment, String> cmCustomerName;
    @FXML
    private TableColumn<Payment, Double> cmAmount;
    @FXML
    private TableColumn<Payment, String> cmDateOfPayment;
    @FXML
    private TableColumn<Payment, String> cmPaymentMethod;
    @FXML
    private TableView<Payment> tablePayment;
    @FXML
    private TextField txtSearch;

    private Connection connect() {
        String url = "jdbc:mysql://localhost:3306/technicalcenter";
        String user = "root";
        String password = "212rr735";
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Error connecting to the database");
            e.printStackTrace();
            return null;
        }
    }

    public void initialize() {
        cmPaymentID.setCellValueFactory(new PropertyValueFactory<>("paymentID"));
        cmCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName")); // Requires joining with
                                                                                        // Customers table
        cmAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        cmDateOfPayment.setCellValueFactory(cellData -> new SimpleStringProperty(
                new SimpleDateFormat("yyyy-MM-dd").format(cellData.getValue().getDateOfPayment())));
        cmPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        loadPayments();
    }

    @FXML
    void handleBtRefresh(ActionEvent event) {
        loadPayments();
    }

    @FXML
    void handleBtSearch(ActionEvent event) {
        searchPayments();
    }

    @FXML
    void insertNewPayment(ActionEvent event) {
        // Implement the logic to open a new payment form or process direct adding
    }

    @FXML
    void updatePayment(ActionEvent event) {
        // Implement the logic to update a selected payment
    }

    @FXML
    void deletePayment(ActionEvent event) {
        deleteSelectedPayment();
    }

    @FXML
    void handleBtDetails(ActionEvent event) {
        showPaymentDetails();
    }

    private void loadPayments() {
        ObservableList<Payment> payments = FXCollections.observableArrayList();
        String query = "SELECT p.*, c.CustomerName FROM Payments p JOIN Customers c ON p.CustomerID = c.CustomerID";
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                payments.add(new Payment(
                        rs.getInt("PaymentID"),
                        rs.getDate("DateOfPayment"),
                        rs.getDouble("Amount"),
                        rs.getString("PaymentMethod"),
                        rs.getInt("CustomerID")));
            }
            tablePayment.setItems(payments);
        } catch (SQLException e) {
            System.out.println("Error loading payments");
            e.printStackTrace();
        }
    }

    private void searchPayments() {
        String searchQuery = "SELECT p.*, c.CustomerName FROM Payments p JOIN Customers c ON p.CustomerID = c.CustomerID WHERE p.PaymentID LIKE ? OR c.CustomerName LIKE ?";
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(searchQuery)) {
            pstmt.setString(1, "%" + txtSearch.getText() + "%");
            pstmt.setString(2, "%" + txtSearch.getText() + "%");
            ResultSet rs = pstmt.executeQuery();
            ObservableList<Payment> payments = FXCollections.observableArrayList();
            while (rs.next()) {
                payments.add(new Payment(
                        rs.getInt("PaymentID"),
                        rs.getDate("DateOfPayment"),
                        rs.getDouble("Amount"),
                        rs.getString("PaymentMethod"),
                        rs.getInt("CustomerID")));
            }
            tablePayment.setItems(payments);
        } catch (SQLException e) {
            System.out.println("Error searching for payments");
            e.printStackTrace();
        }
    }

    private void deleteSelectedPayment() {
        Payment selected = tablePayment.getSelectionModel().getSelectedItem();
        if (selected != null) {
            String deleteQuery = "DELETE FROM Payments WHERE PaymentID = ?";
            try (Connection conn = connect();
                    PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
                pstmt.setInt(1, selected.getPaymentID());
                pstmt.executeUpdate();
                loadPayments(); // Refresh the list after delete
            } catch (SQLException e) {
                System.out.println("Error deleting payment");
                e.printStackTrace();
            }
        }
    }

    private void showPaymentDetails() {
        Payment selected = tablePayment.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Payment Details");
            alert.setHeaderText("Details for Payment ID: " + selected.getPaymentID());
            alert.setContentText("Customer: " + selected.getCustomerID() + "\nAmount: " + selected.getAmount() +
                    "\nDate of Payment: " + new SimpleDateFormat("yyyy-MM-dd").format(selected.getDateOfPayment()) +
                    "\nPayment Method: " + selected.getPaymentMethod());
            alert.showAndWait();
        }
    }
}
