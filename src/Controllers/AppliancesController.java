package Controllers;

import DataBaseClasses.Appliances;
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

public class AppliancesController implements Initializable {

    @FXML
    private ComboBox<String> CombApplianceName;

    @FXML
    private ComboBox<String> CombCompanyName;

    @FXML
    private ComboBox<String> combStorageLocation;

    @FXML
    private Button btSearch;

        @FXML
    private Button btSearch1;

    @FXML
    private TableColumn<Appliances, String> cmStorageLocation;

    @FXML
    private TableColumn<Appliances, Double> cmBuyingPrice;

    @FXML
    private TableColumn<Appliances, String> cmCompanyName;

    @FXML
    private TableColumn<Appliances, String> cmModelNumber;

    @FXML
    private TableColumn<Appliances, String> cmName;

    @FXML
    private TableColumn<Appliances, Double> cmOfferPrice;

    @FXML
    private TableColumn<Appliances, Integer> cmQuantity;

    @FXML
    private TableColumn<Appliances, Double> cmSellingPrice;

    @FXML
    private TableColumn<Appliances, String> cmWarrantee;

    @FXML
    private TableView<Appliances> tableProducts;

    @FXML
    private TextField txSearch;

    private ObservableList<Appliances> appliancesList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmModelNumber.setCellValueFactory(new PropertyValueFactory<>("modelNumber"));
        cmName.setCellValueFactory(new PropertyValueFactory<>("applianceName"));
        cmBuyingPrice.setCellValueFactory(new PropertyValueFactory<>("buyingPrice"));
        cmOfferPrice.setCellValueFactory(new PropertyValueFactory<>("offerPrice"));
        cmSellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        cmQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        cmWarrantee.setCellValueFactory(new PropertyValueFactory<>("warranteeForPeriodOfTime"));
        cmCompanyName.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        cmStorageLocation.setCellValueFactory(new PropertyValueFactory<>("storageLocation"));

        loadAppliancesData();
         loadComboData();

    }

    private void loadAppliancesData() {
        String query = "SELECT a.ModelNumber, a.ApplianceName, a.WarranteeForPeriodOfTime, a.BuyingPrice, a.OfferPrice, a.SellingPrice, a.Quantity, c.CompanyName, s.storageLocation " +
                "FROM Appliances a " +
                "JOIN Company c ON a.CompanyID = c.CompanyID " +
                "JOIN Storage s ON a.StorageID = s.storageID";

        try (Connection conn = new ConnectionToDatabase().connectToDB();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            appliancesList.clear();
            while (rs.next()) {
                Appliances appliance = new Appliances(
                        rs.getString("ModelNumber"),
                        rs.getString("ApplianceName"),
                        rs.getString("WarranteeForPeriodOfTime"),
                        rs.getDouble("BuyingPrice"),
                        rs.getDouble("OfferPrice"),
                        rs.getDouble("SellingPrice"),
                        rs.getInt("Quantity"),
                        rs.getString("CompanyName"),
                        rs.getString("StorageLocation")
                );
                appliancesList.add(appliance);
            }
            tableProducts.setItems(appliancesList);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @FXML
    void handleBtAddNewAppliances(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxml/NewAppliances.fxml")));
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Insert New Appliances");
            window.setScene(new Scene(root));
            window.setResizable(false);
            window.show();
        } catch (IOException exception) {
            System.out.println("Warning: " + exception.getMessage());
        }
    }

    @FXML
    void handleBtRefresh(ActionEvent event) {
        loadAppliancesData();
        CombApplianceName.setPromptText("Select Appliance");

        CombCompanyName.setPromptText("Select Company");

        combStorageLocation.setPromptText("Select Storage Location");
    }

    @FXML
    void handleBtSearch(ActionEvent event) {
        String modelNumber = txSearch.getText();
        if (modelNumber == null || modelNumber.trim().isEmpty()) {
            loadAppliancesData();
            return;
        }

        String query = "SELECT a.ModelNumber, a.ApplianceName, a.WarranteeForPeriodOfTime, a.BuyingPrice, a.OfferPrice, a.SellingPrice, a.Quantity, c.CompanyName, s.storageLocation AS StorageName " +
                "FROM Appliances a " +
                "JOIN Company c ON a.CompanyID = c.CompanyID " +
                "JOIN Storage s ON a.StorageID = s.storageID " +
                "WHERE a.ModelNumber LIKE ?";

        try (Connection conn = new ConnectionToDatabase().connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + modelNumber + "%");
            ResultSet rs = pstmt.executeQuery();
            appliancesList.clear();
            while (rs.next()) {
                Appliances appliance = new Appliances(
                        rs.getString("ModelNumber"),
                        rs.getString("ApplianceName"),
                        rs.getString("WarranteeForPeriodOfTime"),
                        rs.getDouble("BuyingPrice"),
                        rs.getDouble("OfferPrice"),
                        rs.getDouble("SellingPrice"),
                        rs.getInt("Quantity"),
                        rs.getString("CompanyName"),
                        rs.getString("StorageName")
                );
                appliancesList.add(appliance);
            }
            tableProducts.setItems(appliancesList);
        } catch (SQLException e) {
            Message.displayMassage("Error: ", e.getMessage());
        }
    }

    @FXML
    void handleBtUpdate(ActionEvent event) {
        Appliances selectedAppliance = tableProducts.getSelectionModel().getSelectedItem();
        if (selectedAppliance == null) {
            Message.displayMassage("Error", "No appliance selected!");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/NewAppliances.fxml"));
            Parent root = loader.load();

            NewAppliancesController controller = loader.getController();
            controller.loadApplianceData(selectedAppliance);

            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Update Appliance");
            window.setScene(new Scene(root));
            window.setResizable(false);
            window.show();
        } catch (IOException exception) {
            Message.displayMassage("Error", exception.getMessage());
        }
    }

    @FXML
    void handleBtDeleteAppliances(ActionEvent event) {
        Appliances selectedAppliance = tableProducts.getSelectionModel().getSelectedItem();
        if (selectedAppliance == null) {
            Message.displayMassage("Error", "No appliance selected!");
            return;
        }

        String deleteQuery = "DELETE FROM Appliances WHERE ModelNumber = ?";

        try (Connection conn = new ConnectionToDatabase().connectToDB();
             PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
            pstmt.setString(1, selectedAppliance.getModelNumber());
            pstmt.executeUpdate();
            appliancesList.remove(selectedAppliance);
            Message.displayMassage("Success", "Appliance deleted successfully!");
        } catch (SQLException e) {
            Message.displayMassage("Error: ", e.getMessage());
        }
    }


    private void loadComboData() {
        // Load data directly into combo boxes
        ObservableList<String> applianceNames = FXCollections.observableArrayList(
                "NULL",
                "Washing Machine",
                "Refrigerator"
        );
        CombApplianceName.setItems(applianceNames);

        ObservableList<String> companyNames = FXCollections.observableArrayList(
                "NULL",
                "Tech Corp",
                "Home Goods Inc."
        );
        CombCompanyName.setItems(companyNames);

        ObservableList<String> storageLocations = FXCollections.observableArrayList(
                "NULL",
                "Warehouse A",
                "Warehouse B"
        );
        combStorageLocation.setItems(storageLocations);
    }

    @FXML
