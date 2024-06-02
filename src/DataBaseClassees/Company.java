package DataBaseClassees;

public class Company {
    private int companyID;
    private String companyName;
    private String address;
    private String phoneNumber;
    private String account;

    public Company() {
        super();
    }

    public Company(int companyID, String companyName, String address, String phoneNumber, String account) {
        this.companyID = companyID;
        this.companyName = companyName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.account = account;
    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
