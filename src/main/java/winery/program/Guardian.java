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

	public static void initialize(Semaphore semaphore) {
		signal = semaphore;
		new GuardianView(200, 300);
	}

	/**
	 * Celowo pusty prywatny konstruktor - SINGLETON.
	 */
	private Guardian() {
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

	static boolean login(String login, String password) {
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
}
