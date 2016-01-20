package winery.config;

import javax.swing.JPanel;

import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;

public class ThankYouPanel extends JPanel {

	/**
	 * Create the panel.
	 * @param listener 
	 */
	public ThankYouPanel(ActionListener listener) {
		setName("Ukończono kreator!");
		
		JButton btnPrev = new JButton("Wstecz");
		btnPrev.addActionListener(listener);
		
		JButton btnEnd = new JButton("Koniec");
		btnEnd.addActionListener(listener);
		
		JLabel lblTitle = new JLabel(getName());
		
		JLabel lblClickToFinish = new JLabel("Kliknij \"Koniec\" aby przejść do ekranu logowania.");

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnPrev)
							.addPreferredGap(ComponentPlacement.RELATED, 300, Short.MAX_VALUE)
							.addComponent(btnEnd))
						.addComponent(lblTitle)
						.addComponent(lblClickToFinish))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblTitle)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblClickToFinish)
					.addPreferredGap(ComponentPlacement.RELATED, 221, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnEnd)
						.addComponent(btnPrev))
					.addContainerGap())
		);
		setLayout(groupLayout);

	}

}
