package winery.calendar;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;


public class OptionsFrame extends JFrame {

	private JPanel actions;
	private JScrollPane scrollPane;
	
	private JButton add;
	private JButton remove;
	private JButton change;
	private JLabel[] actionsHolder;
	
		public OptionsFrame(int x, int y) {
			this.setTitle("Opcje");
			this.setLayout(null);
			this.setSize(300, 300);
			this.setLocation(x, y);
			this.actionsHolder = new JLabel[24];
			
			actions = new JPanel();
			actions.setBounds(15, 25, 150, 250);
			actions.setBackground(Color.WHITE);
			actions.setLayout(null);
			//actions.setLayout(new BoxLayout(actions, BoxLayout.PAGE_AXIS));
			
			Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
			
			for (int i = 0; i < 24; i++) {
				actionsHolder[i] = new JLabel(Integer.toString(i) + ":00");
				actionsHolder[i].setBounds(0, i*35, actions.getSize().width, 35);
				actionsHolder[i].setBorder(border);
				actionsHolder[i].setVisible(true);
				actions.add(actionsHolder[i]);
			}
			/*
			scrollPane = new JScrollPane(actions, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,  
					   ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setBounds(15, 25, 150, 250);
			//actions.setAutoscrolls(true);
			scrollPane.setVisible(true);
			add(scrollPane);
			*/
			
			add(actions);
			
			
			add = new JButton("Dodaj");
			add.setBounds(175, 25, 120, 35);
			add.setVisible(true);
			
			remove = new JButton("Usuń");
			remove.setBounds(175, 85, 120, 35);
			remove.setVisible(true);
			
			change = new JButton("Zmień");
			change.setBounds(175, 140, 120, 35);
			change.setVisible(true);
			
			add(actions);
			add(add);
			add(remove);
			add(change);
			this.setVisible(true);
			
		}
}

