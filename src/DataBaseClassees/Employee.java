package DataBaseClassees;

public class Employee {
    private String employeeName;
    private double employeeSalary;
    private String employeePhoneNumber;
    private String employeeAddress;
    private int employeeMonthlyDaysWorked;
    private int employeeYearExperience;

    public Employee() {
    }

    public Employee(String employeeName, double employeeSalary, String employeePhoneNumber, String employeeAddress, int employeeMonthlyDaysWorked, int employeeYearExperience) {
        this.employeeName = employeeName;
        this.employeeSalary = employeeSalary;
        this.employeePhoneNumber = employeePhoneNumber;
        this.employeeAddress = employeeAddress;
        this.employeeMonthlyDaysWorked = employeeMonthlyDaysWorked;
        this.employeeYearExperience = employeeYearExperience;
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

    public int getEmployeeMonthlyDaysWorked() {
        return employeeMonthlyDaysWorked;
    }

    public void setEmployeeMonthlyDaysWorked(int employeeMonthlyDaysWorked) {
        this.employeeMonthlyDaysWorked = employeeMonthlyDaysWorked;
    }

    public int getEmployeeYearExperience() {
        return employeeYearExperience;
    }

    public void setEmployeeYearExperience(int employeeYearExperience) {
        this.employeeYearExperience = employeeYearExperience;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeName='" + employeeName + '\'' +
                ", employeeSalary=" + employeeSalary +
                ", employeePhoneNumber='" + employeePhoneNumber + '\'' +
                ", employeeAddress='" + employeeAddress + '\'' +
                ", employeeMonthlyDaysWorked=" + employeeMonthlyDaysWorked +
                ", employeeYearExperience=" + employeeYearExperience +
                '}';
    }
}
