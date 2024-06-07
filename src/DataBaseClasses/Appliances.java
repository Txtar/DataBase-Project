package DataBaseClasses;

public class Appliances {
    private String modelNumber;
    private String applianceName;
    private String warranteeForPeriodOfTime;
    private double buyingPrice;
    private double offerPrice;
    private double sellingPrice;
    private int quantity;
    private int storageID;
    private int companyID;
    private String storageLocation;
    private String companyName;

    public Appliances(String modelNumber, String applianceName, String warranteeForPeriodOfTime, double buyingPrice, double offerPrice, double sellingPrice, int quantity, String storageLocation, String companyName) {
        this.modelNumber = modelNumber;
        this.applianceName = applianceName;
        this.warranteeForPeriodOfTime = warranteeForPeriodOfTime;
        this.buyingPrice = buyingPrice;
        this.offerPrice = offerPrice;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
        this.storageID = storageID;
        this.companyID = companyID;
        this.storageLocation = storageLocation;
        this.companyName = companyName;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getApplianceName() {
        return applianceName;
    }

    public void setApplianceName(String applianceName) {
        this.applianceName = applianceName;
    }

    public String getWarranteeForPeriodOfTime() {
        return warranteeForPeriodOfTime;
    }

    public void setWarranteeForPeriodOfTime(String warranteeForPeriodOfTime) {
        this.warranteeForPeriodOfTime = warranteeForPeriodOfTime;
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public double getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(double offerPrice) {
        this.offerPrice = offerPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStorageID() {
        return storageID;
    }

    public void setStorageID(int storageID) {
        this.storageID = storageID;
    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
