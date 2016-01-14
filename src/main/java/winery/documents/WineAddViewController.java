package winery.documents;


import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import winery.model.Model;
import winery.view.Controller;
import winery.view.View;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;

import dbapi.DBManager;
import dbapi.Wine;

public class WineAddViewController extends View implements Controller, ItemListener, ActionListener {
	
	JPanel cards;
	private WineAddModel model;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField botField;
	private JTextField yearField;
	private JTextField colorField;
	private JTextField kindField;
	private JTextField nameField;
	private JTextField costField;
	private JLabel errorLabel;

	public WineAddViewController() {
		System.out.println("Tworze gui");
		createGui();

		model = new WineAddModel();
		model.register(this);
		
		//model.firstUse();
	}
	
	@Override
	public View getView() {
		return this;
	}

	@Override
	public String getTitle() {
		return "Dodaj wino";
	}

	@Override
	protected void update(Model model) {
		System.out.println("Update");
		WineAddModel model1 = (WineAddModel) model;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//int amountNow = model1.getAmount();
				//statusLabel.setText("Obecnie w bazie: " + amountNow);
			
			}
		});
		return;

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)e.getItem());
	}
	
	private void createGui() {

		JLabel lblNazwa = new JLabel("Nazwa:");
		
		JLabel lblSzczepy = new JLabel("Szczepy:");
		
		JLabel lblBarwa = new JLabel("Barwa:");
		
		JLabel lblRocznik = new JLabel("Rocznik:");
		
		JLabel lblLiczbaButelek = new JLabel("Liczba butelek:");
		
		JLabel lblInformacjeO = new JLabel("Informacje o nowym winie");
		
		botField = new JTextField();
		botField.setColumns(10);
		
		yearField = new JTextField();
		yearField.setColumns(10);
		
		colorField = new JTextField();
		colorField.setColumns(10);
		
		kindField = new JTextField();
		kindField.setColumns(10);
		
		nameField = new JTextField();
		nameField.setColumns(10);
		
		JLabel lblKosztyProdukcji = new JLabel("Koszty produkcji:");
		
		costField = new JTextField();
		costField.setColumns(10);
		
		JButton saveBtn = new JButton("Zapisz");
		saveBtn.setActionCommand("save");
		
		saveBtn.addActionListener(this);
		
		errorLabel = new JLabel("");
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(215, Short.MAX_VALUE)
					.addComponent(lblInformacjeO)
					.addGap(202))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(269, Short.MAX_VALUE)
					.addComponent(saveBtn)
					.addGap(249))
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGap(46)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(errorLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblKosztyProdukcji, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(costField, 391, 391, 391))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblLiczbaButelek)
								.addComponent(lblRocznik)
								.addComponent(lblBarwa)
								.addComponent(lblSzczepy)
								.addComponent(lblNazwa))
							.addGap(28)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(nameField)
								.addComponent(kindField)
								.addComponent(colorField)
								.addComponent(yearField)
								.addComponent(botField, GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE))))
					.addContainerGap(29, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblInformacjeO)
					.addGap(36)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNazwa)
						.addComponent(nameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSzczepy)
						.addComponent(kindField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblBarwa)
						.addComponent(colorField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRocznik)
						.addComponent(yearField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLiczbaButelek)
						.addComponent(botField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(67)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblKosztyProdukcji)
						.addComponent(costField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(saveBtn)
					.addGap(40)
					.addComponent(errorLabel)
					.addContainerGap(36, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
				
		switch (e.getActionCommand()) {
		case "save":
			saveWine(true);
			break;
		default:
			break;
		}
	}

	/**
	 * Metoda dodająca wina do BD.

	 */
	private void saveWine(boolean mode) {
		String nameCont = nameField.getText();
		String kindCont = kindField.getText();
		String colorCont = colorField.getText();
		String yearCont = yearField.getText();
		String botCont = botField.getText();
		String costCont = costField.getText();
		
		if ( yearCont.matches("\\d\\d\\d\\d") && botCont.matches("\\d+") && costCont.matches("\\d+")) {
			int yearInt = Integer.parseInt(yearCont);
			int costInt = Integer.parseInt(costCont);
			int botInt = Integer.parseInt(botCont);
			Wine wine = DBManager.addWine(nameCont, kindCont, colorCont, botInt, 0, 0, costInt, yearInt, 0);
			if (wine == null) {
				printError("Nie udało się dodać wina.");
				return;
			}
			else {
				errorLabel.setForeground(Color.GREEN);
				errorLabel.setText("Poprawnie zapisano wino " + nameCont + ".");
			}
		}
		else {
			if (!yearCont.matches("\\d\\d\\d\\d")) printError("Ciąg " + yearCont + " nie jest poprawnym rokiem.");
			else if (!botCont.matches("\\d+")) printError("Ciąg " + botCont + " nie jest liczbą.");
			else if (!costCont.matches("\\d+")) printError("Ciąg " + costCont + " nie jest liczbą.");
		}
		nameField.setText("");
		kindField.setText("");
		colorField.setText("");
		yearField.setText("");
		botField.setText("");
		costField.setText("");

	}
	
	private void printError(String err) {
		errorLabel.setForeground(Color.RED);
		errorLabel.setText(err);
	}
}
