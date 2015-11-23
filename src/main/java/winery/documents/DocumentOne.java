package winery.documents;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPTable;

/**
Klasa umo�liwiaj�ca generowanie sprecyzowanego  dokumentu, dziedzicz�ca po @see Dokument. 
@author Przemys�aw Iskra
@version 1.0  */

//Jeszcze bez pobierania danych wymaganych przez ten �w dokument. 
public class DocumentOne extends DocumentBasic {
/** Konstruktor  klasy tworz�cej sprecyzowany dokumentu*/
	

	public DocumentOne()
	{     needtable=true;
		  horizontal= new String[3];
	      vertical= new String[5];
	      horizontal[0]=" Wina  ";
	      horizontal[1]="Czerwone albo r�owe";
	      horizontal[2]="Bia�e";
	      vertical[0]=" z Chronion� Nazw� Pochodzenia (ChNP)   ";
	      vertical[1]=" z Chronionym Oznaczeniem Geograficznym (ChOG)   ";
	      vertical[2]=" bez ChNP/ ChOG  z okre�lonej odmiany winoro�li1   ";
	      vertical[3]= " bez ChNP/ ChOG inne ni� z okre�lonej odmiany winoro�li ";
	      vertical[4]= " Pozosta�e";
		  title= "Deklaracja o ilo�ci win bia�ych lub czerwonych wprowadzonych do obrotu w poprzednim roku gospodarczym";
	       date="31 sierpnia";
	       pathFile="hello.pdf";
	       
	}
	
/**Funkcja dodaj�c� do generowanego dokumentu i do wskazanej tablicy  zawarto�� dokumentu
 * @param table wskazana tablica
 * @param inData dane jakie wymaga dokument, jakie firma musi poda� */

	  public PdfPTable addtoTableNew(PdfPTable table, String [][] inData) {
    	  insertCell(table, horizontal[0],Element.ALIGN_CENTER, 4, f);
    	  insertCell(table,horizontal[1],Element.ALIGN_CENTER, 3, f);
    	  insertCell(table,horizontal[2],Element.ALIGN_CENTER, 3, f);
    	  
    	  
    	  insertCell(table, vertical[0],Element.ALIGN_LEFT, 4, f);
    	  insertCell(table,inData[0][0],Element.ALIGN_CENTER, 3, f);
    	  insertCell(table,inData[0][1],Element.ALIGN_CENTER, 3, f);
    	  
    	  
    	  insertCell(table, vertical[1],Element.ALIGN_LEFT, 4, f);
    	  insertCell(table,inData[1][0],Element.ALIGN_CENTER, 3, f);
    	  insertCell(table,inData[1][1],Element.ALIGN_CENTER, 3, f);
    	
    	  
    	  insertCell(table, vertical[2],Element.ALIGN_LEFT, 4, f);
    	  
    	  insertCell(table,inData[2][0],Element.ALIGN_CENTER, 3, f);
    	  insertCell(table,inData[2][1],Element.ALIGN_CENTER, 3, f);
    	  
    	  insertCell(table, vertical[3],Element.ALIGN_LEFT, 4, f);
    	  
    	  insertCell(table,inData[3][0],Element.ALIGN_CENTER, 3, f);
    	  insertCell(table,inData[3][1],Element.ALIGN_CENTER, 3, f);
    	  
    	  insertCell(table,vertical[4],Element.ALIGN_LEFT, 4, f);
    	  insertCell(table,inData[4][0],Element.ALIGN_CENTER, 3, f);
    	  insertCell(table,inData[4][1],Element.ALIGN_CENTER, 3, f);
    	
		return table;
	}
	  
	  
 
 
}
