package winery.documents;

import javax.swing.JOptionPane;

import winery.documents.ChangeDataDocumentGui;

/**
Klasa s³u¿aca do obs³ugi gui WybieranieDokumentu.  
@author Przemys³aw Iskra
@version 1.0  */
public class SelectGenerateDocumentModel {
	
	String title, path="";
//	title to zmienna odpowiadaj¹ca za rozpoznanie dokumentu
	// to zmienna zawieraj¹ca scie¿ka do katalogu i nazwê pliku
	String[] docTable = new String[] {"<html>Deklaracja o iloœci win bia³ych lub czerwonych <br> wprowadzonych do obrotu w poprzednimroku gospodarczym <br>",
	"<html>Testowy tworzy uproszczony dokument. "};
	DocumentBasic doc;
//   docTable zawiera dokumenty do wyboru
	public SelectGenerateDocumentModel()
   {
	   
   }
   
   public String getdocTable(int i)
   {   if(i<docTable.length)
	   return docTable[i];
       else return "";
   }
   public String[] getTable()
   {
	   return docTable;
   }
   
   public void setVtitle(String title)
   {
	   this.title=title;
	   doc=getDokument(title);
	  
   }
   public String getVtitle()
   { //ustawiæ, ¿e tytu³ tworzy te¿ Dokument. 
	  return title;
   }
   public void setVpath(String path)
   {
	   this.path=path;
   }
   public String getVpath()
   {
	  return path;
   }
   
   public DocumentBasic getDokument(String title)
   {  //Jak bêdzie wiêcej dokumentów to napisaæ lepsza wersje tej funkcji. We
	   if (title.equals(docTable[0])) 
			{DocumentOne doc =new DocumentOne();
			
			return doc;
			//doc.createPDF(path, data);   //Generowanie dokumentu. 
			} 
			else if (title.equals(docTable[1]))
			{
				DocumentBasic doc =new DocumentBasic();  
				return doc;
				//doc2.createPDF(path , data);  //Generowanie dokumentu. 

			}
	   return null;
   }
   
   public DocumentBasic getDokument()
   {
	   return getDokument(title);
   }
   /**
    * Funkcja generuj¹ca dokument. 
    * @param info informacje o firmie. 
    * @param data  dane jakie dokument wymaga od firmy
    */
   
   public void generate( String info, String [][] data)
   {  DocumentBasic  doc=getDokument(title);
      doc.createPDF(path, info, data);
	   
   }
   /**
    * 
    * @param path œcie¿ka do pliku
    * @param firmInfo informacje o firmie
    */
   
   
   public void createTableData(String path, String firmInfo) {
		String[][] data = new String[doc.size][doc.size2];
		for(int i=0; i<doc.size; i++)
			{for(int k=0; k<doc.size2; k++)
			    data[i][k]="";
			}
		if(doc.needtable==true)
			{
			
			
			@SuppressWarnings("unused")
			
			ChangeDataDocumentGui  zm= new ChangeDataDocumentGui(doc.horizontal, doc.vertical, data, path, firmInfo, new DocumentOne());
			}
		else
		{
			
			doc.createPDF(path,firmInfo,data);
			JOptionPane.showMessageDialog(null, "Dokument przygotowany. ");
			
		}
		//Otworzenia okienka do poprawy danych, ale te dane poprawione tutaj nie wracaj¹, bo jeszcze tego nie zrobi³em. :D
		//return data;
	}
   
}
