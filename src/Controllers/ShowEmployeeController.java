package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

import DataBaseClassees.Employee;

public class ShowEmployeeController implements Initializable {

    @FXML
    private TableView<Employee> tableEmployee;

    @FXML
    private TableColumn<Employee, Integer> cmID;

    @FXML
    private TableColumn<String, String> cmName;

    @FXML
    private TableColumn<Employee, Double> cmSalary;

    @FXML
    private TableColumn<Employee, String> cmPhone;

    @FXML
    private TableColumn<Employee, String> cmAddress;

    @FXML
    private TableColumn<Employee, Integer> cmMonthlyDaysWorked;

    @FXML
    private TableColumn<Employee, Integer> cmYearsExperience;
    @FXML
    private Button updateEmployees;
    @FXML
    private Button searchEmployee;
    @FXML
    private TextField txtSearch;
    @FXML
    private Button deleteEmployee;
    private Connection con;
    @FXML
    private Button insertNewEmployee;

    @FXML
    private AnchorPane search_appliance;





    @FXML
    void searchEmployee(ActionEvent event) {
        ObservableList<Employee> employees = FXCollections.observableArrayList();

        try {

        ConnectionToDatabase connection = new ConnectionToDatabase();
        connection.setCredentials("yourUsername", "yourPassword");
        Connection con = connection.connectToDB();

            if (con != null) {
                String sql = "SELECT * FROM Employee";
                PreparedStatement statement = con.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    Employee employee = new Employee(
                            resultSet.getInt("employeeID"),
                            resultSet.getString("employeeName"),
                            resultSet.getDouble("employeeSalary"),
                            resultSet.getString("employeePhoneNumber"),
                            resultSet.getString("employeeAddress"),
                            resultSet.getInt("employeeMonthlyDaysWorked"),
                            resultSet.getInt("employeeYearExperience")
                    );
                    employees.add(employee);
                }

                resultSet.close();
                statement.close();
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any SQL exceptions here
        }

        tableEmployee.setItems(employees);
    }

    @FXML
    void handleComboBoxShow(ActionEvent event) {

    }

    @FXML
    void insertNewEmployee(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxml/NewEmployee.fxml")));

            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Insert New Employee");
            window.setScene(new Scene(root));
            window.setResizable(false);
            window.show();
        } catch (IOException exception) {
            Message.displayMassage("Warning", exception.getMessage());
        }
    }


    @FXML
    void updateEmployees(ActionEvent event) {

    }

    @FXML
    void deleteEmployee(ActionEvent event) {

    }

    @FXML
    void handleBtSearch(ActionEvent event) {

        if (!this.txtSearch.getText().trim().isEmpty()) {
            if (!Methods.isNumber(this.txtSearch.getText().trim())) {
                Message.displayMassage("Warning", " Employee id is invalid ");
                this.txtSearch.clear();
                return;
            }


            this.execute(" where E.employeeID=" + Integer.parseInt(this.txtSearch.getText().trim()));
        }

    }
   @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ConnectionToDatabase connection = new ConnectionToDatabase();
        connection.setCredentials("yourUsername", "yourPassword");
        Connection con = connection.connectToDB();

        cmID.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        cmName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        cmSalary.setCellValueFactory(new PropertyValueFactory<>("employeeSalary"));
        cmPhone.setCellValueFactory(new PropertyValueFactory<>("employeePhoneNumber"));
        cmAddress.setCellValueFactory(new PropertyValueFactory<>("employeeAddress"));
        cmMonthlyDaysWorked.setCellValueFactory(new PropertyValueFactory<>("employeeMonthlyDaysWorked"));
        cmYearsExperience.setCellValueFactory(new PropertyValueFactory<>("employeeYearExperience"));

        this.execute(" ");
    }
    public void execute(String str) {
        this.txtSearch.clear();
        this.tableEmployee.getItems().clear();

        try {

            String getEmployee = "SELECT * from Employee E " + str;

            assert con != null;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(getEmployee);
            boolean flag = true;
            while (rs.next()) {


                Employee employee = new Employee(Integer.parseInt(rs.getString(1)), rs.getString(2), Double.parseDouble(rs.getString(3)), rs.getString(4),
                        rs.getString(5), Double.parseDouble(rs.getString(6)), Double.parseDouble(rs.getString(7)));


                this.tableEmployee.getItems().add(employee);
                flag = false;
            }
            if (flag) {
                Message.displayMassage("Warning", "Does not exist");
                return;
            }

            rs.close();
            stmt.close();

        } catch (SQLException sqlException) {
            Message.displayMassage("Warning", sqlException.getMessage());
        }
    }
    @FXML
    void handleBtRefresh(ActionEvent event) {
        this.execute(" ");

    }

}
