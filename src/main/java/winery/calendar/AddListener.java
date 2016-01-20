package winery.calendar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;


public class AddListener implements ActionListener {

	private Utilities util;
	public Calendar calendar;
	
	public AddListener(Calendar calendar, Utilities util) {
		this.util = util;
		this.calendar = calendar;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		AddFrame addFrame = new AddFrame(calendar, util);
	}

}
