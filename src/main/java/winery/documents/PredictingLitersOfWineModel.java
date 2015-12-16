package winery.documents;

import winery.model.Model;

/**
 * Klasa służąca do prgnozowania zbiorów oraz do tworzenia dokumentu z tymi
 * danymi. utworzony.
 * 
 * @author Przemysław Iskra
 * @version 1.0
 */
public class PredictingLitersOfWineModel extends Model {

	String path = ""; // Scieżka do pliku

	public void setVpath(String path) {
		this.path = path;
	}

	public String getVpath() {
		return path;
	}

	public String changeNum(String data) { // Konwertowania ilości krzewów na
											// daną ilość wina
		Integer val = 0;
		Double result = (double) 0;
		try {
			val = Integer.parseInt(data);

			result = val * (3.5 / 2.0) * 0.001;
		} catch (NumberFormatException nfe) {
			return "Błędne dane";
		}
		return "             " + String.format("%.2f", result);
	}

}
