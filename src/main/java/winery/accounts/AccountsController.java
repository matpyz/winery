package winery.accounts;

import java.util.ArrayList;
import java.util.List;

import winery.guardian.Guardian;
import winery.view.Actions;
import winery.view.Controller;
import winery.view.View;

public class AccountsController implements Controller {

	private AccountsModel model;
	private AccountsView view;

	/**
	 * @param model
	 *            AccountsModel do komunikacji z bazą
	 */
	public AccountsController() {
		this.model = new AccountsModel();
		this.view = new AccountsView(this);
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
			model.accessDB(null);
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
	List<String> getAccountData(String userId) {
		model.accessDB(null);
		return null;
	}

	/**
	 * Pobiera listę kont z bazy.
	 * 
	 * @return lista stringów z imionami_nazwiskami
	 */
	List<String> getAccountList() {
		model.accessDB(null);
		List<String> accounts = new ArrayList<>();
		accounts.add("Jan Kowalski");
		return accounts;
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
