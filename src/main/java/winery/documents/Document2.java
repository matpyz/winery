package winery.documents;

import java.util.HashMap;
import java.util.Map.Entry;

import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPTable;

import dbapi.DBManager;
import dbapi.Seed;
import dbapi.Wine;
import winery.documents.DocumentBasic.Color;

/**
 * Klasa umożliwiająca generowanie sprecyzowanego dokumentu, dziedzicząca
 * po @see Dokument.
 * 
 * @author Przemysław Iskra
 * @version 1.0
 */

// Jeszcze bez pobierania danych wymaganych przez ten �w dokument.
public class Document2 extends DocumentBasic {
	/** Konstruktor klasy tworzącej sprecyzowany dokumentu */

	public Document2() {
		needtable = true;
		horizontal = new String[2];
		size = 5;
		vertical = new String[5];
		size2 = 1;
		horizontal[0] = " Wina  ";
		horizontal[1] = " Wina ";

		vertical[0] = " z Chronioną Nazwą Pochodzenia (ChNP)   ";
		vertical[1] = " z Chronionym Oznaczeniem Geograficznym (ChOG)   ";
		vertical[2] = " bez ChNP/ ChOG  z określonej odmiany winorośli   ";
		vertical[3] = " bez ChNP/ ChOG inne niż z określonej odmiany winorośli ";
		vertical[4] = " Pozostałe";
		title = "Deklaracja o szacowanej ilości wina, jaka będzie wyrobiona w danym roku gospodarczym";
		date = "31 sierpnia";
		pathFile = "hello.pdf";

	}

	/**
	 * Funkcja dodająca do generowanego dokumentu i do wskazanej tablicy
	 * zawartość dokumentu
	 * 
	 * @param table
	 *            wskazana tablica
	 * @param inData
	 *            dane jakie wymaga dokument, jakie firma musi podać
	 */

	public PdfPTable addtoTableNew(PdfPTable table, String[][] inData) {
		insertCell(table, horizontal[0], Element.ALIGN_CENTER, 5, f);
		insertCell(table, horizontal[1], Element.ALIGN_CENTER, 5, f);

		insertCell(table, vertical[0], Element.ALIGN_LEFT, 5, f);
		insertCell(table, inData[0][0], Element.ALIGN_CENTER, 5, f);

		insertCell(table, vertical[1], Element.ALIGN_LEFT, 5, f);
		insertCell(table, inData[1][0], Element.ALIGN_CENTER, 5, f);

		insertCell(table, vertical[2], Element.ALIGN_LEFT, 5, f);
		insertCell(table, inData[2][0], Element.ALIGN_CENTER, 5, f);

		insertCell(table, vertical[3], Element.ALIGN_LEFT, 5, f);
		insertCell(table, inData[3][0], Element.ALIGN_CENTER, 5, f);

		insertCell(table, vertical[4], Element.ALIGN_LEFT, 5, f);
		insertCell(table, inData[4][0], Element.ALIGN_CENTER, 5, f);

		return table;
	}

	public String[][] getSpecifyTable() {
		String[][] data = new String[size][size2];
		
		data=this.getTable();
		for (int i = 0; i < size; i++) {
		
				data[i][0] = Integer.toString(addValueFromHashMap( i));
		}
		return data;
		
		
	}
	
	public int addValueFromHashMap( int type) {
		int sum=0;
		HashMap<Integer, Seed>  hash = DBManager.getSeedsByType(type);
		for ( Entry<Integer, Seed> entry : hash.entrySet()) {
		  
		    Seed seed= entry.getValue();
		    sum=sum+ seed.getQuantity();
		    // do something with key and/or tab
		}
		
		return (int) (sum*  (3.5 / 2.0) * 0.001);
	}
	
}