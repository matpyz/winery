package dbapi;

import java.sql.Date;
import java.util.HashMap;

public class Invoice {
	
	private int id;
	private int creatorId;
	private String name;
	private String receiverName;
	private String receiverAddress;
	private String receiverNIP;
	private String receiverDetails;
	private String receiverBankAccount;
	private Date creationDate;
	private HashMap<Integer, InvoiceDetails> invoiceDetails = null;
	
	public Invoice(int id, int creatorId, String name, String receiverName, String receiverAddress, String receiverNIP,
			String receiverDetails, String receiverBankAccount, Date creationDate) {
		super();
		this.id = id;
		this.creatorId = creatorId;
		this.name = name;
		this.receiverName = receiverName;
		this.receiverAddress = receiverAddress;
		this.receiverNIP = receiverNIP;
		this.receiverDetails = receiverDetails;
		this.receiverBankAccount = receiverBankAccount;
		this.creationDate = creationDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public String getReceiverNIP() {
		return receiverNIP;
	}

	public void setReceiverNIP(String receiverNIP) {
		this.receiverNIP = receiverNIP;
	}

	public String getReceiverDetails() {
		return receiverDetails;
	}

	public void setReceiverDetails(String receiverDetails) {
		this.receiverDetails = receiverDetails;
	}

	public String getReceiverBankAccount() {
		return receiverBankAccount;
	}

	public void setReceiverBankAccount(String receiverBankAccount) {
		this.receiverBankAccount = receiverBankAccount;
	}

	public int getId() {
		return id;
	}

	public int getCreatorId() {
		return creatorId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public HashMap<Integer, InvoiceDetails> getInvoiceDetails() {
		return invoiceDetails;
	}

	public void setInvoiceDetails(HashMap<Integer, InvoiceDetails> invoiceDetails) {
		this.invoiceDetails = invoiceDetails;
	}
	
}
