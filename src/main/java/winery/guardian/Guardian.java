package winery.guardian;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import winery.view.Actions;

import dbapi.*;

public class Guardian {

	private static Guardian instance = null;
	private static String userId = null;
	private static int userDbId = -1;

	private static GuardianView view;
	private static MessageDigest hash;
	private static CountDownLatch loginSignal;

	// private SecureRandom random;
/*
	public static synchronized Guardian getInstance() {
		if(instance == null)
			return null;
		
		return instance;
	}
*/
	public static void initialize(CountDownLatch signal) {
		instance = new Guardian();
		loginSignal = signal;
		view = new GuardianView(instance, 200, 300);
		// random = SecureRandom.getInstance("SHA1PRNG","SUN");
		try {
			hash = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	private Guardian() {
		/* Celowo pusty prywatny konstruktor */
	}

	/**
	 * Zwraca listę z uprawnieniami użytkownika.
	 * 
	 * @return kolekcja typu ArrayList<String>
	 */
	public static List<String> getPermissions() {
		List<String> permissions = new ArrayList<>();
		permissions.add("docs");
		permissions.add("accounts");
		return permissions;
	}

	protected static boolean login(String login, String password) {
		User user = DBManager.signIn(login, password);
		if(user != null) {
			hash.update(login.getBytes());
			userId = new String(hash.digest());
			loginSignal.countDown();
			userDbId = user.getId();
			return true;
		}
		else
			return false;
	}
	
	public static int getUserDbId() {
		return userDbId;
	}
}
