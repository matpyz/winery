package winery.calendar;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

//import winery.calendar.String;


public class AddFrame extends JFrame implements ActionListener {
	String name;
	String description;
	Date startDate;
	Date endDate;
	String location;
	int eventTypeId;
	
	public Calendar calendar;
	
	private TextField nameTF;
	private TextField descriptionTF;
	private TextField locationTF;
	private TextField eventTypeIdTF;
	private TextField dateHour;
	private TextField dateHour2;
	
	private JButton save;
	
	public AddFrame(Calendar calendar) {
		this.calendar = calendar;
		
		this.setTitle("Dodaj nowe wydarzenie");
		this.getContentPane().setLayout(null);
		this.setBounds(800, 200, 400, 515);
		
		JLabel label1 = new JLabel("Nazwa:");
		label1.setBounds(10,-1,120,35);
		nameTF = new TextField();
		nameTF.setBounds(10, 40, 120, 35);
		
		JLabel label2 = new JLabel("Opis:");
		label2.setBounds(10,81,120,35);
		descriptionTF = new TextField();
		descriptionTF.setBounds(10, 122, 200, 35);
		
		JLabel label3 = new JLabel("Data początkowa:");
		label3.setBounds(10,154,200,35);
		
		/*JComboBox<Integer> days = new JComboBox<Integer>();
		for (int i = 1; i <= 31; i++) {
			days.addItem(i);
		}
		days.setBounds(0,  200,  120,  35);*/
		JLabel label4 = new JLabel("Dzień:");
		label4.setBounds(10,200,50,35);
		TextField dateDay = new TextField();
		dateDay.setBounds(60, 200, 70, 35);
		JLabel label5 = new JLabel("Godzina:");
		label5.setBounds(134,200,60,35);
		TextField dateHour = new TextField();
		dateHour.setBounds(200, 200, 50, 35);
		
		
		JLabel label6 = new JLabel("Data końcowa:");
		label6.setBounds(10,234,200,35);
		JLabel label7 = new JLabel("Dzień:");
		label7.setBounds(10,280,50,35);
		TextField dateDay2 = new TextField();
		dateDay2.setBounds(60, 280, 70, 35);
		JLabel label8 = new JLabel("Godzina:");
		label8.setBounds(134,280,60,35);
		TextField dateHour2 = new TextField();
		dateHour2.setBounds(200, 280, 50, 35);
		
		JLabel label9 = new JLabel("Miejsce:");
		label9.setBounds(10,319,100,35);
		TextField locationTF = new TextField();
		locationTF.setBounds(10, 359, 120, 35);
		
		JLabel label10 = new JLabel("Typ:");
		label10.setBounds(10,394,100,35);
		TextField eventTypeIdTF = new TextField();
		eventTypeIdTF.setBounds(10, 435, 120, 35);
		
		save = new JButton("Zapisz");
		save.setBounds(250, 435, 120, 35);
		save.addActionListener(this);
		
		getContentPane().add(label1);
		getContentPane().add(nameTF);
		getContentPane().add(label2);
		getContentPane().add(descriptionTF);
		getContentPane().add(label3);
		getContentPane().add(label4);
		getContentPane().add(dateDay);
		getContentPane().add(label5);
		getContentPane().add(dateHour);
		getContentPane().add(label6);
		getContentPane().add(label7);
		getContentPane().add(dateDay2);
		getContentPane().add(label8);
		getContentPane().add(dateHour2);
		getContentPane().add(label9);
		getContentPane().add(locationTF);
		getContentPane().add(label10);
		getContentPane().add(eventTypeIdTF);
		getContentPane().add(save);
		this.setVisible(true);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(dateHour.getText()));
		Date startDate = calendar.getTime();
		
		//u.addEvent(String name, String description, Date startDate, Date endDate, 
			//	String location, int eventTypeId)
	}

}
