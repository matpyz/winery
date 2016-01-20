package winery.config;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

public class CompanyInfoPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField tfCompanyName;
	private JTextField tfCity;
	private JTextField tfPostalCode;
	private JTextField tfStreet;
	private JTextField tfBuildingNo;
	private JTextField tfPhoneNo;
	private JTextField tfNip;
	private JTextField tfRegon;
	private JTextField tfEvidenceNo;

	public String getCompanyData() {
		String companyName = tfCompanyName.getText();
		String cityName = tfCity.getText();
		String postalCode = tfPostalCode.getText();
		String streetName = tfStreet.getText();
		String buildingNo = tfBuildingNo.getText();
		String phoneNo = tfPhoneNo.getText();
		String nip = tfNip.getText();
		String regon = tfRegon.getText();
		String evidenceNo = tfEvidenceNo.getText();

		boolean incorrectData = companyName.equals("") || cityName.equals("") || postalCode.equals("")
				|| streetName.equals("") || buildingNo.equals("") || phoneNo.equals("") || nip.equals("")
				|| regon.equals("") || evidenceNo.equals("");
		if (incorrectData) {
			JOptionPane.showMessageDialog((Component) null, "Wprowadzono błędne lub niekompletne dane!", "Błąd",
					JOptionPane.ERROR_MESSAGE);
		} else {
			String s = "CompanyName " + companyName + "\nCity " + cityName + "\nPostalCode " + postalCode + "\nStreet "
					+ streetName + "\nBuildingNo " + buildingNo + "\nPhoneNo " + phoneNo + "\nNip " + nip + "\nRegon "
					+ regon + "\nEvidenceNo " + evidenceNo;
			return s;
		}
		return null;
	}

	/**
	 * Create the panel.
	 * 
	 * @param listener
	 */
	public CompanyInfoPanel(ActionListener listener) {
		setName("Wprowadź dane firmy.");

		JLabel lblTitle = new JLabel(getName());

		JLabel lblCompanyName = new JLabel("Nazwa firmy:");

		JLabel lblCity = new JLabel("Miejscowość:");

		JLabel lblPostalCode = new JLabel("Kod pocztowy:");

		JLabel lblStreet = new JLabel("Ulica:");

		JLabel lblBuildingNo = new JLabel("Nr lokalu:");

		JLabel lblPhoneNo = new JLabel("Nr telefonu:");

		JLabel lblNip = new JLabel("NIP:");

		JLabel lblRegon = new JLabel("REGON:");

		JLabel lblEvidenceNo = new JLabel("Nr wpisu do ewidencji:");

		JButton btnPrev = new JButton("Wstecz");
		btnPrev.addActionListener(listener);

		JButton btnNext = new JButton("Dalej");
		btnNext.addActionListener(listener);

		tfCompanyName = new JTextField();
		tfCompanyName.setColumns(10);

		tfCity = new JTextField();
		tfCity.setColumns(10);

		tfPostalCode = new JTextField();
		tfPostalCode.setColumns(10);

		tfStreet = new JTextField();
		tfStreet.setColumns(10);

		tfBuildingNo = new JTextField();
		tfBuildingNo.setColumns(10);

		tfPhoneNo = new JTextField();
		tfPhoneNo.setColumns(10);

		tfNip = new JTextField();
		tfNip.setColumns(10);

		tfRegon = new JTextField();
		tfRegon.setColumns(10);

		tfEvidenceNo = new JTextField();
		tfEvidenceNo.setColumns(10);

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblEvidenceNo)
								.addComponent(lblRegon)
								.addComponent(lblNip)
								.addComponent(lblPhoneNo)
								.addComponent(lblBuildingNo)
								.addComponent(lblStreet)
								.addComponent(lblPostalCode)
								.addComponent(lblCity)
								.addComponent(lblCompanyName)
								.addComponent(btnPrev))
							.addPreferredGap(ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(tfRegon, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(tfNip, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(tfPhoneNo, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(tfBuildingNo, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(tfStreet, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(tfPostalCode, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(tfCity, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(tfEvidenceNo, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 305, GroupLayout.PREFERRED_SIZE))
									.addContainerGap())
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(btnNext)
									.addGap(10))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(tfCompanyName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addContainerGap())))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblTitle)
							.addContainerGap(331, Short.MAX_VALUE))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblTitle)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCompanyName)
						.addComponent(tfCompanyName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(tfCity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCity))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(tfPostalCode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPostalCode))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(tfStreet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblStreet))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(tfBuildingNo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblBuildingNo))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(tfPhoneNo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPhoneNo))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(tfNip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNip))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(tfRegon, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblRegon))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(tfEvidenceNo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblEvidenceNo))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNext)
						.addComponent(btnPrev))
					.addContainerGap())
		);
		groupLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {tfCompanyName, tfCity, tfPostalCode, tfStreet, tfBuildingNo, tfPhoneNo, tfNip, tfRegon, tfEvidenceNo});
		setLayout(groupLayout);

	}

	String getTfCompanyName() {
		return tfCompanyName.getText();
	}

	String getTfCity() {
		return tfCity.getText();
	}

	String getTfPostalCode() {
		return tfPostalCode.getText();
	}

	String getTfStreet() {
		return tfStreet.getText();
	}

	String getTfBuildingNo() {
		return tfBuildingNo.getText();
	}

	String getTfPhoneNo() {
		return tfPhoneNo.getText();
	}

	String getTfNip() {
		return tfNip.getText();
	}

	String getTfRegon() {
		return tfRegon.getText();
	}

	String getTfEvidenceNo() {
		return tfEvidenceNo.getText();
	}
}
