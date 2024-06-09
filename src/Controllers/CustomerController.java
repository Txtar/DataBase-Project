package Controllers;

import DataBaseClasses.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    @FXML
    private Button btRefresh;

    @FXML
    private Button btSearch;

    @FXML
    private TableColumn<Customers, Integer> cmID;

    @FXML
    private TableColumn<Customers, String> cmName;

    @FXML
    private TableColumn<Customers, String> cmAddress;

    @FXML
    private TableColumn<Customers, String> cmPhonenumber;

    @FXML
    private TableColumn<Customers, Integer> cmAccount;

    @FXML
    private TableColumn<Customers, Double> cmTotalPrice;

    @FXML
    private Button deleteCustomer;

    @FXML
    private Button insertNewCustomer;

    @FXML
    private TableView<Customers> tableCustomers;

    @FXML
    private TextField txtSearch;

    @FXML
    private Button updateCustomer;

    private ObservableList<Customers> customersList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        cmName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        cmPhonenumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        cmAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        cmAccount.setCellValueFactory(new PropertyValueFactory<>("account"));
        cmTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        loadCustomerData();
    }

    private void loadCustomerData() {
        String query = """
                SELECT 
                    c.CustomerID,
                    c.CustomerName,
                    c.PhoneNumber,
                    c.Address,
                    c.Account,
                    COALESCE(SUM(p.Amount), 0) AS TotalPrice
                FROM 
                    Customers c
                LEFT JOIN 
                    Payments p ON c.CustomerID = p.CustomerID
                GROUP BY 
                    c.CustomerID, c.CustomerName, c.PhoneNumber, c.Address, c.Account
                """;

        try (Connection conn = new ConnectionToDatabase().connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            customersList.clear();
            while (rs.next()) {
                Customers customer = new Customers(
                        rs.getInt("CustomerID"),
                        rs.getString("CustomerName"),
                        rs.getString("PhoneNumber"),
                        rs.getString("Address"),
                        null, // Assuming ModelNumber is not fetched in this query
                        rs.getInt("Account"),
                        rs.getDouble("TotalPrice")
                );
                customersList.add(customer);
            }

            tableCustomers.setItems(customersList);
            System.out.println("Data loaded successfully."); // Debug statement
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @FXML
    void handleBtRefresh(ActionEvent event) {
        loadCustomerData();
    }

    @FXML
    void handleBtSearch(ActionEvent event) {
        String searchQuery = txtSearch.getText().trim();
        if (searchQuery.isEmpty()) {
            loadCustomerData();
            return;
        }

        String query = """
                SELECT 
                    c.CustomerID,
                    c.CustomerName,
                    c.PhoneNumber,
                    c.Address,
                    c.Account,
                    COALESCE(SUM(p.Amount), 0) AS TotalPrice
                FROM 
                    Customers c
                LEFT JOIN 
                    Payments p ON c.CustomerID = p.CustomerID
                WHERE 
                    c.CustomerID = ?
                GROUP BY 
                    c.CustomerID, c.CustomerName, c.PhoneNumber, c.Address, c.Account
                """;

        try (Connection conn = new ConnectionToDatabase().connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, Integer.parseInt(searchQuery));
            try (ResultSet rs = pstmt.executeQuery()) {
                customersList.clear();
                while (rs.next()) {
                    Customers customer = new Customers(
                            rs.getInt("CustomerID"),
                            rs.getString("CustomerName"),
                            rs.getString("PhoneNumber"),
                            rs.getString("Address"),
                            null, // Assuming ModelNumber is not fetched in this query
                            rs.getInt("Account"),
                            rs.getDouble("TotalPrice")
                    );
                    customersList.add(customer);
                }

                tableCustomers.setItems(customersList);
                System.out.println("Search successful."); // Debug statement
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a valid Customer ID.");
        }
    }

    @FXML
    void insertNewCustomer(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxml/NewCustomer.fxml")));
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Insert New Customer");
            window.setScene(new Scene(root));
            window.setResizable(false);
            window.show();
        } catch (IOException exception) {
            System.out.println("Warning: " + exception.getMessage());
        }
    }

    @FXML
    void updateCustomer(ActionEvent event) {
        Customers selectedCustomer = tableCustomers.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            System.out.println("Error: No customer selected!");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/NewCustomer.fxml"));
            Parent root = loader.load();

            NewCustomerController controller = loader.getController();
            controller.loadCustomerData(selectedCustomer);

            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Update Customer");
            window.setScene(new Scene(root));
            window.setResizable(false);
            window.show();
        } catch (IOException exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }

    @FXML
    void deleteCustomer(ActionEvent event) {
        Customers selectedCustomer = tableCustomers.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            System.out.println("Error: No customer selected!");
            return;
        }

        String deleteQuery = "DELETE FROM Customers WHERE CustomerID = ?";
        try (Connection conn = new ConnectionToDatabase().connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
            pstmt.setInt(1, selectedCustomer.getCustomerID());
            pstmt.executeUpdate();
            customersList.remove(selectedCustomer);
            System.out.println("Success: Customer deleted successfully!");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
