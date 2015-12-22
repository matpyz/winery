package winery.documents;

import java.awt.CardLayout;
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

public class ExciseTaxViewController extends View implements Controller, ItemListener {
	
	JPanel cards;
	private JTextField addTextField;
	private JTextField subTextField;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExciseTaxViewController() {
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
		
		JButton btnSub = new JButton("Odejmij");
		
		JPanel statusPanel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(87)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnSub)
						.addComponent(btnAdd)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(addRow, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(subRow, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(statusPanel, GroupLayout.PREFERRED_SIZE, 270, GroupLayout.PREFERRED_SIZE)))
					.addGap(91))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(statusPanel, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addGap(26)
					.addComponent(addRow, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnAdd)
					.addGap(52)
					.addComponent(subRow, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSub)
					.addGap(70))
		);
		setLayout(groupLayout);

		/*
		 * Po utworzeniu okna program ma łączyć się z BD i pobrać aktualną liczbę pasków, wyświetlić ją w statusPanel
		 * statusPanel.setText("");
		 */
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
		return;

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)e.getItem());
	}
}
