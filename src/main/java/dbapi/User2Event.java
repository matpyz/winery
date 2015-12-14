package dbapi;

public class User2Event {

	private int userId;
	private int eventId;
	private int access;
	
	public User2Event(int userId, int eventId, int access) {
		super();
		this.userId = userId;
		this.eventId = eventId;
		this.access = access;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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
