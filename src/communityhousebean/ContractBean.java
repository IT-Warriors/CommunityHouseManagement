package communityhousebean;

import Bean.NhanKhauBean;
import communityhousemodel.ContractModel;
import communityhousemodel.UserAccountModel;

import java.util.List;

public class ContractBean {
    private ContractModel contractModel;
    private UserAccountModel userAccountModel;
    private NhanKhauBean nhanKhauBean;
    private EventBean eventBean;
    private List<HireBean> facilityModelList;

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

    public List<HireBean> getFacilityModelList() {
        return facilityModelList;
    }

    public void setFacilityModelList(List<HireBean> facilityModelList) {
        this.facilityModelList = facilityModelList;
    }
}
