package communityhouseservice.beanservice;

import Bean.NhanKhauBean;
import communityhousebean.UserBean;
import communityhousemodel.UserAccountModel;
import communityhouseservice.UserAccountService;
import services.NhanKhauService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<UserBean> getAllUserBean(){
        List<UserBean> list = new ArrayList<>();
        try {
            List<UserAccountModel> userAccountModels = new UserAccountService().getAllUser();
            NhanKhauService nhanKhauService = new NhanKhauService();
            for(UserAccountModel u: userAccountModels){
                UserBean userBean = new UserBean();
                NhanKhauBean nhanKhauBean = nhanKhauService.getNhanKhauById(u.getPersionId());
                userBean.setAccountModel(u);
                userBean.setNhanKhauBean(nhanKhauBean);
                list.add(userBean);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }
}
