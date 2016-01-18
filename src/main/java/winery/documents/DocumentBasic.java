package winery.documents;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map.Entry;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import dbapi.DBManager;
import dbapi.Wine;

/**
 * Klasa umożliwiająca generowanie szkieletu treści dowolnego dokumentu
 * 
 * @author Przemysław Iskra
 * @version 1.0
 */
// Generowany dokument jeszcze nie ma możliwości wypełnienia danymi wymaganymi
// przez wskazany dokument. .

public class DocumentBasic {

	Font f, fB, fU;

	/**
	 * @param title
	 *            Nazwa dokumentu, i pierwsze linijka w dokumencie.
	 * @param date
	 *            Informacje o terminie oddania dokumentu, którą znajduje się
	 *            też na dokumencie
	 * @param pathFile
	 *            ścieżka do pliku z nazwą, umożliwiająca utworzenie nowego
	 *            dokumentu
	 * @param size
	 *            Ilość wierszy w tabeli zawierającej dane jakie wymaga podanie
	 *            dany dokument
	 * @param size2
	 *            Ilość kolums w tabeli zawierającej dane jakie wymaga podanie
	 *            dany dokument
	 */
	public String title, date, pathFile;
	public int size, size2;
	String[][] data;
	Boolean needtable;
	String[] horizontal, vertical;

	/** Konstruktor klasy tworzącej szkielet dokumentu */
	public DocumentBasic() {
		needtable = false;
		title = " Testowa Deklaracja o ilości win białych lub czerwonych wprowadzonych do obrotu w poprzednim roku gospodarczym";
		date = "31 sierpnia";
		pathFile = "hello.pdf";
		size = 5;
		size2 = 2;

	}

	/**
	 * Metoda umożliwająca generowanie dokumentu
	 * 
	 * @param firmInfo
	 *            to dane firmy, którę są dla wszystkich dokumentów stałe
	 * @param data
	 *            dane jakie dokument wymaga od firmy
	 */
	public void createPDF(String firmInfo, String[][] data) {
		createPDF(pathFile, firmInfo, data);

	}

