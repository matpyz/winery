package dbapi;

public class Permission {

	private int id;
	private String name;
	
	public Permission(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
}
