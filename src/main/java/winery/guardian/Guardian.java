package winery.guardian;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import winery.view.Actions;

public class Guardian {

	private static boolean initialized = false;
	private static String userId = null;

	private GuardianView view;
	private MessageDigest hash;
	private CountDownLatch loginSignal;

	// private SecureRandom random;

	public Guardian(CountDownLatch loginSignal) {
		initialized = true;
		this.loginSignal = loginSignal;
		view = new GuardianView(this, 200, 300);
		// random = SecureRandom.getInstance("SHA1PRNG","SUN");
		try {
			hash = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Zwraca listę z uprawnieniami użytkownika.
	 * 
	 * @return kolekcja typu ArrayList<String>
	 */
	public List<String> getPermissions() {
		List<String> permissions = new ArrayList<>();
		permissions.add("docs");
		permissions.add("accounts");
		return permissions;
	}

	/**
	 * Sprawdza czy użytkownik ma uprawnienia dla danej akcji.
	 * 
	 * @param action
	 *            enum typu Actions reprezentujący wykonaną akcję (jeżeli nie ma
	 *            jej w pliku {@link winery.view.Accounts}, to ją dodaj!)
	 * @return boolean
	 * 
	 */
	public static boolean checkPermission(Actions action) {
		if (!initialized)
			return false;
		else
			return true; /* Tu będzie sprawdzanie pozwoleń */
	}

	public boolean login(String login, String password) {
		if (initialized /* Tu będzie sprawdzanie loginu i hasła */) {
			hash.update(login.getBytes());
			userId = new String(hash.digest());
			loginSignal.countDown();
			return true;
		} else
			return false;
	}
}