	/**
	 * Metoda umożliwająca generowanie dokumentu w wskazanym miejscu
	 * 
	 * @param firmInfo
	 *            to dane firmy, którę są dla wszystkich dokumentów stałe
	 * @param path
	 *            scieżka z nazwą pliku pdf, w którym bedzie zapisywany dokument
	 * @param data
	 *            dane jakie dokument wymaga od firmy
	 */
	public void createPDF(String path, String firmInfo, String[][] data) {
		Calendar now = Calendar.getInstance();
		this.data = data;

		Document doc = new Document();
		PdfWriter docWriter = null;

		try {

			BaseFont bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1250, BaseFont.EMBEDDED);
			f = new Font(bf, 12, Font.NORMAL);

			fB = new Font(bf, 14, Font.BOLD);
			fU = new Font(bf, 12, Font.UNDERLINE);

			pathFile = path;
			String endpdf = path.substring(path.length() - 4, path.length());

			if (endpdf.equals(".pdf"))
				path = path.substring(0, path.length() - 4); // ucinanie
																// koncówki .pdf

			String formattedDate = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(now.getTime());
			path = path + formattedDate + ".pdf"; // tworzenie nazwy pliku
													// zawierającym datę i
													// godzinę , aby dwa razy
													// tworzony dokument miał
													// inną nazwę

			docWriter = PdfWriter.getInstance(doc, new FileOutputStream(path));

			doc.addAuthor("iText Generate"); // Doddanie atrybutów dokumentu.
			doc.addTitle("Doc");
			doc.addKeywords("Metadata, iText, PDF");
			doc.addCreator("My program using iText");

			doc.open();

			// Stworzenia największego fragmentu dokumentu zwanego paragraf,
			// który zawiera różnie sformatowany i ułożony tekst. Dodamy go
			// póżniej do dokumentu.
			Paragraph paragraph = new Paragraph(); // I To jest główny paragraf.
			paragraph.setAlignment(Element.ALIGN_CENTER);

			float[] columnWidths = { 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f }; // Ustawienie
																				// podziału
																				// tablicy,
																				// aby
																				// wygodniej
																				// było
																				// tworzyć
																				// komórki
																				// .

			PdfPTable table = new PdfPTable(columnWidths); // Stworzenie tablicy
															// jaką włożymy do
															// głównego
															// paragrafu a potem
															// do dokumentu.
			table.setWidthPercentage(90f); // Ustawienie szerokości tabeli w
											// dokumencie.

			Paragraph para = new Paragraph();// Tworzenie paragrafu.
			Paragraph paragraphmin = new Paragraph(); // Tworzenie
														// podparagrafów.
			Paragraph paragraphmin2 = new Paragraph();
			paragraphmin.add(new Phrase(title + "\n", fB)); // Dodajemy
															// fragmenty tekstów
															// do odpowiedniego
															// podparagrafu.
			paragraphmin2.add(new Phrase(" przez ", fU));
			paragraphmin2.add(new Phrase(
					" przedsiębiorców wykonujących działalność gospodarczą, o której mowa w art. 17 ust. 1 ustawy z dnia 12 maja 2011 r. o wyrobie i rozlewie wyrobów winiarskich, obrocie tymi wyrobami i organizacji rynku wina, wyrabiających wyroby winiarskie z wina, winogron lub moszczów winogronowych \n",
					f));
			paragraphmin2.add(new Phrase(" do ", fU));
			paragraphmin2.add(new Phrase("Prezesa Agencji Rynku Rolnego \n", f));
			paragraphmin2.add(new Phrase(" w terminie ", fU));

			int year = now.get(Calendar.YEAR); // Pobieramy rok
			paragraphmin2.add(new Phrase(date + " " + year + ".\n" + "\n", f));
			paragraphmin2.setAlignment(Element.ALIGN_LEFT);
			para.add(paragraphmin); // Podparagrafy dodajemy do większego
									// paragrafu
			para.add(paragraphmin2);
			insertCell(table, para, -1, 10); // Utworzony paragraf dodajemy do
												// tabeli, Komórka jest
												// ustawiana na całą szerkość
												// tablicy.
			// Dodanie dalszych danych.
			insertCell(table,
					"Imię i nazwisko, miejsce zamieszkania i adres albo firma, siedziba i adres oraz numer wpisu do ewidencji producentów i przedsiębiorców wyrabiających wino z winogron pozyskanych z upraw winorośli położonych na terytorium Rzeczypospolitej Polskiej podmiotu składającego deklarację, jeżeli podmiot ten taki numer posiada.",
					Element.ALIGN_LEFT, 5, f);
			insertCell(table, firmInfo, Element.ALIGN_LEFT, 5, f);

			// data =createTableData(); //Pobranie danych jakich żadałby wybrany
			// dokument
			table = addtoTableNew(table, data); // Umożliwienia dodawania nowych
												// danych do tablicy bez
												// modyfikacji funkcji
												// createPDF, jedynie
												// modyfikując addtoTableNew.
			paragraph.add(addNewParagraph()); // Umożliwienia dodawania nowych
												// danych bez modyfikacji
												// funkcji createPDF, jedynie
												// modyfikując
												// addNewParagraph().
			paragraph.add(table); // Dodanie do głównego paragrafu tablicy.
			paragraph.add(addNewParagraph2());

			Paragraph dottedline = new Paragraph(" \n"); // Stworzenie miejsca
															// na podpisy.
			dottedline.add("           ..................... ");
			dottedline.add(new Phrase("                                                                           "));
			dottedline.add(".......................\n ");
			dottedline.add(new Phrase("      (miejscowość i data)", f));
			dottedline.add(new Phrase("                                                           "));
			dottedline.add(new Phrase("(podpis składającego deklarację)\n", f));
			paragraph.add(dottedline);

			doc.add(paragraph); // Dodanie głównego paragrafu do dokumentu.

		} catch (DocumentException dex) {
			dex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		finally {
			if (doc != null) {

				doc.close();
				if (Desktop.isDesktopSupported()) {
					try {
						File myFile = new File(path);
						Desktop.getDesktop().open(myFile);
					} catch (IOException ex) {
						// no application registered for PDFs
					}
				}
			}
			if (docWriter != null) {

				docWriter.close();
			}
		}
	}

