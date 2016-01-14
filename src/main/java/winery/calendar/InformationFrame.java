import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class InformationFrame extends JFrame implements ActionListener {
		int i;
	
		String name;
		String description;
		Date startDate;
		Date endDate;
		String location;
		int eventTypeId;
		
		private TextField nameTF;
		private TextField descriptionTF;
		private TextField locationTF;
		private TextField eventTypeIdTF;
		
		private JButton change;
		private JButton remove;
		
		public InformationFrame(int i, Calendar calendar) {
			this.i = i;
			
			
			this.setTitle("Dodaj nowe wydarzenie");
			this.getContentPane().setLayout(null);
			this.setBounds(800, 200, 400, 500);
			
			JLabel label1 = new JLabel("Nazwa:");
			label1.setBounds(0,0,120,35);
			nameTF = new TextField();
			nameTF.setBounds(0, 40, 120, 35);
			
			JLabel label2 = new JLabel("Opis:");
			label2.setBounds(0,80,120,35);
			descriptionTF = new TextField();
			descriptionTF.setBounds(0, 120, 200, 35);
			
			JLabel label3 = new JLabel("Data początkowa:");
			label3.setBounds(0,160,200,35);
			
			/*JComboBox<Integer> days = new JComboBox<Integer>();
			for (int i = 1; i <= 31; i++) {
				days.addItem(i);
			}
			days.setBounds(0,  200,  120,  35);*/
			JLabel label4 = new JLabel("Dzień:");
			label4.setBounds(0,200,50,35);
			TextField dateDay = new TextField();
			dateDay.setBounds(60, 200, 50, 35);
			JLabel label5 = new JLabel("Godzina:");
			label5.setBounds(120,200,80,35);
			TextField dateHour = new TextField();
			dateHour.setBounds(200, 200, 50, 35);
			
			
			JLabel label6 = new JLabel("Data końcowa:");
			label6.setBounds(0,240,200,35);
			JLabel label7 = new JLabel("Dzień:");
			label7.setBounds(0,280,50,35);
			TextField dateDay2 = new TextField();
			dateDay2.setBounds(60, 280, 50, 35);
			JLabel label8 = new JLabel("Godzina:");
			label8.setBounds(120,280,80,35);
			TextField dateHour2 = new TextField();
			dateHour2.setBounds(200, 280, 50, 35);
			
			JLabel label9 = new JLabel("Miejsce:");
			label9.setBounds(0,320,100,35);
			TextField locationTF = new TextField();
			locationTF.setBounds(0, 360, 120, 35);
			
			JLabel label10 = new JLabel("Typ:");
			label10.setBounds(0,400,100,35);
			TextField eventTypeIdTF = new TextField();
			eventTypeIdTF.setBounds(0, 435, 120, 35);
			
			change = new JButton("Zmień");
			change.setBounds(250, 390, 120, 35);
			change.setActionCommand("change");
			change.addActionListener(this);
			
			remove = new JButton("Usuń");
			remove.setBounds(250, 435, 120, 35);
			remove.setActionCommand("remove");
			remove.addActionListener(this);
			
			
			add(label1);
			add(nameTF);
			add(label2);
			add(descriptionTF);
			add(label3);
			add(label4);
			add(dateDay);
			add(label5);
			add(dateHour);
			add(label6);
			add(label7);
			add(dateDay2);
			add(label8);
			add(dateHour2);
			add(label9);
			add(locationTF);
			add(label10);
			add(eventTypeIdTF);
			add(change);
			add(remove);
			this.setVisible(true);
			
	}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
	
}
