//package winery.calendar;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import winery.model.Model;
import winery.view.Controller;
import winery.view.View;

public class CalendarView extends JPanel implements ActionListener/*, Controller*/ {

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

	@SuppressWarnings("deprecation")
	public CalendarView() {
		this.removeAll();
		this.setBounds(0, 25, 600, 550);
		this.setLayout(null);
		
		calendar = new GregorianCalendar().getInstance();
			
		
		
		createGUI();
	}

	private void createGUI() {
		this.removeAll();
		this.repaint();
		this.revalidate();

		previousButton = new JButton("Poprzedni");
		previousButton.setBounds(25, 25, 120, 35);

		nextButton = new JButton("Następny");
		nextButton.setBounds(455, 25, 120, 35);

		currentMonth = calendar.get(Calendar.MONTH);
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
				Utilities u = new Utilities();
				panelHolder[row][column] = new ColorButton(Integer.toString(k));
				calendar.set(Calendar.DAY_OF_MONTH, k);
				calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
				calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
				calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
				Date startDate = calendar.getTime();
				System.out.println(startDate);
				
				calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
				calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
				calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
				Date endDate = calendar.getTime();
				
				
				/*ArrayList<Event> events = u.getAllDayEvents(startDate, endDate);
				for (Event event: events) {
					panelHolder[row][column].addEvent();
				}*/
				k++;
			}
			MyMouseListener myMouseListener = new MyMouseListener(days, panelHolder, row, column, calendar);
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
		
		panelHolder[0][5].addEvent(); // <---- Dodałem, żeby pokazywało, że koloruje

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
		currentMonth -= 1;
		if (currentMonth < 0) {
			currentMonth += 12;
		}
		calendar.set(Calendar.MONTH, currentMonth);
		if (currentMonth == 11) calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
		createGUI();
	}

	private void nextMonth() {
		currentMonth = (currentMonth + 1) % 12;
		calendar.set(Calendar.MONTH, currentMonth);
		if (currentMonth == 0) calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
		createGUI();
	}

}
