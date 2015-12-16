package winery.rss;

import winery.view.Controller;
import winery.view.View;

public class RSSController implements Controller {

	private RSSModel model;
	private RSSView view;
	
	public RSSController() {
		this.model = new RSSModel();
		this.view = new RSSView(this);
	}
	
	public String getLatestEvent() {
		model.getEvents(null);
		return null;
	}
	
	@Override
	public View getView() {
		// TODO Auto-generated method stub
		return view;
	}

	@Override
	public String getTitle() {
		return "Zdarzenia";
	}
	
	public static String getID() {
		return "rss";
	}

}
