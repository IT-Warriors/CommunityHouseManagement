package communityhouseservice.beanservice;

import Bean.NhanKhauBean;
import communityhousebean.UserBean;
import communityhousemodel.UserAccountModel;
import communityhouseservice.UserAccountService;
import services.NhanKhauService;

import java.sql.SQLException;

public class UserBeanService {
    public UserBean getUserBeanById(int userId){
        UserBean userBean = new UserBean();
        try {
            UserAccountModel userMoldel = new UserAccountService().getUserById(userId);
            NhanKhauBean nhanKhauBean = new NhanKhauService().getNhanKhauById(userMoldel.getPersionId());
            userBean.setAccountModel(userMoldel);
            userBean.setNhanKhauBean(nhanKhauBean);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return userBean;
    }
}
