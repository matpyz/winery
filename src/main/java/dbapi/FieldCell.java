package dbapi;

public class FieldCell {

	private int id;
	private String row;
	private String column;
	private String section;
	private int currentStatusId;
	private String description;
	private FieldStatus fieldStatus;
	
	public FieldCell(int id, String row, String column, String section, int currentStatusId, String description) {
		this.id = id;
		this.row = row;
		this.column = column;
		this.section = section;
		this.currentStatusId = currentStatusId;
		this.description = description;
		this.fieldStatus = null;
	}

	public String getRow() {
		return row;
	}

	public void setRow(String row) {
		this.row = row;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public int getCurrentStatusId() {
		return currentStatusId;
	}

	public void setCurrentStatusId(int currentStatusId) {
		this.currentStatusId = currentStatusId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public FieldStatus getFieldStatus() {
		return fieldStatus;
	}

	public void setFieldStatus(FieldStatus fieldStatus) {
		this.fieldStatus = fieldStatus;
	}
	
}
