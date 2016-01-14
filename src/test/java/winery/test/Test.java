package winery.test;

import dbapi.DBManager;
import dbapi.User;

class Test {
	public static void main(String[] args) {
		
		//DBManager.changeUserData(1, "janNowak", "", "haslo", "Jan", "Nienowak", 0);
		//DBManager.addUser("NewBaseUser", "superTajne@1", "niepisz@domnie.pl", "New", "User", 1, 1500);
		System.out.println(DBManager.getUserById(1).getLogin());
		for (User user : DBManager.getUsers().values()) {
			System.out.println(user.getId()+" "+user.getLogin());
		}
		DBManager.changeUserData(22, "error", "", "", "", "", 0, 1234);
		System.out.println(DBManager.getUserById(1).getLogin());
		for (User user : DBManager.getUsers().values()) {
			System.out.println(user.getId()+" "+user.getLogin());
		}
		return;
	}
}
