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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.ResourceBundle;

public class AppliancesController implements Initializable {

    @FXML
    private Button btSearch;

    @FXML
    private TableColumn<Appliances, Double> cmBuyingPrice;

    @FXML
    private TableColumn<Appliances, Integer> cmCompanyID;

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
        cmCompanyID.setCellValueFactory(new PropertyValueFactory<>("companyID"));

        loadAppliancesData();
    }

    private void loadAppliancesData() {
        String query = "SELECT * FROM Appliances";

        try (Connection conn = new ConnectionToDatabase().connectToDB(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
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
                        rs.getInt("StorageID"),
                        rs.getInt("CompanyID")
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
    }

    @FXML
    void handleBtSearch(ActionEvent event) {
        String modelNumber = txSearch.getText();
        if (modelNumber == null || modelNumber.trim().isEmpty()) {
            loadAppliancesData();
            return;
        }

        String query = "SELECT * FROM Appliances WHERE ModelNumber LIKE '%" + modelNumber + "%'";

        try (Connection conn = new ConnectionToDatabase().connectToDB(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
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
                        rs.getInt("StorageID"),
                        rs.getInt("CompanyID")
                );
                appliancesList.add(appliance);
            }
            tableProducts.setItems(appliancesList);
        } catch (SQLException e) {
            Message.displayMassage("Error: " , e.getMessage());
        }
    }

    @FXML
    void handleBtUpdate(ActionEvent event) {
        Appliances selectedAppliance = tableProducts.getSelectionModel().getSelectedItem();
        if (selectedAppliance == null) {
            Message.displayMassage("Error","No appliance selected!");
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
            Message.displayMassage("Error  " , exception.getMessage());

        }
    }

    @FXML
    void handleBtDeleteAppliances(ActionEvent event) {
        Appliances selectedAppliance = tableProducts.getSelectionModel().getSelectedItem();
        if (selectedAppliance == null) {
            Message.displayMassage("Error ","No appliance selected!");
            return;
        }

        String deleteQuery = "DELETE FROM Appliances WHERE ModelNumber = ?";

        try (Connection conn = new ConnectionToDatabase().connectToDB(); PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
            pstmt.setString(1, selectedAppliance.getModelNumber());
            pstmt.executeUpdate();
            appliancesList.remove(selectedAppliance);
            Message.displayMassage("success","Appliance deleted successfully!");
        } catch (SQLException e) {
            Message.displayMassage("Error: " , e.getMessage());
        }
    }
}
