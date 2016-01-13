package winery.rss;

import winery.model.Model;
import winery.view.View;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class RSSView extends View {

	protected static final long serialVersionUID = 1L;
	private RSSController controller;
	
	private JScrollPane scrollPane;
	private JPanel panel;

	public RSSView(RSSController controller) {
		this.controller = controller;
		createGUI();
	}

	private void createGUI() {
		
		setLayout(null);
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		scrollPane = new JScrollPane(panel);
		scrollPane.setBounds(10, 10, 520, 460);
		add(scrollPane);
	}

	@Override
	protected void update(Model model) {
		RSSModel rssmodel = (RSSModel)model;
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Timer(10000, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						controller.getLatestEvents();
						panel.removeAll();
						panel.setVisible(false);
						for(String record : rssmodel.getList()) {
							panel.add(new MyJList(record));
						}
						panel.repaint();
						panel.setVisible(true);
					}
				}).start();
			}
		});
	}
}
