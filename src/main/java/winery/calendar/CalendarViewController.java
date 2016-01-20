package winery.calendar;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import dbapi.Event;
import winery.model.Model;
import winery.view.Controller;
import winery.view.View;

public class CalendarViewController extends View implements ActionListener, Controller {

	private static final long serialVersionUID = 1L;
	private JButton previousButton;
	private JButton nextButton;
	private JButton addButton;

	private JLabel monthNameLabel;
	private JPanel dayNamesPanel;

	private ColorButton[][] panelHolder;
	private JPanel days;

	private String[] months = { "Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec", "Lipiec", "Sierpień",
			"Wrzesień", "Październik", "Listopad", "Grudzień" };
	
	private Calendar calendar;
	private int currentMonth;
	private Utilities util;

	@SuppressWarnings("deprecation")
	public CalendarViewController() {
		this.removeAll();
		this.setBounds(0, 25, 600, 550);
		this.setLayout(null);
		
		calendar = new GregorianCalendar().getInstance();
			
		util = new Utilities();
		
		createGUI();
	}

	private void createGUI() {
		this.removeAll();
		this.repaint();
		this.revalidate();
		
		currentMonth = calendar.get(Calendar.MONTH);

		previousButton = new JButton("Poprzedni");
		previousButton.setBounds(25, 25, 120, 35);

		nextButton = new JButton("Następny");
		nextButton.setBounds(455, 25, 120, 35);

		monthNameLabel = new JLabel(months[currentMonth]);
		monthNameLabel.setBackground(Color.WHITE);
		monthNameLabel.setBounds(275, 25, 200, 35);

		dayNamesPanel = new JPanel();
		dayNamesPanel.setBounds(25, 75, 550, 40);
		dayNamesPanel.setLayout(new GridLayout(1, 7));
		dayNamesPanel.setBackground(Color.GRAY);

		dayNamesPanel.add(new JLabel("Pon", SwingConstants.CENTER));
		dayNamesPanel.add(new JLabel("Wt", SwingConstants.CENTER));
		dayNamesPanel.add(new JLabel("Śr", SwingConstants.CENTER));
		dayNamesPanel.add(new JLabel("Czw", SwingConstants.CENTER));
		dayNamesPanel.add(new JLabel("Pt", SwingConstants.CENTER));
		dayNamesPanel.add(new JLabel("Sob", SwingConstants.CENTER));
		dayNamesPanel.add(new JLabel("Ndz", SwingConstants.CENTER));

		panelHolder = new ColorButton[6][7];		

		days = new JPanel();
		days.setLayout(new GridLayout(6, 7));
		days.setBounds(25, 115, 550, 340);
		days.setBackground(Color.lightGray);
		
		//*****GregorianCalendar***************
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int firstDay = calendar.get(Calendar.DAY_OF_WEEK);
		firstDay = firstDay - 2;
		if(firstDay == -1) firstDay = 6;
		int lastDay = firstDay + calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		//*************************************
		
		int k = 1; // Licznik aktualnie wypisanych dni w miesiacu
		
		for (int i = 0; i < 42; i++) {
			int row = i / 7;
			int column = i % 7;
			if (i < firstDay || i >= lastDay) {
				panelHolder[row][column] = new ColorButton(" ");
				panelHolder[row][column].setVisible(false);
				panelHolder[row][column].setEnabled(false);
			}
			else {
				panelHolder[row][column] = new ColorButton(Integer.toString(k));
				calendar.set(Calendar.DAY_OF_MONTH, k);
				calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
				calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
				calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
				Date startDate = new java.sql.Date(calendar.getTime().getTime());
				System.out.println(startDate);
				
				calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
				calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
				calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
				Date endDate = new java.sql.Date(calendar.getTime().getTime());
				//System.out.println(endDate);
				
				ArrayList<Event> events = util.getAllDayEvents(startDate, endDate);
				panelHolder[row][column].addEvent(events);
				k++;
			}
			MyMouseListener myMouseListener = new MyMouseListener(days, panelHolder, row, column, calendar, util);
			panelHolder[row][column].addMouseListener(myMouseListener);
			days.add(panelHolder[row][column]);

		}
		
		previousButton.setActionCommand("previous");
		nextButton.setActionCommand("next");

		previousButton.addActionListener(this);
		nextButton.addActionListener(this);

		add(previousButton);
		add(monthNameLabel);
		add(nextButton);
		add(dayNamesPanel);
		add(days);
	}

	@Override
	protected void update(Model model) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "previous":
			previousMonth();
			break;
		case "next":
			nextMonth();
			break;
		default:
			break;
		}
	}

	@Override
	public View getView() {
		return this;
	}

	@Override
	public String getTitle() {
		return "Kalendarz";
	}

	public void previousMonth() {
		calendar.set(Calendar.DATE, 0);
		calendar.add(Calendar.DATE, -1);
		createGUI();
	}

	private void nextMonth() {
		/**
		 * Program działa tak, że po wygenerowaniu kalendarza obiekt calendar
		 * jest ustawiony na ostatni dzień miesiąca. Dodanie 1 lokuje nas w
		 * kolejnym miesiącu (też ewentualnie roku).
		 */
		calendar.add(Calendar.DATE, 1);
		createGUI();
	}

	public static String getID() {
		return "calendar";
	}
}
