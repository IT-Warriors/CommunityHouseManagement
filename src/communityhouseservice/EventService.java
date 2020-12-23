package communityhouseservice;

import services.MysqlConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import communityhousemodel.EventModel;

public class EventService {

	/**
	 * This method is used to add a new event into the event table in the database
	 *
	 * @param event The event needs to be added
	 * @return true if the event is added successfully, false otherwise
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public boolean addNewEvent(EventModel event) throws SQLException, ClassNotFoundException {
		java.sql.Connection connection = MysqlConnection.getMysqlConnection();
		String query = "INSERT INTO event(event_name, hall_id, content, from_date, to_date)"
				+ " values (?, ?, ?, ?, ?)";
		PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		try {
			preparedStatement.setString(1, event.getEventName());
			preparedStatement.setInt(2, event.getHallId());
			preparedStatement.setString(3, event.getContent());
			preparedStatement.setDate(4, (Date) event.getFromDate());
			preparedStatement.setDate(5, (Date) event.getToDate());

			preparedStatement.executeUpdate();
			preparedStatement.close();
			connection.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * This method is used to get the list of all events in the database
	 *
	 * @return the list of all events
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<EventModel> getAllEvents() throws SQLException, ClassNotFoundException {
		java.sql.Connection connection = MysqlConnection.getMysqlConnection();
		String query = "SELECT * from event ORDER BY from_date DESC";
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

	/**
	 * This method is used to get the event by eventId
	 *
	 * @param id This is the eventId
	 * @return the event having eventId = id
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public EventModel getEventById(int id) throws SQLException, ClassNotFoundException {
		java.sql.Connection connection = MysqlConnection.getMysqlConnection();
		String query = "SELECT * from event where event_id = " + id;
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

	public EventModel getEvent(int hallId, java.util.Date fromDate) throws SQLException, ClassNotFoundException {
		java.sql.Connection connection = MysqlConnection.getMysqlConnection();
		String query = "SELECT * from event where hall_id = " + hallId + " and from_date = '" + fromDate + "'";
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

	/**
	 * This method is used to get a list of events by eventName or from_date
	 *
	 * @return the list of events by eventName or from_date
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<EventModel> searchEvent(String eventName, String date) throws SQLException, ClassNotFoundException {
		java.sql.Connection connection = MysqlConnection.getMysqlConnection();
		String query = "SELECT * from event where event_name like '%" + eventName + "%' and from_date like '%" + date
				+ "%' ORDER BY from_date DESC";
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

	/**
	 * This method is used to update an event
	 *
	 * @param newEvent
	 * @return true if the event is updated successfully, false otherwise
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public boolean updateEvent(EventModel newEvent) throws SQLException, ClassNotFoundException {
		java.sql.Connection connection = MysqlConnection.getMysqlConnection();
		String query = "UPDATE event set event_name = ?, hall_id = ?, content = ?, from_date = ?, to_date = ? where event_id = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		try {
			preparedStatement.setString(1, newEvent.getEventName());
			preparedStatement.setInt(2, newEvent.getHallId());
			preparedStatement.setString(3, newEvent.getContent());
			preparedStatement.setDate(4, (Date) newEvent.getFromDate());
			preparedStatement.setDate(5, (Date) newEvent.getToDate());
			preparedStatement.setInt(6, newEvent.getEventId());

			preparedStatement.executeUpdate();
			return true;
		} catch (Exception ex) {
			return false;
		} finally {
			preparedStatement.close();
		}
	}

	/**
	 * This method is used to delete an event by its ID
	 *
	 * @param id This is the eventId
	 * @return true if the event is deleted successfully, false otherwise
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public boolean deleteEvent(int id) throws SQLException, ClassNotFoundException {
		java.sql.Connection connection = MysqlConnection.getMysqlConnection();
		String query = "delete from event where event_id = " + id;
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

	public List<EventModel> getLastedEvent() throws SQLException, ClassNotFoundException {
		java.sql.Connection connection = MysqlConnection.getMysqlConnection();
		String query = "SELECT * from event where from_date >= NOW() order by from_date limit 2";
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
}
