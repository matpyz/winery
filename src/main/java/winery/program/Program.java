package winery.program;

import java.util.concurrent.Semaphore;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import winery.accounts.AccountsController;
import winery.calendar.CalendarView;
import winery.config.ConfigWizardFrame;
import winery.documents.EnterDocumentViewController;
import winery.documents.ExciseTaxViewController;
import winery.documents.PredictingLitersOfWineViewContoller;
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
		new Program(new AccountsController(), new CalendarView(), new SelectGenerateDocumentViewController(),
				new PredictingLitersOfWineViewContoller(), new EnterDocumentViewController(),
				new ExciseTaxViewController(), new RSSController(), new WineAddController(), new SeedAddViewController());
	}

	private JFrame frame_;
	private JTabbedPane tabbedPane_;

	/**
	 * Inicjalizuje program.
	 * 
	 * @param views
	 *            widoki do załadowania
	 */
	public Program(Controller... controllers) {
		/**
		 * Tworzy sygnał o wartości 1, który Guardian aktywuje (obniży do 0),
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

		frame_ = new JFrame("Winery");
		tabbedPane_ = new JTabbedPane();

		for (Controller controller : controllers) {
			View view = controller.getView();
			tabbedPane_.addTab(controller.getTitle(), view);
		}

		frame_.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame_.getContentPane().add(tabbedPane_);
		frame_.setSize(600, 600);
		frame_.setVisible(true);
	}
}
