package winery.model;

import java.util.Observable;

import winery.view.View;

/**
 * Model to każdy obiekt reprezentujący dane programu, np. kalendarz, wpis
 * kalendarza (model może mieć podmodele), choć nie każdy element modelu musi
 * być także modelem, należy to rozsądnie rozeznać.
 *
 * Model powinien udostępniać settery i gettery do użycia przez, odpowiednio,
 * kontrolery i widoki.
 * 
 * @author Mateusz
 * 
 */
public abstract class Model extends Observable {
	/**
	 * Każdy setter powinien na koniec wywołać metodę changed().
	 */
	protected void change() {
		setChanged();
		notifyObservers();
	}

	/**
	 * Wpisuje widok na listę obserwujących.
	 * 
	 * @param view
	 *            widok do zapisania na listę
	 */
	public void register(View view) {
		addObserver(view);
	}

	/**
	 * Wypisuje widok z listy obserwujących.
	 * 
	 * @param view
	 *            widok do usunięcia z listy
	 */
	public void unregister(View view) {
		deleteObserver(view);
	}
}
