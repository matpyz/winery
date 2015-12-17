package winery.view;

/**
 * Enum odpowiedzialny za katalogowanie akcji wykonywanych przez użytkownika.
 * Nowe akcje dodajesz poprzez dopisanie ich w tym pliku do już istniejących.
 * Konwencja nakazuje użycie caps locka, przykładowo NEW_ACTION("jpanel_id").
 * String w nawiasie musi pokrywać się z ID zakładki, w jakiej dana akcja będzie
 * wykonywana.
 * 
 * @author Kamil L
 *
 */
public enum Actions {

	ADD_ACCOUNT("accounts"), EDIT_ACCOUNT("accounts"), REMOVE_ACCOUNT("accounts");

	/**
	 * Oznacza grupę kont, które mają uprawnienia dla danej akcji.
	 */
	String permission;

	private Actions(String jpanelID) {
		permission = jpanelID;
	}

	public String getActionPermission() {
		return permission;
	}
}
