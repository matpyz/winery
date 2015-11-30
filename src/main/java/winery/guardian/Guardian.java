package winery.guardian;

import java.util.List;
import java.util.Observable;

import winery.view.Actions;
import winery.view.Controller;
import winery.view.View;

public class Guardian extends Observable {

	private GuardianView view;
	
	private static boolean initialized = false;
	private static String userId;
	
	public Guardian() {
		initialized = true;
		view = new GuardianView(this, 200, 300);
	}
	
	/**
	 * Zwraca listę z uprawnieniami użytkownika.
	 * 
	 * @return
	 */
	public List<String> getPermissions() {
		return null;
	}
	
	/**
	 * Sprawdza czy użytkownik ma uprawnienia dla danej akcji.
	 *  
	 * @param action
	 *            enum typu Actions
	 * @return boolean
	 */
	public static boolean checkPermission(Actions action) {
		if(!initialized)
			return false;
		else
			accessDB(null);
			return false;
	}

	private static boolean accessDB(String query) {
		return false;
	}

	public boolean login(String login, String password) {
		if ( accessDB(login + password) ) {
			userId = login;
			notify();
			return true;
		}
		else
			return false;
	}


}
