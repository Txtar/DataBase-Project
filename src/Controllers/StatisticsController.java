package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StatisticsController {

    @FXML
    private TextField EmployeeTotalPay;

    @FXML
    private Button btClientsTotalDept;

    @FXML
    private Button btCompanyTotalDept;

    @FXML
    private Button btEmployeeSalary;

    @FXML
    private Button btRefreshAll;

    @FXML
    private TextField txtCompanyTotalDept;

    @FXML
    private TextField txtCustomersTotalDept;

    @FXML
    private TextField txtAverageClientDebt;

    @FXML
    private TextField txtAverageCompanyDebt;

    @FXML
    private TextField txtAverageEmployeeSalary;

    @FXML
    void handleBtClientsTotalDept(ActionEvent event) {
        loadClientsTotalDebt();
    }

    @FXML
    void handleBtCompanyTotalDept(ActionEvent event) {
        loadCompanyTotalDebt();
    }

    @FXML
    void handleBtEmployeeSalary(ActionEvent event) {
        loadEmployeeTotalPay();
    }

    @FXML
    void handleBtRefreshAll(ActionEvent event) {
        loadClientsTotalDebt();
        loadCompanyTotalDebt();
        loadEmployeeTotalPay();
        loadAverageClientDebt();
        loadAverageCompanyDebt();
        loadAverageEmployeeSalary();
    }

    private void loadClientsTotalDebt() {
        String query = "SELECT SUM(Payments.Amount) AS totalDebt FROM Payments " +
                       "JOIN Customers ON Payments.CustomerID = Customers.CustomerID " +
                       "WHERE Payments.PaymentMethod = 'Installment'";
        try (Connection conn = new ConnectionToDatabase().connectToDB();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                txtCustomersTotalDept.setText(String.valueOf(rs.getDouble("totalDebt")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

private void loadCompanyTotalDebt() {
    String query = "SELECT SUM(Account) AS totalDebt FROM Company";
    try (Connection conn = new ConnectionToDatabase().connectToDB();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {
        if (rs.next()) {
            txtCompanyTotalDept.setText(String.valueOf(rs.getDouble("totalDebt")));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    private void loadEmployeeTotalPay() {
        String query = "SELECT SUM(employeeSalary) AS totalSalary FROM Employee";
        try (Connection conn = new ConnectionToDatabase().connectToDB();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                EmployeeTotalPay.setText(String.valueOf(rs.getDouble("totalSalary")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadAverageClientDebt() {
        String query = "SELECT AVG(Payments.Amount) AS averageDebt FROM Payments " +
                       "JOIN Customers ON Payments.CustomerID = Customers.CustomerID " +
                       "WHERE Payments.PaymentMethod = 'Installment'";
        try (Connection conn = new ConnectionToDatabase().connectToDB();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                txtAverageClientDebt.setText(String.valueOf(rs.getDouble("averageDebt")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadAverageCompanyDebt() {
        String query = "SELECT AVG(Total_Price) AS averageDebt FROM Transactions";
        try (Connection conn = new ConnectionToDatabase().connectToDB();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                txtAverageCompanyDebt.setText(String.valueOf(rs.getDouble("averageDebt")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadAverageEmployeeSalary() {
        String query = "SELECT AVG(employeeSalary) AS averageSalary FROM Employee";
        try (Connection conn = new ConnectionToDatabase().connectToDB();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                txtAverageEmployeeSalary.setText(String.valueOf(rs.getDouble("averageSalary")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
