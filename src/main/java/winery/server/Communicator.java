/**
 * 
 */
package winery.server;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import dbapi.DBManager;
import dbapi.FieldCell;
import dbapi.FieldStatus;
import dbapi.User;

/**
 * @author FL4SH Klasa odpowiedzialna za komunikację z aplikacją mobilną. Jej
 *         zadaniem jest rozpakować otrzymanego JSON'a i na podstawie zawartych
 *         w nim informacji podjąć właściwą akcję. Na chwilę obecną pozwala
 *         jedynie na logowanie oraz zmianę hasła uzytkownika.
 */
public class Communicator {

	private JSONObject returnObj = null;

	/**
	 * @param json
	 *            JSONObject otrzymany od aplikacji mobilnej.
	 */
	public Communicator(JSONObject json) {
		decodeIncome(json);
	}

	/**
	 * @return JSONObject, który należy odesłać do aplikacji mobilnej.
	 */
	public JSONObject getOutput() {
		return returnObj;
	}

	/**
	 * Metoda rozkodowywująca JSONObject i podejmująca właściwą operację
	 * 
	 * @param json
	 */
	private void decodeIncome(JSONObject json) {
		String code = json.getCode();
		returnObj = new JSONObject();
		returnObj.setType("confirmation");
		returnObj.setCode(code);
		if (code.startsWith("PZK") || code.startsWith("PNP") || code.startsWith("PZS")) {
			// report
			updateDB(json.getUser_id(), json.getUser_pass(), json.getSector(), json.getRow(), json.getColumn(),
					json.getCode(), json.getOther(), json.getDate());
		} else if (code.equals("OTH0")) {
			// update request
			updateRequest(json.getUser_id(), json.getUser_pass(), json.getSector(), json.getRow(), json.getColumn(),
					json.getOther());
		} else if (code.equals("OTH2")) {
			// log in check
			login(json.getUser_id(), json.getUser_pass());
		} else if (code.equals("OTH4")) {
			// change password
			newPasswd(json.getUser_id(), json.getUser_pass(), json.getOther());
		}
	}

	/**
	 * Ustaw nowe hasło dla użytkownika.
	 * 
	 * @param user_id
	 * @param user_pass
	 * @param other
	 */
	private void newPasswd(String user_id, String user_pass, String other) {
		// ask for new password
		User u = login(user_id, user_pass);
		if (u == null) {
			return;
		}
		if (DBManager.changeUserPassword(u.getId(), user_pass, other) == false) {
			returnObj.setOther("ERROR");
			System.err.println("ERROR: User could not change his password.");
			return;
		}
		returnObj.setOther("OK");
	}

	/**
	 * Zwraca zalogowanego użytkownika, bądź null jeżeli się nie udało.
	 * 
	 * @param user_id
	 * @param user_pass
	 * @return
	 */
	private User login(String user_id, String user_pass) {
		// authorize user
		User u = DBManager.signIn(user_id, user_pass);
		if (u == null) {
			returnObj.setOther("ERROR");
			System.err.println("ERROR: User cannot be loged in.");
			return u;
		}
		returnObj.setOther("OK");
		return u;
	}

	/**
	 * Pobiera informacje z BD na temat stanu pola. Ignoruje "zdrowe" pola.
	 * 
	 * @param user_id
	 * @param user_pass
	 * @param sector
	 * @param row
	 * @param column
	 * @param other
	 */
	private void updateRequest(String user_id, String user_pass, String sector, String row, String column,
			String other) {
		if(login(user_id, user_pass) != null){
			FieldCell field = searchForFieldCell(sector, Integer.parseInt(column), Integer.parseInt(row));
			FieldStatus stat = DBManager.getFieldStatusById(field.getCurrentStatusId());
			if(field == null) {
				returnObj.setOther("ERROR");
				System.err.println("ERROR: Given field do not exists. "
						+ "Sector " + sector + ", column " + column + ", row " + row + ".");
				return;
			}
			if (other.equals("date")) {
				// ask for date of last report for given place
				if(field.getFieldStatus() != null) {
					returnObj.setOther(new SimpleDateFormat("dd-MM-yyyy").format(field.getDate()));
				}
				
			} else if (other.equals("code")) {
				// ask for code of last report for given place
				returnObj.setOther(stat.getCode());
				
				
			} else if (other.equals("all")) {
				// ask for last report for given place
				String send = createBlob();
				returnObj.setOther(send);
			}
		}
	}

	/**
	 * Aktualizuje informacje w BD na temat danego pola.
	 * 
	 * @param user_id
	 * @param user_pass
	 * @param sector
	 * @param row
	 * @param column
	 * @param other
	 * @param date
	 * @param string 
	 */
	private void updateDB(String user_id, String user_pass, String sector, String row, String column, String code,
			String other, String date) {
		if(login(user_id, user_pass) != null){
			HashMap<Integer, FieldStatus> stats = DBManager.getAllFieldsStatuses();
			int currentStatusId = 0;
			for(int i : stats.keySet()) {
				if(stats.get(i).getCode().equals(code)) {
					currentStatusId = stats.get(i).getId();
				}
			}
			try {
				Date d = new SimpleDateFormat("dd-MM-yyyy").parse(date);
				DBManager.addFieldCell(Integer.parseInt(row), Integer.parseInt(column), sector, currentStatusId, 
						other, new java.sql.Date(d.getTime()));
			} catch (ParseException e) {
				e.printStackTrace();
				returnObj.setOther("ERROR");
				return;
			}
			
			returnObj.setOther("OK");
		}
		returnObj.setOther("ERROR");
	}
	
	/**
	 * Tworzy potężnego Stringa przechowującego wszystkie pola, na których
	 * coś się działo.
	 * @return
	 */
	private String createBlob() {
		HashMap<Integer, FieldCell> fields = DBManager.getAllFieldsCells();
		StringBuilder sb = new StringBuilder();
		for(int i : fields.keySet()) {
			FieldCell field = fields.get(i);
			if(field.getFieldStatus() != null) {
				if(sb.length()>0){
					sb.append("|");
				}
				sb.append(field.getSection());
				sb.append("/");
				sb.append(field.getRow());
				sb.append("/");
				sb.append(field.getColumn());
				sb.append("/");
				FieldStatus stat = DBManager.getFieldStatusById(field.getCurrentStatusId());
				sb.append(stat.getCode());
			}
		}
		return sb.toString();
	}
	
	/**
	 * Wyszukuje porządany kawałek pola.
	 * @param sector
	 * @param column
	 * @param row
	 * @return
	 */
	private FieldCell searchForFieldCell(String sector, int column, int row) {
		HashMap<Integer, FieldCell> fields = DBManager.getAllFieldsCells();
		for(int i : fields.keySet()) {
			FieldCell field = fields.get(i);
			if(field.getSection() == sector && field.getColumn() == column && field.getRow() == row) {
				return field;
			}
		}
		return null;
	}
}
