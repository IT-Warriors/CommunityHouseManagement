package communityhousemodel;

import java.util.Date;

public class EventModel {
    private int eventId;
    private String eventName;
    private int hallId;
    private String content;
    private Date fromDate;
    private Date toDate;

    public EventModel() {
    }

    public EventModel(String eventName, int hallId, String content, Date fromDate, Date toDate) {
        this.eventName = eventName;
        this.hallId = hallId;
        this.content = content;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public EventModel(int eventId, String eventName, int hallId, String content, Date fromDate, Date toDate) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.hallId = hallId;
        this.content = content;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getHallId() {
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
}
