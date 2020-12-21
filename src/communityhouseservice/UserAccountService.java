package communityhouseservice;

import communityhousemodel.UserAccountModel;
import services.MysqlConnection;

import java.sql.*;

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

    public boolean updateUser(UserAccountModel user) throws SQLException, ClassNotFoundException {
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "UPDATE users_account set phone_number = ? where user_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        try{
            preparedStatement.setString(1, user.getPhoneNumber());
            preparedStatement.setInt(2, user.getUserId());
            preparedStatement.executeUpdate();
            return true;
        } catch (Exception ex){
            return false;
        } finally {
            preparedStatement.close();
        }
    }
}
