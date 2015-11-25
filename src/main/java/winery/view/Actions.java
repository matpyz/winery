package winery.view;

/**
 * Enum odpowiedzialny za katalogowanie akcji wykonywanych przez użytkownika.
 * 
 * @author Kamil L
 *
 */
public enum Actions {

	ADD_ACCOUNT("HR"), EDIT_ACCOUNT("HR"), REMOVE_ACCOUNT("HR");

	/**
	 * Oznacza grupę kont, które mają uprawnienia dla danej akcji.
	 */
	String associatedGroup;

	private Actions(String group) {
		associatedGroup = group;
	}

	public String getGroup() {
		return associatedGroup;
	}
}
