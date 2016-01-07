package winery.accounts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import winery.view.Controller;
import winery.view.View;
import dbapi.*;

public class AccountsController implements Controller {

	private AccountsModel model;
	private AccountsView view;
	String response;

	private HashMap<String, Integer> userMap = new HashMap<>();

	/**
	 * @param model
	 *            AccountsModel do komunikacji z bazą
	 */
	public AccountsController() {
		model = new AccountsModel();
		view = new AccountsView(this);
		
		/* Rejestruje widok po czym wymusza aktualizacje. */
		model.register(view);
		getAccountList();
	}

	/**
	 * Dodaje nowe konto.
	 * 
	 * @param accountData
	 *            dane nowego konta
	 * @return string potwierdzający lub opisujący napotkany błąd
	 */
	public String newAccount(List<String> accountData) {
		// TODO: 
		return "Konto zostało dodane";
	}

	/**
	 * Modyfikuje konto.
	 * 
	 * @param accountData
	 * @return string potwierdzający lub opisujący napotkany błąd
	 */
	public String modifyAccount(List<String> accountData) {
		if(DBManager.changeUserData(userMap.get(accountData.get(0)), accountData.get(0),
				accountData.get(3), accountData.get(4), accountData.get(1),
				accountData.get(2), 0, 0)) {
			//getAccountList();
			return "Dane konta zostały zaktualizowane";
		}
		else
			return "Wystąpił błąd";
	}

	/**
	 * 
	 * 
	 * @param accountId
	 * @return string potwierdzający lub opisujący napotkany błąd
	 */
	public String deleteAccount(String accountId) {
		// TODO:
		return "Konto zostało usunięte";
	}

	/**
	 * Pobiera dane o konkretnym użytkowniku z bazy.
	 * 
	 * @param userId
	 *            id wybranego użytkownika
	 * @return lista stringów z danymi
	 */
	void getAccountData(String userLogin) {
		User user = DBManager
				.getUserById(userMap.get(userLogin));
		model.setAccountData(user.getData());
	}

	/**
	 * Pobiera listę kont z bazy.
	 * 
	 * @return lista stringów z imionami_nazwiskami
	 */
	void getAccountList() {
		HashMap<Integer, User> users = DBManager.getUsers();
		//System.out.println(users.values().toString());
		List<User> userList = new ArrayList<>(users.values());
		ArrayList<String> userData = new ArrayList<>();
		for (User u : userList) {
			userData.add(u.getName() + " " + u.getSurname() + " ("
					+ u.getLogin() + ")");
			userMap.put(u.getLogin(), u.getId());
		}
		model.setAccountList(userData);
	}

	@Override
	public View getView() {
		return view;
	}

	@Override
	public String getTitle() {
		return "Konta";
	}

	public static String getID() {
		return "accounts";
	}
}
