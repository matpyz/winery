package winery.documents;

import java.util.LinkedList;
import java.util.List;

import dbapi.Wine;
import winery.model.Model;

/**
 * Model w kontekście MVC dla 
 * 
 * @author thorik
 *
 */
public class WineAddModel extends Model {

	LinkedList<String> wineList, wineData;
	
	/**
	 * @return lista złożona z nazw win i roczników
	 */
	public List<String> getWineList() {
		return wineList;
	}

	/**
	 * @param wineList
	 */
	public void setWineList(LinkedList<String> wineList) {
		this.wineList = wineList;
		change();
	}

	/**
	 * @return lista z danymi danego wina
	 */
	public LinkedList<String> getWineData() {
		return wineData;
	}
	
	/**
	 * @param wine wino, którego dane chcemy wyświetlić
	 */
	public void setWineData(Wine wine) {
		wineData = wine.getData();
		change();
	}

}
