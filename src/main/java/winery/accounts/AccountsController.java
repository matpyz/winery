package winery.accounts;

import java.util.List;

import winery.guardian.Guardian;
import winery.view.Actions;
import winery.view.Controller;
import winery.view.View;

public class AccountsController implements Controller {

	AccountsModel model;
	AccountsView view;

	/**
	 * @param model
	 *            AccountsModel do komunikacji z bazą
	 */
	public AccountsController() {
		this.model = new AccountsModel();
		this.view = new AccountsView(model);
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
		return null;
	}

	/**
	 * Usuwa konto.
	 * 
	 * @param accountId
	 * @return string potwierdzający lub opisujący napotkany błąd
	 */
	public String modifyAccount(List<String> accountData) {
		if (Guardian.checkPermission(Actions.EDIT_ACCOUNT))
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
		if (Guardian.checkPermission(Actions.REMOVE_ACCOUNT))
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
