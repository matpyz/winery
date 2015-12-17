package winery.rss;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import winery.model.Model;
import winery.view.View;

public class RSSView extends View implements ActionListener {

	protected static final long serialVersionUID = 1L;
	private RSSController controller;

	public RSSView(RSSController controller) {
		this.controller = controller;
		createGUI();
	}

	private void createGUI() {
		// Soon.
	}

	@Override
	protected void update(Model model) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switch (e.getActionCommand()) {
		case "sth":
			break;
		default:
			break;
		}
	}

}
