package communityhousebean;

import communityhousemodel.EventModel;
import communityhousemodel.HallModel;

public class EventBean {
	private HallModel hall;
	private EventModel event;
	
	public HallModel getHall() {
		return hall;
	}
	public void setHall(HallModel hall) {
		this.hall = hall;
	}
	public EventModel getEvent() {
		return event;
	}
	public void setEvent(EventModel event) {
		this.event = event;
	}
	

}
