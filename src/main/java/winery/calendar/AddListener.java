package winery.calendar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;


public class AddListener implements ActionListener {

	public Calendar calendar;
	
	public AddListener(Calendar calendar) {
		this.calendar = calendar;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		AddFrame addFrame = new AddFrame(calendar);
	}

}
