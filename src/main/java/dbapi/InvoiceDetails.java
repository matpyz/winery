package dbapi;

public class InvoiceDetails {
	
	private int id;
	private int invoiceId;
	private String name;
	private int quantity;
	private int baseprice;
	
	public InvoiceDetails(int id, int invoiceId, String name, int quantity, int baseprice) {
		this.id = id;
		this.invoiceId = invoiceId;
		this.name = name;
		this.quantity = quantity;
		this.baseprice = baseprice;
	}
	
	public InvoiceDetails(int invoiceId, String name, int quantity, int baseprice) {
		this.invoiceId = invoiceId;
		this.name = name;
		this.quantity = quantity;
		this.baseprice = baseprice;
	}

	public int getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
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

	public int getBaseprice() {
		return baseprice;
	}

	public void setBaseprice(int baseprice) {
		this.baseprice = baseprice;
	}

	public float getFloatBaseprice() {
		return (float)((float)(baseprice)/100.0);
	}

	public void setFloatBaseprice(float fBaseprice) {
		baseprice = (int)(fBaseprice*100);
	}

	public int getId() {
		return id;
	}

	
}
