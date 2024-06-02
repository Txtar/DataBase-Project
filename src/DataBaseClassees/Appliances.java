package DataBaseClassees;

public class Appliances {
    private int modelNumber;
    private String applianceName;
    private String warranteeForPeriodOfTime;
    private float buyingPrice;
    private float offerPrice;
    private float sellingPrice;
    private int quantity;
    private int storageID;

    public Appliances() {
        super();
    }

    public Appliances(int modelNumber, String applianceName, String warranteeForPeriodOfTime, float buyingPrice, float offerPrice, float sellingPrice, int quantity, int storageID) {
        this.modelNumber = modelNumber;
        this.applianceName = applianceName;
        this.warranteeForPeriodOfTime = warranteeForPeriodOfTime;
        this.buyingPrice = buyingPrice;
        this.offerPrice = offerPrice;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
        this.storageID = storageID;
    }

    public int getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(int modelNumber) {
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

    public float getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(float buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public float getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(float offerPrice) {
        this.offerPrice = offerPrice;
    }

    public float getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(float sellingPrice) {
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
}
