package dbapi;

import java.util.Date;

public class FieldCell {

	private int id;
	private int row;
	private int column;
	private String section;
	private int currentStatusId;
	private String description;
	private FieldStatus fieldStatus;
	private Date date;
	
	public FieldCell(int id, int row, int column, String section, int currentStatusId, String description, Date date) {
		this.id = id;
		this.row = row;
		this.column = column;
		this.section = section;
		this.currentStatusId = currentStatusId;
		this.description = description;
		this.fieldStatus = null;
		this.date = date;
		}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
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
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
