package communityhouseservice.beanservice;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.mysql.jdbc.Connection;
import communityhousebean.HireBean;
import communityhousemodel.FacilityModel;
import services.MysqlConnection;

public class HireBeanService {
	/**
	 * This method is used to get a list of facilities which are hired
	 * 
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<HireBean> getFacilityHired() throws SQLException, ClassNotFoundException {
		Connection connection = (Connection) MysqlConnection.getMysqlConnection();
		String query = "SELECT hire.facility_id, facility_name, total_quantity, available, price, description, last_update,"
				+ "facility_kind, sum(quantity) from hire, facility where "
				+ "hire.facility_id = facility.facility_id group by "
				+ "hire.facility_id, facility_name, total_quantity, available, price, description, last_update, facility_kind ";
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(query);
		List<HireBean> list = new ArrayList<>();

		while (rs.next()) {
			FacilityModel facility = new FacilityModel();
			facility.setFacilityId(rs.getInt("hire.facility_id"));
			facility.setFacilityName(rs.getString("facility_name"));
			facility.setTotalQuantity(rs.getInt("total_quantity"));
			facility.setAvailable(rs.getInt("available"));
			facility.setPrice(rs.getLong("price"));
			facility.setDesciption(rs.getString("description"));
			facility.setLastUpdate(rs.getTimestamp("last_update"));
			facility.setFacilityKind(rs.getString("facility_kind"));

			HireBean hireBean = new HireBean();
			hireBean.setHiredQuantity(rs.getInt("sum(quantity)"));
			hireBean.setFacility(facility);

			list.add(hireBean);
		}
		st.close();
		connection.close();
		return list;
	}
}
