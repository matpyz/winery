package dbapi;

public class FieldStatus {
	
	private int id;
	private String code;
	private String category;
	private String subcategory;
	
	public FieldStatus(int id, String code, String category, String subcategory) {
		this.id = id;
		this.code = code;
		this.category = category;
		this.subcategory = subcategory;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getSubcategory() {
		return subcategory;
	}
	
	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}
	
	public int getId() {
		return id;
	}

}
