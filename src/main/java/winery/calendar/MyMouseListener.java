package winery.calendar;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MyMouseListener implements MouseListener {

	private JPanel information;
	private JButton[][] panelHolder;
	private int row;
	private int column;

	private Timer timer;
	private ShowInformation showInformation;

	public MyMouseListener(JPanel information, JButton[][] panelHolder, int row, int column) {
		this.information = information;
		this.panelHolder = panelHolder;
		this.row = row;
		this.column = column;

		// showInformation = new ShowInformation(row, column);
		// timer = new Timer();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Wciśnięto przycisk: " + row + " x " + column);
		information.setVisible(true);

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		// Timer timer = new Timer();
		// timer.schedule(showInformation, 700);
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		/*
		 * showInformation.cancel(); timer.cancel();
		 */
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
