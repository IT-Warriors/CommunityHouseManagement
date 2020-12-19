package communityhouseservice;

import communityhousemodel.UserAccountModel;
import services.MysqlConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserAccountService {
    public UserAccountModel getUserById(int user_id) throws SQLException, ClassNotFoundException {
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT * from users_account where user_id = " + user_id;
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        UserAccountModel u = new UserAccountModel();
        while(rs.next()){
            u.setUserId(rs.getInt("user_id"));
            u.setUsername(rs.getString("username"));
            u.setPassword(rs.getString("password"));
            u.setPersionId(rs.getInt("person_id"));
            u.setPhoneNumber(rs.getString("phone_number"));
            u.setType(rs.getInt("type"));
        }
        st.close();
        connection.close();
        return u;
    }
}
