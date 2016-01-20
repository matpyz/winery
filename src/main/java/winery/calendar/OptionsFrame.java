package winery.calendar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Scrollable;
import javax.swing.border.Border;

import dbapi.Event;


public class OptionsFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	public Calendar calendar;
	private Utilities util;
	private JPanel actions;
	private JScrollPane scrollPane;
	
	private JButton add;
	private JButton remove;
	private JButton change;
	private JButton[] actionsHolder;
	
	private AddListener addListener;
	
		public OptionsFrame(int x, int y, int numberOfTheDay, Calendar calendar, Utilities util) {
			
			addListener = new AddListener(calendar, util);
			this.util = util;
			this.calendar = calendar;
			this.setTitle("Opcje");
			this.getContentPane().setLayout(null);
			this.setSize(400, 400);
			this.setLocation(x, y);
			this.actionsHolder = new JButton[24];
						
			actions = new JPanel();
			actions.setBackground(Color.WHITE);
			actions.setLayout(new BoxLayout(actions, BoxLayout.Y_AXIS));
			
			Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
			/*
			for (int i = 0; i < 24; i++) {
				if(i<10) {
					actionsHolder[i] = new JButton("0" + Integer.toString(i) + ":00");
				} else {
					actionsHolder[i] = new JButton(Integer.toString(i) + ":00");
				}
				actionsHolder[i].setMinimumSize(new Dimension(150, 30));
				actionsHolder[i].setMaximumSize(new Dimension(150, 30));
				actionsHolder[i].setBorder(border);
				actionsHolder[i].setVisible(true);
				actionsHolder[i].addActionListener(this);
				actionsHolder[i].setActionCommand(Integer.toString(i));
				actions.add(actionsHolder[i]);
				*/
				calendar.set(Calendar.DAY_OF_MONTH, numberOfTheDay);
				//Sprawdzenie, czy jest jakieś wydarzenie w tej godzinie
				calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
				calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
				calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
				Date startDate = new java.sql.Date(calendar.getTime().getTime());
				System.out.println(calendar.getTime());
				
				calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
				calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
				calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
				Date endDate = new java.sql.Date(calendar.getTime().getTime());
				
				// Przykladowe zastepcze wydarzenia
				ArrayList<Event> events = new ArrayList();
				calendar.set(Calendar.HOUR_OF_DAY, 12);
				Date startDate1 = new java.sql.Date(calendar.getTime().getTime());
				calendar.set(Calendar.HOUR_OF_DAY, 14);
				Date endDate1 = new java.sql.Date(calendar.getTime().getTime());
				Event event1 = new Event(1, "Spotkanie", "Opis", startDate, endDate, "Winiarnia", 2,
						3, "Spot-Win");
				events.add(event1);
				Event event2 = new Event(1, "Zbiory", "Opis", startDate, endDate, "Winiarnia", 2,
						3, "Spot-Win");
				events.add(event2);
				
				//ArrayList<Event> events = util.getAllDayEvents(startDate, endDate);
				int i = 0;
				for (Event event: events) {
					//actionsHolder[i].setBackground(Color.BLUE);
					//actionsHolder[i].setText(event.getName());
					actionsHolder[i] = new JButton(event.getStartDate().toString() + " - " + 
							event.getName());
					actionsHolder[i].setMinimumSize(new Dimension(350, 50));
					actionsHolder[i].setMaximumSize(new Dimension(350, 50));
					actionsHolder[i].setBorder(border);
					actionsHolder[i].setVisible(true);
					actionsHolder[i].addActionListener(this);
					actionsHolder[i].setActionCommand(Integer.toString(i));
					actions.add(actionsHolder[i]);
				}
			//}
			
			scrollPane = new JScrollPane(actions);
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scrollPane.setBounds(15, 15, 350, 300);
			//scrollPane.setWheelScrollingEnabled(true);
			getContentPane().add(scrollPane);
			
			//add(actions);
			
			add = new JButton("Dodaj");
			add.setBounds(260, 340, 120, 35);
			add.setVisible(true);
			add.addActionListener(addListener);
			/*
			remove = new JButton("Usuń");
			remove.setBounds(175, 85, 120, 35);
			remove.setVisible(true);
			remove.setActionCommand("remove");
			remove.addActionListener(this);
			
			change = new JButton("Zmień");
			change.setBounds(175, 145, 120, 35);
			change.setVisible(true);
			add.setActionCommand("add");
			add.addActionListener(this);*/
			
			//add(actions);
			getContentPane().add(add);
			//add(remove);
			//add(change);
			this.setVisible(true);
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			int command = Integer.parseInt(e.getActionCommand());
				//System.out.println("Otworzę okno z informacja z numerem: " + command);
				new InformationFrame(command, calendar);
		}
}
