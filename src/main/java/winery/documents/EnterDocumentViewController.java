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
import winery.guardian.Guardian;
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

		JLabel lblName = new JLabel("Nazwa:");

		tfName = new JTextField();
		tfName.setColumns(10);

		JLabel lblType = new JLabel("Typ:");

		tfType = new JTextField();
		tfType.setColumns(10);

		JLabel lblDescription = new JLabel("Opis:");

		JLabel lblPath = new JLabel("Ścieżka do pliku:");

		tfPath = new JTextField();
		tfPath.setColumns(10);

		JButton btnPath = new JButton("Przeglądaj...");
		btnPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FileDialog dlg = new FileDialog((Frame) null);
				dlg.setVisible(true);
				String file = dlg.getDirectory() + dlg.getFile();
				if (file != null) {
					tfPath.setText(file);
				}
			}
		});

		JButton btnSubmit = new JButton("Wprowadź do bazy");
		btnSubmit.addActionListener(this);

		editorDescription = new JEditorPane();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(lblName)
										.addComponent(lblType).addComponent(lblPath).addComponent(lblDescription))
								.addGap(4)
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(tfType, Alignment.TRAILING, 348, 348, Short.MAX_VALUE)
										.addComponent(tfName, Alignment.TRAILING, 348, 348, Short.MAX_VALUE)
										.addComponent(tfPath, Alignment.TRAILING)
										.addComponent(editorDescription)
										.addComponent(btnPath, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 104,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btnSubmit, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 177,
												GroupLayout.PREFERRED_SIZE))
								.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		))));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblName).addComponent(tfName,
						GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(tfType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lblType))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblPath).addComponent(tfPath,
						GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblDescription)
						.addComponent(btnPath))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(editorDescription, GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
				.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnSubmit).addContainerGap()));
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
			int creatorId = Guardian.getUserDbId();
			if (DBManager.addDocument(creatorId, tfName.getText(), editorDescription.getText(), tfType.getText(),
					document)) {
				JOptionPane.showMessageDialog((Component) null, "Wprowadzono poprawnie plik do bazy.", "Sukces",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog((Component) null, "Nie udało się wprowadzić pliku.", "Błąd",
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
