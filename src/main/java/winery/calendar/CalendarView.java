package winery.calendar;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import winery.model.Model;
import winery.view.Controller;
import winery.view.View;

public class CalendarView extends View implements ActionListener, Controller {

	private JButton previousButton;
	private JButton nextButton;
	private JButton addButton;

	private JLabel monthNameLabel;
	private JPanel dayNamesPanel;

	private JButton[][] panelHolder;
	private JPanel information;
	private JPanel days;

	private Calendar gc = GregorianCalendar.getInstance();
	private String[] months = { "Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec", "Lipiec", "Sierpień",
			"Wrzesień", "Październik", "Listopad", "Grudzień" };
	/*
	 * Aby wykonać kalendarz warto wykorzystać metody zawarte w klasie
	 * "GregorianCalendar", dla przykładu przy tworzeniu widoku pobierany jest
	 * obecny miesiąc na podstawie danych lokalnych maszyny, na której został
	 * uruchomiony program.
	 * 
	 * Dwa, chyba powinien istnieć model, który przechowywałby dane takie
	 * właśnie jak obecny miesiąc itd.
	 */
	private int currentMonth = gc.get(Calendar.MONTH);

	public CalendarView() {
		createGUI();
	}

	private void createGUI() {
		// TODO Ogarnąć układ strony
		previousButton = new JButton("Poprzedni");
		previousButton.setBounds(50, 40, 120, 35);

		nextButton = new JButton("Następny");
		nextButton.setBounds(630, 40, 120, 35);

		addButton = new JButton("Dodaj");
		addButton.setBounds(630, 1030, 100, 35);

		monthNameLabel = new JLabel(months[currentMonth]);
		monthNameLabel.setBackground(Color.WHITE);
		monthNameLabel.setBounds(380, 40, 200, 35);

		dayNamesPanel = new JPanel();
		dayNamesPanel.setBounds(25, 100, 750, 40);
		dayNamesPanel.setLayout(new GridLayout(1, 7));
		dayNamesPanel.setBackground(Color.GRAY);

		dayNamesPanel.add(new JLabel("Pon", SwingConstants.CENTER));
		dayNamesPanel.add(new JLabel("Wt", SwingConstants.CENTER));
		dayNamesPanel.add(new JLabel("Śr", SwingConstants.CENTER));
		dayNamesPanel.add(new JLabel("Czw", SwingConstants.CENTER));
		dayNamesPanel.add(new JLabel("Pt", SwingConstants.CENTER));
		dayNamesPanel.add(new JLabel("Sob", SwingConstants.CENTER));
		dayNamesPanel.add(new JLabel("Ndz", SwingConstants.CENTER));

		panelHolder = new JButton[6][7];
		// MyMouseListener myMouseListener = new MyMouseListener(panelHolder);

		information = new JPanel();
		// information.setVisible(false);
		information.setSize(300, 200);
		information.setBackground(Color.BLACK);

		add(information);

		days = new JPanel();
		days.setLayout(new GridLayout(6, 7));
		days.setBounds(25, 140, 750, 400);
		days.setBackground(Color.lightGray);
		for (int i = 0; i < 42; i++) {
			int row = i / 7;
			int column = i % 7;
			panelHolder[row][column] = new JButton(Integer.toString(i));
			MyMouseListener myMouseListener = new MyMouseListener(days, panelHolder, row, column);
			panelHolder[row][column].addMouseListener(myMouseListener);
			days.add(panelHolder[row][column]);
			/*
			 * Tutaj z kolei powinniśmy wykorzystać podobną sztuczkę, a
			 * mianowicie wyszukać czym jest pierwszy dzień miesiąca (pon, wt,
			 * itp.) i na tej podstawie deaktywować wszystkie zbędne przyciski.
			 * Przykładowe użycie GregorianCalendar:
			 * 
			 * gc.set(Calendar.DAY_OF_MONTH, 0);
			 * gc.get(GregorianCalendar.DAY_OF_WEEK); UWAGA! Niedziela jest
			 * zerem, podobnie jak pierwszy dzień miesiąca!
			 * 
			 */

		}

		previousButton.setActionCommand("previous");
		nextButton.setActionCommand("next");
		addButton.setActionCommand("addevent");

		previousButton.addActionListener(this);
		nextButton.addActionListener(this);
		addButton.addActionListener(this);

		add(previousButton);
		add(monthNameLabel);
		add(nextButton);
		add(dayNamesPanel);
		add(days);
		add(addButton);
		// add(windowPanel);
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
		case "addevent":
			addEvent();
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
		monthNameLabel.setName((months[currentMonth]));
		// TODO Aktualizacja wyświetlenia
	}

	private void nextMonth() {
		currentMonth = (currentMonth + 1) % 12;
		monthNameLabel.setName((months[currentMonth]));
		// TODO Aktualizacja wyświetlenia
	}

	private void addEvent() {
		/*
		 * TODO Wyświetl okienko z danymi, wykonaj dodanie eventu User u =
		 * DBManager.signIn(login, hasło); int u_id = u.getId();
		 * if(DBManager.addEvent((int)u_id, (String)nazwa_wydarzenia,
		 * (String)opis, (Date)start, (Date)koniec, (String)lokalizacja,
		 * (int)id_wydarzenia) == false) zwróc błąd
		 * 
		 * Po drodze trzeba sprawdzić czy użytkownik ma prawo do tworzenia
		 * eventu.
		 * 
		 * Aby pobrać id_wydarzenia: Użyć metody
		 * (Event)DBManager.getEventById((int)event_id, (int)u_id) jeżeli znamy
		 * id lub (HashMap<Integer,Event>)getEvents() i przeszukać
		 */

	}

}
