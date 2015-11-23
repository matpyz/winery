package winery.documents;


 

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;

import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

 

/**
Klasa umo¿liwiaj¹ca generowanie szkieletu treœci dowolnego dokumentu 
@author Przemys³aw Iskra
@version 1.0  */
//Generowany dokument jeszcze nie ma mo¿liwoœci wype³nienia danymi wymaganymi przez wskazany dokument. . 

public class DocumentBasic{
 
    Font f,fB,fU;
   
    /**  @param title Nazwa dokumentu, i pierwsze linijka w dokumencie.
     *  @param date  Informacje o terminie oddania dokumentu, któr¹ znajduje siê te¿ na dokumencie
     *  @param  pathFile  œcie¿ka do pliku z nazw¹, umo¿liwiaj¹ca utworzenie nowego dokumentu
     *  @param size Iloœæ wierszy w tabeli zawieraj¹cej dane  jakie wymaga podanie dany dokument
     *   @param size2 Iloœæ kolums w tabeli zawieraj¹cej dane  jakie wymaga podanie dany dokument*/
    public   String title, date,pathFile;
    public int size,size2;
    String[][] data;
    Boolean needtable;
	String[] horizontal,vertical;
    /** Konstruktor  klasy tworz¹cej szkielet dokumentu*/
     public DocumentBasic()
    {  needtable=false;
       title= " Testowa Deklaracja o iloœci win bia³ych lub czerwonych wprowadzonych do obrotu w poprzednim roku gospodarczym";
       date="31 sierpnia";
       pathFile="hello.pdf";
       size=5;
       size2=2;
    	
    	
    }
    
    
/** Metoda umo¿liwaj¹ca  generowanie dokumentu
 * @param firmInfo to dane firmy, którê s¹ dla wszystkich dokumentów sta³e
 * @param data  dane jakie dokument wymaga od firmy*/
     public void createPDF ( String firmInfo,String [][] data){
    	 createPDF(pathFile,firmInfo,data);
    	 
     }
   
     
     /** Metoda umo¿liwaj¹ca  generowanie dokumentu w wskazanym miejscu
      * @param firmInfo to dane firmy, którê s¹ dla wszystkich dokumentów sta³e
      * @param path scie¿ka z nazw¹ pliku pdf, w którym bedzie zapisywany dokument 
      *  @param data  dane jakie dokument wymaga od firmy*/
    public void createPDF ( String path,String firmInfo, String [][]data){
    	  Calendar now = Calendar.getInstance(); 
    	  this.data=data;
    	  
    	  Document doc = new Document();
    	  PdfWriter docWriter = null;
    	 
    	  
    	 
    	  try {
    	    
    	  
    		  BaseFont bf = BaseFont.createFont(BaseFont.TIMES_ROMAN,  
                      BaseFont.CP1250, BaseFont.EMBEDDED);  
              f = new Font(bf, 12, Font.NORMAL);  
              
        	 
              fB = new Font(bf, 14, Font.BOLD);  
              fU = new Font(bf, 12, Font.UNDERLINE);  
    	 
    	 
           
            pathFile = path;
            String endpdf=path.substring( path.length()-4,path.length());

    	    if(endpdf.equals(".pdf")) path=path.substring(0, path.length()-4); //ucinanie koncówki .pdf
    	   
    	    String formattedDate = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(now.getTime());
    	    path =path + formattedDate +".pdf" ; //            tworzenie nazwy pliku zawieraj¹cym datê i godzinê , aby dwa razy tworzony dokument mia³ inn¹ nazwê
    	  
    	 
    	   docWriter = PdfWriter.getInstance(doc , new FileOutputStream(path));
    	    
    	    doc.addAuthor("iText Generate"); //Doddanie atrybutów dokumentu. 
    	    doc.addTitle("Doc");
    	    doc.addKeywords("Metadata, iText, PDF");
            doc.addCreator("My program using iText");
    	 
    	   doc.open();
    	  
    	   
//    	   Stworzenia najwiêkszego fragmentu dokumentu zwanego paragraf, który zawiera ró¿nie sformatowany i u³o¿ony tekst.  Dodamy go pó¿niej do dokumentu. 
    	   Paragraph paragraph = new Paragraph( ); // I To jest g³ówny paragraf. 
    	   paragraph.setAlignment(Element.ALIGN_CENTER);
    	    
    	    
    	   
    	   float[] columnWidths = {2f, 2f, 2f, 2f,2f,2f,2f,2f,2f,2f}; //Ustawienie podzia³u tablicy, aby wygodniej by³o tworzyæ komórki .
    
    	    PdfPTable table = new PdfPTable(columnWidths);  //Stworzenie tablicy jak¹ w³o¿ymy do  g³ównego paragrafu a potem  do dokumentu.
    	    table.setWidthPercentage(90f); //Ustawienie szerokoœci tabeli w dokumencie. 
    	   
    	 
    	
    	     
    	  
            
    	    Paragraph para = new Paragraph();// Tworzenie paragrafu. 
            Paragraph paragraphmin = new Paragraph(); // Tworzenie podparagrafów. 
    		Paragraph paragraphmin2 = new Paragraph();  
    	    paragraphmin.add( new Phrase(title +"\n",fB)); //Dodajemy fragmenty tekstów do odpowiedniego podparagrafu. 
    	    paragraphmin2.add( new Phrase( " przez ",fU)); 
    	    paragraphmin2.add(new Phrase(" przedsiêbiorców wykonuj¹cych dzia³alnoœæ gospodarcz¹, o której mowa w art. 17 ust. 1 ustawy z dnia 12 maja 2011 r. o wyrobie i rozlewie wyrobów winiarskich, obrocie tymi wyrobami i organizacji rynku wina, wyrabiaj¹cych wyroby winiarskie z wina, winogron lub moszczów winogronowych \n",f));
    	    paragraphmin2.add( new Phrase( " do ",fU));
    	    paragraphmin2.add( new Phrase( "Prezesa Agencji Rynku Rolnego \n",f));
    	    paragraphmin2.add( new Phrase( " w terminie ",fU));
    	  
    	    int year = now.get(Calendar.YEAR);  // Pobieramy rok
    	    paragraphmin2.add( new Phrase( date + " "+ year + ".\n" + "\n",f));
    	    paragraphmin2.setAlignment(Element.ALIGN_LEFT);
    	 	para.add(paragraphmin); //Podparagrafy dodajemy do wiêkszego paragrafu 
    		para.add(paragraphmin2);  
    	    insertCell(table,para , -1, 10); //Utworzony paragraf  dodajemy do tabeli,  Komórka jest  ustawiana na ca³¹ szerkoœæ tablicy.
    	    //Dodanie  dalszych danych. 
    	    insertCell(table, "Imiê i nazwisko, miejsce zamieszkania i adres albo firma, siedziba i adres oraz numer wpisu do ewidencji producentów i przedsiêbiorców wyrabiaj¹cych wino z winogron pozyskanych z upraw winoroœli po³o¿onych na terytorium Rzeczypospolitej Polskiej podmiotu sk³adaj¹cego deklaracjê, je¿eli podmiot ten taki numer posiada.", Element.ALIGN_LEFT, 5, f);
    	    insertCell(table, firmInfo,Element.ALIGN_LEFT, 5, f);
    	    
    	  // data =createTableData();  //Pobranie danych jakich ¿ada³by wybrany dokument
    	    table= addtoTableNew(table,data );  //Umo¿liwienia dodawania nowych danych do tablicy bez modyfikacji funkcji createPDF, jedynie modyfikuj¹c addtoTableNew.
    	    paragraph.add( addNewParagraph()); //Umo¿liwienia dodawania nowych danych bez modyfikacji funkcji createPDF, jedynie modyfikuj¹c addNewParagraph().
    	    paragraph.add(table); //Dodanie do g³ównego paragrafu tablicy. 
    	    paragraph.add( addNewParagraph2());
    	    
    	    
    	    Paragraph dottedline = new Paragraph(" \n");  //Stworzenie miejsca na podpisy. 
    	    dottedline.add("           ..................... ");
    	    dottedline.add(new Phrase("                                                                           "));
    	    dottedline.add(".......................\n ");
    	    dottedline.add(new Phrase("      (miejscowoœæ i data)",f));
    	    dottedline.add(new Phrase("                                                           "));
    	    dottedline.add(new Phrase("(podpis sk³adaj¹cego deklaracjê)\n",f));
    	    paragraph.add(dottedline);
    	   
    	    doc.add(paragraph); //Dodanie g³ównego paragrafu do dokumentu. 
    	 
    	  }
    	  catch (DocumentException dex)
    	  {
    	   dex.printStackTrace();
    	  }
    	  catch (Exception ex)
    	  {
    	   ex.printStackTrace();
    	  }
    	  
    	  finally
    	  {
    	   if (doc != null){
    	   
    	    doc.close();
    	    if (Desktop.isDesktopSupported()) {
                try {
                    File myFile = new File( path);
                    Desktop.getDesktop().open(myFile);
                } catch (IOException ex) {
                    // no application registered for PDFs
                }
            }
    	   }
    	   if (docWriter != null){
    	  
    	    docWriter.close();
    	   }
    	  }
    	 }
    	  
    	
    
    
    /**Funkcja umo¿liwiaj¹ca modyfikacjê dokumentu bez modyfikacji funkcji CreatePDF */
    public Element addNewParagraph2() {
		// TODO Auto-generated method stub
		return null;
	}

