package winery.documents;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import winery.model.Model;
import winery.view.Controller;
import winery.view.View;

/**
 * Klasa służaca do tworzenia zakładki z przewidywaniem zbiorów utworzony.
 * 
 * @author Przemysław Iskra
 * @version 2.0
 */
public class PredictingLitersOfWineViewContoller extends View implements Controller {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int a = 500;// długość
	int b = 500;
	private JPanel Pmaks;
	private JButton Bupdate;
	private JPanel[] PpanelTable;
	private JLabel[] Lname;
	private JTextField[] TchangeData;
	private JPanel Pcenter;
	private JPanel Pdown;
	private JLabel[] Lresult;
	private JPanel Ptop;
	private JLabel Linfo;
	private String[] nameTab;
	private JButton Bgenerate;
	private JLabel Linfo2;
	private JTextField Tdata;
	private JPanel Pmin;
	private JButton Bdirectory;
	private JLabel Lfile;
	private JLabel[] Lhekto, Ldata;
	PredictingLitersOfWineModel model;
	private JButton BgenerateDoc;
	private String[] numWine;

	public PredictingLitersOfWineViewContoller()

	{
		model = new PredictingLitersOfWineModel();
		nameTab = new String[5]; // Tabela nazw określonych rodzajów wina,
									// którego będziemy przewidywać ilość
		nameTab[0] = "Wino z Chronioną Nazwą Pochodzenia (ChNP) ";
		nameTab[1] = " Wino  z Chronionym Oznaczeniem Geograficznym (ChOG) ";
		nameTab[2] = "Wino bez ChNP/ ChOG  z określonej odmiany winorośli ";
		nameTab[3] = " Wino bez ChNP/ ChOG inne niż z określonej odmiany winorośli ";
		nameTab[4] = " Wino  Pozostałe";

		numWine = new String[5]; // Tabela ilości krzewów winogron, póżniej te
									// danę bedą pobierane z bazy danych
		numWine[0] = "0";
		numWine[1] = "0";
		numWine[2] = "0";
		numWine[3] = "0";
		numWine[4] = "0";

		setSize(a, b);
		Pmaks = new JPanel(); // Panel główny, do którego wszystko wkładamy.
		Pmaks.setSize(a, b);
		Pmaks.setLayout(new BorderLayout());
		add(Pmaks);
		Pcenter = new JPanel();
		Pdown = new JPanel();
		Ptop = new JPanel();
		Pmaks.add(Pcenter, BorderLayout.CENTER);
		Pmaks.add(Pdown, BorderLayout.SOUTH);
		Pmaks.add(Ptop, BorderLayout.NORTH);

		Linfo = new JLabel(" Prognozowanie Zbiorów.");
		Ptop.add(Linfo); // Opis.

		PpanelTable = new JPanel[5]; // Panel powalający pogrupować podane
										// ilości krzewów i informację ile dają
										// wina
		Lname = new JLabel[5]; // Nazwy wina
		TchangeData = new JTextField[5]; // zmienne dane ilości krzewów
		Lresult = new JLabel[5]; // ilość wina z danej ilości krzewów
		Pcenter.setLayout(new GridLayout(13, 1));
		Lhekto = new JLabel[5];
		Ldata = new JLabel[5];
		for (int i = 0; i < 5; i++) { // Organizacja danych do okienka
			PpanelTable[i] = new JPanel(new FlowLayout(FlowLayout.LEFT));
			Lhekto[i] = new JLabel("Ilość Sadzonek");
			Lname[i] = new JLabel(nameTab[i]);
			Lname[i].setMinimumSize(new Dimension(100, 30));

			TchangeData[i] = new JTextField(15);
			TchangeData[i].setText(numWine[i]);
			Ldata[i] = new JLabel("Ilość Hektolitrów");
			Lresult[i] = new JLabel(model.changeNum(TchangeData[i].getText()));
			Lresult[i].setSize(new Dimension(200, 30));
			Pcenter.add(Lname[i]);
			Pcenter.add(PpanelTable[i]);
			PpanelTable[i].add(Lhekto[i]);
			PpanelTable[i].add(TchangeData[i]);
			PpanelTable[i].add(Ldata[i]);
			PpanelTable[i].add(Lresult[i]);
			

		}
		//Linfo2 = new JLabel(" Podaj dane wystawcy dokumentu.  ");
		//Pcenter.add(Linfo2); // zwykłe info o tym co trzeba wpisać do Jtext.
		//Tdata = new JTextField();
		//Pcenter.add(Tdata); // wpisywanie potrzebnych danych

		Pmin = new JPanel(); // Panel z wyborek katalogu
		Bdirectory = new JButton("Wybierz Katalog !");
		Lfile = new JLabel(" Brak wybranego Karalogu. ");
		Bdirectory.addActionListener(new PathSelectListener());
		Pmin.add(Bdirectory);
		Pmin.add(Lfile);
		Pcenter.add(Pmin); // Koniec panelu z wyborem katalogu

		Bupdate = new JButton(" Aktualizuj. ");
		Bupdate.addActionListener(new UpdateData());
		Pcenter.add(Bupdate);

		BgenerateDoc = new JButton(" Generuj. ");
		BgenerateDoc.addActionListener(new GenerateDocListener());
		Pcenter.add(BgenerateDoc);

	}

	public class UpdateData implements ActionListener { // Aktualizacja danych

		public void actionPerformed(ActionEvent event) {
			for (int i = 0; i < 5; i++) {

				Lresult[i].setText(model.changeNum(TchangeData[i].getText()));

			}

		}
	}

	public class PathSelectListener implements ActionListener { // Ścieżka do
																// pliku

		private JFileChooser Ffilechose;

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
					 String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
				     if (OS.indexOf("win") >= 0) {
				    		model.setVpath(path + "\\document.pdf"); 
				      
				      } else if (OS.indexOf("nux") >= 0) { 	model.setVpath(path + "/document.pdf"); 
				     
				      } else { model.setVpath(path + "\\document.pdf"); 
				       
				      } 
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

	public class GenerateDocListener implements ActionListener { // Generowanie
																	// Dokumentu
																	// od razu,
																	// bez
																	// możliwości
																	// poprawy
																	// danych w
																	// wyskakującym
																	// okienku.

		public void actionPerformed(ActionEvent event) {

			String data = "Dane Wystawczy";
			if ( !model.getVpath().trim().equals("")) // Sprawdzanie,
																			// czy
																			// możemy
																			// generować,
																			// czyli
																			// czy
																			// mamy
																			// potrzebne
																			// dane.
			{
				Document2 doc = new Document2();
				String[][] dataToPDF = new String[5][2];
				for (int i = 0; i < 5; i++) { // Pobieranie danych z okienka

					dataToPDF[i][0] = model.changeNum(TchangeData[i].getText());
					dataToPDF[i][1] = "";

				}
				doc.createPDF(model.getVpath(), data, dataToPDF);
			} else
				JOptionPane.showMessageDialog(null, "Nie wypełniono wszystkich danych.  ");
		}
	}

	@Override
	public View getView() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Przewiduj zbiory";
	}

	@Override
	protected void update(Model model) {
		// TODO Auto-generated method stub

	}

}
