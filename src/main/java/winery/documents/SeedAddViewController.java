package winery.documents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import dbapi.DBManager;
import dbapi.Seed;
import dbapi.Wine;
import winery.documents.PredictingLitersOfWineViewController.GenerateDocListener;
import winery.documents.PredictingLitersOfWineViewController.PathSelectListener;
import winery.documents.PredictingLitersOfWineViewController.UpdateData;
import winery.model.Model;
import winery.view.Controller;
import winery.view.View;

public class SeedAddViewController  extends View implements Controller {

	private static final long serialVersionUID = 1L;
	int a = 500;// długość
	int b = 500;
	private JPanel Pmaks;
	
	private JPanel[] PpanelTable;
	private JLabel[] LinfoTab;
	private JTextField[] TextTab;

	private JPanel Pcenter;
	private JPanel Pdown;

	private JPanel Ptop;
	private JLabel Linfo;
    private  JRadioButton button1,button2,button3,button0,button4;
    JButton ButtonOk;
	SeedAddModel model;
	
	public static int num=0;

	public SeedAddViewController()

	{
		model = new SeedAddModel();
		String nameTab[]={"Rodzaj Sadzonki", "Ilość Sadzonek  "};

		setSize(a, b);
		Pmaks = new JPanel(); // Panel główny, do którego wszystko wkładamy.
		Pmaks.setSize(a, b);
		Pmaks.setLayout(new BorderLayout());
		add(Pmaks);
		Pcenter = new JPanel();
		Pdown = new JPanel();
		Ptop = new JPanel();
		Ptop.setLayout(new GridLayout(3, 1));
		Pmaks.add(Pcenter, BorderLayout.CENTER);
		Pmaks.add(Pdown, BorderLayout.SOUTH);
		Pmaks.add(Ptop, BorderLayout.NORTH);

		Linfo = new JLabel("                                       Dodawanie sadzonek.");
		Ptop.add(Linfo); 

		PpanelTable = new JPanel[2]; 
	    
		Pcenter.setLayout(new GridLayout(1, 1));
		
		LinfoTab = new JLabel[2];
		TextTab = new JTextField[2];
		for (int i = 0; i < 2; i++) { // Organizacja danych do okienka
			PpanelTable[i] = new JPanel(new FlowLayout(FlowLayout.LEFT));
			
			LinfoTab[i] = new JLabel(nameTab[i]);
			LinfoTab[i].setForeground(Color.DARK_GRAY);
			LinfoTab[i].setMinimumSize(new Dimension(100, 30));
			
			TextTab[i] = new JTextField(15);
			
			
			
			Ptop.add(PpanelTable[i]);
			PpanelTable[i].add(LinfoTab[i]);
			PpanelTable[i].add(TextTab[i]);
	
			

		}
		
		String[] nameTabType = new String[5]; // Tabela nazw określonych rodzajów wina,
		// którego będziemy przewidywać ilość
			nameTabType[0] = "Wino z Chronioną Nazwą Pochodzenia (ChNP) ";
			nameTabType[1] = " Wino  z Chronionym Oznaczeniem Geograficznym (ChOG) ";
			nameTabType[2] = "Wino bez ChNP/ ChOG  z określonej odmiany winorośli ";
			nameTabType[3] = " Wino bez ChNP/ ChOG inne niż z określonej odmiany winorośli ";
			nameTabType[4] = " Wino  Pozostałe";
		
      button0  = new JRadioButton(nameTabType[0]);
       button0.setActionCommand("0");
       button0.setSelected(true);
       
       button1  = new JRadioButton(nameTabType[1]);
       button1.setActionCommand("1");
       
       button2  = new JRadioButton(nameTabType[2]);
       button2.setActionCommand("2");
       button3  = new JRadioButton(nameTabType[3]);
       button3.setActionCommand("3");
       button4  = new JRadioButton(nameTabType[4]);
       button4.setActionCommand("4");
		 ButtonGroup group = new ButtonGroup();
		   group.add(button0);
		    group.add(button1);
		    group.add(button2);
		    group.add(button3);
		    group.add(button4);
		    Listener lis= new Listener();
		    button0.addActionListener(lis);
		    button1.addActionListener(lis);
		    button2.addActionListener(lis);
		    button3.addActionListener(lis);
		    button4.addActionListener(lis);
		    
		   
		    JPanel radioPanel = new JPanel(new GridLayout(0, 1));
	        radioPanel.add(button0);
	        radioPanel.add(button1);
	        radioPanel.add(button2);
	        radioPanel.add(button3);
	        radioPanel.add(button4);
		    Pcenter.add(radioPanel);

		    ButtonOk= new JButton("Dodaj");
		    ButtonOk.addActionListener(new ListenerOk());
		    Pdown.add(ButtonOk);
	}

	@Override
	public View getView() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Dodaj sadzonki";
	}

	@Override
	protected void update(Model model) {
		// TODO Auto-generated method stub
		
	}

	
	public class Listener implements ActionListener { // Aktualizacja danych

		public void actionPerformed(ActionEvent event) {
			
			 try {
			        num= Integer.parseInt(event.getActionCommand());
			    } catch (NumberFormatException e) {
			        num=0;
			    }
		}
	}
	
	public class ListenerOk implements ActionListener { // Aktualizacja danych

		public void actionPerformed(ActionEvent event) {
			
		      String name=TextTab[0].getText();
		      String number=TextTab[1].getText();
		      try
		      {
		    	  
		    	
						int numberInt = Integer.parseInt(number);
			
						Calendar now = Calendar.getInstance();   // Gets the current date and time
						int year = now.get(Calendar.YEAR); 
						Seed seed = DBManager.addSeed(name, numberInt, num, year, "", "");
						if (seed == null) {
							JOptionPane.showMessageDialog(null, "Błędne dane.  ");	
						}
						else {JOptionPane.showMessageDialog(null, "Dodano do bazy sadzonki. ");
							
						}
					
					
		      
		       }
		   catch (NumberFormatException ex) {
			   JOptionPane.showMessageDialog(null, "Błedny format daty. ");
		      
		    }
			
		
		}
	}
	
	public static String getID() {
		return "seedadd";
	}
}
