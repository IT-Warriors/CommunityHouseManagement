package communityhousebean;

import Bean.NhanKhauBean;
import javafx.util.Pair;
import communityhousemodel.ContractModel;
import communityhousemodel.FacilityModel;
import communityhousemodel.UserAccountModel;

import java.util.List;

public class ContractBean {
    private ContractModel contractModel;
    private UserAccountModel userAccountModel;
    private NhanKhauBean nhanKhauBean;
    private EventBean eventBean;
    private List<Pair<FacilityModel, Integer>> facilityModelList;

    public EventBean getEventBean() {
        return eventBean;
    }

    public void setEventBean(EventBean eventBean) {
        this.eventBean = eventBean;
    }

    public NhanKhauBean getNhanKhauBean() {
        return nhanKhauBean;
    }

    public void setNhanKhauBean(NhanKhauBean nhanKhauBean) {
        this.nhanKhauBean = nhanKhauBean;
    }

    public ContractModel getContractModel() {
        return contractModel;
    }

    public void setContractModel(ContractModel contractModel) {
        this.contractModel = contractModel;
    }

    public UserAccountModel getUserAccountModel() {
        return userAccountModel;
    }

    public void setUserAccountModel(UserAccountModel userAccountModel) {
        this.userAccountModel = userAccountModel;
    }

    public List<Pair<FacilityModel, Integer>> getFacilityModelList() {
        return facilityModelList;
    }

    public void setFacilityModelList(List<Pair<FacilityModel, Integer>> facilityModelList) {
        this.facilityModelList = facilityModelList;
    }
}
