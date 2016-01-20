package winery.config;

import javax.swing.JPanel;

import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

public class WelcomePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 * @param configWizardFrame 
	 */
	public WelcomePanel(ActionListener listener) {
		setName("Witamy w konfiguracji programu Winnica!");
		
		JLabel lblTitle = new JLabel(getName());
		
		JLabel lblClickToContinue = new JLabel("Kliknij \"Dalej\", aby kontynuować.");
		
		JLabel lblInfo = new JLabel("Ten kreator przeprowadzi Cię przez proces wstępnej konfiguracji.");
		
		JButton btnNext = new JButton("Dalej");
		btnNext.addActionListener(listener);
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTitle)
						.addComponent(lblInfo)
						.addComponent(lblClickToContinue))
					.addContainerGap(242, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(351, Short.MAX_VALUE)
					.addComponent(btnNext)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblTitle)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblInfo)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblClickToContinue)
					.addPreferredGap(ComponentPlacement.RELATED, 201, Short.MAX_VALUE)
					.addComponent(btnNext)
					.addContainerGap())
		);
		setLayout(groupLayout);

	}
}
