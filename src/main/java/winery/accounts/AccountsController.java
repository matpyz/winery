package winery.accounts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dbapi.DBManager;
import dbapi.User;
import winery.view.Controller;
import winery.view.View;

public class AccountsController implements Controller {

	private AccountsModel model;
	private AccountsView view;
	String response = "";

	private HashMap<String, Integer> userIdMap = new HashMap<>();

	/**
	 * @param model
	 *            AccountsModel do komunikacji z bazą
	 */
	public AccountsController() {
		model = new AccountsModel();
		view = new AccountsView(this);

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
	public void newAccount(String name, String surname, String login, String password, String mail, String payment,
			String groupName) {
		DBManager.addUser(login, password, mail, name, surname, DBManager.getGroupIdByName(groupName),
				new Integer(payment));
		getAccountList();
		response = "konto " + login + " zostało dodane";
	}

	/**
	 * Modyfikuje konto.
	 * 
	 * @param accountData
	 * @return string potwierdzający lub opisujący napotkany błąd
	 */
	public void modifyAccount(String name, String surname, String login, String password, String mail, String payment,
			String groupName) {
		// DBManager.changeUserData(userId, login, password, mail, name,
		// surname, groupId, payment)
		int groupId = DBManager.getGroupIdByName(groupName);
		System.out.println(payment);
		if (DBManager.changeUserData(userIdMap.get(login), login, password, mail, name, surname, groupId,
				new Integer(payment))) {
			getAccountList();
			response = "dane konta '" + login + "' zostały zaktualizowane";
		} else
			response = "wystąpił błąd przy zmianie danych";
	}

	/**
	 * 
	 * 
	 * @param accountId
	 * @return string potwierdzający lub opisujący napotkany błąd
	 */
	public void deleteAccount(String userLogin) {
		DBManager.removeUserById(userIdMap.get(userLogin));
		getAccountList();
		response = "konto '" + userLogin + "' zostało usunięte";
	}

	/**
	 * Pobiera dane o konkretnym użytkowniku z bazy.
	 * 
	 * @param userId
	 *            id wybranego użytkownika
	 * @return lista stringów z danymi
	 */
	void getAccountData(String userLogin) {
		User user = DBManager.getUserById(userIdMap.get(userLogin));
		model.setAccountData(user.getData());
		response = "";
	}

	/**
	 * Pobiera listę kont z bazy.
	 * 
	 * @return lista stringów z imionami_nazwiskami
	 */
	void getAccountList() {
		HashMap<Integer, User> users = DBManager.getUsers();

		List<User> userList = new ArrayList<>(users.values());
		ArrayList<String> userData = new ArrayList<>();
		for (User u : userList) {
			userData.add(u.getName() + " " + u.getSurname() + " (" + u.getLogin() + ")");
			userIdMap.put(u.getLogin(), u.getId());
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
