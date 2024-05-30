package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NewEmployeeController implements Initializable {

    @FXML
    private Button addEmployee;

    @FXML
    private ComboBox<String> combAddress;

    @FXML
    private TextField txtMonthlyDaysWorked;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhoneNumber;

    @FXML
    private TextField txtSalary;

    @FXML
    private TextField txtYearsExperience;


    @FXML
    void handleCombAddress(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        combAddress.getItems().add("Bethlehem");
        combAddress.getItems().add("Hebron");
        combAddress.getItems().add("Ramallah");
    }

    @FXML
    void addEmployee(ActionEvent event) {

        try {
            if (!txtName.getText().isEmpty() && !txtPhoneNumber.getText().isEmpty() &&
                    !txtSalary.getText().isEmpty() &&
                    !txtMonthlyDaysWorked.getText().isEmpty() &&
                    !txtYearsExperience.getText().isEmpty() &&
                    !combAddress.getValue().isEmpty()) {
                ConnectionToDatabase connection = new ConnectionToDatabase();
                Connection con = connection.connectToDB();


                assert con != null;
                String sql = "INSERT INTO Employee (employeeName, employeeSalary, employeePhoneNumber, employeeAddress, employeeMonthlyDaysWorked, employeeYearExperience) " +
                        "VALUES ( ?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setString(1, txtName.getText()); // employeeName
                statement.setDouble(2, Double.parseDouble(txtSalary.getText())); // employeeSalary
                statement.setString(3, txtPhoneNumber.getText()); // employeePhoneNumber
                statement.setString(4, combAddress.getValue()); // employeeAddress
                statement.setInt(5, Integer.parseInt(txtMonthlyDaysWorked.getText())); // employeeMonthlyDaysWorked
                statement.setInt(6, Integer.parseInt(txtYearsExperience.getText())); // employeeYearExperience

                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    Message.displayMassage("success", "Employee added successfully!");
                } else {
                    Message.displayMassage("Error", "Failed to add employee!");
                }
            } else {
                Message.displayMassage("Error", "Please insert all required data.");

            }


        } catch (SQLException e) {
            Message.displayMassage("Error", "An error occurred while adding employee:\n" + e.getMessage());

        } catch (NumberFormatException e) {
            Message.displayMassage("Error", "Please enter valid numeric values for salary, monthly days worked, and years of experience.");
        }
    }
}
