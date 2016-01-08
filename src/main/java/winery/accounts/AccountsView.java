package winery.accounts;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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

public class AccountsView extends View implements ActionListener {

	/**
	 * Numer wersji, nieistotna wartość domyślna.
	 */
	protected static final long serialVersionUID = 1L;

	private static int actionNumber = 0;

	JComboBox<String> cBox_accountList;

	private JTextField txt_name;
	private JTextField txt_surname;
	private JTextField txt_password;
	private JTextField txt_login;
	private JTextField txt_gmail;

	private JButton btn_add;
	private JButton btn_rmv;
	private JButton btn_edit;

	private JLabel lbl_name;
	private JLabel lbl_surname;
	private JLabel lbl_password;
	private JLabel lbl_login;
	private JLabel lbl_gmail;
	private JLabel lbl_result;

	AccountsController controller;
	AccountsModel model;

	public AccountsView(AccountsController controller) {
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
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);

		cBox_accountList = new JComboBox<String>();
		GridBagConstraints gbc_cBox_accountList = new GridBagConstraints();
		gbc_cBox_accountList.fill = GridBagConstraints.HORIZONTAL;
		gbc_cBox_accountList.insets = new Insets(0, 0, 5, 5);
		gbc_cBox_accountList.gridx = 0;
		gbc_cBox_accountList.gridy = 0;
		add(cBox_accountList, gbc_cBox_accountList);

		cBox_accountList.addActionListener(this);

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
				FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, }));

		lbl_name = new JLabel("Imię");
		panel_accountData.add(lbl_name, "2, 2, right, default");

		txt_name = new JTextField();
		txt_name.setEditable(false);
		panel_accountData.add(txt_name, "4, 2, fill, default");
		txt_name.setColumns(10);

		lbl_surname = new JLabel("Nazwisko");
		panel_accountData.add(lbl_surname, "2, 4, right, default");

		txt_surname = new JTextField();
		txt_surname.setEditable(false);
		panel_accountData.add(txt_surname, "4, 4, fill, default");
		txt_surname.setColumns(10);

		lbl_password = new JLabel("Hasło");
		panel_accountData.add(lbl_password, "2, 6, right, default");

		lbl_login = new JLabel("Login");
		panel_accountData.add(lbl_login, "2, 8, right, default");

		txt_password = new JTextField();
		txt_password.setEditable(false);
		panel_accountData.add(txt_password, "4, 6, fill, default");
		txt_password.setColumns(10);

		txt_login = new JTextField();
		txt_login.setEditable(false);
		panel_accountData.add(txt_login, "4, 8, fill, default");
		txt_login.setColumns(10);

		lbl_gmail = new JLabel("Gmail");
		panel_accountData.add(lbl_gmail, "2, 10, right, default");

		txt_gmail = new JTextField();
		txt_gmail.setEditable(false);
		panel_accountData.add(txt_gmail, "4, 10, fill, default");
		txt_gmail.setColumns(10);

		btn_add.setActionCommand("add");
		btn_rmv.setActionCommand("rmv");
		btn_edit.setActionCommand("edit");

		btn_add.addActionListener(this);
		btn_rmv.addActionListener(this);
		btn_edit.addActionListener(this);

		lbl_result = new JLabel(actionNumber + "");
		GridBagConstraints gbc_lbl_result = new GridBagConstraints();
		gbc_lbl_result.gridwidth = 4;
		gbc_lbl_result.fill = GridBagConstraints.HORIZONTAL;
		gbc_lbl_result.insets = new Insets(0, 0, 0, 5);
		gbc_lbl_result.gridx = 0;
		gbc_lbl_result.gridy = 2;
		add(lbl_result, gbc_lbl_result);
	}

	@Override
	public void update(Model model) {
		AccountsModel accountsModel = (AccountsModel)model;
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
		
				cBox_accountList.removeAllItems();
				for (String account : accountsModel.getAccountList())
					cBox_accountList.addItem(account);
				
				txt_name.setText(accountsModel.getAccountData().get(0));
				txt_surname.setText(accountsModel.getAccountData().get(1));
				txt_gmail.setText(accountsModel.getAccountData().get(2));
				txt_login.setText(accountsModel.getAccountData().get(3));
					
				lbl_result.setText(++actionNumber + ": " + controller.response);		
			
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cBox_accountList) {
				controller.getAccountData(((String) cBox_accountList
					.getSelectedItem()).split("[()]")[1]);
		}
		
		switch (e.getActionCommand()) {
		case "add":
			addAccount();
			break;
		case "rmv":
			rmvAccount();
			break;
		case "edit":
			editAccount();
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

	private void editAccount() {
		btn_edit.setText("Zatwierdź");
		btn_edit.setActionCommand("end_edit");

		btn_add.setEnabled(false);
		btn_rmv.setEnabled(false);
		cBox_accountList.setEnabled(false);

		txt_name.setEditable(true);
		txt_surname.setEditable(true);
		txt_password.setEditable(true);
		txt_gmail.setEditable(true);
	}

	private void endEdit() {
		btn_edit.setText("Edytuj");
		btn_edit.setActionCommand("edit");

		btn_add.setEnabled(true);
		btn_rmv.setEnabled(true);
		cBox_accountList.setEnabled(true);

		txt_name.setEditable(false);
		txt_surname.setEditable(false);
		txt_password.setEditable(false);
		txt_gmail.setEditable(false);

		List<String> accountData = new ArrayList<>();
		accountData.add(txt_login.getText());
		accountData.add(txt_name.getText());
		accountData.add(txt_surname.getText());
		accountData.add(txt_password.getText());
		accountData.add(txt_gmail.getText());

		controller.modifyAccount(accountData);
	}

	private void rmvAccount() {
		String accountId = txt_name.getText() + txt_surname.getText();

		controller.deleteAccount(accountId);
	}

	private void addAccount() {
		btn_add.setText("Zatwierdź");
		btn_add.setActionCommand("end_add");

		btn_edit.setEnabled(false);
		btn_rmv.setEnabled(false);
		cBox_accountList.setEnabled(false);

		txt_name.setText("");
		txt_surname.setText("");
		txt_password.setText("");
		txt_gmail.setText("");

		txt_name.setEditable(true);
		txt_surname.setEditable(true);
		txt_password.setEditable(true);
		txt_gmail.setEditable(true);
	}

	private void endAdd() {
		btn_add.setText("Dodaj");
		btn_add.setActionCommand("add");

		btn_edit.setEnabled(true);
		btn_rmv.setEnabled(true);
		cBox_accountList.setEnabled(true);

		txt_name.setEditable(false);
		txt_surname.setEditable(false);
		txt_password.setEditable(false);
		txt_gmail.setEditable(false);

		List<String> accountData = new ArrayList<>();
		accountData.add(txt_name.getText());
		accountData.add(txt_surname.getText());
		accountData.add(txt_password.getText());
		accountData.add(txt_gmail.getText());

		controller.newAccount(accountData);
	}

}
