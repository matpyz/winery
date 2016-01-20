package winery.config;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.Semaphore;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ConfigWizardFrame extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pane[] = { new CompanyInfoPanel(this), new FieldPanel(this) };
	private int i = 0;
	private Semaphore ready;
	public static String path = "E:\\Dropbox\\Eclip\\winery\\.config";

	/**
	 * Odpal kreator konfiguracji w przypadku pierwszego uruchomienia.
	 * 
	 * @param semaphore
	 */
	public static void loadConfig(Semaphore semaphore) {
		if (!new File(path).exists()) {
			new ConfigWizardFrame(semaphore);
		} else {
			semaphore.release();
		}
	}

	/**
	 * Create and make the frame visible.
	 * 
	 * @param semaphore
	 */
	public ConfigWizardFrame(Semaphore semaphore) {
		ready = semaphore;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 400);
		setContentPane(pane[i]);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		switch (button.getText()) {
		case "Dalej":
			pane[i].setVisible(false);
			setContentPane(pane[++i]);
			pane[i].setVisible(true);
			repaint();
			break;
		case "Wstecz":
			pane[i].setVisible(false);
			setContentPane(pane[--i]);
			pane[i].setVisible(true);
			repaint();
			break;
		case "Koniec":
			String personal = ((CompanyInfoPanel) pane[0]).getPersonalData();
			String field = ((FieldPanel) pane[1]).getFieldData();
			if (personal == null || field == null)
				break;
			writeToFile(personal, field);
			dispose();
			ready.release();
			break;
		}
	}

	private void writeToFile(String first, String second) {
		PrintWriter configFile;
		System.out.println("Pisze dopliku");
		try {
			configFile = new PrintWriter(path, "UTF-8");
			configFile.println(first);
			configFile.flush();
			configFile.print(second);
			configFile.flush();
			configFile.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
