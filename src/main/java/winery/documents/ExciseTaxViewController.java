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

public class ExciseTaxViewController extends View implements Controller, ItemListener, ActionListener {
	
	JPanel cards;
	private JTextField addTextField;
	private JTextField subTextField;
	private JLabel statusLabel;
	private JLabel errorLabel;
	private ExciseTaxModel model;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExciseTaxViewController() {
		createGui();

		/*
		 * Po utworzeniu okna program ma łączyć się z BD i pobrać aktualną liczbę pasków, wyświetlić ją w statusPanel
		 * statusPanel.setText("");
		 */
		
		model = new ExciseTaxModel();
		model.register(this);
		model.firstUse();
	}
	
	@Override
	public View getView() {
		return this;
	}

	@Override
	public String getTitle() {
		return "Paski akcyzowe";
	}

	@Override
	protected void update(Model model) {
		ExciseTaxModel model1 = (ExciseTaxModel) model;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				int amountNow = model1.getAmount();
				statusLabel.setText("Obecnie w bazie: " + amountNow);
			
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
		statusLabel = new JLabel("Obecnie w bazie: ");
		JLabel addJLabel1 = new JLabel("Dodaj    ");

		addTextField = new JTextField();
		addTextField.setColumns(5);

		JLabel addJLabel2 = new JLabel("  pasków akcyzowych ");
		
		JPanel addRow = new JPanel();
		addRow.setLayout(new BoxLayout(addRow, BoxLayout.X_AXIS));
		addRow.setAlignmentX(CENTER_ALIGNMENT);
		
		addRow.add(addJLabel1);
		addRow.add(addTextField);
		addRow.add(addJLabel2);
		
		JLabel subJLabel1 = new JLabel("Odejmij ");

		subTextField = new JTextField();
		subTextField.setColumns(5);

		JLabel subJLabel2 = new JLabel("  pasków akcyzowych ");
		
		JPanel subRow = new JPanel();
		subRow.setLayout(new BoxLayout(subRow, BoxLayout.X_AXIS));
		subRow.setAlignmentX(CENTER_ALIGNMENT);
		
		subRow.add(subJLabel1);
		subRow.add(subTextField);
		subRow.add(subJLabel2);
		
		JButton btnAdd = new JButton("Dodaj");
		btnAdd.setActionCommand("add");
		
		btnAdd.addActionListener(this);
		
		JButton btnSub = new JButton("Odejmij");
		btnSub.setActionCommand("sub");

		btnSub.addActionListener(this);
		
		JPanel statusPanel = new JPanel();
		statusPanel.add(statusLabel);
		
		JPanel errorPanel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGap(86)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(statusPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
						.addComponent(errorPanel, GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(31)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(subRow, 0, 0, Short.MAX_VALUE)
								.addComponent(addRow, GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE))
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(31)
									.addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(18)
									.addComponent(btnSub)))
							.addGap(24)))
					.addGap(91))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(statusPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(57)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(addRow, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnAdd))
					.addGap(71)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(subRow, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSub, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(errorPanel, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addGap(83))
		);
		
		errorLabel = new JLabel("");
		errorPanel.add(errorLabel);
		setLayout(groupLayout);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
				
		switch (e.getActionCommand()) {
		case "add":
			modifyAmount(true);
			break;
		case "sub":
			modifyAmount(false);
			break;
		default:
			break;
		}
	}

	/**
	 * Metoda wykonująca modyfikację liczby pasków akcyzowych w bazie danych.
	 * @param mode true - dodawanie, false - odejmowanie
	 */
	private void modifyAmount(boolean mode) {
		int exciseNow = model.getAmount();
		int newExcise = 0;
		String content;
		if (mode) content = addTextField.getText();
		else content = subTextField.getText();
		if ( content.matches("\\d+") ) {
			int contentInt = Integer.parseInt(content);
			if (mode) { //DODAJ
				newExcise = exciseNow + contentInt;
			}
			else { //ODEJMIJ
				newExcise = exciseNow - contentInt;
				if (newExcise < 0) {
					printError("Błąd - nie można mieć ujemnej liczby pasków.");
					return;
				}
			}
			if (DBManager.updateDataForExciseById(model.getId(), "", newExcise) ) {
				model.setAmount(newExcise);
				if (mode) addTextField.setText("");
				else subTextField.setText("");
				errorLabel.setText("");
			}
			else {
				printError("Nie udało się.");
			}
			
		}
		else {
			printError("Ciąg " + content + " nie jest liczbą");
			System.out.println("Ciąg " + content + " nie jest liczbą");
		}
	}
	
	private void printError(String err) {
		errorLabel.setForeground(Color.RED);
		errorLabel.setText(err);
	}
	
	public static String getID() {
		return "excisetax";
	}
}
