package winery.accounts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import winery.guardian.Guardian;
import winery.view.Actions;
import winery.view.Controller;
import winery.view.View;
import dbapi.*;

public class AccountsController implements Controller {

	private AccountsModel model;
	private AccountsView view;

	private HashMap<String, Integer> userMap = new HashMap<>();

	/**
	 * @param model
	 *            AccountsModel do komunikacji z bazą
	 */
	public AccountsController() {
		this.model = new AccountsModel();
		this.view = new AccountsView(this);
		// this.userMap = new HashMap<>();
	}

	/**
	 * Dodaje nowe konto.
	 * 
	 * @param accountData
	 *            dane nowego konta
	 * @return string potwierdzający lub opisujący napotkany błąd
	 */
	public String newAccount(List<String> accountData) {
		if (Guardian.checkPermission(Actions.ADD_ACCOUNT))
			model.accessDB(null);
		else
			return "Brak uprawnień";
		return "Konto zostało dodane";
	}

	/**
	 * Modyfikuje konto.
	 * 
	 * @param accountData
	 * @return string potwierdzający lub opisujący napotkany błąd
	 */
	public String modifyAccount(List<String> accountData) {
		if (Guardian.checkPermission(Actions.EDIT_ACCOUNT)) {
			DBManager.changeUserData(userMap.get(accountData.get(0)), accountData.get(0),
					accountData.get(3), accountData.get(4), accountData.get(1),
					accountData.get(2), 0);
			return "Dane konta zostały zaktualizowane";
		} else
			return "Brak uprawnień";
	}

	/**
	 * 
	 * 
	 * @param accountId
	 * @return string potwierdzający lub opisujący napotkany błąd
	 */
	public String deleteAccount(String accountId) {
		if (Guardian.checkPermission(Actions.REMOVE_ACCOUNT))
			model.accessDB(null);
		else
			return "Brak uprawnień";
		return "Konto zostało usunięte";
	}

	/**
	 * Pobiera dane o konkretnym użytkowniku z bazy.
	 * 
	 * @param userId
	 *            id wybranego użytkownika
	 * @return lista stringów z danymi
	 */
	List<String> getAccountData(String userData) {
		User user = DBManager
				.getUserById(userMap.get(userData.split("[()]")[1]));
		return user.getData();
	}

	/**
	 * Pobiera listę kont z bazy.
	 * 
	 * @return lista stringów z imionami_nazwiskami
	 */
	List<String> getAccountList() {
		HashMap<Integer, User> users = DBManager.getUsers();
		List<User> userList = new ArrayList<>(users.values());
		System.out.println(userList.toString());
		List<String> userData = new ArrayList<>();
		for (User u : userList) {
			userData.add(u.getName() + " " + u.getSurname() + " ("
					+ u.getLogin() + ")");
			userMap.put(u.getLogin(), u.getId());
		}
		return userData;
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
