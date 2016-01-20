package winery.documents;

import winery.documents.DocumentBasic.Color;

/**
 * Klasa umożliwiająca generowanie sprecyzowanego dokumentu, dziedzicząca
 * po @see Dokument.
 * 
 * @author Przemysław Iskra
 * @version 1.0
 */

// Jeszcze bez pobierania danych wymaganych przez ten �w dokument.
public class Document3 extends Document1 {
	/** Konstruktor klasy tworzącej sprecyzowany dokumentu */

	public Document3() {
		needtable = true;
		horizontal = new String[3];
		size = 5;
		vertical = new String[5];
		size2 = 2;
		horizontal[0] = " Wina, winogrona i moszcze winogronowe  ";
		horizontal[1] = " szacowane dostawy do przedsiębiorcy ";
		horizontal[2] = " szacowane wykorzystanie ";
		vertical[0] = " z Chronioną Nazwą Pochodzenia (ChNP)   ";
		vertical[1] = " z Chronionym Oznaczeniem Geograficznym (ChOG)   ";
		vertical[2] = " bez ChNP/ ChOG  z określonej odmiany winorośli1   ";
		vertical[3] = " bez ChNP/ ChOG inne niż z określonej odmiany winorośli ";
		vertical[4] = " Pozostałe";
		title = "Deklaracja o szacowanej ilości win, winogron lub moszczu winogronowego, które będą dostarczone do przedsiębiorcy, oraz o szacowanej ilości ich wykorzystania w danym roku gospodarczym";
		date = "31 sierpnia";
		pathFile = "hello.pdf";

	}
	
	
	public String[][] getSpecifyTable() {
		String[][] data = new String[size][size2];
		data=this.getTable();
		for (int i = 0; i < size; i++) {
		
				data[i][0] = Double.toString(addValueFromHashMap(Color.RÓŻOWE.toString(), i) + addValueFromHashMap(Color.BIAŁE.toString(), i) + addValueFromHashMap(Color.CZERWONE.toString(), i));
		}
		return data;
		
		
	}

}