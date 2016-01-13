package winery.rss;

import java.util.ArrayList;

import winery.model.Model;

public class RSSModel extends Model {

	ArrayList<String> list;
	
	public RSSModel() {
		// TODO Auto-generated constructor stub
	}
	
	public void setList(ArrayList<String> list) {
		this.list = list;
		change();
	}
	
	public ArrayList<String> getList() {
		return list;
	}
	
	public int getSize() {
		return list.size();
	}
		
	public Object getElementAt(int index) {
		return list.get(index);
	}
}
