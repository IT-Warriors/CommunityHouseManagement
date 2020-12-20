package communityhouseservice.beanservice;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import java.sql.*;
import services.MysqlConnection;
import communityhousebean.EventBean;
import communityhousemodel.EventModel;
import communityhousemodel.HallModel;

public class EventBeanService {
	 

    public List<EventBean> getAllEventBean() throws SQLException, ClassNotFoundException {
        Connection connection = (Connection) MysqlConnection.getMysqlConnection();
        String query = "SELECT * from event, hall where event.hall_id = hall.hall_id";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        List<EventBean> list = new ArrayList<>();
        
        while(rs.next()){
            EventModel event = new EventModel();
            event.setEventId(rs.getInt("event_id"));
            event.setEventName(rs.getString("event_name"));
            event.setHallId(rs.getInt("hall_id"));
            event.setContent(rs.getString("content"));
            event.setFromDate(rs.getDate("from_date"));
            event.setToDate(rs.getDate("to_date"));
            
            HallModel hall = new HallModel();
            hall.setHallName(rs.getString("hall_name"));
                       
            EventBean eventBean = new EventBean();
            eventBean.setEvent(event);
            eventBean.setHall(hall);
            
            list.add(eventBean);
        }
        st.close();
        connection.close();
        return list;
    }

    public EventBean getEventBeanById(int eventId) throws SQLException, ClassNotFoundException {
        Connection connection = (Connection) MysqlConnection.getMysqlConnection();
        String query = "SELECT * from event, hall where event.hall_id = hall.hall_id and event_id = " + eventId;
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        EventBean eventBean = new EventBean();

        while(rs.next()){
            EventModel event = new EventModel();
            event.setEventId(rs.getInt("event_id"));
            event.setEventName(rs.getString("event_name"));
            event.setHallId(rs.getInt("hall_id"));
            event.setContent(rs.getString("content"));
            event.setFromDate(rs.getDate("from_date"));
            event.setToDate(rs.getDate("to_date"));

            HallModel hall = new HallModel();
            hall.setHallName(rs.getString("hall_name"));

            eventBean.setEvent(event);
            eventBean.setHall(hall);
        }
        st.close();
        connection.close();
        return eventBean;
    }

    public boolean deleteEvent(int eventId){
        try {
            Connection connection = (Connection) MysqlConnection.getMysqlConnection();
            EventBean eventBean = getEventBeanById(eventId);
            String query1 = "delete from event where event_id = " + eventId;
            String query2 = "delete from hall where hall_id = " + eventBean.getHall().getHallId();
            Statement st = connection.createStatement();
            st.executeUpdate(query1);
            st.executeUpdate(query2);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            return true;
        }
    }
    
}
