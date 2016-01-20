package winery.program;

import java.util.List;
import java.util.concurrent.Semaphore;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import winery.accounts.AccountsController;
import winery.calendar.CalendarViewController;
import winery.config.ConfigWizardFrame;
import winery.documents.EnterDocumentViewController;
import winery.documents.ExciseTaxViewController;
import winery.documents.PredictingLitersOfWineViewController;
import winery.documents.SeedAddViewController;
import winery.documents.SelectGenerateDocumentViewController;
import winery.documents.WineAddController;
import winery.rss.RSSController;
import winery.view.Controller;
import winery.view.View;

/**
 * Zawiera punkt startowy programu. Jako główne okno, prezentuje panele-widoki
 * {@link winery.view.View} w zakładkach.
 * 
 * @author Mateusz
 *
 */
public class Program {

	/**
	 * Numer wersji, nieistotna wartość domyślna.
	 */
	protected static final long serialVersionUID = 1L;

	/**
	 * Uruchamia program.
	 * 
	 * @param args
	 *            argumenty wywołania, ignorowane
	 */
	public static void main(String[] args) {
		new Program();
	}

	private JFrame frame;
	private JTabbedPane tabbedPane;
	
	private static Controller createController(String controllerID) {
		switch (controllerID) {
		case "accounts":
			return new AccountsController();
		case "calendar":
			return new CalendarViewController();
		case "enterdocument":
			return new EnterDocumentViewController();
		case "excisetax":
			return new ExciseTaxViewController();
		case "predictinglitersofwine":
			return new PredictingLitersOfWineViewController();
		case "rss":
			return new RSSController();
		case "seedadd":
			return new SeedAddViewController();
		case "selectgeneratedocument":
			return new SelectGenerateDocumentViewController();
		case "wineadd":
			return new WineAddController();
		}
		return null;
	}

	/**
	 * Inicjalizuje program.
	 * 
	 * @param views
	 *            widoki do załadowania
	 */
	public Program() {
		/**
		 * Tworzy sygnał o wartości 0, który Guardian aktywuje (podniesie do 1),
		 * gdy logowanie przebiegnie pomyślnie. Wtedy program ruszy dalej i
		 * wyświetli właściwe okno programu.
		 */
		try {
			Semaphore loginSignal = new Semaphore(0);
			ConfigWizardFrame.loadConfig(loginSignal);
			loginSignal.acquire();
			Guardian.initialize(loginSignal);
			loginSignal.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		frame = new JFrame("Winery");
		tabbedPane = new JTabbedPane();

		List<String> permissions = Guardian.getPermissions();
		for (int i = 0; i < permissions.size(); i++) {
			Controller controller = createController(permissions.get(i));
			View view = controller.getView();
			tabbedPane.addTab(controller.getTitle(), view);
		}

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(tabbedPane);
		frame.setSize(600, 600);
		frame.setVisible(true);
	}
}
