package winery.calendar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JFormattedTextField;

//import winery.calendar.String;


public class AddFrame extends JFrame implements ActionListener {

	private Utilities util;
	int eventTypeId;
	
	public Calendar calendar;
	
	private String nameString;
	private String locationString;
	private String startDateString;
	private String endDateString;
	private String startHourString;
	private String endHourString;
	private String descriptionString;
	
	private JButton save;
	private JTextField nameText;
	private JTextField locationText;
	private JFormattedTextField startDateText;
	private JFormattedTextField endDateText;
	private JFormattedTextField startHourText;
	private JFormattedTextField endHourText;
	private JTextArea descriptionText;
	
	public AddFrame(Calendar calendar, Utilities util) {
		this.calendar = calendar;
		this.util = util;
		this.setTitle("Dodaj nowe wydarzenie");
		
		try {
			createGUI();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private void createGUI() throws ParseException {
		//	this.getContentPane().setLayout(null);
			this.setBounds(800, 400, 550, 560);

		
		JLabel lblDodawanieNowegoZdarzenia = new JLabel("Dodawanie nowego zdarzenia");
		lblDodawanieNowegoZdarzenia.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNaz = new JLabel("Nazwa:");
		
		JLabel lblNewLabel = new JLabel("Opis:");
		
		nameText = new JTextField();
		nameText.setColumns(10);
		
		descriptionText = new JTextArea();
		
		MaskFormatter formatter = new MaskFormatter("##-##-####");
		formatter.setPlaceholderCharacter('_');
		MaskFormatter formatter2 = new MaskFormatter("##:##");
		formatter.setPlaceholderCharacter('_');

		startDateText = new JFormattedTextField(formatter);
		
		JLabel lblPocztekWydarzenia = new JLabel("Początek wydarzenia");
		lblPocztekWydarzenia.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNewLabel_1 = new JLabel("Data:");
		
		JLabel lblGodzina = new JLabel("Godzina:");
		
		endDateText = new JFormattedTextField(formatter);
		
		startHourText = new JFormattedTextField(formatter2);
		
		JLabel lblKoniecWydarzenia = new JLabel("Koniec wydarzenia");
		lblKoniecWydarzenia.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblMiejsce = new JLabel("Miejsce:");
		
		locationText = new JTextField();
		locationText.setColumns(10);
		
		JLabel lblTyp = new JLabel("Typ:");
		
		JLabel lblData = new JLabel("Data:");
		
		JLabel lblGodzina_1 = new JLabel("Godzina:");
		
		endHourText = new JFormattedTextField(formatter2);
		
		save = new JButton("Zapisz");
		
		save.addActionListener(this);
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblDodawanieNowegoZdarzenia, GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNaz)
								.addComponent(lblNewLabel))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(descriptionText, GroupLayout.PREFERRED_SIZE, 418, GroupLayout.PREFERRED_SIZE)
								.addComponent(nameText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblMiejsce)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(locationText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblTyp))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addGap(8)
							.addComponent(lblPocztekWydarzenia, GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblGodzina)
								.addComponent(lblNewLabel_1))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(startDateText, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
								.addComponent(startHourText, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblKoniecWydarzenia, GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblGodzina_1)
								.addComponent(lblData))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(endDateText, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
								.addComponent(endHourText, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(237, Short.MAX_VALUE)
					.addComponent(save)
					.addGap(229))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblDodawanieNowegoZdarzenia)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNaz)
						.addComponent(nameText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel)
						.addComponent(descriptionText, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE))
					.addGap(13)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMiejsce)
						.addComponent(locationText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblTyp)
					.addGap(18)
					.addComponent(lblPocztekWydarzenia)
					.addGap(23)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(startDateText, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblGodzina)
						.addComponent(startHourText, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(lblKoniecWydarzenia)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(23)
							.addComponent(lblData))
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(endDateText, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)))
					.addGap(17)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblGodzina_1)
						.addComponent(endHourText, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(save)
					.addContainerGap(71, Short.MAX_VALUE))
		);
		getContentPane().setLayout(groupLayout);
		
		
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		nameString = nameText.getText();
		locationString = locationText.getText();
		descriptionString = descriptionText.getText();
		
		startDateString = startDateText.getText();
		endDateString = endDateText.getText();
		startHourString = startHourText.getText();
		endHourString = endHourText.getText();
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		String startDateInString = startDateString + " " + startHourString;	
		String endDateInString = endDateString + " " + endHourString;
			
		Date startDate;
		Date endDate;
		try {
				startDate = formatter.parse(startDateInString);
				endDate = formatter.parse(endDateInString);
				System.out.println(endDate);
				System.out.println(formatter.format(endDate));

		} catch (ParseException ex) {
			//ex.printStackTrace();
			System.out.println("Podano niekompletne lub błędne dane");
		}
		
		/*util.addEvent(nameString, descriptionString, startDate, endDate, 
				locationString, int eventTypeId);*/
		
		/*
		 * TODO: Dalsza walidacja danych
		 * TODO: konwersja początku i końca na Date
		 * FIXME: możliwe że za pomocą innego obiektu Calendar ???
		 */		
		
		/*
		 * TODO: wywołanie 
		 * util.addEvent(String name, String description, Date startDate, Date endDate, 
				String location, int eventTypeId)
		 */
		

	}
}