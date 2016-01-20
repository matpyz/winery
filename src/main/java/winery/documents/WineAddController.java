package winery.documents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import dbapi.DBManager;
import dbapi.User;
import dbapi.Wine;
import winery.accounts.AccountsModel;
import winery.accounts.AccountsView;
import winery.model.Model;
import winery.view.Controller;
import winery.view.View;

public class WineAddController implements Controller {

	HashMap<String, Integer> wineIdMap = new HashMap<>();
	
	WineAddModel model;
	WineAddView view;
	
	protected String response;

	public WineAddController() {
		model = new WineAddModel();
		view = new WineAddView(this);

		model.register(view);
		getWineList();
	}
	
	private void getWineList() {
		HashMap<Integer, Wine> wines = DBManager.getAllWines();

		List<Wine> wineList = new ArrayList<>(wines.values());
		LinkedList<String> userData = new LinkedList<>();
		for (Wine w : wineList) {
			userData.add(w.getName() + " (" + w.getYear() + ")");
			wineIdMap.put(w.getName()+w.getYear(), w.getId());
		}
		model.setWineList(userData);
	}

	public void firstUse() {


	}

	@Override
	public View getView() {
		return view;
	}

	@Override
	public String getTitle() {
		return "Magazyn";
	}

	public String getId() {
		// TODO Auto-generated method stub
		return "wineadd";
	}
	
	public void getWineData(String string) {
		// TODO Auto-generated method stub
		
	}

	public void modifyWine(String name, String surname, String login,
			String password, String mail, String payment, String group) {
		// TODO Auto-generated method stub
		
	}

	public void deleteWine(String string) {
		// TODO Auto-generated method stub
		
	}
	
	
}
