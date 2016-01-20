package winery.config;

import javax.swing.JPanel;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
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
		setName("Witamy w konfiguracji programu Winnica.");
		
		JLabel lblTitle = new JLabel(getName());
		
		JLabel lblClickToContinue = new JLabel("Kliknij \"Dalej\", aby kontynuować.");
		
		JLabel lblInfo = new JLabel("Ten kreator przeprowadzi Cię przez proces wstępnej konfiguracji.");
		
		JButton btnNext = new JButton("Dalej");
		btnNext.addActionListener(listener);
		
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(WelcomePanel.class.getResourceAsStream("/logo2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JLabel lblLogo = new JLabel(new ImageIcon(myPicture));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblLogo)
						.addComponent(lblTitle)
						.addComponent(lblInfo)
						.addComponent(lblClickToContinue))
					.addContainerGap(126, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(383, Short.MAX_VALUE)
					.addComponent(btnNext)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblLogo)
					.addComponent(lblTitle)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblInfo)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblClickToContinue)
					.addPreferredGap(ComponentPlacement.RELATED, 181, Short.MAX_VALUE)
					.addComponent(btnNext)
					.addContainerGap())
		);
		setLayout(groupLayout);

	}
}
