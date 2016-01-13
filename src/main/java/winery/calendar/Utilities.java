package winery.calendar;

import java.sql.Date;
import java.util.HashMap;

import dbapi.DBManager;
import dbapi.Permission;
import dbapi.User;
import winery.guardian.Guardian;

public class Utilities {

	public static boolean addEvent(String name, String description, Date startDate, Date endDate, 
			String location, int eventTypeId) {
		User u = DBManager.getUserById(Guardian.getUserDbId());
		// Sprawdź pozwolenie na tworzenie eventu
		HashMap<Integer, Permission> perm = u.getPermissions();
		for(int x : perm.keySet()){
			if(perm.get(x).getName().equals("ADD_EVENT")) {
				// Spróbuj utworzyć event
				if(DBManager.addEvent(u.getId(), name, description, startDate, endDate, location, eventTypeId) == false) {
					return false;
				}
				return true;
			}
		}
		return false;
	}
	
	public static boolean editEvent(int eventId, String name, String description, Date startDate, Date endDate, 
			String location, int eventTypeId) {
		User u = DBManager.getUserById(Guardian.getUserDbId());
		// Sprawdź pozwolenie na tworzenie eventu
		HashMap<Integer, Permission> perm = u.getPermissions();
		for(int x : perm.keySet()){
			if(perm.get(x).getName().equals("EDIT_EVENT")) { //sprawdzane w DBManager?
				// Spróbuj zmienić event
				if(DBManager.updateEventDataById(u.getId(), eventId, name, description, startDate, endDate, 
						location, eventTypeId) == false) {
					return false;
				}
				return true;
			}
		}
		return false;
	}
	
	public static boolean removeEvent(int eventId) {
		User u = DBManager.getUserById(Guardian.getUserDbId());
		// Sprawdź pozwolenie na tworzenie eventu
		HashMap<Integer, Permission> perm = u.getPermissions();
		for(int x : perm.keySet()){
			if(perm.get(x).getName().equals("REMOVE_EVENT")) { //sprawdzane w DBManager?
				// Spróbuj usunąć event
				if(DBManager.removeEventById(u.getId(), eventId) == false) {
					return false;
				}
				return true;
			}
		}
		return false;
	}
	
}
