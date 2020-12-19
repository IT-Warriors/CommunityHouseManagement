package communityhouseservice;

import services.MysqlConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import communityhousemodel.EventModel;

public class EventService {
	public boolean addNewEvent(EventModel event) throws SQLException, ClassNotFoundException {
		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "INSERT INTO event(event_name, hall_id, content, from_date, to_date)"
				+ " values (?, ?, ?, ?, ?)";
		PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, event.getEventName());
		preparedStatement.setInt(2, event.getHallId());
		preparedStatement.setString(3, event.getContent());
		preparedStatement.setDate(4, (Date) event.getFromDate());
		preparedStatement.setDate(5, (Date) event.getToDate());

		preparedStatement.executeUpdate();
		preparedStatement.close();
		connection.close();
		return true;
	}

	public List<EventModel> getListEvent() throws SQLException, ClassNotFoundException {
		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "SELECT * from event";
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(query);
		List<EventModel> list = new ArrayList<>();
		while (rs.next()) {
			EventModel event = new EventModel();
			event.setEventId(rs.getInt("event_id"));
			event.setEventName(rs.getString("event_name"));
			event.setHallId(rs.getInt("hall_id"));
			event.setContent(rs.getString("content"));
			event.setFromDate(rs.getDate("from_date"));
			event.setToDate(rs.getDate("to_date"));
			list.add(event);
		}
		st.close();
		connection.close();
		return list;
	}

	public EventModel getEventById(int id) throws SQLException, ClassNotFoundException {
		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "SELECT * from facility where facility_id = " + id;
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(query);
		EventModel event = new EventModel();
		while (rs.next()) {
			event.setEventId(rs.getInt("event_id"));
			event.setEventName(rs.getString("event_name"));
			event.setHallId(rs.getInt("hall_id"));
			event.setContent(rs.getString("content"));
			event.setFromDate(rs.getDate("from_date"));
			event.setToDate(rs.getDate("to_date"));
		}
		st.close();
		connection.close();
		return event;
	}

	public EventModel getEvent(String eventName, String date) throws SQLException, ClassNotFoundException {
		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "SELECT * from event where event_name like '%" + eventName + "%' and from_date like '%" + date
				+ "%'";
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(query);
		EventModel event = new EventModel();
		while (rs.next()) {
			event.setEventId(rs.getInt("event_id"));
			event.setEventName(rs.getString("event_name"));
			event.setHallId(rs.getInt("hall_id"));
			event.setContent(rs.getString("content"));
			event.setFromDate(rs.getDate("from_date"));
			event.setToDate(rs.getDate("to_date"));
		}
		st.close();
		connection.close();
		return event;
	}

	public boolean updateEvent(EventModel newEvent) throws SQLException, ClassNotFoundException {
		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "UPDATE event set event_name = ?, hall_id = ?, content = ?, from_date = ?, to_date = ? where event_id = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		try {
			preparedStatement.setString(1, newEvent.getEventName());
			preparedStatement.setInt(2, newEvent.getHallId());
			preparedStatement.setString(3, newEvent.getContent());
			preparedStatement.setDate(4, (Date) newEvent.getFromDate());
			preparedStatement.setDate(5, (Date) newEvent.getToDate());

			preparedStatement.executeUpdate();
			return true;
		} catch (Exception ex) {
			return false;
		} finally {
			preparedStatement.close();
		}
	}

	public boolean deleteEvent(int id) throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "DELETE FROM event WHERE event_id = ?";
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(query);
		EventModel event = new EventModel();
		while (rs.next()) {
			event.setEventId(rs.getInt("event_id"));
			event.setEventName(rs.getString("event_name"));
			event.setHallId(rs.getInt("hall_id"));
			event.setContent(rs.getString("content"));
			event.setFromDate(rs.getDate("from_date"));
			event.setToDate(rs.getDate("to_date"));
		}
		st.close();
		connection.close();
		return true;
	}

	public static void main(String[] args) {
		EventService eventService = new EventService();
		try {
			EventModel event = eventService.getEvent("Đám cưới", "2020-06-12");
			System.out.println(event.getEventId());
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
