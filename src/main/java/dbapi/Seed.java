package dbapi;

public class Seed {

	private int id;
	private String name;
	private int quantity;
	private String additional;
	
	public Seed(int id, String name, int quantity, String additional) {
		super();
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.additional = additional;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getAdditional() {
		return additional;
	}

	public void setAdditional(String additional) {
		this.additional = additional;
	}

	public int getId() {
		return id;
	}
	
}
