package dbapi;

import java.sql.Date;

public class Event {

	private int id;
	private String name;
	private String description;
	private Date startDate;
	private Date endDate;
	private String location;
	private int creatorId;
	private int eventTypeId;
	private String typeName;

	public Event(int id, String name, String description, Date startDate, Date endDate, String location, int creatorId,
			int eventTypeId, String typeName) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.location = location;
		this.creatorId = creatorId;
		this.eventTypeId = eventTypeId;
		this.typeName = typeName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getEventTypeId() {
		return eventTypeId;
	}

	public void setEventTypeId(int eventTypeId) {
		this.eventTypeId = eventTypeId;
	}

	public int getId() {
		return id;
	}

	public int getCreatorId() {
		return creatorId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
