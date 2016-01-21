package winery.program;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

import dbapi.*;

public class Guardian {
	private static User user = null;
	private static Semaphore signal;

	/**
	 * @param semaphore
	 *            Semafor który udzieli pozwolenia głównemu wątkowi przy udanym
	 *            zalogowaniu.
	 */
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
		List<Permission> permissionList = new LinkedList<Permission>(DBManager
				.getPermissionsForGroupId(user.getGroupId()).values());
		List<String> permissions = new ArrayList<>();
		for (Permission permission : permissionList)
			permissions.add(permission.getName());

		return permissions;
	}

	/**
	 * Loguje użytkownika.
	 * @param login
	 * @param password
	 * @return true w wypadku udanego logowanie, false w przeciwnym razie
	 */
	protected static boolean login(String login, String password) {
		user = DBManager.signIn(login, password);
		if (user != null) {
			signal.release();
			return true;
		} else
			return false;
	}

	/**
	 * @return ID użytkownika po zalogowaniu, -1 przed
	 */
	public static int getUserId() {
		if (user == null)
			return -1;
		else
			return user.getId();
	}
}
