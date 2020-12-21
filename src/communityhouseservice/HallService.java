package communityhouseservice;

import communityhousemodel.FacilityModel;
import communityhousemodel.HallModel;
import services.MysqlConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HallService {
    public List<HallModel> getAllHall(){
        Connection connection = null;
        Statement st = null;
        List<HallModel> list = new ArrayList<>();
        try {
            connection = MysqlConnection.getMysqlConnection();
            String query = "SELECT * FROM hall";
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                HallModel hall = new HallModel();
                hall.setHallId(rs.getInt("hall_id"));
                hall.setHallName(rs.getString("hall_name"));
                hall.setPrice(rs.getLong("price"));
                hall.setDesciption(rs.getString("description"));
                list.add(hall);
            }
            st.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }
}
