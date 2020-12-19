package communityhouseservice;

import communityhousemodel.ContractModel;
import services.MysqlConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContractService {
    public boolean addNewContract(ContractModel contractModel) throws SQLException, ClassNotFoundException {
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "INSERT INTO contract(user_id, event_id, cost)"
                + " values (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        try{
            preparedStatement.setInt(1, contractModel.getUserId());
            preparedStatement.setInt(2, contractModel.getEventId());
            preparedStatement.setLong(3, contractModel.getCost());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<ContractModel> getListContract() throws SQLException, ClassNotFoundException {
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT * from contract";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        List<ContractModel> list = new ArrayList<>();
        while(rs.next()){
            ContractModel contractModel = new ContractModel();
            contractModel.setContractId(rs.getInt("contract_id"));
            contractModel.setUserId(rs.getInt("user_id"));
            contractModel.setEventId(rs.getInt("event_id"));
            contractModel.setCreateDate(rs.getTimestamp("create_date"));
            contractModel.setCost(rs.getLong("cost"));
            contractModel.setIsAccepted(rs.getInt("is_accepted"));
            list.add(contractModel);
        }
        st.close();
        connection.close();
        return list;
    }

    public ContractModel getContractById(int id) throws SQLException, ClassNotFoundException {
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT * from contract where contract_id = " + id;
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        ContractModel contractModel = new ContractModel();
        while(rs.next()){
            contractModel.setContractId(rs.getInt("contract_id"));
            contractModel.setUserId(rs.getInt("user_id"));
            contractModel.setEventId(rs.getInt("event_id"));
            contractModel.setCreateDate(rs.getTimestamp("create_date"));
            contractModel.setCost(rs.getLong("cost"));
            contractModel.setIsAccepted(rs.getInt("is_accepted"));
        }
        st.close();
        connection.close();
        return contractModel;
    }

    public boolean updateContract(ContractModel newContract) throws SQLException, ClassNotFoundException {
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "UPDATE contract set user_id = ?, event_id = ?, create_date = ?, cost = ?, is_accepted = ? where contract_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        try{
            preparedStatement.setInt(1, newContract.getUserId());
            preparedStatement.setInt(2, newContract.getEventId());
            preparedStatement.setTimestamp(3, (Timestamp) newContract.getCreateDate());
            preparedStatement.setLong(4, newContract.getCost());
            preparedStatement.setInt(5, newContract.getIsAccepted());
            preparedStatement.setInt(6, newContract.getContractId());

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception ex){
            return false;
        } finally {
            preparedStatement.close();
        }
    }

    public boolean deleteContract(int id) throws SQLException, ClassNotFoundException{
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "delete from contract where contract_id = " + id;
        Statement st = connection.createStatement();
        try{
            if(st.executeUpdate(query) > 0){
                return true;
            };
            return false;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
