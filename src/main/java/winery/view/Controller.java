package winery.view;

/**
 * Kontroler, odpowiada za mediację między akcjami użytkownika, a modelem.
 * Powinien nasłuchiwać żądań użytkownika i na nie reagować. Może w tym celu
 * implementować interfejsy *Listener. Kontroler może potrzebować dostępu do
 * widoku, by pobrać dane, dlatego ważne jest wówczas udostępnienie odpowiednich
 * getterów przez widok. Dopuszcza się połączenie kontrolera i widoku w jedno.
 * 
 * @author Mateusz
 *
 */
public interface Controller {
	
	/**
	 * Udostępnia unikalny widok podporządkowany kontrolerowi.
	 * @return widok przypisany do kontrolera
	 */
	public abstract View getView();

	/**
	 * Udostępnia tytuł zakładki.
	 * @return tytuł zakładki
	 */
	public abstract String getTitle();
}
