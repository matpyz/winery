package winery.calendar;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class MyMouseListener implements MouseListener {
	
	private OptionsFrame options;
	private Dimension screenSize;

	private JPanel days;
	private ColorButton[][] panelHolder;
	private int row;
	private int column;
	private int x;
	private int y;
	
	public Calendar calendar;
	
	private Timer timer;
	private ShowInformation showInformation;
	
	public MyMouseListener(JPanel days, ColorButton[][] panelHolder, int row, int column, Calendar calendar) {
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.days = days;
		this.panelHolder = panelHolder;
		this.row = row;
		this.column = column;
		
		this.calendar = calendar;
		
		
		showInformation = new ShowInformation(row, column);
		timer = new Timer();
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		System.out.println("Wciśnięto przycisk: " + row + " x " + column);
		
		// Położenie danego przycisku
		x = days.getLocationOnScreen().x + 85 + (column*78);
		if (x + 300 >= screenSize.getWidth()) x = days.getLocationOnScreen().x + (column*80) - 315;
		
		y = days.getLocationOnScreen().y + (row*58);
		if (y + 300 >= screenSize.getHeight()) y = (int) (screenSize.getHeight() - 330);
		System.out.println(x + " - " + y);
		
		int numberOfTheDay = Integer.parseInt(panelHolder[row][column].getText());
		options = new OptionsFrame(x, y, numberOfTheDay, calendar);		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

}
