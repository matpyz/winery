package winery.view;

import java.util.concurrent.CountDownLatch;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import winery.documents.PredictingLitersOfWineViewContoller;
// Widoki
import winery.documents.SelectGenerateDocumentViewController;
import winery.guardian.Guardian;
import winery.accounts.AccountsController;
import winery.calendar.CalendarView;

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
		new Program(new SelectGenerateDocumentViewController(), new AccountsController(), new CalendarView(), new PredictingLitersOfWineViewContoller());
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
		CountDownLatch loginSignal = new CountDownLatch(1);
		new Guardian(loginSignal);
		try {
			loginSignal.await();
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
