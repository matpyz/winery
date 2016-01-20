package winery.documents;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import winery.model.Model;
import winery.view.ConfigFrame;
import winery.view.Controller;
import winery.view.View;

/**
 * Klasa Gui do wyboru generowanego dokumentu oraż scieżki gdzie zapisać
 * utworzony.
 * 
 * @author Przemysław Iskra
 * @version 2.0
 */
public class SelectGenerateDocumentViewController extends View implements Controller {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String OS = System.getProperty("os.name").toLowerCase();
	JPanel Pmaks, Pmin;
	JFileChooser Ffilechose;
	JLabel Linfo1, Linfo2, Lfile;
	JComboBox<String> CBdocuments;
	JTextArea Tdata;
	// String title, path="";
	// title zmienna mówiąca jaki jest wybrany dokument.
	// path zmienna mówiąca jaką wybrana jest scieżka, na razie pusta.
	JButton Bready, Bdirectory;
	int a = 600;// długość
	int b = 600; // szerokość
	SelectGenerateDocumentModel model;

	// docTable tablica zawierająca wszystkie dokumenty jakie można wybrać.
	// String[] docTable = new String[] {"<html>Deklaracja o ilości win białych
	// lub czerwonych <br> wprowadzonych do obrotu w poprzednimroku gospodarczym
	// <br>",
	// "<html>Deklaracja o szacowanej ilości win, winogron lub moszczu<br>
	// winogronowego, którebędą dostarczone do przedsiębiorcy, oraz o
	// szacowanej<br> ilości ich wykorzystaniaw danym <br> roku gospodarczym"};
	public SelectGenerateDocumentViewController() {
		setLayout(new GridLayout(1, 1));
		model = new SelectGenerateDocumentModel();
		setSize(a, b);
		Pmaks = new JPanel(); // Panel, do którego wrzucamy wszystko. Ultra
								// Panel taki.
		Pmaks.setSize(a, b);
		Pmaks.setLayout(new FlowLayout(FlowLayout.CENTER, 600, 10));
		add(Pmaks);
		Linfo1 = new JLabel("Wybierz Dokument:"); // Zwykłe Info
		Linfo1.setHorizontalAlignment(SwingConstants.CENTER);
		// Linfo1.setOpaque(true);
		// Linfo1.setBackground(Color.GRAY);
		Pmaks.add(Linfo1);

		CBdocuments = new JComboBox<>(model.getTable()); // Combo Box służy do
															// wyboru jednej
															// rzeczy z listy
															// rozwijanej, tutaj
															// wybieramy
															// dokument.
		CBdocuments.setSelectedIndex(0); // ustawienie co najpierw wybieramy.
		// title=docTable[0];
		model.setVtitle(model.getdocTable(0)); // ustawienie w modelu jaki
												// dokument jest wstępnie
												// wybrany

		CBdocuments.addActionListener(new DocSelectListener());
		Pmaks.add(CBdocuments);

		Linfo2 = new JLabel(
				"<html> Dane Firmy.");
		Pmaks.add(Linfo2); // zwykłe info o tym co trzeba wpisać do textArea.
		Tdata = new JTextArea(3, 30);
		Tdata.setLineWrap(true);
		Tdata.setWrapStyleWord(true);
		try {
			Tdata.setText(readFromFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Pmaks.add(Tdata);
		Bready = new JButton(" Generuj Dokument."); // Przycisk, którego
													// kliknięcie powoduje
													// generowanie dokumentu.
		Bready.addActionListener(new GenerateDocListener());

		Pmin = new JPanel();
		Bdirectory = new JButton("Wybierz Katalog !"); // Przycisk
																		// którego
																		// klikniecie
																		// powoduje
																		// wybranie
																		// katalogu.
		Lfile = new JLabel(" Brak wybranego Karalogu. "); // Jaki katalog
														// wybrano
															// informacja.
		Bdirectory.addActionListener(new PathSelectListener());

		Pmin.add(Bdirectory);
		Pmin.add(Lfile);

		Pmaks.add(Pmin);

		/*
		      
		*/
		Pmaks.add(Bready);

	}

	/**
	 * Klasa odpowiedzialna za reakcje przycisku Bready, czyli generowanie
	 * dokumentu
	 */
	public class GenerateDocListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {

			String data = Tdata.getText().trim();
			if (!data.equals("") && !model.getVpath().trim().equals("")) // Sprawdzanie,
																			// czy
																			// aby
																			// potrzebna
																			// informacja
																			// ni
																			// jest
																			// pustapusta.
			{
				model.createTableData(model.getVpath(), data); // Tworzenie
																// tablicy
																// danych do
																// dokumentu (w
																// wyskakującym
																// okienko,
																// zakończenie
																// wybierania
																// powoduje
																// generowanie
																// dokumentu.
				// Przenieść Generowanie tablicy do modelu.
				// JOptionPane.showMessageDialog(null, "Dokument przygotowany.
				// ");

			} else
				JOptionPane.showMessageDialog(null, "Nie wypełniono wszystkich danych.  ");
		}
	}

	/**
	 * Klasa odpowiedzialna za reakcje przycisku Bdirectory , czyli wybieranie
	 * katalogu
	 */
	public class PathSelectListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			Ffilechose = new JFileChooser();
			Ffilechose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Wybranie
																			// katalogu
																			// do
																			// zapisu
																			// pliku.
			Ffilechose.showSaveDialog(null);
			try {
				model.setVpath(Ffilechose.getSelectedFile().toString());
				String path = model.getVpath();

				File f = new File(model.getVpath());
				if (f.isDirectory()) // Sprawdzenie czy jest to katalog
				{
					
				
				     if (OS.indexOf("win") >= 0) {
				    		model.setVpath(path + "\\document.pdf"); 
				      
				      } else if (OS.indexOf("nux") >= 0) { 	model.setVpath(path + "/document.pdf"); 
				     
				      } else { model.setVpath(path + "\\document.pdf"); 
				       
				      } 
					
					// dodanie nazwy
																// pdfa
																// tworzonego
				} else {
					String endpdf = path.substring(path.length() - 4, path.length());

					if (!endpdf.equals(".pdf"))
						model.setVpath(path + ".pdf"); // Sprawdzenie czy cztery
														// ostatnie znaki nie
														// zawierają koncówki
														// .pdf i dodanie jej
														// wtedy.

				}
				Lfile.setText(model.getVpath());
			} catch (NullPointerException e) {

			}

		}
	}

	/**
	 * Klasa odpowiedzialna za reakcje CBdocuments , czyli wybieranie dokumentu
	 */
	public class DocSelectListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			@SuppressWarnings("unchecked")
			JComboBox<String> combo = (JComboBox<String>) event.getSource();
			String selectedDoc = (String) combo.getSelectedItem();
			model.setVtitle(selectedDoc);

		}
	}
	
	 /* public static void main(String args[]) {
	 
	  SelectGenerateDocumentViewController b = new
	 SelectGenerateDocumentViewController(); JFrame j = new JFrame();
	 j.setSize(600, 600); b.setVisible(true); j.add(b);
	  j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); j.setVisible(true);
	
	 }
	*/

	@Override
	protected void update(Model model) {
		// TODO: Być może należy tutaj dopisać obsługę zmian, do rozważenia.

	}

	@Override
	public String getTitle() {
		return "Utwórz dokument";
	}

	@Override
	public View getView() {
		return this;
	}
	
	
	public String readFromFile() throws IOException {
		
		String text="";
	    Path path = Paths.get(ConfigFrame.path);
	    try (BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset())){
	        String line = null;
	        while ((line = reader.readLine()) != null) {
	          text=text+line + " ";
          
	    }
	        
	} catch (Exception e) {}
	    return text;
	}
}