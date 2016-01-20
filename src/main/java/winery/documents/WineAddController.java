package winery.documents;

import dbapi.DBManager;
import winery.model.Model;
import winery.view.Controller;
import winery.view.View;

public class WineAddController implements Controller {

	
	protected String response;

	public WineAddController() {
		
	}
	
	public void firstUse() {


	}

	@Override
	public View getView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	public void getWineData(String string) {
		// TODO Auto-generated method stub
		
	}

	public void modifyWine(String name, String surname, String login,
			String password, String mail, String payment, String group) {
		// TODO Auto-generated method stub
		
	}
	
	public static String getID() {
		return "wineadd";
	}
}
