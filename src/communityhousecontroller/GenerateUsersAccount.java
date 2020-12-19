package communityhousecontroller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenerateUsersAccount {
    public static void main(String[] args) throws SQLException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            String dbURL = "jdbc:mysql://localhost/quan_ly_nhan_khau";
            String username = "root";
            String password = "";

            conn = DriverManager.getConnection(dbURL, username, password);
            if (conn != null) {
                System.out.println("Kết nối thành công");
            }
            st = conn.createStatement();
            String sql = "select ID, hoTen, year(namSinh) from nhan_khau";
            rs = st.executeQuery(sql);

            List<String> a = new ArrayList<>();
            List<String> b = new ArrayList<>();

            while(rs.next()){
                String id = rs.getString("ID");
                String name = rs.getString("hoTen");
                a.add(id);
                b.add(name);
            }
            for(int i = 0; i < a.size(); i++){
                String id = a.get(i);
                String name = b.get(i);
                String s = "insert into Users_account (person_id, username, password, type) values (" + id +
                        ", '" + name + "', '1234',0)";
                st.executeUpdate(s);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            conn.close();
        }
    }
}
