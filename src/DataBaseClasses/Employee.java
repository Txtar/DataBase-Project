package DataBaseClasses;
public class Employee  {
    private int employeeID;
    private String employeeName;
    private double employeeSalary;
    private String employeePhoneNumber;
    private String employeeAddress;
    private double employeeMonthlyDaysWorked;
    private double employeeYearExperience;

    public Employee() {
    }

    public Employee(int employeeID, String employeeName, double employeeSalary, String employeePhoneNumber, String employeeAddress, double employeeMonthlyDaysWorked, double employeeYearExperience) {
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.employeeSalary = employeeSalary;
        this.employeePhoneNumber = employeePhoneNumber;
        this.employeeAddress = employeeAddress;
        this.employeeMonthlyDaysWorked = employeeMonthlyDaysWorked;
        this.employeeYearExperience = employeeYearExperience;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public double getEmployeeSalary() {
        return employeeSalary;
    }

    public void setEmployeeSalary(double employeeSalary) {
        this.employeeSalary = employeeSalary;
    }

    public String getEmployeePhoneNumber() {
        return employeePhoneNumber;
    }

    public void setEmployeePhoneNumber(String employeePhoneNumber) {
        this.employeePhoneNumber = employeePhoneNumber;
    }

    public String getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public double getEmployeeMonthlyDaysWorked() {
        return employeeMonthlyDaysWorked;
    }

    public void setEmployeeMonthlyDaysWorked(double employeeMonthlyDaysWorked) {
        this.employeeMonthlyDaysWorked = employeeMonthlyDaysWorked;
    }

    public double getEmployeeYearExperience() {
        return employeeYearExperience;
    }

    public void setEmployeeYearExperience(double employeeYearExperience) {
        this.employeeYearExperience = employeeYearExperience;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeID=" + employeeID +
                ", employeeName='" + employeeName + '\'' +
                ", employeeSalary=" + employeeSalary +
                ", employeePhoneNumber='" + employeePhoneNumber + '\'' +
                ", employeeAddress='" + employeeAddress + '\'' +
                ", employeeMonthlyDaysWorked=" + employeeMonthlyDaysWorked +
                ", employeeYearExperience=" + employeeYearExperience +
                '}';
    }

}
