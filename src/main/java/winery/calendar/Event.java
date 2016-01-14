import java.util.Date;

public class Event {

	String name;
	String description;
	Date startDate;
	Date endDate;
	String location;
	int eventTypeId;
	
	public Event(String name, String description, Date startDate, Date endDate, 
			String location, int eventTypeId) {
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.location = location;
		this.eventTypeId = eventTypeId;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public Date getEndDate() {
		return startDate;
	}
	
	public String getLocation() {
		return location;
	}
	
	public int getEventTypeId() {
		return eventTypeId;
	}
	
	
	
}
