package communityhouseservice;

import communityhousemodel.HireModel;
import services.MysqlConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HireService {
    FacilityService facilityService = new FacilityService();
    public boolean addNewHire(HireModel hireModel) throws SQLException, ClassNotFoundException {
        java.sql.Connection connection = MysqlConnection.getMysqlConnection();
        String query = "INSERT INTO hire (contract_id, facility_id, quantity)"
                + " values (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        try {
            preparedStatement.setInt(1, hireModel.getContractId());
            preparedStatement.setInt(2, hireModel.getFacilityId());
            preparedStatement.setInt(3, hireModel.getQuantity());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            facilityService.updateFacilityAvailable(hireModel.getFacilityId(), - hireModel.getQuantity());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteHireByContractId(int contractId) throws SQLException, ClassNotFoundException {
        java.sql.Connection connection = MysqlConnection.getMysqlConnection();
        String query = "select * from hire where contract_id = " + contractId;
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        List<Integer> facilities = new ArrayList<>();
        while (rs.next()){
            facilityService.updateFacilityAvailable(rs.getInt("facility_id"), rs.getInt("quantity"));
        }
        query = "delete from hire where contract_id = " + contractId;

        try {
            if (st.executeUpdate(query) >= 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {

    }
}
