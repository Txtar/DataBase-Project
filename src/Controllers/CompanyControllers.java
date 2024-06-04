package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CompanyControllers {
    @FXML
    private Button btRefresh;

    @FXML
    private Button btSearch;

    @FXML
    private TableColumn<?, ?> cmAccount;

    @FXML
    private TableColumn<?, ?> cmAddress;

    @FXML
    private TableColumn<?, ?> cmCompanyId;

    @FXML
    private TableColumn<?, ?> cmCompanyName;

    @FXML
    private TableColumn<?, ?> cmPhoneNumber;

    @FXML
    private Button deleteCompany;

    @FXML
    private Button insertNewCompany;

    @FXML
    private TableView<?> tableEmployee;

    @FXML
    private TextField txtSearch;

    @FXML
    private Button updateCompany;

    @FXML
    void deleteComapny(ActionEvent event) {

    }

    @FXML
    void handleBtRefresh(ActionEvent event) {

    }

    @FXML
    void handleBtSearch(ActionEvent event) {

    }

    @FXML
    void insertNewComapny(ActionEvent event) {

    }

    @FXML
    void updateCompany(ActionEvent event) {

    }
}
