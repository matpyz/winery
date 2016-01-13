package winery.rss;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import winery.model.Model;
import winery.view.View;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Font;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.List;

public class RSSView extends View implements ActionListener {

	protected static final long serialVersionUID = 1L;
	private RSSController controller;
	
	List list;
	
	private JButton updateButton;

	public RSSView(RSSController controller) {
		this.controller = controller;
		createGUI();
	}

	private void createGUI() {
		
		setLayout(null);
		list = new List();
		list.setName("Zdarzenia");
		list.setBackground(SystemColor.control);
		list.setBounds(10, 5, 430, 244);
		add(list);
		
		updateButton = new JButton("Zaktualizuj listę");
		updateButton.setBounds(117, 260, 223, 29);
		add(updateButton);
		updateButton.addActionListener(this);
	}

	@Override
	protected void update(Model model) {
		// TODO Auto-generated method stub
		RSSModel rssmodel = (RSSModel)model;
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				list.removeAll();
				for(String record : rssmodel.getList()) {
					list.add(record);
				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == updateButton) {
			controller.getLatestEvents();
		}
	}

}
