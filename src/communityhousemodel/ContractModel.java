package communityhousemodel;

import java.util.Date;

public class ContractModel {
    private int contractId;
    private int userId;
    private int eventId;
    private Date createDate;
    private long cost;
    private int isAccepted;

    public ContractModel(){}

    public ContractModel(int userId, int eventId, Date createDate, long cost, int isAccepted) {
        this.userId = userId;
        this.eventId = eventId;
        this.createDate = createDate;
        this.cost = cost;
        this.isAccepted = isAccepted;
    }

    public ContractModel(int contractId, int userId, int eventId, Date createDate, long cost, int isAccepted) {
        this.contractId = contractId;
        this.userId = userId;
        this.eventId = eventId;
        this.createDate = createDate;
        this.cost = cost;
        this.isAccepted = isAccepted;
    }

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public int getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(int isAccepted) {
        this.isAccepted = isAccepted;
    }
}
