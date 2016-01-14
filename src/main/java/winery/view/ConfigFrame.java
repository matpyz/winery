package winery.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ConfigFrame extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// FIXME podmienić ten drugi na prawidłowy z parametrem 'this'
	private JPanel pane[] = { new ConfigCompanyInfoPanel(this), new ConfigFieldPanel(this) };
	private int i = 0;
	private boolean ready = false;
	private static String path = "E:\\Dropbox\\Eclip\\winery\\.config";

	/**
	 * Odpal konfigurację w razie potrzeby.
	 */
	public static void loadConfig() {
		if(!new File(path).exists()) {
			ConfigFrame frame = new ConfigFrame();
			frame.setVisible(true);
			while(!frame.isReady()) {	
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
			}
		}
	}

	private boolean isReady() {
		return ready;
	}

	/**
	 * Create the frame.
	 */
	public ConfigFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 400);
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
			pane[i].setVisible(false);
			setContentPane(pane[++i]);
			pane[i].setVisible(true);
			//getContentPane().remove(pane[i]);
			//getContentPane().add(pane[++i]);
			repaint();
			break;
		case "Wstecz":
			pane[i].setVisible(false);
			setContentPane(pane[--i]);
			pane[i].setVisible(true);
			repaint();
			break;
		case "Koniec":
			// FIXME Przepisać dane do pliku
			String personal = ((ConfigCompanyInfoPanel)pane[0]).getPersonalData();
			String field = ((ConfigFieldPanel)pane[1]).getFieldData();
			if(personal == null || field == null) {
				break;
			}
			
			writeToFile(personal, field);
			this.dispose();
			ready = true;
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
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
