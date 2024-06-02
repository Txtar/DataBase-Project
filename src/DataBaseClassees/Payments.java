package DataBaseClassees;

import java.util.Date;

public class Payments {
    private int paymentID;
    private Date dateOfPayment;
    private float amount;
    private String paymentMethod;
    private int customerID;

    public Payments() {
        super();
    }

    public Payments(int paymentID, Date dateOfPayment, float amount, String paymentMethod, int customerID) {
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

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
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