void HandleCombApplianceName(ActionEvent event) {
    String selectedAppliance = CombApplianceName.getValue();
    // You can implement logic based on the selected appliance
}

@FXML
void HandleCombCompanyName(ActionEvent event) {
    String selectedCompany = CombCompanyName.getValue();
    // You can implement logic based on the selected company
}

@FXML
void HandleCombStorageName(ActionEvent event) {
    String selectedStorageLocation = combStorageLocation.getValue();
    // You can implement logic based on the selected storage location
}


@FXML
void handleBtSearch1(ActionEvent event) {
    String selectedAppliance = CombApplianceName.getValue();
    String selectedCompany = CombCompanyName.getValue();
    String selectedStorageLocation = combStorageLocation.getValue();

    // Construct your base query
    StringBuilder queryBuilder = new StringBuilder("SELECT ModelNumber, ApplianceName, WarranteeForPeriodOfTime, BuyingPrice, OfferPrice, SellingPrice, Quantity, CompanyName, storageLocation " +
                   "FROM Appliances a " +
                   "JOIN Company c ON a.CompanyID = c.CompanyID " +
                   "JOIN Storage s ON a.StorageID = s.storageID ");

    // Construct WHERE clause based on selected values
    StringBuilder whereClause = new StringBuilder("WHERE 1=1 "); // Always true to make it easier to append conditions

    if (selectedAppliance != null && !selectedAppliance.isEmpty()) {
        whereClause.append("AND a.ApplianceName = ? ");
    }
    if (selectedCompany != null && !selectedCompany.isEmpty()) {
        whereClause.append("AND c.CompanyName = ? ");
    }
    if (selectedStorageLocation != null && !selectedStorageLocation.isEmpty()) {
        whereClause.append("AND s.storageLocation = ? ");
    }

    // Remove trailing space from the last AND or OR
    if (whereClause.length() > 0) {
        whereClause.delete(whereClause.length() - 1, whereClause.length());
    }

    String query = queryBuilder.append(whereClause).toString();

    try (Connection conn = new ConnectionToDatabase().connectToDB();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        int parameterIndex = 1;
        if (selectedAppliance != null && !selectedAppliance.isEmpty()) {
            pstmt.setString(parameterIndex++, selectedAppliance);
        }
        if (selectedCompany != null && !selectedCompany.isEmpty()) {
            pstmt.setString(parameterIndex++, selectedCompany);
        }
        if (selectedStorageLocation != null && !selectedStorageLocation.isEmpty()) {
            pstmt.setString(parameterIndex, selectedStorageLocation);
        }

        ResultSet rs = pstmt.executeQuery();
        // Clear previous data
        appliancesList.clear();
        while (rs.next()) {
            Appliances appliance = new Appliances(
                rs.getString("ModelNumber"),
                rs.getString("ApplianceName"),
                rs.getString("WarranteeForPeriodOfTime"),
                rs.getDouble("BuyingPrice"),
                rs.getDouble("OfferPrice"),
                rs.getDouble("SellingPrice"),
                rs.getInt("Quantity"),
                rs.getString("CompanyName"),
                rs.getString("storageLocation")
            );
            appliancesList.add(appliance);
        }
        tableProducts.setItems(appliancesList); // Set the retrieved data to the TableView

// Reset combo box values and prompt text
CombApplianceName.getSelectionModel().clearSelection();
CombApplianceName.setPromptText("Select Appliance");

CombCompanyName.getSelectionModel().clearSelection();
CombCompanyName.setPromptText("Select Company");

combStorageLocation.getSelectionModel().clearSelection();
combStorageLocation.setPromptText("Select Storage Location");

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

}