	/**
	 * Funkcja umożliwiająca modyfikację dokumentu bez modyfikacji funkcji
	 * CreatePDF
	 */
	public Element addNewParagraph2() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Funkcja umożliwiająca modyfikację dokumentu bez modyfikacji funkcji
	 * CreatePDF
	 */
	public Element addNewParagraph() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Funkcja umożliwiająca modyfikację tabeli w dokumencie bez modyfikacji
	 * funkcji CreatePDF
	 * 
	 * @param table
	 *            tabelą, którą modyfikujemy
	 * @param inData
	 *            dane jakie dokument wymaga od firmy
	 */
	public PdfPTable addtoTableNew(PdfPTable table, String[][] inData) {

		return table;
	}

	/**
	 * funkcja umożliwiająca wkłądanie to tabeli tekstu o odpowiednim rozmiarze
	 * komórki i ustawieniach tekstu
	 * 
	 * @param table
	 *            tabela do której wkładamy dane
	 * @param text
	 *            tekst który wkładamy
	 * @param align
	 *            ustawienie wyrównania tekstu.
	 * @param colspan
	 *            jaką cześć tabeli zajmuje komórka, w wartościach całkowitych
	 *            od 1 do 10
	 * @param font
	 *            czcionka
	 */
	public void insertCell(PdfPTable table, String text, int align, int colspan, Font font) {
		Paragraph parag = new Paragraph(new Phrase(text.trim(), font));

		insertCell(table, parag, align, colspan);

	}

	/**
	 * funkcja umożliwiająca wkłądanie to tabeli paragrafu o odpowiednim
	 * rozmiarze komórki i ustawieniach tekstu
	 * 
	 * @param table
	 *            tabela do której wkładamy dane
	 * @param text
	 *            tekst który włozony jest do paragrafu.
	 * @param align
	 *            ustawienie wyrównania tekstu.
	 * @param colspan
	 *            jaką cześć tabeli zajmuje komórka, w wartościach całkowitych
	 *            od 1 do 10
	 * @param font
	 *            czcionka
	 */
	public void insertCell(PdfPTable table, Paragraph text, int align, int colspan) {

		PdfPCell cell = new PdfPCell(text);
		if (align != -1)
			cell.setHorizontalAlignment(align);

		cell.setColspan(colspan);

		table.addCell(cell);

	}
	
	
	public String[][] getSpecifyTable() {
		String[][] data = new String[size][size2];
		for (int i = 0; i < size; i++) {
			for (int k = 0; k < size2; k++)
				data[i][k] = "";
		}
		return data;
	
		
	}
	
	
	public String[][] getTable() {
	
		
		String[][] data = new String[size][size2];
		for (int i = 0; i < size; i++) {
			for (int k = 0; k < size2; k++)
				data[i][k] = "0";
		}	
		
		
		return data;
		
	}
	
	
	
	
	
	public int addValueFromHashMap(String color, int type) {
		int sum=0;
		HashMap<Integer, Wine>  hash = DBManager.getWineByCororAndType(color, type);
		for ( Entry<Integer, Wine> entry : hash.entrySet()) {
		  
		    Wine Wine= entry.getValue();
		    sum=sum+ Wine.getProduced();
		    // do something with key and/or tab
		}
		
		return (int) (sum*0.75);
	}
	
	public enum Color {
	    CZERWONE("czerwone"), BIAŁE("białe"), RÓŻOWE("różowe")
	    ;
	    private final String value;

	    private Color(final String value) {
	        this.value = value;
	    }

	    public String getValue() {
	        return value;
	    }

	    @Override
	    public String toString() {
	        // TODO Auto-generated method stub
	        return getValue();
	    }
	}
	
	
	
	
}