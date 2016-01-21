package winery.documents;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import dbapi.DBManager;
import dbapi.Wine;
import winery.view.Controller;
import winery.view.View;

public class WineAddController implements Controller {

	/**
	 * Mapa aktualizowana przy każdym pobraniu listy win. Kluczem jest String
	 * złożony z nazwy i rocznika oddzielonych spacją.
	 */
	HashMap<String, Integer> wineIdMap = new HashMap<>();

	WineAddModel model;
	WineAddView view;

	/**
	 * Rezultat ostatniej akcji wykonanej przez instancję.
	 */
	protected String response;

	/**
	 * Bazowy adres API sklepu.
	 */
	String apiUrl = "http://46.101.188.159:8080/WineShop";

	public WineAddController() {
		model = new WineAddModel();
		view = new WineAddView(this);

		model.register(view);
		getWineList();
	}

	/**
	 * Pobiera z bazy danych aktualną listę win.
	 */
	private void getWineList() {
		HashMap<Integer, Wine> wines = DBManager.getAllWines();
		List<Wine> wineList = new ArrayList<>(wines.values());
		LinkedList<String> wineData = new LinkedList<>();
		for (Wine w : wineList) {
			wineData.add(w.getName() + " " + w.getYear());
			wineIdMap.put(w.getName() + " " + w.getYear(), w.getId());
		}
		model.setWineList(wineData);
	}

	@Override
	public View getView() {
		return view;
	}

	@Override
	public String getTitle() {
		return "Magazyn";
	}

	public static String getID() {
		return "wineadd";
	}

	/**
	 * Pobiera z bazy danych informacje o wybranym winie.
	 * 
	 * @param name_year
	 *            nazwa i rocznik wina, oddzielone spacją
	 */
	protected void getWineData(String name_year) {
		model.setWineData(DBManager.getWineById(wineIdMap.get(name_year)));
	}

	/**
	 * Zmienia dane wina w bazie danych. Parametry pokrywają się z kolumnami w
	 * tabeli.
	 * 
	 * @param name
	 * @param grapes
	 * @param color
	 * @param produced
	 * @param sold
	 * @param baseprice
	 * @param productionCost
	 * @param year
	 * @param forSale
	 */
	protected void modifyWine(String name, String grapes, String color,
			int produced, int sold, int baseprice, int productionCost,
			int year,/* int protectedOrigin, */int forSale) {
		if (DBManager.updateDataForWineById(wineIdMap.get(name + " " + year),
				name, grapes, color, produced, sold, baseprice, productionCost,
				year, 0, forSale)) {
			getWineList();
			try {
				URL url = new URL(apiUrl + "/api/add");
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");

				String input = "{ \"name\": \"" + name + "\",\"amount\": \""
						+ forSale + "\" }";

				OutputStream os = conn.getOutputStream();
				os.write(input.getBytes());
				os.flush();

				if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
					response = "Zaktualizowano dane wina " + name
							+ ", rocznik " + year;
				else
					response = "Nie udało się zaktualizować danych wina "
							+ name + ", rocznik " + year;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else
			response = "Nie udało się zaktualizować danych wina " + name
					+ ", rocznik " + year;
	}

	/**
	 * Usuwa z bazy danych wybrane wino.
	 * 
	 * @param name_year
	 *            nazwa i rocznik wina, oddzielone spacją
	 */
	protected void deleteWine(String name_year) {
		if (DBManager.removeWineById(wineIdMap.get(name_year))) {
			getWineList();
			response = "Usunięto wino " + name_year;
		} else
			response = "Nie udało się usunąć wina " + name_year;

	}

	/**
	 * Dodaje do bazy danych nowe wino. Parametry pokrywają się z kolumnami w
	 * tabeli.
	 * 
	 * @param name
	 * @param grapes
	 * @param color
	 * @param produced
	 * @param sold
	 * @param baseprice
	 * @param productionCost
	 * @param year
	 * @param forSale
	 */
	protected void newWine(String name, String grapes, String color,
			int produced, int sold, int baseprice, int productionCost,
			int year, /* int protectedOrigin, */int forSale) {
		if (DBManager.addWine(name, grapes, color, produced, sold, baseprice,
				productionCost, year, 0, forSale) != null) {
			getWineList();
			try {
				URL url = new URL(apiUrl + "/api/addtype");
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");

				String input = "{ \"wineName\": \"" + name + "\",\"color\": \""
						+ color + "\",\"type\": \"" + grapes
						+ "\",\"price\": \"" + baseprice + "\" }";

				OutputStream os = conn.getOutputStream();
				os.write(input.getBytes());
				os.flush();

				if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
					response = "Dodano wino " + name + ", rocznik " + year;
				else
					response = "Nie udało się dodać wina " + name
							+ ", rocznik " + year;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else
			response = "Nie udało się dodać wina " + name + ", rocznik " + year;
	}
}
