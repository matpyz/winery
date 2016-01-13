package dbapi;

public class Excise {
	
	private int id;
	private String name;
	private int amount;
	
	
	public Excise(int id, String name, int amount) {
		this.id = id;
		this.name = name;
		this.amount = amount;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getAmount() {
		return amount;
	}


	public void setAmount(int amount) {
		this.amount = amount;
	}


	public int getId() {
		return id;
	}

}
