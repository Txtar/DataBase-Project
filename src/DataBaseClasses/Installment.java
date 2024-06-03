package DataBaseClasses;

public class Installment {
    private int paymentID;
    private int installmentPeriod;

    public Installment() {
        super();
    }

    public Installment(int paymentID, int installmentPeriod) {
        this.paymentID = paymentID;
        this.installmentPeriod = installmentPeriod;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public int getInstallmentPeriod() {
        return installmentPeriod;
    }

    public void setInstallmentPeriod(int installmentPeriod) {
        this.installmentPeriod = installmentPeriod;
    }
}
