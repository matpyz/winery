package winery.calendar;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import winery.accounts.AccountsController;
import winery.model.Model;
import winery.view.View;


public class CalendarView extends View implements ActionListener {
	
	private JButton previousButton;
	private JButton nextButton;
	
	private JLabel monthNameLabel;
	private JPanel dayNamesPanel;
	
	private JButton[][] panelHolder;
	private JPanel information;
	private JPanel days;
	
	private String[] months = {"Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec", "Lipiec", "Sierpień", "Wrzesień", "Październik", "Listopad", "Grudzien"};

	AccountsController controller;
	
	
	public CalendarView(AccountsController controller) {
		this.controller = controller;

		createGUI();
		
		
	}
	
	private void createGUI() {		
		previousButton = new JButton("Poprzedni");
		previousButton.setBounds(50, 40, 120, 35);
		
		nextButton = new JButton("Następny");
		nextButton.setBounds(630, 40, 120, 35);
		
		monthNameLabel = new JLabel("Miesiąc");
		monthNameLabel.setBackground(Color.WHITE);
		monthNameLabel.setBounds(380, 40, 200, 35);
			
		dayNamesPanel = new JPanel();
		dayNamesPanel.setBounds(25, 100, 750, 40);
		dayNamesPanel.setLayout(new GridLayout (1, 7));
		dayNamesPanel.setBackground(Color.GRAY);
		
		
		dayNamesPanel.add(new JLabel("Pon", SwingConstants.CENTER));
		dayNamesPanel.add(new JLabel("Wt", SwingConstants.CENTER));
		dayNamesPanel.add(new JLabel("Śr", SwingConstants.CENTER));
		dayNamesPanel.add(new JLabel("Czw", SwingConstants.CENTER));
		dayNamesPanel.add(new JLabel("Pt", SwingConstants.CENTER));
		dayNamesPanel.add(new JLabel("Sob", SwingConstants.CENTER));
		dayNamesPanel.add(new JLabel("Ndz", SwingConstants.CENTER));
		
		panelHolder = new JButton[6][7]; 
		//MyMouseListener myMouseListener = new MyMouseListener(panelHolder);
		
		
		information = new JPanel();
		//information.setVisible(false);
		information.setSize(300, 200);
		information.setBackground(Color.BLACK);
		
		add(information);
		
		days = new JPanel();
		days.setLayout(new GridLayout(6,7));
		days.setBounds(25, 140, 750, 400);
		days.setBackground(Color.lightGray);
		for (int i = 0; i < 42; i++) {
			int row = i/7;
			int column = i%7;
			panelHolder[row][column] = new JButton(Integer.toString(i));
			MyMouseListener myMouseListener = new MyMouseListener(days, panelHolder, row, column);
			panelHolder[row][column].addMouseListener(myMouseListener);
			days.add(panelHolder[row][column]);
		}
		
		previousButton.setActionCommand("previous");
		nextButton.setActionCommand("next");
		
		previousButton.addActionListener(this);
		nextButton.addActionListener(this);
		
		add(previousButton);
		add(nextButton);
		add(monthNameLabel);
		add(dayNamesPanel);
		add(days);
		//add(windowPanel);
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

	public void previousMonth() {
		// TODO Auto-generated method stub
	}
	
	private void nextMonth() {
		// TODO Auto-generated method stub
		
	}

}
