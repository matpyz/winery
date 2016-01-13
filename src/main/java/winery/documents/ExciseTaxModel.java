package winery.documents;

import dbapi.DBManager;
import dbapi.Excise;
import winery.model.Model;

public class ExciseTaxModel extends Model {

	private int amount;
	private int id = 4;
	
	ExciseTaxModel() {
		
	}
	
	public void firstUse() {
		Excise exc = DBManager.getExciseById(id);
		System.out.println("Konstruktor");
		/*
		 * będę używał tylko id = 1
		 * Jeśli exc = DBManager.getExciseById(0) == null, to znaczy, że pierwszy raz 
			 * uruchamiam program, nie mam jeszcze pasków w BD
			 * W takim razie dodaję paski
			 * exc = addExcise("paski", 0);
			 * exc.getId() powinno być 0
		 * ELSE
		 * excFirst zawiera informacje o ilosci pasków w BD jako excFirst.getAmount()
		 */
		if (exc == null) {
			exc = DBManager.addExcise("paski", 0);
			if (exc == null) {
				System.out.println("Problem z BD");
				return;
			}
			if (exc.getId() != id) System.out.println("Różne od 1 i równe " + exc.getId());
		}
		int amountNow = exc.getAmount();
		this.setAmount(amountNow);

	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int a) {
		System.out.println("setter: " + a);
		this.amount = a;
		change();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int a) {
		this.id = a;
		change();
	}
	
	
}
