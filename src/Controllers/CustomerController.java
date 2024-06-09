package Controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DataBaseClasses.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CustomerController {

    @FXML
    private TableView<Customers> customerTable;
    @FXML
    private TableColumn<Customers, Integer> cmID;
    @FXML
    private TableColumn<Customers, String> cmName;
    @FXML
    private TableColumn<Customers, String> cmAddress;
    @FXML
    private TableColumn<Customers, String> cmPhonenumber;
    @FXML
    private TableColumn<Customers, String> cmAccount;
    @FXML
    private TableColumn<Customers, Double> cmTotalPrice;
    @FXML
    private TextField customerIDTxt1;
    @FXML
    private TextField customerNameTxt1;
    @FXML
    private Button showCustomersButton;
    @FXML
    private Button addNewCustomerButton;
    @FXML
    private Button searchButton;
    @FXML
    private Button updateCustomersButton;

    private Connection connect() {
        String url = "jdbc:mysql://localhost:3306/technicalcenter";
        String user = "taki";
        String password = "taki";
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Error connecting to the database " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void initialize() {
        cmID.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        cmName.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
        cmAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        cmPhonenumber.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));
        // cmAccount.setCellValueFactory(new PropertyValueFactory<>("Account"));
        // cmTotalPrice.setCellValueFactory(new PropertyValueFactory<>("TotalPrice"));
        loadCustomers();
    }

    @FXML
    private void loadCustomers() {
        ObservableList<Customers> data = FXCollections.observableArrayList();
        String query = "SELECT * FROM Customers";
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                data.add(new Customers(rs.getInt("CustomerID"), rs.getString("CustomerName"), rs.getString("Address"),
                        rs.getString("PhoneNumber")));
            }
            customerTable.setItems(data);
        } catch (SQLException e) {
            System.out.println("Error loading customers from database");
            e.printStackTrace();
        }
    }

    @FXML
    private void addNewCustomer(ActionEvent event) {
        String insertQuery = "INSERT INTO Customers (CustomerName, Address, PhoneNumber) VALUES (?, ?, ?)";
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
            pstmt.setString(1, customerNameTxt1.getText());
            pstmt.setString(2, "Address"); // Update as needed
            pstmt.setString(3, "PhoneNumber"); // Update as needed
            // pstmt.setString(4, "Account"); // Update as needed
            // pstmt.setDouble(5, 0.0); // Update as needed
            pstmt.executeUpdate();
            loadCustomers();
        } catch (SQLException e) {
            System.out.println("Error adding new customer");
            e.printStackTrace();
        }
    }

    @FXML
    private void searchCustomers(ActionEvent event) {
        ObservableList<Customers> data = FXCollections.observableArrayList();
        String query = "SELECT * FROM Customers WHERE CustomerID = ? OR CustomerName = ?";
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, Integer.parseInt(customerIDTxt1.getText()));
            pstmt.setString(2, customerNameTxt1.getText());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                data.add(new Customers(rs.getInt("CustomerID"), rs.getString("CustomerName"), rs.getString("Address"),
                        rs.getString("PhoneNumber")));
            }
            customerTable.setItems(data);
        } catch (SQLException e) {
            System.out.println("Error searching for customers");
            e.printStackTrace();
        }
    }

    @FXML
    private void updateCustomer(ActionEvent event) {
        Customers selected = customerTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            String updateQuery = "UPDATE Customers SET CustomerName = ?, Address = ?, PhoneNumber = ? WHERE CustomerID = ?";
            try (Connection conn = connect();
                    PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                pstmt.setString(1, selected.getCustomerName());
                pstmt.setString(2, selected.getAddress());
                pstmt.setString(3, selected.getPhoneNumber());
                // pstmt.setString(4, selected.getAccount());
                // pstmt.setDouble(5, selected.getTotalPrice());
                pstmt.setInt(6, selected.getCustomerID());
                pstmt.executeUpdate();
                loadCustomers();
            } catch (SQLException e) {
                System.out.println("Error updating customer");
                e.printStackTrace();
            }
        }
    }
}