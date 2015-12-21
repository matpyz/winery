package winery.documents;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import winery.model.Model;
import winery.view.Controller;
import winery.view.View;

public class ExciseTaxViewController extends View implements Controller, ItemListener {
	
	JPanel cards;
	private JTextField addTextField;
	private JTextField subTextField;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExciseTaxViewController() {
		JLabel addJLabel1 = new JLabel("Dodaj ");

		addTextField = new JTextField();
		addTextField.setColumns(5);

		JLabel addJLabel2 = new JLabel("pasków akcyzowych ");
		
		JPanel addRow = new JPanel();
		addRow.setLayout(new BoxLayout(addRow, BoxLayout.X_AXIS));
		addRow.setAlignmentX(CENTER_ALIGNMENT);
		
		addRow.add(addJLabel1);
		addRow.add(addTextField);
		addRow.add(addJLabel2);
		
		JLabel subJLabel1 = new JLabel("Odejmij ");

		subTextField = new JTextField();
		subTextField.setColumns(5);

		JLabel subJLabel2 = new JLabel("pasków akcyzowych ");
		
		JPanel subRow = new JPanel();
		subRow.setLayout(new BoxLayout(subRow, BoxLayout.X_AXIS));
		subRow.setAlignmentX(CENTER_ALIGNMENT);
		
		subRow.add(subJLabel1);
		subRow.add(subTextField);
		subRow.add(subJLabel2);
		
		String string1 = "Dodaj";
		String string2 = "Odejmij";
		
		JPanel comboBoxPane = new JPanel(); //use FlowLayout
		String comboBoxItems[] = { string1, string2 };
		JComboBox cb = new JComboBox(comboBoxItems);
		cb.setEditable(false);
		cb.addItemListener(this);
		comboBoxPane.add(cb);
		
        cards = new JPanel(new CardLayout());
        cards.add(addRow, string1);
        cards.add(subRow, string2);
		
        //this.setLayout(new BorderLayout());
        this.add(comboBoxPane, BorderLayout.PAGE_START);
        this.add(cards, BorderLayout.CENTER);

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
