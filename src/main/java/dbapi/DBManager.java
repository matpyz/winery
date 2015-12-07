package dbapi;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class DBManager {

	String polaczenieURL;
	static Connection conn = null;
	public static DBManager dbManager = new DBManager();
	
	DBManager() {
		this.polaczenieURL = "jdbc:mysql://db4free.net/testwina?user=testwina&password=testwina";
	}
	
	
	/**
	 * @param query - metoda wywołująca zapytania do bazy
	 * @return zwraza wynik zapytania, który należy następnie obrobić, by następnie zamknąć połączenie
	 */
	ResultSet query(String query) {
		try {
			 
            conn = DriverManager.getConnection(polaczenieURL);
            Class.forName("com.mysql.jdbc.Driver");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            return rs;
		}
		catch(ClassNotFoundException wyjatek) {
            System.out.println("Problem ze sterownikiem");
            return null;
		}
		catch(SQLException wyjatek) {
			System.out.println("SQLException: " + wyjatek.getMessage());
			System.out.println("SQLState: " + wyjatek.getSQLState());
			System.out.println("VendorError: " + wyjatek.getErrorCode());
			return null;
		}
	}
	
	private static HashMap<Integer, User> users = null;
	
	/**
	 * @return Zwraca obiekty wszystkich użytkowników w bazie
	 */
	public static HashMap<Integer, User> getUsers() {
		if (DBManager.users == null) {
			HashMap<Integer, User> users = new HashMap<Integer, User>();
			DBManager.users = new HashMap<Integer, User>();
			String query = "SELECT `user`.`id`, `login`, `user`.`name`, `surname`, `email`, `groupId`, `group`.`name` as `groupName` FROM `user`, `user2group`, `group` WHERE `user`.`id`=`user2group`.`userId` AND `user2group`.`groupId`=`group`.`id`";
			ResultSet rs = dbManager.query(query);
			try {
				while (rs.next()) {
					User user = new User(rs.getInt("id"), rs.getString("login"), rs.getString("name"), rs.getString("surname"), rs.getString("email"), rs.getInt("groupId"), rs.getString("groupName"));
					users.put(rs.getInt("id"), user);
				}
				conn.close();
			}
			catch(SQLException ex) {
				System.out.println(ex.getMessage());
			}
			for (User user : users.values()) {
				user.setPermissions(DBManager.getPermissionsForGroupid(user.getGroupId()));
				DBManager.users.put(user.getId(), user);
			}
		}
		return DBManager.users;
	}
	
	/**
	 * Metoda odpowiedzialna za logowanie się użytkownika
	 * @param login - login użytkownika który chce się zalogować
	 * @param password - zaszyfrowane hasło, które będzie porównane z tym na serwerze
	 * @return Obiekt zalogowanego użytkownika
	 */
	public static User signIn(String login, String password) {
		String query = "SELECT `user`.`id`, `login`, `user`.`name`, `surname`, `email`, `groupId`, `group`.`name` as `groupName` FROM `user`, `user2group`, `group` WHERE `user`.`id`=`user2group`.`userId` AND `user2group`.`groupId`=`group`.`id` AND `login`='"+login+"' AND `password`='"+password+"'";
		ResultSet rs = dbManager.query(query);
		User user = null;
		try {
				user = new User(rs.getInt("id"), rs.getString("login"), rs.getString("name"), rs.getString("surname"), rs.getString("email"), rs.getInt("groupId"), rs.getString("groupName"));
				conn.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return user;
	}
	
	/**
	 * @param id - id użytkownika, który ma być usunięty
	 */
	public static void removeUserById(int id) {
		dbManager.query("DELETE FROM `user` WHERE `id`="+id);
		dbManager.query("DELETE FROM `user2group` WHERE `userId`="+id);
		if(DBManager.users != null) {
			DBManager.users.remove(id);
		}
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Matoda dodaje nowego użytkownika do bazy
	 * @param login - Login użytkownika
	 * @param password - Hasło użytkownika
	 * @param mail - Hasło użytkownika
	 * @param name - Imię użytkownika
	 * @param surname - Nazwisko użytkownika
	 * @param groupId - Id grupy do której będzie przypisany
	 */
	public static void addUser(String login, String password, String mail, String name, String surname, int groupId) {
		String query = "INSERT INTO `user` (`name`, `email`, `password`, `login`, `surname`) VALUES ('"+name+"', '"+mail+"', '"+password+"', '"+login+"', '"+surname+"');";
		dbManager.query(query);
		query = "SELECT `id` FROM `user` WHERE `login`='"+login+"' AND `password`='"+password+"'";
		ResultSet rs = dbManager.query(query);
		try {
			int id = rs.getInt("id");
			query = "INSERT INTO `user2group` (`name`, `groupId`) VALUES ('"+id+"', '"+groupId+"');";
			if (DBManager.users == null) {
				DBManager.getUsers();
			}
			else {
				query = "SELECT `user`.`id`, `login`, `user`.`name`, `surname`, `email`, `groupId`, `group`.`name` as `groupName` FROM `user`, `user2group`, `group` WHERE `user`.`id`=`user2group`.`userId` AND `user2group`.`groupId`=`group`.`id` AND `user`.`id`='"+id+"'";
				rs = dbManager.query(query);
				User user = new User(rs.getInt("id"), rs.getString("login"), rs.getString("name"), rs.getString("surname"), rs.getString("email"), rs.getInt("groupId"), rs.getString("groupName"));
				DBManager.users.put(rs.getInt("id"), user);

			}
			conn.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	/**
	 * @param userId - Id użytkownika
	 * @param oldPass - Stare hasło użytkownika
	 * @param newPass - Nowe hasło użytkownika
	 * @return Funkcja zwraca true jeśli zmiana się powiodła i false w przeciwnym wypadku
	 */
	public static boolean changeUserPassword(int userId, String oldPass, String newPass) {
		String query = "SELECT `id` FROM `user` WHERE `id` = "+userId+" AND `password='"+oldPass+"'`";
		ResultSet rs = dbManager.query(query);
		try {
			if(userId == rs.getInt("id")) {
				query = "UPDATE `user` SET `password` = '"+newPass+"' WHERE `user`.`id` = "+userId+";";
				rs = dbManager.query(query);
				query = "SELECT `id` FROM `user` WHERE `id` = "+userId+" AND `password='"+newPass+"'`";
				rs = dbManager.query(query);
				int id = rs.getInt("id");
				conn.close();
				if(userId == id) return true;
				else return false;
			}
			else {
				conn.close();
				return false;
			}
		}
		catch(SQLException ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
	
	/**
	 * @param groupId - Id grupy której chcemy pobrać uprawnienia
	 * @return HashMapa uprawnień
	 */
	public static HashMap<Integer, Permission> getPermissionsForGroupid(int groupId) {
		String query = "SELECT * FROM `permission`, `group2permission` WHERE `group2permission`.`groupId` = '"+groupId+"' AND `group2permission`.`permissionId` = `permission`.`id`";
		ResultSet rs = dbManager.query(query);
		HashMap<Integer, Permission> permissions = new HashMap<Integer, Permission>();
		try {
			while (rs.next()) {
				Permission permission = new Permission(rs.getInt("id"), rs.getString("name"));
				permissions.put(rs.getInt("id"), permission);
			}
			conn.close();
		}
		catch(SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return permissions;
	}
}
