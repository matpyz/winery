package winery.rss;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import winery.model.Model;
import winery.view.View;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class RSSView extends View implements ActionListener {

	protected static final long serialVersionUID = 1L;
	private RSSController controller;
	
	private JButton updateButton;
	private JScrollPane panel;

	public RSSView(RSSController controller) {
		this.controller = controller;
		createGUI();
	}

	private void createGUI() {
		
		setLayout(null);
		
		panel = new JScrollPane();
		panel.setBounds(10, 10, 520, 460);
		//panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		add(panel);
		updateButton = new JButton("Odśwież");
		updateButton.setBounds(180, 480, 200, 30);
		add(updateButton);
		updateButton.addActionListener(this);
	}

	@Override
	protected void update(Model model) {
		RSSModel rssmodel = (RSSModel)model;
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				panel.removeAll();
				int x = 0;
				for(String record : rssmodel.getList()) {
					//panel.add(new JLabel(record));
					panel.add(new MyJList(record, x));
					x+=60;
					System.out.println(x);
				}
				System.out.println(panel.getComponentCount());
				panel.repaint();
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == updateButton) {
			controller.getLatestEvents();
		}
	}
}
