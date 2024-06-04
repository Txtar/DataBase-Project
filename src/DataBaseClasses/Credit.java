package DataBaseClasses;


public class Credit {
    private int paymentID;
    private String creditCardNumber;

    public Credit() {
        super();

    }

    public Credit(int paymentID, String creditCardNumber) {
        this.paymentID = paymentID;
        this.creditCardNumber = creditCardNumber;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }
}
