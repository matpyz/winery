package winery.rss;

import java.util.ArrayList;
import java.util.HashMap;

import dbapi.DBManager;
import dbapi.FieldCell;
import dbapi.FieldStatus;
import winery.view.Controller;
import winery.view.View;

public class RSSController implements Controller {

	private RSSModel model;
	private RSSView view;

	public RSSController() {
		this.model = new RSSModel();
		this.view = new RSSView(this);
		
		model.register(view);
		//getLatestEvents();
	}

	/**
	 * Tworzy listę zdarzeń.
	 */
	public void getLatestEvents() {
		ArrayList<String> list = new ArrayList<String>();
		/*HashMap<Integer, FieldCell> fields = DBManager.getAllFieldsCells();
		for(int x : fields.keySet()) {
			FieldCell f = fields.get(x);
			if(f.getFieldStatus() != null){
				StringBuilder sb = new StringBuilder();
				sb.append(" W sektorze ");
				sb.append(f.getSection());
				sb.append("|");
				sb.append(f.getRow());
				sb.append("|");
				sb.append(f.getColumn());
				sb.append(" zaszły następujące zdarzenia:\n");
				sb.append("Kod: ");
				FieldStatus fs = DBManager.getFieldStatusById(f.getCurrentStatusId());
				sb.append(fs.getCode());
				sb.append(" ");
				sb.append(fs.getCategory());
				sb.append(" ");
				sb.append(fs.getSubcategory());
				sb.append("\nOpis: ");
				sb.append(f.getDescription());
				list.add(sb.toString());
			}
		}	*/	
		StringBuilder sb = new StringBuilder();
		sb.append(" W sektorze ");
		sb.append("A");
		sb.append("|");
		sb.append("32");
		sb.append("|");
		sb.append("10");
		sb.append(" zaszły następujące zdarzenia:\n");
		sb.append("Kod: ");
		sb.append("PZP2");
		sb.append(" ");
		sb.append("Uszkodzenia krzaczka");
		sb.append(" ");
		sb.append("Choroba");
		sb.append("\nOpis: ");
		sb.append("pojawiło się psze pana coś białego >.<");
		list.add(sb.toString());
		
		sb = new StringBuilder();
		sb.append(" W sektorze ");
		sb.append("B");
		sb.append("|");
		sb.append("22");
		sb.append("|");
		sb.append("19");
		sb.append(" zaszły następujące zdarzenia:\n");
		sb.append("Kod: ");
		sb.append("PZP9");
		sb.append(" ");
		sb.append("Ogrodzenie");
		sb.append(" ");
		sb.append("Zniszczenie");
		sb.append("\nOpis: ");
		sb.append("ktos sie chyba wypierdolil");
		list.add(sb.toString());
		
		sb = new StringBuilder();
		sb.append(" W sektorze ");
		sb.append("B");
		sb.append("|");
		sb.append("22");
		sb.append("|");
		sb.append("19");
		sb.append(" zaszły następujące zdarzenia:\n");
		sb.append("Kod: ");
		sb.append("PZP9");
		sb.append(" ");
		sb.append("Ogrodzenie");
		sb.append(" ");
		sb.append("Zniszczenie");
		sb.append("\nOpis: ");
		sb.append("ktos sie chyba wypierdolil");
		list.add(sb.toString());
		model.setList(list);
	}

	@Override
	public View getView() {
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
