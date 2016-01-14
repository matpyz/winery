package winery.calendar;

import java.util.TimerTask;


public class ShowInformation extends TimerTask {
	private int row;
	private int column;
	
	public ShowInformation(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public void run() {
	       System.out.println("Informacja przycisku: " + row + " x " + column); 
	}
}
