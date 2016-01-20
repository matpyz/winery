package winery.documents;

import java.util.LinkedList;
import java.util.List;

import dbapi.Wine;
import winery.model.Model;

public class WineAddModel extends Model {

	LinkedList<String> wineList;
	
	public List<String> getWineList() {
		return wineList;
	}

	public Wine getWine() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setWineList(LinkedList<String> wineList) {
		this.wineList = wineList;
		change();
	}

}
