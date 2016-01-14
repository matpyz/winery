package winery.view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.util.ArrayList;
import java.awt.Component;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ScrollPaneConstants;

public class ConfigFieldPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private JTextField sectorText;
	private JTextField columnText;
	private JTextField rowText;
	private JPanel panel;
	private JScrollPane scrollPane;
	private JButton btnDodaj;
	
	JButton btnWstecz;
	JButton btnKoniec;
	
	ArrayList<String> sectors = new ArrayList<String>();
	ArrayList<Integer> columns = new ArrayList<Integer>();
	ArrayList<Integer> rows = new ArrayList<Integer>();
	
	public ConfigFieldPanel(ActionListener actionListener) {
		
		setLayout(null);
		
		JLabel lblSektor = new JLabel("Sektor");
		lblSektor.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSektor.setHorizontalAlignment(SwingConstants.CENTER);
		lblSektor.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblSektor.setBounds(40, 20, 100, 20);
		add(lblSektor);
		
		JLabel lblIloKolumn = new JLabel("Liczba kolumn");
		lblIloKolumn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblIloKolumn.setHorizontalAlignment(SwingConstants.CENTER);
		lblIloKolumn.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblIloKolumn.setBounds(150, 20, 100, 20);
		add(lblIloKolumn);
		
		JLabel lblIlo = new JLabel("Liczba rzędów");
		lblIlo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblIlo.setHorizontalAlignment(SwingConstants.CENTER);
		lblIlo.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblIlo.setBounds(260, 20, 100, 20);
		add(lblIlo);
		
		btnWstecz = new JButton("Wstecz");
		btnWstecz.setBounds(40, 335, 89, 23);
		btnWstecz.addActionListener(actionListener);
		add(btnWstecz);
		
		btnKoniec = new JButton("Koniec");
		btnKoniec.setBounds(386, 335, 89, 23);
		btnKoniec.addActionListener(actionListener);
		add(btnKoniec);
		
		panel = new JPanel();
		panel.setLayout(null);
		
		btnDodaj = new JButton("Dodaj");
		btnDodaj.setBounds(330, 9, 89, 23);
		btnDodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(check(sectorText.getText(), Integer.parseInt(columnText.getText()), Integer.parseInt(rowText.getText())) == false) {
						JOptionPane.showMessageDialog((Component) null, "Wprowadzony sektor ju� istnieje!.", "B��d",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				} catch(NumberFormatException nfe) {
					JOptionPane.showMessageDialog((Component) null, "Wprowadzono b��dne dane!.", "B��d",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				sectorText.setVisible(false);
				columnText.setVisible(false);
				rowText.setVisible(false);
				JLabel temp;

				sectors.add(sectorText.getText());
				Rectangle s = sectorText.getBounds();
				temp = new JLabel(sectorText.getText());
				temp.setBounds(s);
				panel.add(temp);
				s.setLocation(s.x, s.y+25);
				sectorText = new JTextField();
				sectorText.setBounds(s);
				sectorText.setColumns(10);
				
				columns.add(Integer.parseInt(columnText.getText()));
				Rectangle c = columnText.getBounds();
				temp = new JLabel(columnText.getText());
				temp.setBounds(c);
				panel.add(temp);
				c.setLocation(c.x, c.y+25);
				columnText = new JTextField();
				columnText.setBounds(c);
				columnText.setColumns(10);
				
				rows.add(Integer.parseInt(rowText.getText()));
				Rectangle r = rowText.getBounds();
				temp = new JLabel(rowText.getText());
				temp.setBounds(r);
				panel.add(temp);
				r.setLocation(r.x, r.y+25);
				rowText = new JTextField();
				rowText.setBounds(r);
				rowText.setColumns(10);
				
				Rectangle b = btnDodaj.getBounds();
				b.setLocation(b.x, b.y+25);
				btnDodaj.setBounds(b);
				
				panel.add(sectorText);
				panel.add(columnText);
				panel.add(rowText);
				sectorText.setVisible(true);
				columnText.setVisible(true);
				rowText.setVisible(true);
				sectorText.setFocusable(true);
			}
		});
		panel.add(btnDodaj);

		sectorText = new JTextField();
		sectorText.setBounds(0, 10, 100, 20);
		panel.add(sectorText);
		sectorText.setColumns(10);
		
		columnText = new JTextField();
		columnText.setBounds(110, 10, 100, 20);
		panel.add(columnText);
		columnText.setColumns(10);
		
		rowText = new JTextField();
		rowText.setBounds(220, 10, 100, 20);
		panel.add(rowText);
		rowText.setColumns(10);

		scrollPane = new JScrollPane(panel);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(40, 42, 435, 260);
		add(scrollPane);
	}
	
	public String getFieldData() {
		StringBuilder sb = null;
		for(int i = 0; i<sectors.size(); i++) {
			 sb = new StringBuilder();
			 sb.append(sectors.get(i));
			 sb.append(" ");
			 sb.append(columns.get(i));
			 sb.append(" ");
			 sb.append(rows.get(i));
			 sb.append("\n");
		}
		
		return sb.toString();
	}
	
	private boolean check(String sect, int column, int row) {
		for(String s : sectors) {
			if(sect.equals(s)) {
				return false;
			}
		}
		for(int i : columns) {
			if(column == i) {
				return false;
			}
		}
		for(int i : rows) {
			if(row == i) {
				return false;
			}
		}
		return true;
	}
}
