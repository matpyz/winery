package dbapi;

public class Seed {

	private int id;
	private String name;
	private int quantity;
	private int protectedOrigin;
	private String additional;
	private String additional2;
	private int year;

	public Seed(int id, String name, int quantity, int protectedOrigin, int year, String additional, String additional2) {
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.additional = additional;
		this.year = year;
		this.protectedOrigin = protectedOrigin;
		this.additional2 = additional2;
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
	
	public int getProtectedOrigin() {
		return protectedOrigin;
	}

	public void setProtectedOrigin(int protectedOrigin) {
		this.protectedOrigin = protectedOrigin;
	}

	public String getAdditional2() {
		return additional2;
	}

	public void setAdditional2(String additional2) {
		this.additional2 = additional2;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
}
