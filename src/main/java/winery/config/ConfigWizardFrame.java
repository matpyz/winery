package winery.config;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Semaphore;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ConfigWizardFrame extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final CompanyInfoPanel companyInfoPanel = new CompanyInfoPanel(this);
	private final FieldPanel fieldPanel = new FieldPanel(this);
	private final JPanel pane[] = { new WelcomePanel(this), companyInfoPanel, fieldPanel, new ThankYouPanel(this) };
	private int i = 0;
	private final Semaphore ready;
	private static final String path = ".config";

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
		setTitle(pane[i].getName());
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		switch (button.getText()) {
		case "Dalej":
			pane[i++].setVisible(false);
			setContentPane(pane[i]);
			setTitle(pane[i].getName());
			pane[i].setVisible(true);
			break;
		case "Wstecz":
			pane[i--].setVisible(false);
			setContentPane(pane[i]);
			setTitle(pane[i].getName());
			pane[i].setVisible(true);
			break;
		case "Koniec":
			String company = companyInfoPanel.getCompanyData();
			String field = fieldPanel.getFieldData();
			if (company == null || field == null)
				break;
			writeToFile(company, field);
			dispose();
			ready.release();
			break;
		}
	}

	private void writeToFile(String first, String second) {
		PrintWriter configFile;
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

	public static Path getPath() {
		return Paths.get(path);
	}
}
