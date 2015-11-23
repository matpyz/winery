package winery.documents;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JTextField;
import javax.swing.SwingConstants;


/**
Klasa s³u¿aca do obs³ugi gui poprawy danych dokumentu.   
@author Przemys³aw Iskra
@version 1.0  */

public class ChangeDataDocumentGui  extends JFrame{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    JPanel Pmax, Pcenter,Pdown,Ppom;
    JLabel Linfo, Ltext;
    JPanel[] PpanelTable;
    JLabel[] LinfoHorizontal;
    JTextField[]   TFhorizontal;
    JButton Bnext;
    int itt,end, size;
  
    String[][] data;
    String[] horizontal, vertical;
    String path,firmInfo;
    DocumentBasic dokument;
/**    Klasa tworz¹ca  wyskakuj¹ce okienko zmiany danych.
 *     a Idea jest taka: dokument potrzebuje danych, którê praktycznie mo¿na zorganizowaæ w tabelce
      *  a wiêc, dla ka¿dego wiersza danych wymaganych przez dokument wyœwietlamy czego te dany dotycz¹ oraz same proponowane 
      *  dane pobrane z bazy, to wszystko w gui. 
      *  U¿ytkownik mo¿e je zmieniæ lub nie ruszaæ. Przyciskiem potwierdzu, ¿e skoñczy³, 
      *  to wyskocza nowe dane i tak a¿ u¿ytkownik sprawdzi wszystkie dane.   
 * 
 * @param horizontal dane z dokumentu tworz¹ce tytu³y kolumn danych z dokumentu
 * @param vertical  dane z dokumentu tworz¹ce tytu³y wierszy danych z dokumentu
 * @param data    dane jakie dokument wymaga od firmy 
 * @param path  œcie¿ka do zapisu pliku
 * @param firmInfo  informacje o firmie
 * @param dokument  Objekt reprezentuj¹cy dany dokument. 
 */
    
public ChangeDataDocumentGui(String[] horizontal, String[] vertical, String[][] data, String path, String firmInfo, DocumentBasic dokument) {
	     size=horizontal.length-1; 
	      end=vertical.length;
	     
	     this.data=data;
	     this.horizontal=horizontal;
	     this.vertical=vertical;
	     this.path=path;
	     this.firmInfo=firmInfo;
	     this.dokument=dokument;
	     // Zrobiæ sprawdzanie czy tabelka data ma odpowiednie d³ugoœci wed³ug horizontal vertical tablic. 
	      itt=0;
	      Pmax = new JPanel();  //Du¿y panel do którego wszystko wrzucamy.
	      Pmax.setSize(500,500);
	      setSize(500,500);
	      add(Pmax);
	      Pmax.setLayout(new BorderLayout());
	      Pcenter = new JPanel();  //PodPanel œrodkowy, tutaj bêdziemy zmieniaæ proponowane dane lub zostawiaæ je jak s¹ dobre
	      Pdown = new JPanel();   //Podpanel dolny  z guzikiem
	      Pmax.add(Pcenter, BorderLayout.CENTER);
	      Pmax.add(Pdown, BorderLayout.SOUTH);
	      Pcenter.setLayout(new FlowLayout(FlowLayout.RIGHT, 150,  10));
	      Bnext = new JButton("Dalej"); //Przycisk odpowiedzialny za pobieranie danych i wstawianie nastêpnych do pobrania
	      Pdown.add(Bnext);
	      Bnext.addActionListener(new ButtonListener());
	      //Linfo, Ltext Informacje. 
	      Linfo = new JLabel(horizontal[0] + " "+vertical[itt], SwingConstants.CENTER);
	      Ltext = new JLabel("<html>SprawdŸ poprawnoœæ poni¿szych danych,  jeœli trzeba popraw: <br> Uwaga wersje nie pobiera jeszcze z bazy danych", SwingConstants.CENTER);
	 
	     
	      Ppom = new JPanel( );
	      Ppom.setLayout( new GridLayout(2, 1));
	    //  Ppom.setLayout();
	      Ppom.add(Ltext);
	      Ppom.add(Linfo);
	      Pmax.add(Ppom, BorderLayout.NORTH);
	     // Pcenter.add(Ppom);
	      PpanelTable= new JPanel[size];
	      LinfoHorizontal = new JLabel[size];  //czego dane z dokumentu dotycz¹ 
	      TFhorizontal = new JTextField[size];  // textfield s³u¿acy do zmiany danych
     
	    for(int i=0;i<size;i++)
	    { PpanelTable[i] =new JPanel();
	       
	      Pcenter.add(PpanelTable[i]);
	      LinfoHorizontal[i] = new JLabel(horizontal[i+1]); //Plus jeden bo tabelka horizontal zawiera jedn¹ dodatkow¹ danê, któr¹ omijamy tutaj. 
	      LinfoHorizontal[i].setMinimumSize(new Dimension(100,30));
	      TFhorizontal[i] = new JTextField(15);
	      TFhorizontal[i].setText(data[itt][i]);
	      PpanelTable[i].add(LinfoHorizontal[i]);
	      PpanelTable[i].add(TFhorizontal[i]);
	    	
	    	
	    }
		
        
	    addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
               
                dispose();
            }
        });
       
		setVisible(true);
		
	}

public class ButtonListener implements ActionListener
{
  
      
	 public void actionPerformed(ActionEvent event) 
	 {  if(itt<end-1)   // Jeœli s¹ jeszcze jakieœ dane do sprawdzenia i uzupe³nenia. 
	 		{  for(int i=0;i<size;i++) 
	 				{
	 				data[itt][i]=TFhorizontal[i].getText();   //zapisujemy wczeœniej wpisane oraz niezmienione dane
	 				}
	           	itt++;
	           	Linfo.setText(horizontal[0] + " "+vertical[itt]);  //poprawiamy okreœlenie  danych jakie nada³ im dokument
	           	for(int i=0;i<size;i++) 
	           		{ 
	           		TFhorizontal[i].setText(data[itt][i]);  //wsadzamy nastêpne proponowane dane
		      
	           		}
	           	if(itt==end-1) Bnext.setText("Koniec");
	 		}
	 	else  
	 		{  for(int i=0;i<size;i++) 
	 			{data[itt][i]=TFhorizontal[i].getText(); }//Ostatnie dane zostaj¹ pobrane,
	 		
	 		    dokument.createPDF(path, firmInfo, data);  //generujemy dokument. 
	 		   JOptionPane.showMessageDialog(null, "Dokument przygotowany. ");
                dispose();// okienko siê zamyka. 
	 		}
	 
       
			
  
	 
	 }
 }




/*
public static void main(String args[]){  
	
	String[] a={" S³owa ","takie","piêkne", "Jej"};
	String[] b={"S¹ tutaj  dziwne","jejjj", "Lego"};
	String[][] d={{"---","---", " ------"}, {"---","---","--------"}, {"---","---","--------"}};
	@SuppressWarnings("unused")
	//BRAKKKKK ZWRACANIA GOTOWEJ TABELII,,,, ZROBIÆ!!!!!!!!!!!!!!.
	ZmianaDanychDokumentuGui c=new ZmianaDanychDokumentuGui(a,b,d);  
	} */

}
