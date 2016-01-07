package winery.accounts;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import winery.model.Model;

/**
 * @author Kamil L
 *
 */
public class AccountsModel extends Model {

	private HashMap<String, Integer> userMap = new HashMap<>();
	private ArrayList<String> accountList;
	private ArrayList<String> userData;

	public AccountsModel() {
		// TODO Auto-generated constructor stub
	}

	public ResultSet accessDB(String query) {
		// TODO DB access
		return null;
	}

	public ArrayList<String> getAccountList() {
		return accountList;
	}
	
	public void setAccountList(ArrayList<String> userData) {
		System.out.println(userData.toString());
		accountList = userData;
		change();
	}

	public ArrayList<String> getAccountData() {
		return userData;
	}
	
	public void setAccountData(ArrayList<String> data) {
		userData = data;
		change();
	}
}