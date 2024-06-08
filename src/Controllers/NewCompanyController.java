package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import DataBaseClasses.Company;
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

public class NewCompanyController implements Initializable {

    @FXML
    private Button addCompany;

    @FXML
    private ComboBox<String> comboAddress;

    @FXML
    private TextField txtAccount;

    @FXML
    private TextField txtCompanyName;

    @FXML
    private TextField txtPhoneNumber;

    private ObservableList<String> addressList = FXCollections.observableArrayList();
    private Company companyToUpdate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAddresses();
    }

    private void loadAddresses() {
        String query = "SELECT DISTINCT Address FROM Company";
        try (Connection conn = new ConnectionToDatabase().connectToDB(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                addressList.add(rs.getString("Address"));
            }
            comboAddress.setItems(addressList);
        } catch (SQLException e) {
            Message.displayMassage("Error: ", e.getMessage());
        }
    }
    @FXML
    void addCompany(ActionEvent event) {
        if (!txtCompanyName.getText().isEmpty()
                && !txtPhoneNumber.getText().isEmpty()
                && !txtAccount.getText().isEmpty()
                && !comboAddress.getValue().isEmpty()) {
            String companyName = txtCompanyName.getText();
            String phoneNumber = txtPhoneNumber.getText();
            String account = txtAccount.getText();
            String address = comboAddress.getValue();

            if (address == null) {
                Message.displayMassage("Error", "Please select an address!");
                return;
            }

            if (companyToUpdate == null) {
                String insertQuery = "INSERT INTO Company (CompanyName, PhoneNumber, Account, Address) VALUES (?, ?, ?, ?)";
                try (Connection conn = new ConnectionToDatabase().connectToDB(); PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                    pstmt.setString(1, companyName);
                    pstmt.setString(2, phoneNumber);
                    pstmt.setString(3, account);
                    pstmt.setString(4, address);

                    pstmt.executeUpdate();
                    Message.displayMassage("Success", "New company added successfully!");

                } catch (SQLException e) {
                    Message.displayMassage("Error: ", e.getMessage());
                }
            } else {
                String updateQuery = "UPDATE Company SET CompanyName = ?, PhoneNumber = ?, Account = ?, Address = ? WHERE CompanyID = ?";
                try (Connection conn = new ConnectionToDatabase().connectToDB(); PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                    pstmt.setString(1, companyName);
                    pstmt.setString(2, phoneNumber);
                    pstmt.setString(3, account);
                    pstmt.setString(4, address);
                    pstmt.setInt(5, companyToUpdate.getCompanyID());

                    pstmt.executeUpdate();
                    Message.displayMassage("Success", "Company updated successfully!");

                } catch (SQLException e) {
                    Message.displayMassage("Error: ", e.getMessage());
                }
            }
        } else {
            Message.displayMassage("Error", "Please insert all required data.");
        }
    }

    @FXML
    void comboAddress(ActionEvent event) {
        String selectedAddress = comboAddress.getValue();
        System.out.println("Selected Address: " + selectedAddress);
    }

    public void loadCompanyData(Company company) {
        companyToUpdate = company;
        txtCompanyName.setText(company.getCompanyName());
        txtPhoneNumber.setText(company.getPhoneNumber());
        txtAccount.setText(company.getAccount());
        comboAddress.setValue(company.getAddress());
    }
}
