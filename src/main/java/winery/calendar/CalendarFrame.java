import javax.swing.JFrame;


public class CalendarFrame extends JFrame {
	
	public CalendarView calendarView = new CalendarView();

	public CalendarFrame() {
		JFrame calendarFrame = new JFrame("Kalendarz");
		//calendarFrame.setVisible(true);
		calendarFrame.setSize(600, 600);
		calendarFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		calendarFrame.setLayout(null);
		calendarFrame.add(calendarView);
		calendarFrame.setVisible(true);
	}
	
	
	public static void main(String[] args) {
		CalendarFrame frame = new CalendarFrame();
	}
}
