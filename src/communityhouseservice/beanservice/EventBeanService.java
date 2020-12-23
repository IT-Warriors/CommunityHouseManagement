package communityhouseservice.beanservice;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    public ObservableList<EventBean> searchEvent(String eventId, String eventName, String date) {
        List<EventBean> listEventBean = new ArrayList<>();
        try{
            Connection connection = (Connection) MysqlConnection.getMysqlConnection();
            String query = "SELECT * from event, hall where event.hall_id = hall.hall_id and event_id like '%" + eventId
                    + "%' AND event_name like '%" + eventName + "%' AND from_date like '%" + date + "%'";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                EventBean eventBean = new EventBean();
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
                listEventBean.add(eventBean);
            }
            st.close();
            connection.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(listEventBean);
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
            String query2 = "delete from event where event_id = " + eventId;
            Statement st = connection.createStatement();
            st.executeUpdate(query2);
        } catch (SQLException throwables) {
            return false;
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }
    
}
