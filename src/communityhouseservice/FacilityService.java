package communityhouseservice;

import communityhousemodel.FacilityModel;
import services.MysqlConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacilityService {
    public boolean addNewFacility(FacilityModel facilityModel) throws SQLException, ClassNotFoundException {
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "INSERT INTO facility(facility_name, total_quantity, available, price, description)"
                + " values (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        try{
            preparedStatement.setString(1, facilityModel.getFacilityName());
            preparedStatement.setInt(2, facilityModel.getTotalQuantity());
            preparedStatement.setInt(3, facilityModel.getAvailable());
            preparedStatement.setLong(4, facilityModel.getPrice());
            preparedStatement.setString(5, facilityModel.getDesciption());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<FacilityModel> getListFacility() throws SQLException, ClassNotFoundException {
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT * from facility";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        List<FacilityModel> list = new ArrayList<>();
        while(rs.next()){
            FacilityModel f = new FacilityModel();
            f.setFacilityId(rs.getInt("facility_id"));
            f.setFacilityName(rs.getString("facility_name"));
            f.setTotalQuantity(rs.getInt("total_quantity"));
            f.setAvailable(rs.getInt("available"));
            f.setPrice(rs.getLong("price"));
            f.setDesciption(rs.getString("description"));
            f.setLastUpdate(rs.getTimestamp("last_update"));
            list.add(f);
        }
        st.close();
        connection.close();
        return list;
    }

    public FacilityModel getFacilityById(int id) throws SQLException, ClassNotFoundException {
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT * from facility where facility_id = " + id;
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        FacilityModel f = new FacilityModel();
        while(rs.next()){
            f.setFacilityId(rs.getInt("facility_id"));
            f.setFacilityName(rs.getString("facility_name"));
            f.setTotalQuantity(rs.getInt("total_quantity"));
            f.setAvailable(rs.getInt("available"));
            f.setPrice(rs.getLong("price"));
            f.setDesciption(rs.getString("description"));
            f.setLastUpdate(rs.getTimestamp("last_update"));
        }
        st.close();
        connection.close();
        return f;
    }

    public boolean updateFacility(FacilityModel newFacility) throws SQLException, ClassNotFoundException {
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "UPDATE facility set facility_name = ?, total_quantity = ?, available = ?, price = ?, description = ?, last_update = NOW() where facility_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        try{
            preparedStatement.setString(1, newFacility.getFacilityName());
            preparedStatement.setInt(2, newFacility.getTotalQuantity());
            preparedStatement.setInt(3, newFacility.getAvailable());
            preparedStatement.setLong(4, newFacility.getPrice());
            preparedStatement.setString(5, newFacility.getDesciption());
            preparedStatement.setInt(6, newFacility.getFacilityId());

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception ex){
            return false;
        } finally {
            preparedStatement.close();
        }
    }

    public boolean deleteFacility(int id) throws SQLException, ClassNotFoundException{
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "delete from facility where facility_id = " + id;
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

    public static void main(String[] args) {
        FacilityService facilityService = new FacilityService();
        try {
            FacilityModel facilityModel = facilityService.getFacilityById(1);
            //facilityModel.setAvailable(6);
            System.out.println(facilityService.deleteFacility(3));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
