package communityhouseservice;

import communityhousemodel.FacilityModel;
import services.MysqlConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.openxmlformats.schemas.drawingml.x2006.main.CTTextListStyle;

import com.mysql.jdbc.Connection;

public class FacilityService {
    /**
     * This method is used to add a new facility into facility table in the database
     *
     * @param facilityModel
     * @return true if the facility is added successfully, false otherwise
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public boolean addNewFacility(FacilityModel facilityModel) throws SQLException, ClassNotFoundException {
        java.sql.Connection connection = MysqlConnection.getMysqlConnection();
        String query = "INSERT INTO facility(facility_name, total_quantity, available, price, description, facility_kind)"
                + " values (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        try {
            preparedStatement.setString(1, facilityModel.getFacilityName());
            preparedStatement.setInt(2, facilityModel.getTotalQuantity());
            preparedStatement.setInt(3, facilityModel.getAvailable());
            preparedStatement.setLong(4, (long) facilityModel.getPrice());
            preparedStatement.setString(5, facilityModel.getDesciption());
            preparedStatement.setString(6, facilityModel.getFacilityKind());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * This method is used to get a list of all facilities in the database
     *
     * @return the list of all facilities
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public List<FacilityModel> getAllFacilities() throws SQLException, ClassNotFoundException {
        java.sql.Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT * FROM facility";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        List<FacilityModel> list = new ArrayList<>();
        while (rs.next()) {
            FacilityModel f = new FacilityModel();
            f.setFacilityId(rs.getInt("facility_id"));
            f.setFacilityName(rs.getString("facility_name"));
            f.setTotalQuantity(rs.getInt("total_quantity"));
            f.setAvailable(rs.getInt("available"));
            f.setPrice(rs.getLong("price"));
            f.setDesciption(rs.getString("description"));
            f.setLastUpdate(rs.getTimestamp("last_update"));
            f.setFacilityKind(rs.getString("facility_kind"));

            list.add(f);
        }
        st.close();
        connection.close();
        return list;
    }

    /**
     * This method is used to get a list of facilities ordered by last_update
     *
     * @return the list of facilities ordered by last_update
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public List<FacilityModel> getFacilityByLastUpdate() throws SQLException, ClassNotFoundException {
        java.sql.Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT * FROM facility ORDER BY last_update DESC";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        List<FacilityModel> list = new ArrayList<>();
        while (rs.next()) {
            FacilityModel f = new FacilityModel();
            f.setFacilityId(rs.getInt("facility_id"));
            f.setFacilityName(rs.getString("facility_name"));
            f.setTotalQuantity(rs.getInt("total_quantity"));
            f.setAvailable(rs.getInt("available"));
            f.setPrice(rs.getLong("price"));
            f.setDesciption(rs.getString("description"));
            f.setLastUpdate(rs.getTimestamp("last_update"));
            f.setFacilityKind(rs.getString("facility_kind"));

            list.add(f);
        }
        st.close();
        connection.close();
        return list;
    }

    /**
     * This method is used to get a list of facilities ordered by name from A to Z
     *
     * @return the list of facilities ordered by name from A to Z
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public List<FacilityModel> getFacilityByNameAToZ() throws SQLException, ClassNotFoundException {
        java.sql.Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT * FROM facility ORDER BY facility_name ASC";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        List<FacilityModel> list = new ArrayList<>();
        while (rs.next()) {
            FacilityModel f = new FacilityModel();
            f.setFacilityId(rs.getInt("facility_id"));
            f.setFacilityName(rs.getString("facility_name"));
            f.setTotalQuantity(rs.getInt("total_quantity"));
            f.setAvailable(rs.getInt("available"));
            f.setPrice(rs.getLong("price"));
            f.setDesciption(rs.getString("description"));
            f.setLastUpdate(rs.getTimestamp("last_update"));
            f.setFacilityKind(rs.getString("facility_kind"));

            list.add(f);
        }
        st.close();
        connection.close();
        return list;
    }

    /**
     * This method is used to get a list of facilities ordered by name from Z to A
     *
     * @return the list of facilities ordered by name from Z to A
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public List<FacilityModel> getFacilityByNameZToA() throws SQLException, ClassNotFoundException {
        java.sql.Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT * FROM facility ORDER BY facility_name DESC";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        List<FacilityModel> list = new ArrayList<>();
        while (rs.next()) {
            FacilityModel f = new FacilityModel();
            f.setFacilityId(rs.getInt("facility_id"));
            f.setFacilityName(rs.getString("facility_name"));
            f.setTotalQuantity(rs.getInt("total_quantity"));
            f.setAvailable(rs.getInt("available"));
            f.setPrice(rs.getLong("price"));
            f.setDesciption(rs.getString("description"));
            f.setLastUpdate(rs.getTimestamp("last_update"));
            f.setFacilityKind(rs.getString("facility_kind"));

            list.add(f);
        }
        st.close();
        connection.close();
        return list;
    }

    /**
     * This method is used to get a list of facilities which are not available
     *
     * @return the list of facilities which are not available
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public List<FacilityModel> getNotAvailableFacility() throws SQLException, ClassNotFoundException {
        java.sql.Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT * FROM facility WHERE available = 0";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        List<FacilityModel> list = new ArrayList<>();
        while (rs.next()) {
            FacilityModel f = new FacilityModel();
            f.setFacilityId(rs.getInt("facility_id"));
            f.setFacilityName(rs.getString("facility_name"));
            f.setTotalQuantity(rs.getInt("total_quantity"));
            f.setAvailable(rs.getInt("available"));
            f.setPrice(rs.getLong("price"));
            f.setDesciption(rs.getString("description"));
            f.setLastUpdate(rs.getTimestamp("last_update"));
            f.setFacilityKind(rs.getString("facility_kind"));

            list.add(f);
        }
        st.close();
        connection.close();
        return list;
    }

    /**
     * This method is used to get a facility by facilityId
     *
     * @param id This is the facilityId
     * @return the facility with its facilityId
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public FacilityModel getFacilityById(int id) throws SQLException, ClassNotFoundException {
        java.sql.Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT * from facility where facility_id = " + id;
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        FacilityModel f = new FacilityModel();
        while (rs.next()) {
            f.setFacilityId(rs.getInt("facility_id"));
            f.setFacilityName(rs.getString("facility_name"));
            f.setTotalQuantity(rs.getInt("total_quantity"));
            f.setAvailable(rs.getInt("available"));
            f.setPrice(rs.getLong("price"));
            f.setDesciption(rs.getString("description"));
            f.setLastUpdate(rs.getTimestamp("last_update"));
            f.setFacilityKind(rs.getString("facility_kind"));
        }
        st.close();
        connection.close();
        return f;
    }

    /**
     * This method is used to update a facility in the database
     *
     * @param newFacility
     * @return true if the facility is updated successfully, false otherwise
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public boolean updateFacility(FacilityModel newFacility) throws SQLException, ClassNotFoundException {
        java.sql.Connection connection = MysqlConnection.getMysqlConnection();
        String query = "UPDATE facility set facility_name = ?, total_quantity = ?, available = ?, price = ?, description = ?, last_update = NOW(), facility_kind = ? where facility_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        try {
            preparedStatement.setString(1, newFacility.getFacilityName());
            preparedStatement.setInt(2, newFacility.getTotalQuantity());
            preparedStatement.setInt(3, newFacility.getAvailable());
            preparedStatement.setLong(4, (long) newFacility.getPrice());
            preparedStatement.setString(5, newFacility.getDesciption());
            preparedStatement.setString(6, newFacility.getFacilityKind());
            preparedStatement.setInt(7, newFacility.getFacilityId());

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception ex) {
            return false;
        } finally {
            preparedStatement.close();
        }
    }

    public boolean updateFacilityAvailable(int facilityId, int change) throws SQLException, ClassNotFoundException {
        java.sql.Connection connection = MysqlConnection.getMysqlConnection();
        String query = "UPDATE facility set available = available + ? where facility_id = " + facilityId;
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        try {
            preparedStatement.setInt(1, change);
            preparedStatement.executeUpdate();
            return true;
        } catch (Exception ex) {
            return false;
        } finally {
            preparedStatement.close();
        }
    }

    /**
     * This method is used to get a list of all facilities by facilityName or
     * facilityKind
     *
     * @return the list of all facilities by facilityName or facilityKind
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public List<FacilityModel> searchFacility(String facilityName, String facilityKind)
            throws SQLException, ClassNotFoundException {
        Connection connection = (Connection) MysqlConnection.getMysqlConnection();
        String query = "SELECT * from facility where facility_name like '%" + facilityName
                + "%' and facility_kind like '%" + facilityKind + "%'";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        List<FacilityModel> list = new ArrayList<>();

        while (rs.next()) {
            FacilityModel facility = new FacilityModel();
            facility.setFacilityId(rs.getInt("facility_id"));
            facility.setFacilityName(rs.getString("facility_name"));
            facility.setTotalQuantity(rs.getInt("total_quantity"));
            facility.setAvailable(rs.getInt("available"));
            facility.setPrice(rs.getLong("price"));
            facility.setDesciption(rs.getString("description"));
            facility.setLastUpdate(rs.getTimestamp("last_update"));
            facility.setFacilityKind(rs.getString("facility_kind"));

            list.add(facility);
        }
        st.close();
        connection.close();
        return list;
    }

    /**
     * This method is used to delete a facility by facilityId in the database
     *
     * @param id This is facilityId
     * @return true if deleted successfully, false otherwise
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public boolean deleteFacility(int id) throws SQLException, ClassNotFoundException {
        java.sql.Connection connection = MysqlConnection.getMysqlConnection();
        String query = "delete from facility where facility_id = " + id;
        Statement st = connection.createStatement();
        try {
            if (st.executeUpdate(query) > 0) {
                return true;
            }
            ;
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}