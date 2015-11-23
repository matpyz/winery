package winery.documents;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Klasa służaca do obsługi gui poprawy danych dokumentu.
 * 
 * @author Przemysław Iskra
 * @version 1.0
 */

public class ChangeDataDocumentGui extends JFrame {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	JPanel Pmax, Pcenter, Pdown, Ppom;
	JLabel Linfo, Ltext;
	JPanel[] PpanelTable;
	JLabel[] LinfoHorizontal;
	JTextField[] TFhorizontal;
	JButton Bnext;
	int itt, end, size;

	String[][] data;
	String[] horizontal, vertical;
	String path, firmInfo;
	DocumentBasic dokument;

	/**
	 * Klasa tworząca wyskakujące okienko zmiany danych. a Idea jest taka:
	 * dokument potrzebuje danych, którę praktycznie można zorganizować w
	 * tabelce a więc, dla każdego wiersza danych wymaganych przez dokument
	 * wyświetlamy czego te dany dotyczą oraz same proponowane dane pobrane z
	 * bazy, to wszystko w gui. Użytkownik może je zmienić lub nie ruszać.
	 * Przyciskiem potwierdzu, że skończył, to wyskocza nowe dane i tak aż
	 * użytkownik sprawdzi wszystkie dane.
	 * 
	 * @param horizontal
	 *            dane z dokumentu tworzące tytuły kolumn danych z dokumentu
	 * @param vertical
	 *            dane z dokumentu tworzące tytuły wierszy danych z dokumentu
	 * @param data
	 *            dane jakie dokument wymaga od firmy
	 * @param path
	 *            ścieżka do zapisu pliku
	 * @param firmInfo
	 *            informacje o firmie
	 * @param dokument
	 *            Objekt reprezentujący dany dokument.
	 */

	public ChangeDataDocumentGui(String[] horizontal, String[] vertical, String[][] data, String path, String firmInfo,
			DocumentBasic dokument) {
		size = horizontal.length - 1;
		end = vertical.length;

		this.data = data;
		this.horizontal = horizontal;
		this.vertical = vertical;
		this.path = path;
		this.firmInfo = firmInfo;
		this.dokument = dokument;
		// Zrobić sprawdzanie czy tabelka data ma odpowiednie długości według
		// horizontal vertical tablic.
		itt = 0;
		Pmax = new JPanel(); // Duży panel do którego wszystko wrzucamy.
		Pmax.setSize(500, 500);
		setSize(500, 500);
		add(Pmax);
		Pmax.setLayout(new BorderLayout());
		Pcenter = new JPanel(); // PodPanel środkowy, tutaj będziemy zmieniać
								// proponowane dane lub zostawiać je jak są
								// dobre
		Pdown = new JPanel(); // Podpanel dolny z guzikiem
		Pmax.add(Pcenter, BorderLayout.CENTER);
		Pmax.add(Pdown, BorderLayout.SOUTH);
		Pcenter.setLayout(new FlowLayout(FlowLayout.RIGHT, 150, 10));
		Bnext = new JButton("Dalej"); // Przycisk odpowiedzialny za pobieranie
										// danych i wstawianie następnych do
										// pobrania
		Pdown.add(Bnext);
		Bnext.addActionListener(new ButtonListener());
		// Linfo, Ltext Informacje.
		Linfo = new JLabel(horizontal[0] + " " + vertical[itt], SwingConstants.CENTER);
		Ltext = new JLabel(
				"<html>Sprawdź poprawność poniższych danych,  jeśli trzeba popraw: <br> Uwaga wersje nie pobiera jeszcze z bazy danych",
				SwingConstants.CENTER);

		Ppom = new JPanel();
		Ppom.setLayout(new GridLayout(2, 1));
		// Ppom.setLayout();
		Ppom.add(Ltext);
		Ppom.add(Linfo);
		Pmax.add(Ppom, BorderLayout.NORTH);
		// Pcenter.add(Ppom);
		PpanelTable = new JPanel[size];
		LinfoHorizontal = new JLabel[size]; // czego dane z dokumentu dotyczą
		TFhorizontal = new JTextField[size]; // textfield służacy do zmiany
												// danych

		for (int i = 0; i < size; i++) {
			PpanelTable[i] = new JPanel();

			Pcenter.add(PpanelTable[i]);
			LinfoHorizontal[i] = new JLabel(horizontal[i + 1]); // Plus jeden bo
																// tabelka
																// horizontal
																// zawiera jedną
																// dodatkową
																// danę, którą
																// omijamy
																// tutaj.
			LinfoHorizontal[i].setMinimumSize(new Dimension(100, 30));
			TFhorizontal[i] = new JTextField(15);
			TFhorizontal[i].setText(data[itt][i]);
			PpanelTable[i].add(LinfoHorizontal[i]);
			PpanelTable[i].add(TFhorizontal[i]);

		}

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {

				dispose();
			}
		});

		setVisible(true);

	}

	public class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			if (itt < end - 1) // Jeśli są jeszcze jakieś dane do sprawdzenia i
								// uzupełnenia.
			{
				for (int i = 0; i < size; i++) {
					data[itt][i] = TFhorizontal[i].getText(); // zapisujemy
																// wcześniej
																// wpisane oraz
																// niezmienione
																// dane
				}
				itt++;
				Linfo.setText(horizontal[0] + " " + vertical[itt]); // poprawiamy
																	// określenie
																	// danych
																	// jakie
																	// nadał im
																	// dokument
				for (int i = 0; i < size; i++) {
					TFhorizontal[i].setText(data[itt][i]); // wsadzamy następne
															// proponowane dane

				}
				if (itt == end - 1)
					Bnext.setText("Koniec");
			} else {
				for (int i = 0; i < size; i++) {
					data[itt][i] = TFhorizontal[i].getText();
				} // Ostatnie dane zostają pobrane,

				dokument.createPDF(path, firmInfo, data); // generujemy
															// dokument.
				JOptionPane.showMessageDialog(null, "Dokument przygotowany. ");
				dispose();// okienko się zamyka.
			}

		}
	}

	/*
	 * public static void main(String args[]){
	 * 
	 * String[] a={" Słowa ","takie","piękne", "Jej"}; String[] b={
	 * "Są tutaj  dziwne","jejjj", "Lego"}; String[][] d={{"---","---",
	 * " ------"}, {"---","---","--------"}, {"---","---","--------"}};
	 * 
	 * @SuppressWarnings("unused") //BRAKKKKK ZWRACANIA GOTOWEJ TABELII,,,,
	 * ZROBIĆ!!!!!!!!!!!!!!. ZmianaDanychDokumentuGui c=new
	 * ZmianaDanychDokumentuGui(a,b,d); }
	 */

}
