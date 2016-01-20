package winery.accounts;

import java.util.ArrayList;

import winery.model.Model;

/**
 * @author Kamil L
 *
 */
public class AccountsModel extends Model {

	private ArrayList<String> accountList;
	private ArrayList<String> userData;

	public ArrayList<String> getAccountList() {
		return accountList;
	}
	
	public void setAccountList(ArrayList<String> userData) {
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