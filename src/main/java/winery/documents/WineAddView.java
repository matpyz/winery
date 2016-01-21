package winery.documents;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import winery.model.Model;
import winery.view.View;

public class WineAddView extends View implements ActionListener {

	/**
	 * Numer wersji, nieistotna wartość domyślna.
	 */
	protected static final long serialVersionUID = 1L;

	private JComboBox<String> cBox_wineList;

	private JTextField txt_name;
	private JTextField txt_year;
	private JTextField txt_sold;
	private JTextField txt_produced;
	private JTextField txt_forSale;
	private JTextField txt_cost;
	private JTextField txt_price;
	private JTextField txt_colour;
	private JTextField txt_type;
	
	private LinkedList<JTextField> txtList;

	private JButton btn_add;
	private JButton btn_rmv;
	private JButton btn_edit;

	private LinkedList<JButton> btnList;

	private JLabel lbl_name;
	private JLabel lbl_year;
	private JLabel lbl_sold;
	private JLabel lbl_produced;
	private JLabel lbl_forSale;
	private JLabel lbl_cost;
	private JLabel lbl_price;
	private JLabel lbl_colour;
	private JLabel lbl_type;
	private JLabel lbl_result;
	
	WineAddController controller;

	public WineAddView (WineAddController controller) {
		this.controller = controller;

		createGUI();
	}

	/**
	 * Wielka litania do Pana naszego Swing'a.
	 */
	private void createGUI() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		cBox_wineList = new JComboBox<String>();
		GridBagConstraints gbc_cBox_accountList = new GridBagConstraints();
		gbc_cBox_accountList.fill = GridBagConstraints.HORIZONTAL;
		gbc_cBox_accountList.insets = new Insets(0, 0, 5, 5);
		gbc_cBox_accountList.gridx = 0;
		gbc_cBox_accountList.gridy = 0;
		add(cBox_wineList, gbc_cBox_accountList);

		cBox_wineList.addActionListener(this);
		cBox_wineList.setActionCommand("select");

		btn_edit = new JButton("Edytuj");
		GridBagConstraints gbc_btn_edit = new GridBagConstraints();
		gbc_btn_edit.insets = new Insets(0, 0, 5, 5);
		gbc_btn_edit.gridx = 1;
		gbc_btn_edit.gridy = 0;
		add(btn_edit, gbc_btn_edit);

		btn_rmv = new JButton("Usuń");
		GridBagConstraints gbc_btn_rmv = new GridBagConstraints();
		gbc_btn_rmv.insets = new Insets(0, 0, 5, 5);
		gbc_btn_rmv.gridx = 2;
		gbc_btn_rmv.gridy = 0;
		add(btn_rmv, gbc_btn_rmv);

		btn_add = new JButton("Dodaj");
		GridBagConstraints gbc_btn_add = new GridBagConstraints();
		gbc_btn_add.insets = new Insets(0, 0, 5, 0);
		gbc_btn_add.gridx = 3;
		gbc_btn_add.gridy = 0;
		add(btn_add, gbc_btn_add);

		JPanel panel_accountData = new JPanel();
		GridBagConstraints gbc_panel_accountData = new GridBagConstraints();
		gbc_panel_accountData.insets = new Insets(0, 0, 5, 0);
		gbc_panel_accountData.gridwidth = 4;
		gbc_panel_accountData.fill = GridBagConstraints.BOTH;
		gbc_panel_accountData.gridx = 0;
		gbc_panel_accountData.gridy = 1;
		add(panel_accountData, gbc_panel_accountData);

