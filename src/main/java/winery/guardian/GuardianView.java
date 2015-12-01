package winery.guardian;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GuardianView extends JFrame implements ActionListener {

	/**
	 * Numer wersji, nieistotna wartość domyślna.
	 */
	protected static final long serialVersionUID = 1L;

	Guardian controller;

	private JTextField txt_login;
	private JTextField txt_password;
	private JButton btn_login;

	public GuardianView(Guardian controller, int height, int width) {

		this.controller = controller;

		createGUI(width, height);
		pack();
		setVisible(true);
	}

	private void createGUI(int width, int height) {
		setLayout(new GridBagLayout());
		setMinimumSize(new Dimension(width + 20, 160));
		setPreferredSize(new Dimension(width, height));

		JPanel pane = new JPanel();
		pane.setPreferredSize(new Dimension(width, 125));
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

		JPanel pane_login = new JPanel();
		pane_login.setLayout(new BoxLayout(pane_login, BoxLayout.X_AXIS));
		txt_login = new JTextField();
		txt_login.setHorizontalAlignment(SwingConstants.CENTER);
		txt_login.setMaximumSize(new Dimension(Integer.MAX_VALUE, txt_login
				.getPreferredSize().height));
		txt_login.setToolTipText("Podaj swój login");
		pane_login.setBorder(BorderFactory.createTitledBorder("Login"));
		pane_login.add(txt_login);
		pane.add(pane_login);

		JPanel pane_password = new JPanel();
		pane_password.setLayout(new BoxLayout(pane_password, BoxLayout.X_AXIS));
		txt_password = new JPasswordField();
		txt_password.setHorizontalAlignment(SwingConstants.CENTER);
		txt_password.setMaximumSize(new Dimension(Integer.MAX_VALUE,
				txt_password.getPreferredSize().height));
		txt_password.setToolTipText("Podaj swoje hasło");
		pane_password.setBorder(BorderFactory.createTitledBorder("Hasło"));
		pane_password.add(txt_password);
		pane.add(pane_password);

		btn_login = new JButton("Zaloguj");
		btn_login.setHorizontalAlignment(SwingConstants.TRAILING);
		btn_login.setAlignmentX(CENTER_ALIGNMENT);
		btn_login.addActionListener(this);
		pane.add(btn_login);

		int borderWidth = height * width / 10000;
		pane.setBorder(BorderFactory.createEmptyBorder(borderWidth,
				borderWidth, borderWidth, borderWidth));

		add(pane, new GridBagConstraints());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (controller.login(txt_login.getText(), txt_password.getText())) {
			setVisible(false); 
			dispose(); // Destroy the JFrame object
		} else
			JOptionPane.showMessageDialog(this, "Nieudane logowanie");
	}
/*	
	public static void main(String[] args) {
		Guardian model = new Guardian();
		GuardianView gView = new GuardianView(model, 400, 400);
		gView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gView.pack();
		gView.setVisible(true);
	}
*/
}
