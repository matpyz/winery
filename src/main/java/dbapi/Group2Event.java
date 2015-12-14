package dbapi;

public class Group2Event {
	private int groupId;
	private int eventId;
	private int access;
	
	public Group2Event(int groupId, int eventId, int access) {
		super();
		this.groupId = groupId;
		this.eventId = eventId;
		this.access = access;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public int getAccess() {
		return access;
	}

	public void setAccess(int access) {
		this.access = access;
	}
}
