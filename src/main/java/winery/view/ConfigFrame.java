package winery.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

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
	private JPanel pane[] = { new ConfigCompanyInfoPanel(this), new ConfigFieldPanel(this) };
	private int i = 0;

	/**
	 * Odpal konfigurację w razie potrzeby.
	 */
	public static void loadConfig() {
		// FIXME wykonać porządne sprawdzenie
		//boolean firstTimeLaunch = new File(".config").exists();
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
		setBounds(100, 100, 550, 350);
		setContentPane(pane[i]);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		switch(button.getText()) {
		case "Dalej":
			//String personal = ((ConfigCompanyInfoPanel)pane[0]).getPersonalData();
			//
			//writeToFile(personal, "aaaa");
			setContentPane(pane[++i]);
			break;
		case "Wstecz":
			setContentPane(pane[--i]);
			break;
		case "Koniec":
			// FIXME Przepisać dane do pliku
			String personal = ((ConfigCompanyInfoPanel)pane[0]).getPersonalData();
			//
			writeToFile(personal, "aaaa");
			this.dispose();
			break;
		}
	}
	
	private void writeToFile(String first, String second) {
		PrintWriter configFile;
		System.out.println("Pisze dopliku");
		try {
			configFile = new PrintWriter(".config", "UTF-8");
			configFile.println(first);
			configFile.flush();
			configFile.println(second);
			configFile.flush();
			configFile.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
