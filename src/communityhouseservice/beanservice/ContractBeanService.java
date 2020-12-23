package communityhouseservice.beanservice;

import Bean.NhanKhauBean;
import communityhousebean.ContractBean;
import communityhousebean.EventBean;
import communityhousebean.HireBean;
import communityhousemodel.*;
import communityhouseservice.EventService;
import communityhouseservice.HireService;
import javafx.util.Pair;
import communityhouseservice.ContractService;
import communityhouseservice.UserAccountService;
import services.MysqlConnection;
import services.NhanKhauService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContractBeanService {
    ContractService contractService = new ContractService();
    UserAccountService userAccountService = new UserAccountService();
    EventService eventService = new EventService();
    HireService hireService = new HireService();

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
                List<HireBean> list = getListFacilityHire(c.getContractId());
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
                List<HireBean> list = getListFacilityHire(c.getContractId());
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

    public boolean updateContract(ContractBean contractBean){
        try {
            int contractId = contractBean.getContractModel().getContractId();
            System.out.println(contractService.updateContract(contractBean.getContractModel()));
            userAccountService.updateUser(contractBean.getUserAccountModel());
            eventService.updateEvent(contractBean.getEventBean().getEvent());
            hireService.deleteHireByContractId(contractId);

            for(HireBean hire: contractBean.getFacilityModelList()){
                HireModel hireModel = new HireModel();
                hireModel.setContractId(contractId);
                hireModel.setFacilityId(hire.getFacility().getFacilityId());
                hireModel.setQuantity(hire.getHiredQuantity());
                hireService.addNewHire(hireModel);
            }
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void insertContract(ContractBean contract){
        try {
            userAccountService.updateUser(contract.getUserAccountModel());
            eventService.addNewEvent(contract.getEventBean().getEvent());
            EventModel event = eventService.getEvent(contract.getEventBean().getHall().getHallId(), contract.getEventBean().getEvent().getFromDate());
            contract.getContractModel().setEventId(event.getEventId());
            contractService.addNewContract(contract.getContractModel());

            ContractModel contractModel = contractService.getContractByEventId(event.getEventId());
            int contractId = contractModel.getContractId();

            for(HireBean hire: contract.getFacilityModelList()){
                HireModel hireModel = new HireModel();
                hireModel.setContractId(contractId);
                hireModel.setFacilityId(hire.getFacility().getFacilityId());
                hireModel.setQuantity(hire.getHiredQuantity());
                hireService.addNewHire(hireModel);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteContract(ContractBean contractBean){
        try {
            hireService.deleteHireByContractId(contractBean.getContractModel().getContractId());
            contractService.deleteContract(contractBean.getContractModel().getContractId());
            eventService.deleteEvent(contractBean.getEventBean().getEvent().getEventId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public List<ContractBean> searchContract(String contractId, String eventName, String fromDate, String isAccepted) throws SQLException, ClassNotFoundException {
        ContractService contractService = new ContractService();
        List<ContractBean> contractBeans = new ArrayList<>();
        NhanKhauService nhanKhauService = new NhanKhauService();
        EventBeanService eventBeanService = new EventBeanService();
        UserAccountService userAccountService = new UserAccountService();
        com.mysql.jdbc.Connection connection = (com.mysql.jdbc.Connection) MysqlConnection.getMysqlConnection();
        String query = "select * from contract c, event e where c.event_id = e.event_id and c.contract_id like '%"+ contractId + "%'" +
                " and e.event_name like '%" + eventName +"%' and e.from_date like '%" + fromDate + "%' and is_accepted like '%" + isAccepted + "%'";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()){
            ContractBean contractBean = new ContractBean();
            ContractModel contractModel = contractService.getContractById(rs.getInt("contract_id"));
            UserAccountModel userAccountModel = userAccountService.getUserById(rs.getInt("user_id"));
            NhanKhauBean nhanKhauBean = nhanKhauService.getNhanKhauById(userAccountModel.getPersionId());
            EventBean eventBean = eventBeanService.getEventBeanById(rs.getInt("event_id"));
            List<HireBean> hireBeans = getListFacilityHire(rs.getInt("contract_id"));
            contractBean.setUserAccountModel(userAccountModel);
            contractBean.setContractModel(contractModel);
            contractBean.setFacilityModelList(hireBeans);
            contractBean.setEventBean(eventBean);
            contractBean.setNhanKhauBean(nhanKhauBean);
            contractBeans.add(contractBean);
        }
        st.close();
        connection.close();
        return contractBeans;
    }

    public List<HireBean> getListFacilityHire(int contractId) throws SQLException, ClassNotFoundException {
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT * from hire, facility where hire.facility_id = facility.facility_id and contract_id = " + contractId;
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        List<HireBean> list = new ArrayList<>();
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
            HireBean hire = new HireBean();
            hire.setFacility(f);
            hire.setHiredQuantity(quantity);
            list.add(hire);
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