		panel_accountData.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));

		lbl_name = new JLabel("Nazwa");
		panel_accountData.add(lbl_name, "2, 2, right, default");

		txt_name = new JTextField();
		txt_name.setEditable(false);
		panel_accountData.add(txt_name, "4, 2, fill, default");
		txt_name.setColumns(10);

		lbl_year = new JLabel("Rocznik");
		panel_accountData.add(lbl_year, "2, 4, right, default");

		txt_year = new JTextField();
		txt_year.setEditable(false);
		panel_accountData.add(txt_year, "4, 4, fill, default");
		txt_year.setColumns(10);

		lbl_produced = new JLabel("Wyprodukowano");
		panel_accountData.add(lbl_produced, "2, 6, right, default");

		txt_produced = new JTextField();
		txt_produced.setEditable(false);
		panel_accountData.add(txt_produced, "4, 6, fill, default");
		txt_produced.setColumns(10);

		lbl_sold = new JLabel("Sprzedano");
		panel_accountData.add(lbl_sold, "2, 8, right, default");

		txt_sold = new JTextField();
		txt_sold.setEditable(false);
		panel_accountData.add(txt_sold, "4, 8, fill, default");
		txt_sold.setColumns(10);

		lbl_forSale = new JLabel("Na sprzedaż");
		panel_accountData.add(lbl_forSale, "2, 10, right, default");

		txt_forSale = new JTextField();
		txt_forSale.setEditable(false);
		panel_accountData.add(txt_forSale, "4, 10, fill, default");
		txt_forSale.setColumns(10);

		lbl_cost = new JLabel("Koszt");
		panel_accountData.add(lbl_cost, "2, 12, right, default");

		txt_cost = new JTextField();
		txt_cost.setEditable(false);
		txt_cost.setColumns(10);
		panel_accountData.add(txt_cost, "4, 12, fill, default");

		lbl_price = new JLabel("Cena");
		panel_accountData.add(lbl_price, "2, 14, right, default");

		txt_price = new JTextField();
		txt_price.setEditable(false);
		txt_price.setColumns(10);
		panel_accountData.add(txt_price, "4, 14, fill, default");

		btn_add.setActionCommand("add");
		btn_rmv.setActionCommand("rmv");
		btn_edit.setActionCommand("edit");

		btn_add.addActionListener(this);
		btn_rmv.addActionListener(this);
		btn_edit.addActionListener(this);

		lbl_result = new JLabel("");
		GridBagConstraints gbc_lbl_result = new GridBagConstraints();
		gbc_lbl_result.gridwidth = 4;
		gbc_lbl_result.fill = GridBagConstraints.HORIZONTAL;
		gbc_lbl_result.insets = new Insets(0, 0, 0, 5);
		gbc_lbl_result.gridx = 0;
		gbc_lbl_result.gridy = 2;
		add(lbl_result, gbc_lbl_result);
		
		lbl_colour = new JLabel("Kolor");
		panel_accountData.add(lbl_colour, "2, 16, right, default");
		
		txt_colour = new JTextField();
		txt_colour.setEditable(false);
		txt_colour.setColumns(10);
		panel_accountData.add(txt_colour, "4, 16, fill, default");
		
		lbl_type = new JLabel("Typ");
		panel_accountData.add(lbl_type, "2, 18, right, default");
		
		txt_type = new JTextField();
		txt_type.setEditable(false);
		txt_type.setColumns(10);
		panel_accountData.add(txt_type, "4, 18, fill, default");

		txtList = new LinkedList<>();
		txtList.add(txt_name);
		txtList.add(txt_year);
		txtList.add(txt_produced);
		txtList.add(txt_sold);
		txtList.add(txt_forSale);
		txtList.add(txt_cost);
		txtList.add(txt_price);
		txtList.add(txt_colour);
		txtList.add(txt_type);
		
		btnList = new LinkedList<>();
		btnList.add(btn_edit);
		btnList.add(btn_rmv);
		btnList.add(btn_add);
	}

	/* (non-Javadoc)
	 * @see winery.view.View#update(winery.model.Model)
	 */
	@Override
	public synchronized void update(Model model) {
		WineAddModel wineAddModel = (WineAddModel) model;

		cBox_wineList.setActionCommand("removing");
		cBox_wineList.removeAllItems();
		cBox_wineList.setActionCommand("select");

		for (String wine : wineAddModel.getWineList())
			// Tak wiem, ale cóż
			if (((DefaultComboBoxModel<String>) cBox_wineList.getModel()).getIndexOf(wine) == -1)
				cBox_wineList.addItem(wine);

		for (int i = 0; i < wineAddModel.getWineData().size(); i++)
			txtList.get(i).setText(wineAddModel.getWineData().get(i));

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				lbl_result.setText(controller.response);
			}
		});

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "select":
			controller.getWineData((String) cBox_wineList.getSelectedItem());
			break;
		case "add":
			addWine();
			break;
		case "rmv":
			rmvWine();
			break;
		case "edit":
			editWine();
			break;
		case "end_add":
			endAdd();
			break;
		case "end_edit":
			endEdit();
			break;
		default:
			break;
		}
	}

	private void editWine() {
		btn_edit.setText("Zatwierdź");
		btn_edit.setActionCommand("end_edit");

		btn_add.setEnabled(false);
		btn_rmv.setEnabled(false);
		cBox_wineList.setEnabled(false);

		for (JTextField txt : txtList) {
			txt.setEditable(true);
		}
	}

	private void endEdit() {
		btn_edit.setText("Edytuj");
		btn_edit.setActionCommand("edit");

		btn_add.setEnabled(true);
		btn_rmv.setEnabled(true);
		cBox_wineList.setEnabled(true);

		for (JTextField txt : txtList) {
			txt.setEditable(false);
		}

		String name = txt_name.getText();
		int year = new Integer(txt_year.getText());
		int produced = new Integer(txt_produced.getText());
		int sold = new Integer(txt_sold.getText());
		int forSale = new Integer(txt_forSale.getText());
		int cost = new Integer(txt_cost.getText());
		int price = new Integer(txt_price.getText());
		String colour = txt_colour.getText();
		String type = txt_type.getText();

		controller.modifyWine(name, type, colour, produced, sold, price, cost, year, forSale);
	}

	private void rmvWine() {
		String name = txt_name.getText();
		String year = txt_year.getText();

		controller.deleteWine(name+" "+year);
	}

	private void addWine() {
		btn_add.setText("Zatwierdź");
		btn_add.setActionCommand("end_add");

		btn_edit.setEnabled(false);
		btn_rmv.setEnabled(false);
		cBox_wineList.setEnabled(false);

		for (JTextField txt : txtList) {
			if(txt == txt_forSale)
				txt.setText("0");
			else
				txt.setText("");
		}

		for (JTextField txt : txtList) {
			if(txt != txt_forSale)
				txt.setEditable(true);
		}

	}

	private void endAdd() {
		btn_add.setText("Dodaj");
		btn_add.setActionCommand("add");

		btn_edit.setEnabled(true);
		btn_rmv.setEnabled(true);
		cBox_wineList.setEnabled(true);

		for (JTextField txt : txtList) {
			txt.setEditable(false);
		}

		String name = txt_name.getText();
		int year = new Integer(txt_year.getText());
		int produced = new Integer(txt_produced.getText());
		int sold = new Integer(txt_sold.getText());
		int forSale = new Integer(txt_forSale.getText());
		int cost = new Integer(txt_price.getText());
		int price = new Integer(txt_price.getText());
		String colour = txt_colour.getText();
		String type = txt_type.getText();

		controller.newWine(name, type, colour, produced, sold, price, cost, year, forSale);
	}

}