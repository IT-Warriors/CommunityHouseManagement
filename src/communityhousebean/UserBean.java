package communityhousebean;

import Bean.NhanKhauBean;
import communityhousemodel.UserAccountModel;

import java.util.List;

public class UserBean {
    private UserAccountModel accountModel;
    private NhanKhauBean nhanKhauBean;

    public UserAccountModel getAccountModel() {
        return accountModel;
    }

    public void setAccountModel(UserAccountModel accountModel) {
        this.accountModel = accountModel;
    }

    public NhanKhauBean getNhanKhauBean() {
        return nhanKhauBean;
    }

    public void setNhanKhauBean(NhanKhauBean nhanKhauBean) {
        this.nhanKhauBean = nhanKhauBean;
    }
}
