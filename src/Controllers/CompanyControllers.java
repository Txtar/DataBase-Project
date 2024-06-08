package Controllers;

import DataBaseClasses.Company;
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

public class CompanyControllers implements Initializable {

    @FXML
    private Button btSearch;
    @FXML
    private Button deleteCompany;
    @FXML
    private Button btRefresh;
    @FXML
    private Button insertNewCompany;
    @FXML
    private Button updateCompany;

    @FXML
    private TableColumn<Company, Integer> cmCompanyId;

    @FXML
    private TableColumn<Company, String> cmCompanyName;

    @FXML
    private TableColumn<Company, String> cmPhoneNumber;

    @FXML
    private TableColumn<Company, String> cmAddress;

    @FXML
    private TableColumn<Company, String> cmAccount;

    @FXML
    private TableView<Company> tableCompanies;

    @FXML
    private TextField txtSearch;

    private ObservableList<Company> companyList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmCompanyId.setCellValueFactory(new PropertyValueFactory<>("companyID"));
        cmCompanyName.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        cmPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        cmAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        cmAccount.setCellValueFactory(new PropertyValueFactory<>("account"));

        loadCompanyData();
    }

    private void loadCompanyData() {
        String query = "SELECT * FROM Company";

        try (Connection conn = new ConnectionToDatabase().connectToDB(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            companyList.clear();
            while (rs.next()) {
                Company company = new Company(
                        rs.getInt("CompanyID"),
                        rs.getString("CompanyName"),
                        rs.getString("PhoneNumber"),
                        rs.getString("Address"),
                        rs.getString("Account")
                );
                companyList.add(company);
            }
            tableCompanies.setItems(companyList);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @FXML
    void insertNewCompany(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxml/NewCompany.fxml")));
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Insert New Company");
            window.setScene(new Scene(root));
            window.setResizable(false);
            window.show();
        } catch (IOException exception) {
            Message.displayMassage("Warning: " , exception.getMessage());
        }
    }

    @FXML
    void handleBtRefresh(ActionEvent event) {
        loadCompanyData();
    }

    @FXML
    void handleBtSearch(ActionEvent event) {
        String searchQuery = txtSearch.getText();
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            loadCompanyData();
            return;
        }

        String query = "SELECT * FROM Company WHERE CompanyID =" + searchQuery;
        try (Connection conn = new ConnectionToDatabase().connectToDB(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            try (ResultSet rs = pstmt.executeQuery()) {
                companyList.clear();
                while (rs.next()) {
                    Company company = new Company(
                            rs.getInt("CompanyID"),
                            rs.getString("CompanyName"),
                            rs.getString("PhoneNumber"),
                            rs.getString("Address"),
                            rs.getString("Account")
                    );
                    companyList.add(company);
                }
                tableCompanies.setItems(companyList);
            }
        } catch (SQLException e) {
            Message.displayMassage("Error: " , e.getMessage());
        }
    }

    @FXML
    void updateCompany(ActionEvent event) {
        Company selectedCompany = tableCompanies.getSelectionModel().getSelectedItem();
        if (selectedCompany == null) {
            Message.displayMassage("Error","No company selected!");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/NewCompany.fxml"));
            Parent root = loader.load();

            NewCompanyController controller = loader.getController();
            controller.loadCompanyData(selectedCompany);

            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Update Company");
            window.setScene(new Scene(root));
            window.setResizable(false);
            window.show();
        } catch (IOException exception) {
            Message.displayMassage("Error " , exception.getMessage());
        }
    }

    @FXML
    void deleteComapny(ActionEvent event) {
        Company selectedCompany = tableCompanies.getSelectionModel().getSelectedItem();
        if (selectedCompany == null) {
            Message.displayMassage("Error ","No company selected!");
            return;
        }

        String deleteQuery = "DELETE FROM Company WHERE CompanyID = ?";

        try (Connection conn = new ConnectionToDatabase().connectToDB(); PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
            pstmt.setInt(1, selectedCompany.getCompanyID());
            pstmt.executeUpdate();
            companyList.remove(selectedCompany);
            Message.displayMassage("Success","Company deleted successfully!");
        } catch (SQLException e) {
            Message.displayMassage("Error: " , e.getMessage());
        }
    }
}
