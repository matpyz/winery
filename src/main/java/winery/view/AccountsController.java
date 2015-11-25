package winery.view;

import java.util.List;

import winery.model.AccountsModel;

class AccountsController implements Controller {

	AccountsModel model;
	AccountsView view;

	/**
	 * @param model
	 *            AccountsModel do komunikacji z bazą
	 */
	public AccountsController(AccountsModel model, AccountsView view) {
		this.model = model;
		this.view = view;
	}

	/**
	 * Sprawdza czy użytkownik ma uprawnienia dla danej akcji.
	 * 
	 * @param action
	 *            enum typu Actions
	 * @return boolean
	 */
	public boolean checkPrivileges(Actions action) {
		model.accessDB(null);
		return false;
	}

	/**
	 * Dodaje nowe konto.
	 * 
	 * @param accountData
	 *            dane nowego konta
	 * @return string potwierdzający lub opisujący napotkany błąd
	 */
	public String newAccount(List<String> accountData) {
		if (checkPrivileges(Actions.ADD_ACCOUNT))
			model.accessDB(null);
		else
			return "Brak uprawnień";
		return null;
	}

	/**
	 * Usuwa konto.
	 * 
	 * @param accountId
	 * @return string potwierdzający lub opisujący napotkany błąd
	 */
	public String modifyAccount(List<String> accountData) {
		if (checkPrivileges(Actions.EDIT_ACCOUNT))
			model.accessDB(null);
		else
			return "Brak uprawnień";
		return null;
	}

	/**
	 * 
	 * 
	 * @param accountId
	 * @return string potwierdzający lub opisujący napotkany błąd
	 */
	public String deleteAccount(String accountId) {
		if (checkPrivileges(Actions.REMOVE_ACCOUNT))
			model.accessDB(null);
		else
			return "Brak uprawnień";
		return null;
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
		return null;
	}

	@Override
	public View getView() {
		return view;
	}

	@Override
	public String getTitle() {
		return "Konta";
	}

}
