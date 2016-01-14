//package winery.calendar;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;


public class OptionsFrame extends JFrame implements ActionListener {
	public Calendar calendar;
	private JPanel actions;
	private JScrollPane scrollPane;
	
	private JButton add;
	private JButton remove;
	private JButton change;
	private JButton[] actionsHolder;
	
	private AddListener addListener;
	
		public OptionsFrame(int x, int y, int numberOfTheDay, Calendar calendar) {
			
			addListener = new AddListener(calendar);
			this.calendar = calendar;
			this.setTitle("Opcje");
			this.getContentPane().setLayout(null);
			this.setSize(300, 300);
			this.setLocation(x, y);
			this.actionsHolder = new JButton[24];
						
			actions = new JPanel();
			actions.setBounds(15, 25, 150, 250);
			actions.setBackground(Color.WHITE);
			actions.setLayout(null);
			
			Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
			
			for (int i = 0; i < 24; i++) {
				actionsHolder[i] = new JButton(Integer.toString(i) + ":00");
				actionsHolder[i].setBounds(0, i*35, 150, 35);
				actionsHolder[i].setBorder(border);
				actionsHolder[i].setVisible(true);
				actionsHolder[i].addActionListener(this);
				actionsHolder[i].setActionCommand(Integer.toString(i));
				actions.add(actionsHolder[i]);
				
				//Sprawdzenie, czy jest jakieś wydarzenie w tej godzinie
				calendar.set(Calendar.HOUR_OF_DAY, i);
				calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
				calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
				Date startDate = calendar.getTime();
				System.out.println(startDate);
				
				calendar.set(Calendar.HOUR_OF_DAY, i+1);
				calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
				calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
				Date endDate = calendar.getTime();
				
				Utilities u = new Utilities();
				ArrayList<Event> events = u.getAllDayEvents(startDate, endDate);
				for (Event event: events) {
					actionsHolder[i].setBackground(Color.BLUE);
					actionsHolder[i].setText(event.getName());
				}
			}
			
			/*
			scrollPane = new JScrollPane(actions);
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
			scrollPane.setBounds(15, 25, 150, 250);
			//scrollPane.setVisible(true);
			actions.setVisible(true);
			add(scrollPane);
			
			*/
			add(actions);
			
			
			add = new JButton("Dodaj");
			add.setBounds(175, 25, 120, 35);
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
			
			add(actions);
			add(add);
			//add(remove);
			//add(change);
			this.setVisible(true);
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			int command = Integer.parseInt(e.getActionCommand());
				//System.out.println("Otworzę okno z informacja z numerem: " + command);
				InformationFrame informationFrame = new InformationFrame(command, calendar);
		}
}
