package DataBaseClassees;

public class Storage {
    private int storageID;
    private String storageLocation;

    public Storage() {
    }

    public Storage(int storageID, String storageLocation) {
        this.storageID = storageID;
        this.storageLocation = storageLocation;
    }

    public int getStorageID() {
        return storageID;
    }

    public void setStorageID(int storageID) {
        this.storageID = storageID;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

    @Override
    public String toString() {
        return "Storage{" +
                "storageID=" + storageID +
                ", storageLocation='" + storageLocation + '\'' +
                '}';
    }
}
