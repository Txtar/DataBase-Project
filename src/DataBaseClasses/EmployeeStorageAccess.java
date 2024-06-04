package DataBaseClasses;

public class EmployeeStorageAccess {
    private int employeeID;
    private int storageID;


    public EmployeeStorageAccess() {
        super();
    }

    public EmployeeStorageAccess(int employeeID, int storageID) {
        this.employeeID = employeeID;
        this.storageID = storageID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public int getStorageID() {
        return storageID;
    }

    public void setStorageID(int storageID) {
        this.storageID = storageID;
    }
}
