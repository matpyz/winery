package winery.documents;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JOptionPane;

/**
 * Klasa służaca do obsługi gui WybieranieDokumentu.
 * 
 * @author Przemysław Iskra
 * @version 1.0
 */
public class SelectGenerateDocumentModel extends PredictingLitersOfWineModel {

	String title;
	// title to zmienna odpowiadająca za rozpoznanie dokumentu
	// to zmienna zawierająca scieżka do katalogu i nazwę pliku
	DateFormat dateFormat = new SimpleDateFormat("dd:MM:yyy");
	Calendar cal = Calendar.getInstance();
	String[] docTable = new String[] {
			"<html>Deklaracja o ilości win białych lub czerwonych <br> wprowadzonych do obrotu w poprzednimroku   <br>gospodarczym",
			"<html>Deklaracja o szacowanej ilości wina, jaka będzie <br>wyrobiona w danym roku gospodarczym ",
			"<html> Deklaracja o szacowanej ilości win, winogron lub <br> moszczu winogronowego, ktęre  będą  <br>dostarczone do  przedsiębiorcy, oraz o szacowanej ilości  <br> ich wykorzystania w  danym roku gospodarczym",
			"<html>Deklaracja o zapasach win lub moszczów winogronowych<br> posiadanych w dniu " 
					+ dateFormat.format(cal.getTime()) };
	DocumentBasic doc;

	// docTable zawiera dokumenty do wyboru
	public SelectGenerateDocumentModel() {

	}

	public String getdocTable(int i) {
		if (i < docTable.length)
			return docTable[i];
		else
			return "";
	}

	public String[] getTable() {
		return docTable;
	}

	public void setVtitle(String title) {
		this.title = title;
		doc = getDokument(title);

	}

	public String getVtitle() { // ustawić, że tytuł tworzy też Dokument.
		return title;
	}

	public DocumentBasic getDokument(String title) { // Jak b�dzie wi�cej
														// dokument�w to napisa�
														// lepsza wersje tej
														// funkcji. We
		if (title.equals(docTable[0])) {
			Document1 doc = new Document1();

			return doc;
			// doc.createPDF(path, data); //Generowanie dokumentu.
		} else if (title.equals(docTable[1])) {
			Document2 doc = new Document2();
			return doc;
			// doc2.createPDF(path , data); //Generowanie dokumentu.

		}

		else if (title.equals(docTable[2])) {
			Document3 doc = new Document3();
			return doc;
			// doc2.createPDF(path , data); //Generowanie dokumentu.

		} else if (title.equals(docTable[3])) {
			Document4 doc = new Document4();
			return doc;
			// doc2.createPDF(path , data); //Generowanie dokumentu.

		}
		return null;
	}

	public DocumentBasic getDokument() {
		return getDokument(title);
	}

	/**
	 * Funkcja generująca dokument.
	 * 
	 * @param info
	 *            informacje o firmie.
	 * @param data
	 *            dane jakie dokument wymaga od firmy
	 */

	public void generate(String info, String[][] data) {
		DocumentBasic doc = getDokument(title);
		doc.createPDF(path, info, data);

	}

	/**
	 * 
	 * @param path
	 *            ścieżka do pliku
	 * @param firmInfo
	 *            informacje o firmie
	 */

	public void createTableData(String path, String firmInfo) {
		String[][] data = new String[doc.size][doc.size2];
		for (int i = 0; i < doc.size; i++) {
			for (int k = 0; k < doc.size2; k++)
				data[i][k] = "";
		}
		if (doc.needtable == true) {

			@SuppressWarnings("unused")

			ChangeDataDocumentGui zm = new ChangeDataDocumentGui(doc.horizontal, doc.vertical, data, path, firmInfo,
					doc);
		} else {

			doc.createPDF(path, firmInfo, data);
			JOptionPane.showMessageDialog(null, "Dokument przygotowany. ");

		}
		// Otworzenia okienka do poprawy danych, ale te dane poprawione tutaj
		// nie wracają, bo jeszcze tego nie zrobiłem. :D
		// return data;
	}

}
