package winery.view;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

// Widoki
import winery.documents.SelectGenerateDocumentGui;
import winery.accounts.AccountsController;

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
		new Program(new SelectGenerateDocumentGui(), new AccountsController());
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
