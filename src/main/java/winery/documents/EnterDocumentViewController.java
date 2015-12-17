package winery.documents;

import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import dbapi.DBManager;
import winery.model.Model;
import winery.view.Controller;
import winery.view.View;

/**
 * 
 * @author Mateusz
 *
 */
public class EnterDocumentViewController extends View implements Controller, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField tfName;
	private JTextField tfType;
	private JTextField tfPath;
	private JEditorPane editorDescription;

	/**
	 * Create the panel.
	 */
	public EnterDocumentViewController() {

		JLabel lblName = new JLabel("Nazwa");

		tfName = new JTextField();
		tfName.setColumns(10);

		JLabel lblType = new JLabel("Typ");

		tfType = new JTextField();
		tfType.setColumns(10);

		JLabel lblDescription = new JLabel("Opis");

		editorDescription = new JEditorPane();

		JLabel lblPath = new JLabel("Ścieżka do pliku");

		tfPath = new JTextField();
		tfPath.setColumns(10);

		JButton btnPath = new JButton("Przeglądaj...");
		btnPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FileDialog dlg = new FileDialog((Frame) null);
				dlg.setVisible(true);
				String file = dlg.getFile();
				if (file != null) {
					tfPath.setText(file);
				}
			}
		});

		JButton btnSubmit = new JButton("Wprowadź do bazy");
		btnSubmit.addActionListener(this);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(80)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnSubmit)
						.addComponent(lblPath)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(tfPath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnPath))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(tfType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblType)
								.addComponent(tfName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblName))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(editorDescription, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblDescription))))
					.addContainerGap(183, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(40)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblName)
						.addComponent(lblDescription))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(tfName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblType)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(editorDescription))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblPath)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(tfPath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnPath))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnSubmit)
					.addContainerGap(91, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}

	@Override
	protected void update(Model model) {
		return;
	}

	@Override
	public View getView() {
		return this;
	}

	@Override
	public String getTitle() {
		return "Wprowadzanie dokumentów";
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Blob document;
		try {
			document = DBManager.loadBlob(tfPath.getText());
			/*
			 * FIXME Jak określić poprawną wartość creatorId? Ma związek z
			 * zalogowanym użytkownikiem, potrzebna nowa funkcjonalność
			 * Guardiana?
			 * 
			 * Zwalidować dane: sprawdzić długość napisów?
			 */
			int creatorId = 0;
			if (DBManager.addDocument(creatorId, tfName.getText(), editorDescription.getText(), tfType.getText(),
					document)) {
				return;
			} else {
				JOptionPane.showMessageDialog((Component) null, "Nie udało się zapamiętać pliku.", "Błąd",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog((Component) null, "Nie istnieje plik o podanej ścieżce.", "Błąd",
					JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog((Component) null, "Błąd połączenia z bazą danych.", "Błąd",
					JOptionPane.ERROR_MESSAGE);
		}

	}
}
