package winery.calendar;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import dbapi.DBManager;
import dbapi.Event;
import dbapi.Permission;
import dbapi.User;
import winery.program.Guardian;

public class Utilities {
	
	private HashMap<Integer, Event> allEvents;
	private HashMap<Integer, Permission> permissions;
	private HashMap<String, Integer> eventTypes;
	private User user;
	
	public Utilities() {
		allEvents = DBManager.getEvents();
		user = DBManager.getUserById(Guardian.getUserId());
		setEventTypes(DBManager.getEventType());
		//permissions = user.getPermissions();
	}
	
	private void updateEvents() {
		allEvents = DBManager.getEvents();
	}

	public boolean addEvent(String name, String description, Date startDate, Date endDate, 
			String location, int eventTypeId) {
		if(DBManager.addEvent(user.getId(), name, description, startDate, endDate, location, 
			eventTypeId) == true) {
			updateEvents();
			return true;
		}
		return false;
	}
	
	public boolean editEvent(int eventId, String name, String description, Date startDate, 
			Date endDate, String location, int eventTypeId) {
		if(DBManager.updateEventDataById(user.getId(), eventId, name, description, startDate, endDate, 
			location, eventTypeId) == true) {
			updateEvents();
			return true;
		}
		return false;
	}
	
	public boolean removeEvent(int eventId) {
		if(DBManager.removeEventById(user.getId(), eventId) == true) {
			updateEvents();
			return true;
		}
		return false;
	}
	
	public ArrayList<Event> getAllDayEvents(Date startDate, Date endDate){
		ArrayList<Event> events = new ArrayList<Event>();
		for(int i : allEvents.keySet()) {
			Event e = allEvents.get(i);
			if(e.getStartDate().after(startDate) && e.getStartDate().before(endDate)) {
				events.add(e);
			}
		}
		return events;
	}

	public HashMap<String, Integer> getEventTypes() {
		return eventTypes;
	}

	public void setEventTypes(HashMap<String, Integer> eventTypes) {
		this.eventTypes = eventTypes;
	}
	
}