package winery.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import winery.model.Model;

/**
 * Widok
 * 
 * @author Mateusz
 * 
 */
public abstract class View extends JPanel implements Observer {

	/**
	 * Numer wersji, nieistotna wartość domyślna.
	 */
	protected static final long serialVersionUID = 1L;

	/**
	 * Procedura wywoływana tylko pośrednio przez {@link winery.model.Model}
	 * .change(), implementowana inaczej przez każdy widok, odświeża prezentację
	 * danych po zmianie.
	 * 
	 * @param model
	 *            model, który uległ zmianie
	 */
	protected abstract void update(Model model);

	@Override
	public void update(Observable o, Object arg) {
		update((Model) o);
	}

}
