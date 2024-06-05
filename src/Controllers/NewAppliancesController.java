package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import DataBaseClasses.Appliances;
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

public class NewAppliancesController implements Initializable {

    @FXML
    private Button btAddNewAppliances;

    @FXML
    private ComboBox<Integer> comboCompanyID;

    @FXML
    private ComboBox<Integer> comboStorgeId;

    @FXML
    private TextField txtApplianceName;

    @FXML
    private TextField txtBuyingPrice;

    @FXML
    private TextField txtModelNumber;

    @FXML
    private TextField txtOfferPrice;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtSellingPrice;

    @FXML
    private TextField txtWarrantee;

    @FXML
    private TextField txtCompanyName;

    private ObservableList<Integer> storageIDList = FXCollections.observableArrayList();
    private ObservableList<Integer> companyIDList = FXCollections.observableArrayList();

    private Appliances applianceToUpdate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadStorageIDs();
        loadCompanyIDs();
    }

    private void loadStorageIDs() {
        String query = "SELECT storageID FROM Storage";
        try (Connection conn = new ConnectionToDatabase().connectToDB(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                storageIDList.add(rs.getInt("storageID"));
            }
            comboStorgeId.setItems(storageIDList);
        } catch (SQLException e) {
            Message.displayMassage("Error: ", e.getMessage());
        }
    }

    private void loadCompanyIDs() {
        String query = "SELECT CompanyID FROM Company";
        try (Connection conn = new ConnectionToDatabase().connectToDB(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                companyIDList.add(rs.getInt("CompanyID"));
            }
            comboCompanyID.setItems(companyIDList);
        } catch (SQLException e) {
            Message.displayMassage("Error: ", e.getMessage());
        }
    }

    @FXML
    void btHandleAddNewAppliances(ActionEvent event) {
        String modelNumber = txtModelNumber.getText();
        String applianceName = txtApplianceName.getText();
        double buyingPrice = Double.parseDouble(txtBuyingPrice.getText());
        double offerPrice = Double.parseDouble(txtOfferPrice.getText());
        double sellingPrice = Double.parseDouble(txtSellingPrice.getText());
        int quantity = Integer.parseInt(txtQuantity.getText());
        int storageID = Integer.parseInt(String.valueOf(comboStorgeId.getValue()));
        int companyID = Integer.parseInt(String.valueOf(comboCompanyID.getValue()));
        String warrantee = txtWarrantee.getText();

        if (comboStorgeId == null || comboCompanyID == null) {
            Message.displayMassage("success","Storage ID or Company ID  is not selected!");
            return;
        }

        if (applianceToUpdate == null) {
            String insertQuery = "INSERT INTO Appliances (ModelNumber, ApplianceName, BuyingPrice, OfferPrice, SellingPrice, Quantity, StorageID, CompanyID, WarranteeForPeriodOfTime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = new ConnectionToDatabase().connectToDB(); PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setString(1, modelNumber);
                pstmt.setString(2, applianceName);
                pstmt.setDouble(3, buyingPrice);
                pstmt.setDouble(4, offerPrice);
                pstmt.setDouble(5, sellingPrice);
                pstmt.setInt(6, quantity);
                pstmt.setInt(7, storageID);
                pstmt.setInt(8, companyID);
                pstmt.setString(9, warrantee);

                pstmt.executeUpdate();
                Message.displayMassage("success", "New appliance added successfully!");

            } catch (SQLException e) {
                Message.displayMassage("Error: ", e.getMessage());
            }
        } else {
            String updateQuery = "UPDATE Appliances SET ApplianceName = ?, BuyingPrice = ?, OfferPrice = ?, SellingPrice = ?, Quantity = ?, StorageID = ?, CompanyID = ?, WarranteeForPeriodOfTime = ? WHERE ModelNumber = ?";

            try (Connection conn = new ConnectionToDatabase().connectToDB(); PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                pstmt.setString(1, applianceName);
                pstmt.setDouble(2, buyingPrice);
                pstmt.setDouble(3, offerPrice);
                pstmt.setDouble(4, sellingPrice);
                pstmt.setInt(5, quantity);
                pstmt.setInt(6, storageID);
                pstmt.setInt(7, companyID);
                pstmt.setString(8, warrantee);
                pstmt.setString(9, modelNumber);

                pstmt.executeUpdate();

                Message.displayMassage("success", "Appliance updated successfully!");

            } catch (SQLException e) {
                Message.displayMassage("Error", e.getMessage());

            }
        }
    }

    @FXML
    void handlecomboCompanyID(ActionEvent event) {
        int selectedCompanyID = Integer.parseInt(String.valueOf(comboCompanyID.getValue()));
        txtCompanyName.setEditable(false);
        if (comboCompanyID.getValue() != null) {
            String query = "SELECT CompanyName FROM Company WHERE CompanyID = ?";
            try (Connection conn = new ConnectionToDatabase().connectToDB(); PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, selectedCompanyID);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        txtCompanyName.setText(rs.getString("CompanyName"));
                    }
                }
            } catch (SQLException e) {
                Message.displayMassage("Error: ", e.getMessage());
            }
        }
        System.out.println("Selected Company ID: " + selectedCompanyID);
    }

    @FXML
    void handlecomboStorgeId(ActionEvent event) {
        int selectedStorageID = Integer.parseInt(String.valueOf(comboStorgeId.getValue()));
        System.out.println("Selected Storage ID: " + selectedStorageID);
    }

    public void loadApplianceData(Appliances appliance) {
        applianceToUpdate = appliance;
        txtModelNumber.setText(appliance.getModelNumber());
        txtApplianceName.setText(appliance.getApplianceName());
        txtBuyingPrice.setText(Double.toString(appliance.getBuyingPrice()));
        txtOfferPrice.setText(Double.toString(appliance.getOfferPrice()));
        txtSellingPrice.setText(Double.toString(appliance.getSellingPrice()));
        txtQuantity.setText(Integer.toString(appliance.getQuantity()));
        txtWarrantee.setText(appliance.getWarranteeForPeriodOfTime());
        comboStorgeId.setValue(appliance.getStorageID());
        comboCompanyID.setValue(appliance.getCompanyID());
    }
}
