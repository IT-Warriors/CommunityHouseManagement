
package communityhousecontroller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import communityhousemodel.UserAccountModel;
import communityhouseservice.UserAccountService;
import models.UserMoldel;
import services.MysqlConnection;

public class LoginController {

    public static UserAccountModel currentUser;

    public boolean login(String userName, String passwod) throws SQLException, ClassNotFoundException{
        Connection connection = MysqlConnection.getMysqlConnection();
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM users_account WHERE username = '" + userName +"'");
        if (rs == null) {
            return false;
        }
        while (rs.next()) {
            if (rs.getString("password") == null ? passwod == null : rs.getString("password").equals(passwod)) {
                currentUser = new UserAccountService().getUserById(rs.getInt("user_id"));
                return true;
            }
        }
        connection.close();
        return false;
    }
}