    /**Funkcja umo¿liwiaj¹ca modyfikacjê dokumentu bez modyfikacji funkcji CreatePDF */
	public  Element addNewParagraph() {
		// TODO Auto-generated method stub
		return null;
	}

	

	/**Funkcja umo¿liwiaj¹ca modyfikacjê  tabeli w dokumencie  bez modyfikacji funkcji CreatePDF  
	 * @param table  tabel¹, któr¹ modyfikujemy
	 * @param inData dane jakie dokument wymaga od firmy */
	public PdfPTable addtoTableNew(PdfPTable table, String [][] inData) {
    	
		return table;
	}

/** 
 * funkcja umo¿liwiaj¹ca wk³¹danie to tabeli tekstu o odpowiednim rozmiarze komórki i ustawieniach tekstu
 * @param table tabela do której wk³adamy dane
 * @param text tekst który wk³adamy
 * @param align ustawienie wyrównania tekstu.
 * @param colspan jak¹ czeœæ tabeli zajmuje komórka, w wartoœciach ca³kowitych od 1 do 10
 * @param font czcionka 
 * */
	public void insertCell(PdfPTable table, String text, int align, int colspan, Font font){
 	   Paragraph parag = new Paragraph (new Phrase(text.trim(),font));
 	 
			insertCell(table,parag, align, colspan );
 	   
 	 }
 	 
	/** 
	 * funkcja umo¿liwiaj¹ca wk³¹danie to tabeli paragrafu o odpowiednim rozmiarze komórki i ustawieniach tekstu
	 * @param table tabela do której wk³adamy dane
	 * @param text tekst który w³ozony jest do paragrafu. 
	 * @param align ustawienie wyrównania tekstu.
	 * @param colspan jak¹ czeœæ tabeli zajmuje komórka, w wartoœciach ca³kowitych od 1 do 10
	 * @param font czcionka 
	 * */ 
    	public void insertCell(PdfPTable table, Paragraph text, int align, int colspan){
      	 
      	 
      	  PdfPCell cell = new PdfPCell(text);
      	  if(align!=-1)
      	  cell.setHorizontalAlignment(align);
      	
      	  cell.setColspan(colspan);
      	 
      	 
      	  table.addCell(cell);
      	   
      	 }
}