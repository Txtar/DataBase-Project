package DataBaseClasses;

import java.util.Date;

public class Transactions {
    private int transactionID;
    private Date purchaseDate;
    private int quantityBought;
    private float totalPrice;
    private int companyID;

    public Transactions() {
        super();
    }

    public Transactions(int transactionID, Date purchaseDate, int quantityBought, float totalPrice, int companyID) {
        this.transactionID = transactionID;
        this.purchaseDate = purchaseDate;
        this.quantityBought = quantityBought;
        this.totalPrice = totalPrice;
        this.companyID = companyID;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getQuantityBought() {
        return quantityBought;
    }

    public void setQuantityBought(int quantityBought) {
        this.quantityBought = quantityBought;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }
}

