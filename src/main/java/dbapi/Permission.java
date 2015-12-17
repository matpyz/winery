package dbapi;

public class Permission {

	private int id;
	private String name;
	private int access;

	public Permission(int id, String name, int access) {
		super();
		this.id = id;
		this.name = name;
		this.access = access;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getAccess() {
		return access;
	}

}
