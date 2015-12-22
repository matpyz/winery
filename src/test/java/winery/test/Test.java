package winery.test;

import dbapi.DBManager;

class Test {
	public static void main(String[] args) {
		
		DBManager.changeUserData(1, "janNowak", "", "haslo", "Jan", "Nienowak", 0);
		System.out.println(DBManager.getUserById(1).getLogin());
		return;
	}
}
