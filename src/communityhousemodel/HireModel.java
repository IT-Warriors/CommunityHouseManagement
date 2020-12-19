package communityhousemodel;

public class HireModel {
    private int contractId;
    private int facilityId;
    private int quantity;

    public HireModel(){}

    public HireModel(int contractId, int facilityId, int quantity) {
        this.contractId = contractId;
        this.facilityId = facilityId;
        this.quantity = quantity;
    }

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public int getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(int facilityId) {
        this.facilityId = facilityId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
