package winery.calendar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

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
import javax.swing.JPanel;

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
	private JTextField nameText;
	private JTextArea descriptionText;
	private JTextField locationText;
	private JFormattedTextField startDateText;
	private JFormattedTextField startHourText;
	private JFormattedTextField endHourText;
	private JFormattedTextField endDateText;
	
	private JComboBox eventTypeComboBox;
	
	private JButton save;
	
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
		
		MaskFormatter formatter = new MaskFormatter("##-##-####");
		formatter.setPlaceholderCharacter('_');
		MaskFormatter formatter2 = new MaskFormatter("##:##");
		formatter.setPlaceholderCharacter('_');

		startDateText = new JFormattedTextField(formatter);
		
		JLabel lblNewLabel_1 = new JLabel("Data:");
		
		JLabel lblGodzina = new JLabel("Godzina:");
		
		startHourText = new JFormattedTextField(formatter2);
		
		JLabel lblKoniecWydarzenia = new JLabel("Koniec wydarzenia");
		lblKoniecWydarzenia.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panel = new JPanel();
		
		endHourText = new JFormattedTextField(formatter2);
		
		JLabel label_5 = new JLabel("Godzina:");
		
		JLabel label_6 = new JLabel("Data:");

		endDateText = new JFormattedTextField(formatter);
		
		save = new JButton("Zapisz");
		save.addActionListener(this);
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
								.addContainerGap()
								.addComponent(panel, GroupLayout.PREFERRED_SIZE, 535, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(37)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(lblGodzina)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(startHourText, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(lblNewLabel_1)
										.addGap(35)
										.addComponent(startDateText, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)))))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblKoniecWydarzenia, GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(42)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(label_5, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(endHourText, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(label_6, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
									.addGap(35)
									.addComponent(endDateText, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(231)
							.addComponent(save, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 249, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(startDateText, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblGodzina)
						.addComponent(startHourText, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(lblKoniecWydarzenia)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_6)
						.addComponent(endDateText, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(label_5)
						.addComponent(endHourText, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addGap(21)
					.addComponent(save)
					.addContainerGap(70, Short.MAX_VALUE))
		);
				
				JLabel label = new JLabel("Nazwa:");
		
				
				JLabel lblDodawanieNowegoZdarzenia = new JLabel("Dodawanie nowego zdarzenia");
				lblDodawanieNowegoZdarzenia.setHorizontalAlignment(SwingConstants.CENTER);
				
				JLabel label_1 = new JLabel("Opis:");
				
				nameText = new JTextField();
				nameText.setColumns(10);
				
				descriptionText = new JTextArea();
				
				JLabel label_2 = new JLabel("Miejsce:");
				
				locationText = new JTextField();
				locationText.setColumns(10);
				
				JLabel label_3 = new JLabel("Typ:");
				
				eventTypeComboBox = new JComboBox();
				/*
				 * TODO Dodać elementy do JComboBox
				 * EventType wszystkie w BD są dostępne pod metodą
				 * util.getEventTypes()
				 * Zwraca HashMap<String, Integer>
				 * JComboBox może byc isEditable - można wtedy dodawać nowe elemtny do listy
				 * W takim razie trzeba wywołać na DB i zupdate'ować w util.
				 */
				
				JLabel label_4 = new JLabel("Początek wydarzenia");
				label_4.setHorizontalAlignment(SwingConstants.CENTER);
				GroupLayout gl_panel = new GroupLayout(panel);
				gl_panel.setHorizontalGroup(
					gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
									.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
										.addContainerGap()
										.addComponent(lblDodawanieNowegoZdarzenia, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
										.addGap(24)
										.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
											.addGroup(gl_panel.createSequentialGroup()
												.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
													.addComponent(label)
													.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
													.addComponent(nameText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
													.addComponent(descriptionText, GroupLayout.PREFERRED_SIZE, 418, GroupLayout.PREFERRED_SIZE)))
											.addGroup(gl_panel.createSequentialGroup()
												.addGap(6)
												.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
													.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
													.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
													.addComponent(eventTypeComboBox, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
													.addComponent(locationText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))))
								.addGroup(gl_panel.createSequentialGroup()
									.addContainerGap()
									.addComponent(label_4, GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)))
							.addContainerGap())
				);
				gl_panel.setVerticalGroup(
					gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblDodawanieNowegoZdarzenia)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(label)
								.addComponent(nameText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(label_1)
								.addComponent(descriptionText, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(locationText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_2))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(label_3)
								.addComponent(eventTypeComboBox, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addComponent(label_4)
							.addGap(23))
				);
				panel.setLayout(gl_panel);
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
				util.addEvent(nameString, descriptionString, new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()), 
						locationString, 1);

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
