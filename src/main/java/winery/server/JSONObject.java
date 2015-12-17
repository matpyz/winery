package winery.server;

/**
 * Klasa reprezentująca komunikaty JSON, jakie może odbierać serwer
 * 
 * @author aleks
 *
 */
public class JSONObject {
	// Pola użyteczne dla aplikacji mobilnej
	private String type;
	private String user_id;
	private String user_pass;
	private String code;
	private String sector;
	private String row;
	private String column;
	private String other;
	private String date;

	/*
	 * TODO: Tutaj będą dalsze pola, dla innych komunikatów niż z aplikacji
	 * mobilnej
	 */
	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_pass() {
		return user_pass;
	}

	public void setUser_pass(String user_pass) {
		this.user_pass = user_pass;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
