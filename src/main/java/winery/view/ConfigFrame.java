package winery.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ConfigFrame extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// FIXME podmienić ten drugi na prawidłowy z parametrem 'this'
	private JPanel pane[] = { new ConfigCompanyInfoPanel(this), new JPanel() };
	private int i = 0;

	/**
	 * Odpal konfigurację w razie potrzeby.
	 */
	public static void loadConfig() {
		// FIXME wykonać porządne sprawdzenie
		boolean firstTimeLaunch = true;
		if(firstTimeLaunch) {
			ConfigFrame frame = new ConfigFrame();
			frame.setVisible(true);
		}
	}

	/**
	 * Create the frame.
	 */
	public ConfigFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setContentPane(pane[i]);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		switch(button.getText()) {
		case "Dalej":
			setContentPane(pane[++i]);
			break;
		case "Wstecz":
			setContentPane(pane[--i]);
			break;
		case "Koniec":
			// FIXME Przepisać dane do pliku
			this.dispose();
			break;
		}
	}

}
