package communityhouseservice.beanservice;

import Bean.NhanKhauBean;
import communityhousebean.ContractBean;
import communityhousebean.EventBean;
import javafx.util.Pair;
import communityhousemodel.ContractModel;
import communityhousemodel.FacilityModel;
import communityhousemodel.UserAccountModel;
import communityhouseservice.ContractService;
import communityhouseservice.UserAccountService;
import services.MysqlConnection;
import services.NhanKhauService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ContractBeanService {
    public List<ContractBean> getAllContractBean(){
        ContractService contractService = new ContractService();
        List<ContractBean> contractBeans = new ArrayList<>();
        try {
            List<ContractModel> contractModelList = contractService.getListContract();
            NhanKhauService nhanKhauService = new NhanKhauService();
            EventBeanService eventBeanService = new EventBeanService();
            UserAccountService userAccountService = new UserAccountService();
            for (ContractModel c: contractModelList){
                ContractBean contractBean = new ContractBean();
                List<Pair<FacilityModel, Integer>> list = getListFacilityHire(c.getContractId());
                UserAccountModel userAccountModel = userAccountService.getUserById(c.getUserId());
                NhanKhauBean nhanKhauBean = nhanKhauService.getNhanKhauById(userAccountModel.getPersionId());
                EventBean eventBean = eventBeanService.getEventBeanById(c.getEventId());
                contractBean.setEventBean(eventBean);
                contractBean.setContractModel(c);
                contractBean.setFacilityModelList(list);
                contractBean.setNhanKhauBean(nhanKhauBean);
                contractBean.setUserAccountModel(userAccountModel);
                contractBeans.add(contractBean);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return contractBeans;
    }

    public List<ContractBean> getContractBeanByUserId(int user_id){
        ContractService contractService = new ContractService();
        List<ContractBean> contractBeans = new ArrayList<>();
        try {
            List<ContractModel> contractModelList = contractService.getContractByUserId(user_id);
            NhanKhauService nhanKhauService = new NhanKhauService();
            EventBeanService eventBeanService = new EventBeanService();
            UserAccountService userAccountService = new UserAccountService();
            for (ContractModel c: contractModelList){
                ContractBean contractBean = new ContractBean();
                List<Pair<FacilityModel, Integer>> list = getListFacilityHire(c.getContractId());
                UserAccountModel userAccountModel = userAccountService.getUserById(c.getUserId());
                NhanKhauBean nhanKhauBean = nhanKhauService.getNhanKhauById(userAccountModel.getPersionId());
                EventBean eventBean = eventBeanService.getEventBeanById(c.getEventId());
                contractBean.setEventBean(eventBean);
                contractBean.setContractModel(c);
                contractBean.setFacilityModelList(list);
                contractBean.setNhanKhauBean(nhanKhauBean);
                contractBean.setUserAccountModel(userAccountModel);
                contractBeans.add(contractBean);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return contractBeans;
    }

    public List<Pair<FacilityModel, Integer>> getListFacilityHire(int contractId) throws SQLException, ClassNotFoundException {
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT * from hire, facility where hire.facility_id = facility.facility_id and contract_id = " + contractId;
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        List<Pair<FacilityModel, Integer>> list = new ArrayList<>();
        while(rs.next()){
            FacilityModel f = new FacilityModel();
            f.setFacilityId(rs.getInt("facility_id"));
            f.setFacilityName(rs.getString("facility_name"));
            f.setTotalQuantity(rs.getInt("total_quantity"));
            f.setAvailable(rs.getInt("available"));
            f.setPrice(rs.getLong("price"));
            f.setDesciption(rs.getString("description"));
            f.setLastUpdate(rs.getTimestamp("last_update"));
            int quantity = rs.getInt("quantity");
            Pair<FacilityModel, Integer> pair = new Pair<>(f, quantity);
            list.add(pair);
        }
        st.close();
        connection.close();
        return list;
    }

    public static void main(String[] args) {
        ContractBeanService contractBeanService = new ContractBeanService();
        List<ContractBean> contractBeanList = contractBeanService.getContractBeanByUserId(6);
        System.out.println(contractBeanList.get(0).getContractModel().getContractId());
    }
}
