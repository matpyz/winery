/**
 * 
 */
package winery.server;

import dbapi.DBManager;
import dbapi.User;

/**
 * @author FL4SH
 *
 */
public class Communicator {

	private JSONObject returnObj = null;
	
	public Communicator(JSONObject json) {
		decodeIncome(json);
	}
	
	public JSONObject getOutput() {
		return returnObj;
	}
	
	private void decodeIncome(JSONObject json) {
		String code = json.getCode();
		returnObj = new JSONObject();
		returnObj.setType("confirmation");
		returnObj.setCode(code);
		if(code.startsWith("PZK") || code.startsWith("PNP") || code.startsWith("PZS")){
			//report
			updateDB(json.getUser_id(), json.getUser_pass(), 
					json.getSector(), json.getRow(), json.getColumn(),
					json.getOther(), json.getDate());
		} else if (code.equals("OTH0")) {
			//update request
			updateRequest(json.getUser_id(), json.getUser_pass(), 
					json.getSector(), json.getRow(), json.getColumn(), json.getOther());
		} else if (code.equals("OTH2")) {
			//log in check
			login(json.getUser_id(), json.getUser_pass());
		} else if (code.equals("OTH4")) {
			//change password
			newPasswd(json.getUser_id(), json.getUser_pass(), json.getOther());
		}
	}
	
	private void newPasswd(String user_id, String user_pass, String other) {
		//ask for new password
		User u = login(user_id, user_pass);
		if(u == null) { return; }
		if(DBManager.changeUserPassword(u.getId(), user_pass, other) == false){
			returnObj.setOther("ERROR");
			System.err.println("ERROR: User could not change his password.");
			return;
		}
		returnObj.setOther("OK");	
	}

	private User login(String user_id, String user_pass) {
		//authorize user
		User u = DBManager.signIn(user_id, user_pass);
		if(u == null) {
			returnObj.setOther("ERROR");
			System.err.println("ERROR: User cannot be loged in.");
			return u;
		}
		returnObj.setOther("OK");
		return u;
	}

	private void updateRequest(String user_id, String user_pass,
			String sector, String row, String column, String other) {
		if(other.equals("date")) {
			//ask for date of last report for given place
		} else if (other.equals("code")) {
			//ask for code of last report for given place
		} else if (other.equals("all")) {
			//ask for last report for given place
		}
	}

	private void updateDB(String user_id, String user_pass,
			String sector, String row, String column, String other, String date) {
		// TODO Connect with DB for setting additional status 
		
	}
}
