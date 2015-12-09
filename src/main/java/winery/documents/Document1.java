package winery.documents;

import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPTable;

/**
 * Klasa umożliwiająca generowanie sprecyzowanego dokumentu, dziedzicząca
 * po @see Dokument.
 * 
 * @author Przemysław Iskra
 * @version 1.0
 */

// Jeszcze bez pobierania danych wymaganych przez ten ów dokument.
public class Document1 extends DocumentBasic {
	/** Konstruktor klasy tworzącej sprecyzowany dokumentu */

	public Document1() {
		needtable = true;
		horizontal = new String[3]; size=5;
		vertical = new String[5]; size2=2;
		horizontal[0] = " Wina  ";
		horizontal[1] = "Czerwone albo różowe";
		horizontal[2] = "Białe";
		vertical[0] = " z Chronioną Nazwą Pochodzenia (ChNP)   ";
		vertical[1] = " z Chronionym Oznaczeniem Geograficznym (ChOG)   ";
		vertical[2] = " bez ChNP/ ChOG  z określonej odmiany winorośli1   ";
		vertical[3] = " bez ChNP/ ChOG inne niż z określonej odmiany winorośli ";
		vertical[4] = " Pozostałe";
		title = "Deklaracja o ilości win białych lub czerwonych wprowadzonych do obrotu w poprzednim roku gospodarczym";
		date = "31 sierpnia";
		pathFile = "hello.pdf";

	}

	/**
	 * Funkcja dodającą do generowanego dokumentu i do wskazanej tablicy
	 * zawartość dokumentu
	 * 
	 * @param table
	 *            wskazana tablica
	 * @param inData
	 *            dane jakie wymaga dokument, jakie firma musi podać
	 */

	public PdfPTable addtoTableNew(PdfPTable table, String[][] inData) {
		insertCell(table, horizontal[0], Element.ALIGN_CENTER, 4, f);
		insertCell(table, horizontal[1], Element.ALIGN_CENTER, 3, f);
		insertCell(table, horizontal[2], Element.ALIGN_CENTER, 3, f);

		insertCell(table, vertical[0], Element.ALIGN_LEFT, 4, f);
		insertCell(table, inData[0][0], Element.ALIGN_CENTER, 3, f);
		insertCell(table, inData[0][1], Element.ALIGN_CENTER, 3, f);

		insertCell(table, vertical[1], Element.ALIGN_LEFT, 4, f);
		insertCell(table, inData[1][0], Element.ALIGN_CENTER, 3, f);
		insertCell(table, inData[1][1], Element.ALIGN_CENTER, 3, f);

		insertCell(table, vertical[2], Element.ALIGN_LEFT, 4, f);

		insertCell(table, inData[2][0], Element.ALIGN_CENTER, 3, f);
		insertCell(table, inData[2][1], Element.ALIGN_CENTER, 3, f);

		insertCell(table, vertical[3], Element.ALIGN_LEFT, 4, f);

		insertCell(table, inData[3][0], Element.ALIGN_CENTER, 3, f);
		insertCell(table, inData[3][1], Element.ALIGN_CENTER, 3, f);

		insertCell(table, vertical[4], Element.ALIGN_LEFT, 4, f);
		insertCell(table, inData[4][0], Element.ALIGN_CENTER, 3, f);
		insertCell(table, inData[4][1], Element.ALIGN_CENTER, 3, f);

		return table;
	}

}
