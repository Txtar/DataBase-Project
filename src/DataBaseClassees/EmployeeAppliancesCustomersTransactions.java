package DataBaseClassees;


public class EmployeeAppliancesCustomersTransactions {
    private int employeeID;
    private int modelNumber;
    private int customerID;

    public EmployeeAppliancesCustomersTransactions() {
        super();

    }

    public EmployeeAppliancesCustomersTransactions(int employeeID, int modelNumber, int customerID) {
        this.employeeID = employeeID;
        this.modelNumber = modelNumber;
        this.customerID = customerID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public int getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(int modelNumber) {
        this.modelNumber = modelNumber;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
}
