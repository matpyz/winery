package winery.program;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

import dbapi.*;

public class Guardian {

	private static Guardian instance = null;
	private static User user = null;
	private static Semaphore signal;

	// private SecureRandom random;
	/*
	 * public static synchronized Guardian getInstance() { if(instance == null)
	 * return null;
	 * 
	 * return instance; }
	 */
	public static void initialize(Semaphore semaphore) {
		instance = new Guardian();
		signal = semaphore;
		GuardianView view = new GuardianView(instance, 200, 300);
	}

	private Guardian() {
		/* Celowo pusty prywatny konstruktor, HAIL SINGLETON */
	}

	/**
	 * Zwraca listę z uprawnieniami użytkownika.
	 * 
	 * @return kolekcja typu ArrayList<String>
	 */
	public static List<String> getPermissions() {
		List<Permission> permissionList = new LinkedList<Permission>(
				DBManager.getPermissionsForGroupId(user.getGroupId()).values());
		List<String> permissions = new ArrayList<>();
		for (Permission permission : permissionList)
			permissions.add(permission.getName());

		return permissions;
	}

	protected static boolean login(String login, String password) {
		user = DBManager.signIn(login, password);
		if (user != null) {
			signal.release();
			return true;
		} else
			return false;
	}

	public static int getUserId() {
		if (user == null)
			return -1;
		else
			return user.getId();
	}

	public static int getUserDbId() {
		return getUserId();
	}
}
