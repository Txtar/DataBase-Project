package DataBaseClasses;

import java.util.Date;

public class Payment {
    private int paymentID;
    private Date dateOfPayment;
    private double amount;
    private String paymentMethod;
    private int customerID;

    public Payment() {
        super();
    }

    public Payment(int paymentID, Date dateOfPayment, double amount, String paymentMethod, int customerID) {
        this.paymentID = paymentID;
        this.dateOfPayment = dateOfPayment;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.customerID = customerID;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public Date getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(Date dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
}
