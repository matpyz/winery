/**
 * 
 */
package winery.server;

import java.util.ArrayList;

/**
 * @author FL4SH
 *
 */
public class Communicator {

	private JSONObject returnObj = null;
	
	public void decodeIncome(JSONObject json) {
		String code = json.getCode();
		if(code.startsWith("PZK") || code.startsWith("PNP") || code.startsWith("PZS")){
			//report
			updateDB(json.getUser_id(), json.getUser_pass(), 
					json.getPlace(), json.getOther(), json.getDate());
		} else if (code.equals("OTH0")) {
			//update request
			updateRequest(json.getUser_id(), json.getUser_pass(), 
					json.getPlace(), json.getOther());
		} else if (code.equals("OTH2")) {
			//log in check
			login(json.getUser_id(), json.getUser_pass());
		} else if (code.equals("OTH4")) {
			//change password
			newPasswd(json.getUser_id(), json.getUser_pass(), json.getOther());
		}
		if(data == null) {
			System.err.println("ERROR! Given JSONObject has wrong format or holds"
					+ "not enough data.");
			return;
		}
		//Creating JSONObject for getOutput() method.
		//returnObj = JSONOperations.parseJSONToObject(data);
	}
	
	public JSONObject getOutput() {
		return returnObj;
	}
	
	private String newPasswd(String user_id, String user_pass, String other) {
		//ask for new password
		return null;
	}

	private String login(String user_id, String user_pass) {
		//authorize user
		return null;
	}

	private String updateRequest(String user_id, String user_pass,
			ArrayList<String> place, String other) {
		if(other.equals("date")) {
			//ask for date of last report for given place
		} else if (other.equals("code")) {
			//ask for code of last report for given place
		} else if (other.equals("all")) {
			//ask for last report for given place
		}
		return null;
	}

	private String updateDB(String user_id, String user_pass,
			ArrayList<String> place, String other, String date) {
		// TODO Connect with DB for setting additional status 
		return null;
		
	}
}
