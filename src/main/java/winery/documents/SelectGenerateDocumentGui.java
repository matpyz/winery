package winery.documents;


import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
/**
Klasa Gui do wyboru generowanego dokumentu ora� scie�ki gdzie zapisa� utworzony.  
@author Przemys�aw Iskra
@version 2.0  */
public class SelectGenerateDocumentGui extends JPanel  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    JPanel Pmaks,Pmin;
    JFileChooser Ffilechose;
    JLabel Linfo1,Linfo2, Lfile;
    JComboBox<String> CBdocuments;
    JTextArea Tdata;
   // String title, path="";
//    title zmienna m�wi�ca jaki jest wybrany dokument. 
//path zmienna m�wi�ca jak� wybrana jest scie�ka, na razie pusta. 
    JButton Bready,Bdirectory;
    int a=600;//d�ugo��
    int b=600; //szeroko��
    SelectGenerateDocumentModel model;
    //docTable tablica zawieraj�ca wszystkie dokumenty jakie mo�na wybra�. 
  //  String[] docTable = new String[] {"<html>Deklaracja o ilo�ci win bia�ych lub czerwonych <br> wprowadzonych do obrotu w poprzednimroku gospodarczym <br>",
    //		"<html>Deklaracja o szacowanej ilo�ci win, winogron lub moszczu<br> winogronowego,  kt�reb�d� dostarczone do przedsi�biorcy, oraz o szacowanej<br> ilo�ci ich wykorzystaniaw danym <br> roku gospodarczym"};
	public SelectGenerateDocumentGui() {
		setLayout(new GridLayout(1,1));
        model = new SelectGenerateDocumentModel();
        setSize(a,b);
        Pmaks= new JPanel();  //Panel, do kt�rego wrzucamy wszystko. Ultra Panel taki. 
        Pmaks.setSize(a,b);
        Pmaks.setLayout(new FlowLayout(FlowLayout.CENTER,  600,  10) );
        add(Pmaks);
        Linfo1= new JLabel("Wybierz Dokument:");   //Zwyk�e Info
         Linfo1.setHorizontalAlignment(SwingConstants.CENTER);
       // Linfo1.setOpaque(true);
       // Linfo1.setBackground(Color.GRAY);
        Pmaks.add(Linfo1);
        
        
        CBdocuments = new JComboBox<>(model.getTable());  // Combo Box s�u�y do wyboru jednej rzeczy z listy rozwijanej, tutaj wybieramy dokument. 
        CBdocuments.setSelectedIndex(0);  //ustawienie co najpierw wybieramy. 
       // title=docTable[0];
        model.setVtitle(model.getdocTable(0));  //ustawienie w modelu jaki dokument jest wst�pnie wybrany 
       

       
       CBdocuments.addActionListener(new DocSelectListener());
        Pmaks.add(CBdocuments);
        
        Linfo2=new JLabel("<html> Podaj Imi� i nazwisko, miejsce zamieszkania i adres <br> albo firm�, siedzib� i adres oraz numer wpisu<br> do ewidencji producent�w i przedsi�biorc�w<br> wyrabiaj�cych wino z winogron pozyskanychz <br> upraw winoro�li po�o�onych na terytorium<br> Rzeczypospolitej Polskiej podmiotu<br> sk�adaj�cego deklaracj�, je�eli podmiot ten<br> taki numer posiada: ");
        Pmaks.add(Linfo2);  // zwyk�e info o tym  co trzeba wpisa� do textArea. 
        Tdata = new JTextArea(5,30);
        
        
        Pmaks.add(Tdata);
        Bready = new JButton(" Generuj Dokument.");  //Przycisk, kt�rego klikni�cie powoduje generowanie dokumentu.
        Bready.addActionListener(new GenerateDocListener());
        
        Pmin= new JPanel();
        Bdirectory= new JButton("Wybierz Katalog oraz podaj nazw�!");  //Przycisk kt�rego klikniecie powoduje wybranie katalogu. 
        Lfile= new JLabel(" Brak wybranego Karalogu. "); //Jaki katalog wybrano informacja. 
        Bdirectory.addActionListener(new PathSelectListener());
        
        Pmin.add(Bdirectory);
        Pmin.add(Lfile);
        
        Pmaks.add(Pmin);
        
/*
      
        */
        Pmaks.add(Bready); 
        
		
		
	}
	
	  /**Klasa odpowiedzialna za reakcje przycisku Bready, czyli generowanie dokumentu */               
    public class GenerateDocListener implements ActionListener
      {
        
            
    	public void actionPerformed(ActionEvent event) 
    		{
	    	
	    	String data=Tdata.getText().trim();
	    	  if(!data.equals("")  &&  !model.getVpath().trim().equals("")) //Sprawdzanie, czy aby  potrzebna informacja  ni jest pustapusta. 
	    	   		{ model.createTableData(model.getVpath(), data); //Tworzenie tablicy danych do dokumentu (w wyskakuj�cym okienko, zako�czenie wybierania powoduje generowanie dokumentu.
	    	   		//Przenie�� Generowanie tablicy do modelu. 
	    	   		//JOptionPane.showMessageDialog(null, "Dokument przygotowany. ");
	    	                      
	    	   		}
	    	  else JOptionPane.showMessageDialog(null, "Nie wype�niono wszystkich danych.  ");
	    	}
       }  
    
    
    /**Klasa odpowiedzialna za reakcje przycisku Bdirectory , czyli wybieranie katalogu*/               
    public class PathSelectListener implements ActionListener
      {
        
            
    	public void actionPerformed(ActionEvent event) {
	    	  Ffilechose= new JFileChooser();   
	          Ffilechose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //Wybranie katalogu do zapisu pliku. 
	          Ffilechose.showSaveDialog(null);	
	          try {
	          model.setVpath(Ffilechose.getSelectedFile().toString());
	          String path=model.getVpath();
	        
	          File f = new File(model.getVpath());
              if (f.isDirectory())  //Sprawdzenie czy jest to katalog 
              		{ 
              	     model.setVpath(path +"\\document.pdf"); //dodanie nazwy pdfa tworzonego
              		}
              else 
              		{
              	     String endpdf=path.substring( path.length()-4,path.length()); 

              	    if(!endpdf.equals(".pdf")) model.setVpath(path+".pdf");  //Sprawdzenie czy cztery ostatnie znaki nie zawieraj� konc�wki .pdf i dodanie jej wtedy. 

              		}
	          Lfile.setText(model.getVpath());
	          }
	          catch(NullPointerException e)
	          {
	        	  
	          }
	    	                      
	    }
       }  
    
    /**Klasa odpowiedzialna za reakcje  CBdocuments , czyli wybieranie dokumentu*/               
    public class DocSelectListener implements ActionListener
      {
        
            
    	 public void actionPerformed(ActionEvent event) 
    	 {
 	        @SuppressWarnings("unchecked")
				JComboBox<String> combo = (JComboBox<String>) event.getSource();
 	          String selectedDoc = (String) combo.getSelectedItem();
 	           model.setVtitle(selectedDoc);
 	   
    	 
    	 }
       } 
	
	public static void main(String args[]){  
		
		SelectGenerateDocumentGui b=new SelectGenerateDocumentGui();  
		JFrame j= new JFrame();
		j.setSize(600, 600);
		b.setVisible(true);
		j.add(b);
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.setVisible(true);
		
		}  
}