package dbapi;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class DBManager {

	String polaczenieURL;
	Connection conn = null;
	public static DBManager dbManager = new DBManager();
	
	DBManager() {
		this.polaczenieURL = "jdbc:mysql://mysql.hostinger.co.uk/u516903769_test?user=u516903769_test&password=wino12";
	}
	ResultSet query(String query) {
		try {
			 
            conn = DriverManager.getConnection(polaczenieURL);
            Class.forName("com.mysql.jdbc.Driver");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            conn.close();
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
	
	public static HashMap<Integer, User> getUsers() {
		if (DBManager.users == null) {
			DBManager.users = new HashMap<Integer, User>();
			String query = "SELECT `user`.`id`, `login`, `user`.`name`, `surname`, `email`, `groupId`, `group`.`name` FROM `user`, `user2group`, `group` WHERE `user`.`id`=`user2group`.`userId` AND `user2group`.`groupId`=`group`.`id`";
			ResultSet rs = dbManager.query(query);
			try {
				while (rs.next()) {
					User user = new User(rs.getInt("id"), rs.getString("login"), rs.getString("name"), rs.getString("surname"), rs.getString("email"), rs.getInt("groupId"), rs.getString("groupName"));
					DBManager.users.put(rs.getInt("id"), user);
				}
			}
			catch(SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
		return DBManager.users;
	}
	
	public static User signIn(String login, String password) {
		String query = "SELECT `user`.`id`, `login`, `user`.`name`, `surname`, `email`, `groupId`, `group`.`name` FROM `user`, `user2group`, `group` WHERE `user`.`id`=`user2group`.`userId` AND `user2group`.`groupId`=`group`.`id` AND `login`='"+login+"' AND `password`='"+password+"'";
		ResultSet rs = dbManager.query(query);
		User user = null;
		try {
				user = new User(rs.getInt("id"), rs.getString("login"), rs.getString("name"), rs.getString("surname"), rs.getString("email"), rs.getInt("groupId"), rs.getString("groupName"));
		}
		catch(SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return user;
	}
	
	public static void removeUserById(int id) {
		dbManager.query("DELETE FROM `user` WHERE `id`="+id);
		dbManager.query("DELETE FROM `user2group` WHERE `userId`="+id);
		if(DBManager.users != null) {
			DBManager.users.remove(id);
		}
	}
	
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
				query = "SELECT `user`.`id`, `login`, `user`.`name`, `surname`, `email`, `groupId`, `group`.`name` FROM `user`, `user2group`, `group` WHERE `user`.`id`=`user2group`.`userId` AND `user2group`.`groupId`=`group`.`id` AND `user`.`id`='"+id+"'";
				rs = dbManager.query(query);
				User user = new User(rs.getInt("id"), rs.getString("login"), rs.getString("name"), rs.getString("surname"), rs.getString("email"), rs.getInt("groupId"), rs.getString("groupName"));
				DBManager.users.put(rs.getInt("id"), user);

			}
		}
		catch(SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}
}
