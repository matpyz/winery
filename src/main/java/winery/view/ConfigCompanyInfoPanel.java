package winery.view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.event.ActionListener;

public class ConfigCompanyInfoPanel extends JPanel {
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
	
	private String getCompanyName() {
		return tfCompanyName.getText();
	}

	public String getPersonalData() {
		String companyName = tfCompanyName.getText();
		String cityName = tfCity.getText();
		String postalCode = tfPostalCode.getText();
		String streetName = tfStreet.getText();
		String buildingNo = tfBuildingNo.getText();
		String phoneNo = tfPhoneNo.getText();
		String nip = tfNip.getText();
		String regon = tfRegon.getText();
		String evidenceNo = tfEvidenceNo.getText();
		
		boolean incorrectData = companyName.equals("") || cityName.equals("") ||
				 postalCode.equals("") || streetName.equals("") || 
				 buildingNo.equals("") || phoneNo.equals("") ||
				 nip.equals("") || regon.equals("") || evidenceNo.equals("");
		if (incorrectData) {
			System.out.println("Niepoprawne dane");
			//TODO: wyskakujące okienko
		}
		else {
			String s = "CompanyName " + companyName + "\nCity " + cityName +
					"\nPostalCode " + postalCode + "\nStreet: " + streetName + 
					"\nBuildingNo " + buildingNo + "\nPhoneNo " + phoneNo + 
					"\nNip " + nip + "\nRegon " + regon + 
					"\nEvidenceNo " + evidenceNo;
			return s;
		}
		return null;
	}
	/**
	 * Create the panel.
	 * @param listener 
	 */
	public ConfigCompanyInfoPanel(ActionListener listener) {
		
		JLabel lblCompanyName = new JLabel("Nazwa firmy:");
		
		JLabel lblCity = new JLabel("Miejscowość:");
		
		JLabel lblPostalCode = new JLabel("Kod pocztowy:");
		
		JLabel lblStreet = new JLabel("Ulica:");
		
		JLabel lblBuildingNo = new JLabel("Nr lokalu:");
		
		JLabel lblPhoneNo = new JLabel("Nr telefonu:");
		
		JLabel lblNip = new JLabel("NIP:");
		
		JLabel lblRegon = new JLabel("REGON:");
		
		JLabel lblEvidenceNo = new JLabel("Nr wpisu do ewidencji:");
		
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
								.addComponent(lblCompanyName)
								.addComponent(lblCity)
								.addComponent(lblPostalCode)
								.addComponent(lblStreet)
								.addComponent(lblBuildingNo)
								.addComponent(lblPhoneNo)
								.addComponent(lblNip)
								.addComponent(lblRegon)
								.addComponent(lblEvidenceNo))
							.addGap(18, 18, Short.MAX_VALUE)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(tfRegon, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(tfNip, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(tfPhoneNo, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(tfBuildingNo, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(tfStreet, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(tfPostalCode, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(tfCity, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(tfCompanyName, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(tfEvidenceNo, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 305, GroupLayout.PREFERRED_SIZE))
							.addContainerGap())
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(btnNext)
							.addGap(10))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCompanyName)
						.addComponent(tfCompanyName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCity)
						.addComponent(tfCity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPostalCode)
						.addComponent(tfPostalCode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblStreet)
						.addComponent(tfStreet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblBuildingNo)
						.addComponent(tfBuildingNo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPhoneNo)
						.addComponent(tfPhoneNo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNip)
						.addComponent(tfNip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRegon)
						.addComponent(tfRegon, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEvidenceNo)
						.addComponent(tfEvidenceNo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
					.addComponent(btnNext)
					.addContainerGap())
		);
		groupLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {tfCompanyName, tfCity, tfPostalCode, tfStreet, tfBuildingNo, tfPhoneNo, tfNip, tfRegon, tfEvidenceNo});
		setLayout(groupLayout);

	}
}
