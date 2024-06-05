package DataBaseClasses;

import java.util.Date;

public class Transactions {
    private int transactionID;
    private Date purchaseDate;
    private double amount;
    private int quantityBought;
    private double totalPrice;
    private int companyID;
    private String companyName;
    private String model;

    public Transactions() {
        super();
    }

    public Transactions(int transactionID, Date purchaseDate, double amount, int quantityBought, double totalPrice, int companyID) {
        this.transactionID = transactionID;
        this.purchaseDate = purchaseDate;
        this.amount = amount;
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
