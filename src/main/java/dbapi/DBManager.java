package dbapi;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class DBManager {

	String polaczenieURL;
	static Connection conn = null;
	public static DBManager dbManager = new DBManager();

	private static HashMap<Integer, User> users = null;
	private static HashMap<Integer, Document> documents = null;
	private static HashMap<Integer, Event> events = null;
	private static HashMap<String, User2Event> users2events = null;
	private static HashMap<String, Group2Event> groups2events = null;
	private static HashMap<Integer, Invoice> invoices = null;

	DBManager() {
		this.polaczenieURL = "jdbc:mysql://46.101.252.224/winiery?user=root&password=winieryWPPT";
	}
	
	private void createConnection() {
		try {
			if (conn == null || conn.isClosed()) {
				conn = DriverManager.getConnection(polaczenieURL);
				Class.forName("com.mysql.jdbc.Driver");
			}
				
		} catch (ClassNotFoundException wyjatek) {
			System.out.println("Problem ze sterownikiem");
		} catch (SQLException wyjatek) {
			System.out.println("SQLException: " + wyjatek.getMessage());
			System.out.println("SQLState: " + wyjatek.getSQLState());
			System.out.println("VendorError: " + wyjatek.getErrorCode());
		}
	}

	/**
	 * @param query
	 *            - metoda wywołująca zapytania do bazy
	 * @return zwraza wynik zapytania, który należy następnie obrobić, by
	 *         następnie zamknąć połączenie
	 */
	ResultSet selectQuery(String query) {
		try {
			dbManager.createConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = null;
			rs = stmt.executeQuery(query);

			return rs;
		} catch (SQLException wyjatek) {
			System.out.println("SQLException: " + wyjatek.getMessage());
			System.out.println("SQLState: " + wyjatek.getSQLState());
			System.out.println("VendorError: " + wyjatek.getErrorCode());
			return null;
		}
	}

	int otherQuery(String query) {
		try {

			dbManager.createConnection();
			Statement stmt = conn.createStatement();
			int rs = stmt.executeUpdate(query);

			return rs;
		} catch (SQLException wyjatek) {
			System.out.println("SQLException: " + wyjatek.getMessage());
			System.out.println("SQLState: " + wyjatek.getSQLState());
			System.out.println("VendorError: " + wyjatek.getErrorCode());
			return 0;
		}
	}

	/**
	 * @return Zwraca obiekty wszystkich użytkowników w bazie
	 */
	public static HashMap<Integer, User> getUsers() {
		
		String query = "SELECT COUNT(*) as `n` FROM `user`";
		ResultSet rs = dbManager.selectQuery(query);
		int usersQuant = -1;
		try {
			if (rs.next()) {
				usersQuant = rs.getInt("n");
			}
			conn.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		
		if (DBManager.users == null || usersQuant != DBManager.users.size()) {
			HashMap<Integer, User> users = new HashMap<Integer, User>();
			DBManager.users = new HashMap<Integer, User>();
			query = "SELECT `user`.`id`, `login`, `user`.`name`, `surname`, `email`, `groupId`, `payment`, `group`.`name` as `groupName` FROM `user`, `user2group`, `group` WHERE `user`.`id`=`user2group`.`userId` AND `user2group`.`groupId`=`group`.`id`";
			rs = dbManager.selectQuery(query);
			try {
				while (rs.next()) {
					User user = new User(rs.getInt("id"), rs.getString("login"), rs.getString("name"),
							rs.getString("surname"), rs.getString("email"), rs.getInt("groupId"),
							rs.getString("groupName"), rs.getInt("payment"));
					users.put(rs.getInt("id"), user);
				}
				conn.close();
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
			for (User user : users.values()) {
				user.setPermissions(DBManager.getPermissionsForGroupId(user.getGroupId()));
				DBManager.users.put(user.getId(), user);
			}
		}
		return DBManager.users;
	}

	/**
	 * Metoda zwraca użytkownika o zadanym id z bazy danych oraz aktualizuje
	 * HashMapę użytkowników
	 * 
	 * @param userId
	 *            - id użytkownika
	 * @return - obiekt użytkownika
	 */
	public static User getUserById(int userId) {
		String query = "SELECT `user`.`id`, `login`, `user`.`name`, `surname`, `email`, `groupId`, `group`.`name` as `groupName`, `payment` FROM `user`, `user2group`, `group` WHERE `user`.`id`="
				+ userId + " AND `user`.`id`=`user2group`.`userId` AND `user2group`.`groupId`=`group`.`id`";
		ResultSet rs = dbManager.selectQuery(query);
		User user = null;
		try {
			if (rs.next()) {
				user = new User(rs.getInt("id"), rs.getString("login"), rs.getString("name"), rs.getString("surname"),
						rs.getString("email"), rs.getInt("groupId"), rs.getString("groupName"), rs.getInt("payment"));
			}
			if (DBManager.users != null) {
				if ( DBManager.users.containsKey(userId) ) {
					DBManager.users.replace(userId, user);
				}
				else {
					DBManager.users.put(userId, user);
				}
			}
			conn.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return user;
	}

	/**
	 * Metoda odpowiedzialna za logowanie się użytkownika
	 * 
	 * @param login
	 *            - login użytkownika który chce się zalogować
	 * @param password
	 *            - zaszyfrowane hasło, które będzie porównane z tym na serwerze
	 * @return Obiekt zalogowanego użytkownika
	 */
	public static User signIn(String login, String password) {
		String query = "SELECT `user`.`id`, `login`, `user`.`name`, `surname`, `email`, `groupId`, `group`.`name` as `groupName`, `payment` FROM `user`, `user2group`, `group` WHERE `user`.`id`=`user2group`.`userId` AND `user2group`.`groupId`=`group`.`id` AND `login`='"
				+ login + "' AND `password`='" + password + "'";
		ResultSet rs = dbManager.selectQuery(query);
		User user = null;
		try {
			if (rs.next()) {
				user = new User(rs.getInt("id"), rs.getString("login"), rs.getString("name"), rs.getString("surname"),
						rs.getString("email"), rs.getInt("groupId"), rs.getString("groupName"), rs.getInt("payment"));
			}
			conn.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return user;
	}

	/**
	 * Metoda do usuwania użytkownika
	 * 
	 * @param id
	 *            - id użytkownika, który ma być usunięty
	 */
	public static void removeUserById(int id) {
		dbManager.otherQuery("DELETE FROM `user` WHERE `id`=" + id);
		dbManager.otherQuery("DELETE FROM `user2group` WHERE `userId`=" + id);
		if (DBManager.users != null) {
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
	 * 
	 * @param login
	 *            - Login użytkownika
	 * @param password
	 *            - Hasło użytkownika
	 * @param mail
	 *            - Hasło użytkownika
	 * @param name
	 *            - Imię użytkownika
	 * @param surname
	 *            - Nazwisko użytkownika
	 * @param groupId
	 *            - Id grupy do której będzie przypisany
	 * @param payment
	 *            - wysokość wypłaty użytkownika
	 */
	public static void addUser(String login, String password, String mail, String name, String surname, int groupId, int payment) {
		
		int userId = 1;
		String query = "INSERT INTO `user` (`name`, `email`, `password`, `login`, `surname`, `payment`) VALUES ('" + name + "', '"
				+ mail + "', '" + password + "', '" + login + "', '" + surname + "', '" + payment + "');";
		try {
			dbManager.otherQuery(query);
			query = "SELECT `id` FROM `user` WHERE `login`='" + login + "' AND `password`='" + password + "'";
			ResultSet rs = dbManager.selectQuery(query);
			if (rs.next()) {
				int id = rs.getInt("id");
				System.out.println("" + id);
				query = "INSERT INTO `user2group` (`userId`, `groupId`) VALUES ('" + id + "', '" + groupId + "');";
				dbManager.otherQuery(query);
				if (DBManager.users == null) {
					DBManager.getUsers();
				} else {
					query = "SELECT `user`.`id`, `login`, `user`.`name`, `surname`, `email`, `groupId`, `payment`, `group`.`name` as `groupName` FROM `user`, `user2group`, `group` WHERE `user`.`id`=`user2group`.`userId` AND `user2group`.`groupId`=`group`.`id` AND `user`.`id`='"
							+ id + "'";
					rs = dbManager.selectQuery(query);
					User user = new User(rs.getInt("id"), rs.getString("login"), rs.getString("name"),
							rs.getString("surname"), rs.getString("email"), rs.getInt("groupId"),
							rs.getString("groupName"), rs.getInt("payment"));
					userId = rs.getInt("id");
					DBManager.users.put(userId, user);

				}
			}
			conn.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		DBManager.getUserById(userId);
	}

	/**
	 * @param userId
	 *            - Id użytkownika
	 * @param oldPass
	 *            - Stare hasło użytkownika
	 * @param newPass
	 *            - Nowe hasło użytkownika
	 * @return Funkcja zwraca true jeśli zmiana się powiodła i false w
	 *         przeciwnym wypadku
	 */
	public static boolean changeUserPassword(int userId, String oldPass, String newPass) {
		String query = "SELECT `id` FROM `user` WHERE `id` = " + userId + " AND `password='" + oldPass + "'`";
		ResultSet rs = dbManager.selectQuery(query);
		try {
			if (rs.next()) {
				if (userId == rs.getInt("id")) {
					query = "UPDATE `user` SET `password` = '" + newPass + "' WHERE `user`.`id` = " + userId + ";";
					dbManager.otherQuery(query);
					query = "SELECT `id` FROM `user` WHERE `id` = " + userId + " AND `password='" + newPass + "'`";
					rs = dbManager.selectQuery(query);
					int id = rs.getInt("id");
					conn.close();
					if (userId == id) {
						DBManager.getUserById(userId);
						return true;
					}
					else
						return false;
				} else {
					conn.close();
					return false;
				}
			} else {
				conn.close();
				return false;
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}

	/**
	 * Metoda odpowiedzialna za aktualizowanie wszystkich rozsądnych danych
	 * użytkownika W przypadku pól typu String - jeżeli puste, tzn "" to nie
	 * zmieniaj W przypadku pól typu Integer - jeżeli puste, tzn <=0 to nie
	 * zmieniaj
	 * 
	 * @param userId
	 *            - id potrzebne do zlokalizowania usera, którego dane mają być
	 *            zmienione
	 * @param login
	 *            - nowy Login
	 * @param password
	 *            - nowe Hasło
	 * @param mail
	 *            - nowy Mail
	 * @param name
	 *            - nowe Imię
	 * @param surname
	 *            - nowe Nazwisko
	 * @param groupId
	 *            - id grupy do której użytkownik ma być przeniesiony
	 * @param payment
	 *            - nowa wysokość wypłaty pracownika
	 * @return Zwraca true jeśli operacja się udała, a w przeciwnym wypadku
	 *         domyśl się
	 */
	public static boolean changeUserData(int userId, String login, String password, String mail, String name,
			String surname, int groupId, int payment) {
		String query = "";
		boolean isUpdateAllWithoutGroup = true;
		boolean isUpdateGroup = true;
		if (!login.isEmpty())
			query += "`login` = '" + login + "'";
		if (!password.isEmpty()) {
			if (!query.isEmpty())
				query += ", ";
			query += "`password` = '" + password + "'";
		}
		if (!mail.isEmpty()) {
			if (!query.isEmpty())
				query += ", ";
			query += "`email` = '" + mail + "'";
		}
		if (!name.isEmpty()) {
			if (!query.isEmpty())
				query += ", ";
			query += "`name` = '" + name + "'";
		}
		if (!surname.isEmpty()) {
			if (!query.isEmpty())
				query += ", ";
			query += "`surname` = '" + surname + "'";
		}
		if (payment > 0) {
			if (!query.isEmpty())
				query += ", ";
			query += "`payment` = '" + payment + "'";
		}
		if (!query.isEmpty()) {
			query = "UPDATE `user` SET " + query + " WHERE `user`.`id`=" + userId + ";";
			int result = dbManager.otherQuery(query);
			try {
				isUpdateAllWithoutGroup = result > 0;
				conn.close();
				if (groupId > 0) {
					query = "UPDATE `user2group` SET `groupId`=" + groupId + " WHERE `user2group`.`userId`=" + userId
							+ ";";
					result = dbManager.otherQuery(query);
					isUpdateGroup = result > 0;
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		if (isUpdateAllWithoutGroup && isUpdateGroup) {
			
			DBManager.getUserById(userId);
			return true;
		}
		else
			return false;
	}

	/**
	 * @param groupId
	 *            - Id grupy której chcemy pobrać uprawnienia
	 * @return HashMapa uprawnień
	 */
	public static HashMap<Integer, Permission> getPermissionsForGroupId(int groupId) {
		String query = "SELECT * FROM `permission`, `group2permission` WHERE `group2permission`.`groupId` = '" + groupId
				+ "' AND `group2permission`.`permissionId` = `permission`.`id`";
		ResultSet rs = dbManager.selectQuery(query);
		if(rs == null)
			System.err.println("rs jest nullem");
		HashMap<Integer, Permission> permissions = new HashMap<Integer, Permission>();
		try {
			while (rs.next()) {
				Permission permission = new Permission(rs.getInt("id"), rs.getString("name"), rs.getInt("access"));
				permissions.put(rs.getInt("id"), permission);
			}
			conn.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return permissions;
	}

	/**
	 * @param userLogin
	 *            - login użytkownika
	 * @return Zwraca id użytkownika lub 0 jeśli błąd
	 */
	public static int getUserIdBylogin(String userLogin) {
		String query = "SELECT `id` FROM `user` WHERE `login`='" + userLogin + "'";
		ResultSet rs = dbManager.selectQuery(query);
		try {
			if (rs.next()) {
				return rs.getInt("id");
			}
			return 0;
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return 0;
		}
	}

	/**
	 * @param groupName
	 *            - nazwa grupy
	 * @return Zwraca id grupy lub 0 jeśli błąd
	 */
	public static int getGroupIdByName(String groupName) {
		String query = "SELECT `id` FROM `group` WHERE `name`='" + groupName + "'";
		ResultSet rs = dbManager.selectQuery(query);
		try {
			if (rs.next()) {
				return rs.getInt("id");
			}
			return 0;
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return 0;
		}
	}

	/**
	 * @param permissionName
	 *            - nazwa uprawnienia
	 * @return Zwraca id uprawnienia lub 0 jeśli błąd
	 */
	public static int getPermissionIdByName(String permissionName) {
		String query = "SELECT `id` FROM `permission` WHERE `name`='" + permissionName + "'";
		ResultSet rs = dbManager.selectQuery(query);
		try {
			if (rs.next()) {
				return rs.getInt("id");
			}
			return 0;
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return 0;
		}
	}

	// Documents

	/**
	 * Metoda zwraca wszystkie dokumenty w bazie
	 * 
	 * @return zwraca hashmapę dokumpentów
	 */
	public static HashMap<Integer, Document> getDocuments() {
		if (DBManager.documents == null) {
			String query = "SELECT * FROM `documents`";
			ResultSet rs = dbManager.selectQuery(query);
			documents = new HashMap<Integer, Document>();
			try {
				while (rs.next()) {
					Document document = new Document(rs.getInt("id"), rs.getInt("creatorId"), rs.getString("name"),
							rs.getString("description"), rs.getString("type"), rs.getDate("createdAt"),
							rs.getBlob("document"));
					documents.put(rs.getInt("id"), document);
				}
				conn.close();
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
		return DBManager.documents;
	}

	/**
	 * Metoda zwraca dokument o danym id
	 * 
	 * @param documentId
	 *            - id dokumentu
	 * @return dokument o zadanym id
	 */
	public static Document getDocumentById(int documentId) {
		String query = "SELECT * FROM `documents` WHERE `id`='" + documentId + "'";
		ResultSet rs = dbManager.selectQuery(query);
		Document document = null;
		try {
			if (rs.next()) {
				document = new Document(rs.getInt("id"), rs.getInt("creatorId"), rs.getString("name"),
						rs.getString("description"), rs.getString("type"), rs.getDate("createdAt"),
						rs.getBlob("document"));
				if (DBManager.documents != null) {
					DBManager.documents.replace(documentId, document);
				}
			}
			conn.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return document;
	}

	/**
	 * Metoda do dodania dokumentu do bazy
	 * 
	 * @param creatorId
	 *            - id użytkownika tworzącego dokument
	 * @param name
	 *            - Nazwa dokumentu
	 * @param description
	 *            - Opis dokumentu
	 * @param type
	 *            - Typ dokumentu
	 * @param document
	 *            - Dokument jako typ Blob
	 * @return Zwraca true jeśli operacja się powiodła lub false w przeciwnym
	 *         wypadku
	 */
	public static boolean addDocument(int creatorId, String name, String description, String type, Blob document) {
		String query = "SELECT * FROM `user` WHERE `id`='" + creatorId + "'";
		try {
			ResultSet rs = dbManager.selectQuery(query);
			if (rs.next()) {
				if (rs.getInt("id") == creatorId) {
					Date createdAt = Date.valueOf(LocalDate.now());
					query = "INSERT INTO `documents` (`creatorId`, `name`, `description`, `type`, `createdAt`, `document`) VALUES ('"
							+ creatorId + "', '" + name + "', '" + description + "', '" + type + "', '" + createdAt
							+ "', '" + document + "')";
					int result = dbManager.otherQuery(query);
					if (result > 0) {
						query = "SELECT `id` FROM `documents` WHERE `name`='" + name + "' AND `createdAt`='" + createdAt
								+ "'";
						rs = dbManager.selectQuery(query);
						if (rs.next()) {
							int id = rs.getInt("id");
							if (DBManager.documents == null) {
								DBManager.getDocuments();
							} else {
								DBManager.documents.put(id,
										new Document(id, creatorId, name, description, type, createdAt, document));
							}
							return true;
						}
					}
				}
			}
			conn.close();
			return false;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	/**
	 * Metoda do aktualizacji dokumentów w bazie (Wszystkie opcje są opcjonalne,
	 * poza id)
	 * 
	 * @param id
	 *            - id dokumentu do aktualizacji
	 * @param name
	 *            - Nowa nazwa dokumentu, w przypadku braku chęci zmiany wstawić
	 *            ""
	 * @param description
	 *            - Nowy opis dokumentu, w przypadku braku chęci zmiany wstawić
	 *            ""
	 * @param type
	 *            - Nowy typ dokumentu, w przypadku braku chęci zmiany wstawić
	 *            ""
	 * @param document
	 *            - Nowy plik dokumenty, a właściwie Blob, w przypadku braku
	 *            chęci zmiany wstawić null
	 * @return Zwraca true jeśli operacja się powiodła lub false w przeciwnym
	 *         wypadku
	 */
	public static boolean updateDocumentDataById(int id, String name, String description, String type, Blob document) {
		String query = "";
		if (!name.isEmpty()) {
			query += "`name`='" + name + "'";
		}
		if (!description.isEmpty()) {
			if (!query.isEmpty())
				query += ", ";
			query += "`description`='" + description + "'";
		}
		if (!type.isEmpty()) {
			if (!query.isEmpty())
				query += ", ";
			query += "`type`='" + type + "'";
		}
		if (document != null) {
			if (!query.isEmpty())
				query += ", ";
			query += "`document`='" + document + "' ";
		}
		if (!query.isEmpty()) {
			query = "UPDATE `documents` SET " + query + "WHERE `id`='" + id + "'";
			try {
				int result = dbManager.otherQuery(query);
				conn.close();
				if (result > 0) {
					DBManager.getDocumentById(id);
					return true;
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		return false;
	}

	/**
	 * Metoda usuwa użytkownika o zadanym id
	 * 
	 * @param id
	 *            - id użytkownika do usunięcia
	 * @return Zwraca true jeśli operacja się powiodła lub false w przeciwnym
	 *         wypadku
	 */
	public static boolean removeDocumentById(int id) {
		dbManager.otherQuery("DELETE FROM `documents` WHERE `id`=" + id);
		if (DBManager.documents != null) {
			DBManager.documents.remove(id);
		}
		try {
			conn.close();
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	// Eventy

	/**
	 * Metoda zwracająca wszystkie eventy
	 * 
	 * @return hashmapa eventów
	 */
	public static HashMap<Integer, Event> getEvents() {
		if (DBManager.events == null) {
			DBManager.events = new HashMap<Integer, Event>();
			String query = "SELECT `event`.*, `eventType`.`name` as `eventTypeName` FROM `event`, `eventType` WHERE `event`.`eventTypeId`=`eventType`.`id`";
			ResultSet rs = dbManager.selectQuery(query);
			try {
				while (rs.next()) {
					int id = rs.getInt("id");
					Event event = new Event(id, rs.getString("name"), rs.getString("description"),
							rs.getDate("startDate"), rs.getDate("endDate"), rs.getString("location"),
							rs.getInt("creatorId"), rs.getInt("eventTypeId"), rs.getString("eventTypeName"));
					DBManager.events.put(id, event);
				}
				conn.close();
				DBManager.initEventsAccess();
				return DBManager.events;
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				return DBManager.events;
			}
		} else {
			return DBManager.events;
		}
	}

	/**
	 * Metoda, która pobiera z bazy danych uprawnienia użytkowników oraz całych
	 * grup do przeglądania oraz zarządzania eventami
	 */
	public static void initEventsAccess() {
		if (DBManager.users2events == null) {
			DBManager.users2events = new HashMap<String, User2Event>();
			String query = "SELECT * FROM `user2event`";
			ResultSet rs = dbManager.selectQuery(query);
			try {
				while (rs.next()) {
					int eventId = rs.getInt("eventId");
					int userId = rs.getShort("userId");
					User2Event user2event = new User2Event(userId, eventId, rs.getInt("access"));
					DBManager.users2events.put(userId + "|" + eventId, user2event);
				}
				conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		if (DBManager.groups2events == null) {
			DBManager.groups2events = new HashMap<String, Group2Event>();
			String query = "SELECT * FROM `group2event`";
			ResultSet rs = dbManager.selectQuery(query);
			try {
				while (rs.next()) {
					int eventId = rs.getInt("eventId");
					int groupId = rs.getShort("groupId");
					Group2Event user2event = new Group2Event(groupId, eventId, rs.getInt("access"));
					DBManager.groups2events.put(groupId + "|" + eventId, user2event);
				}
				conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * Metoda doająca uprawnienia użytkownikowi o zadanym id odpowienie
	 * uprawnienia dla eventu o zadanym id
	 * 
	 * @param userId
	 *            - id użytkownika
	 * @param eventId
	 *            - id eventy
	 * @param access
	 *            - uprawnienia
	 */
	public static void addUserAccessToEvent(int userId, int eventId, int access) {

		if (DBManager.users2events == null) {
			DBManager.initEventsAccess();
		}
		String query = "INSERT INTO `user2event` (`userId`, `eventId`, `access`) VALUES ('" + userId + "', '" + eventId
				+ "', '" + access + "');";
		int result = dbManager.otherQuery(query);
		if (result > 0) {
			DBManager.users2events.put(userId + "|" + eventId, new User2Event(userId, eventId, access));
		}

	}

	/**
	 * Metoda nadaje uprawnienia grupie do odpowiednich działań na odpowiednim
	 * evencie
	 * 
	 * @param groupId
	 *            - id grupy
	 * @param eventId
	 *            - id eventu
	 * @param access
	 *            - uprawnienia
	 */
	public static void addGroupAccessToEvent(int groupId, int eventId, int access) {

		if (DBManager.groups2events == null) {
			DBManager.initEventsAccess();
		}
		String query = "INSERT INTO `group2event` (`groupId`, `eventId`, `access`) VALUES ('" + groupId + "', '"
				+ eventId + "', '" + access + "');";
		int result = dbManager.otherQuery(query);
		if (result > 0) {
			DBManager.groups2events.put(groupId + "|" + eventId, new Group2Event(groupId, eventId, access));
		}
	}

	/**
	 * Metoda zwraca event o podanym id
	 * 
	 * @param eventId
	 *            - id eventu do pobrania
	 * @param userId
	 *            - id użytkownika, by sprawdzić czy ma uprawnienia do
	 *            przeprowadzenia operacji
	 * @return obiekt eventu
	 */
	public static Event getEventById(int eventId, int userId) {

		Event event = null;

		if (DBManager.users2events == null || DBManager.groups2events == null) {
			DBManager.initEventsAccess();
		}
		int userAccess = 0, groupAccess = 0, groupId = 0;
		if (DBManager.users2events.containsKey(userId + "|" + eventId)) {
			userAccess = DBManager.users2events.get(userId + "|" + eventId).getAccess();
		}
		if (DBManager.users == null) {
			groupId = DBManager.getUserById(userId).getGroupId();
		} else {
			groupId = DBManager.users.get(userId).getGroupId();
		}
		if (DBManager.groups2events.containsKey(groupId + "|" + eventId)) {
			groupAccess = DBManager.groups2events.get(groupId + "|" + eventId).getAccess();
		}
		if (userAccess >= 4 || groupAccess >= 4) {
			String query = "SELECT `event`.*, `eventType`.`name` as `eventTypeName` FROM `event`, `eventType` WHERE `event`.`eventTypeId`=`eventType`.`id` AND `event`.`id`='"
					+ eventId + "'";
			ResultSet rs = dbManager.selectQuery(query);
			try {
				if (rs.next()) {
					int id = rs.getInt("id");
					event = new Event(id, rs.getString("name"), rs.getString("description"), rs.getDate("startDate"),
							rs.getDate("endDate"), rs.getString("location"), rs.getInt("creatorId"),
							rs.getInt("eventTypeId"), rs.getString("eventTypeName"));
					if (DBManager.events != null) {
						DBManager.events.replace(eventId, event);
					}
				}
				conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("Użytkownik nie ma uprawnień, by pobrać informacje o evencie!");
		}

		return event;
	}

	/**
	 * Metoda dodająca event
	 * 
	 * @param creatorId
	 *            - użytkownik dodający event, który będzie miał nadane prawa do
	 *            odczytu oraz zapisy dla tego wydarzenia
	 * @param name
	 *            - Nazwa wydarzenia
	 * @param description
	 *            - OPiS wydarzenia
	 * @param startDate
	 *            - data rozpoczęcia wydarzenia
	 * @param endDate
	 *            - data zakończenia wydarzenia
	 * @param location
	 *            - lokalizacja wydarzenia
	 * @param eventTypeId
	 *            - id typu wydarzenia
	 * @return - jeśli się udało utworzyć event to true, jeśli nie to false
	 */
	public static boolean addEvent(int creatorId, String name, String description, Date startDate, Date endDate,
			String location, int eventTypeId) {
		String query = "SELECT * FROM `user` WHERE `id`='" + creatorId + "'";
		try {
			ResultSet rs = dbManager.selectQuery(query);
			if (rs.next()) {
				if (rs.getInt("id") == creatorId) {
					query = "INSERT INTO `event` (`creatorId`, `name`, `description`, `startDate`, `endDate`, `location`, `eventTypeId`) VALUES ('"
							+ creatorId + "', '" + name + "', '" + description + "', '" + startDate + "', '" + endDate
							+ "', '" + location + "', '" + eventTypeId + "')";
					int result = dbManager.otherQuery(query);
					if (result > 0) {
						conn.close();
						query = "SELECT `event`.`id`, `eventType`.`name` as `eventTypeName` FROM `event`, `eventType` WHERE `name`='"
								+ name + "' AND `creatorId`='" + creatorId + "' AND `startDate`='" + startDate
								+ "' AND `event`.`eventTypeId`=`eventType`.`id`";
						rs = dbManager.selectQuery(query);
						if (rs.next()) {
							int id = rs.getInt("id");
							String eventTypeName = rs.getString("eventTypename");
							conn.close();
							if (DBManager.events == null) {
								DBManager.getDocuments();
							} else {
								DBManager.addUserAccessToEvent(creatorId, id, 6);
								DBManager.events.put(id, new Event(id, name, description, startDate, endDate, location,
										creatorId, eventTypeId, eventTypeName));
							}
							return true;
						}
					}
				}
			}
			conn.close();
			return false;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	/**
	 * Metoda aktualizująca event, jeśli użytkownik ma uprawnienia.
	 * 
	 * @param userId
	 *            - id użytkownika, by sprawdzić czy ma uprawnienia do
	 *            przeprowadzenia operacji
	 * @param id
	 *            - id eventu, który ma być zaktualizowany
	 * @param name
	 *            - nowa nazwa eventu, jeśli pusta, nie zostanie dokonana zmiana
	 * @param description
	 *            - nowy opis eventu, jeśli pusty, nie zostanie zaktualizowany
	 * @param startDate
	 *            - nowa data początku eventu, jeśli null, nie zostanie
	 *            zaktualizowana
	 * @param endDate
	 *            - nowa data zakończenia eventu, jeśli null, nie zostanie
	 *            zaktualizowana
	 * @param location
	 *            - nowa lokalizacja eventu, jeśli pusta, nie zostanie
	 *            zaktualizowana
	 * @param eventTypeId
	 *            - nowe id typu eventu, jeśli 0 to typ pozostanie bez zmian
	 * @return true jeśli się udało i false w przeciwnym wypadku
	 */
	public static boolean updateEventDataById(int userId, int id, String name, String description, Date startDate,
			Date endDate, String location, int eventTypeId) {

		if (DBManager.users2events == null || DBManager.groups2events == null) {
			DBManager.initEventsAccess();
		}
		int userAccess = 0, groupAccess = 0, groupId = 0;
		if (DBManager.users2events.containsKey(userId + "|" + id)) {
			userAccess = DBManager.users2events.get(userId + "|" + id).getAccess();
		}
		if (DBManager.users == null) {
			groupId = DBManager.getUserById(userId).getGroupId();
		} else {
			groupId = DBManager.users.get(userId).getGroupId();
		}
		if (DBManager.groups2events.containsKey(groupId + "|" + id)) {
			groupAccess = DBManager.groups2events.get(groupId + "|" + id).getAccess();
		}
		if (userAccess >= 6 || groupAccess >= 6) {

			String query = "";
			if (!name.isEmpty()) {
				query += "`name`='" + name + "'";
			}
			if (!description.isEmpty()) {
				if (!query.isEmpty())
					query += ", ";
				query += "`description`='" + description + "'";
			}
			if (startDate == null) {
				if (!query.isEmpty())
					query += ", ";
				query += "`startDate`='" + startDate + "'";
			}
			if (endDate != null) {
				if (!query.isEmpty())
					query += ", ";
				query += "`endDate`='" + endDate + "'";
			}
			if (!location.isEmpty()) {
				if (!query.isEmpty())
					query += ", ";
				query += "`location`='" + location + "'";
			}
			if (eventTypeId != 0) {
				if (!query.isEmpty())
					query += ", ";
				query += "`eventTypeId`='" + eventTypeId + "' ";
			}
			if (!query.isEmpty()) {
				query = "UPDATE `event` SET " + query + "WHERE `id`='" + id + "'";
				try {
					int result = dbManager.otherQuery(query);
					conn.close();
					if (result > 0) {
						DBManager.getEventById(id, userId);
						return true;
					}
				} catch (SQLException e) {
					System.out.println(e.getMessage());
					return false;
				}
			}
		} else {
			System.out.println("Użytkownik nie ma uprawnień, by dokonać edycji eventu!");
		}

		return false;
	}

	/**
	 * Metoda usuwa event o zadanym id
	 * 
	 * @param id
	 *            - id eventu do usunięcia
	 * @param userId
	 *            - id użytkownika, by sprawdzić czy ma uprawnienia do
	 *            przeprowadzenia operacji
	 * @return Zwraca true jeśli operacja się powiodła lub false w przeciwnym
	 *         wypadku
	 */
	public static boolean removeEventById(int id, int userId) {

		if (DBManager.users2events == null || DBManager.groups2events == null) {
			DBManager.initEventsAccess();
		}
		int userAccess = 0, groupAccess = 0, groupId = 0;
		if (DBManager.users2events.containsKey(userId + "|" + id)) {
			userAccess = DBManager.users2events.get(userId + "|" + id).getAccess();
		}
		if (DBManager.users == null) {
			groupId = DBManager.getUserById(userId).getGroupId();
		} else {
			groupId = DBManager.users.get(userId + "|" + id).getGroupId();
		}
		if (DBManager.groups2events.containsKey(groupId + "|" + id)) {
			groupAccess = DBManager.groups2events.get(groupId + "|" + id).getAccess();
		}
		if (userAccess == 2 || userAccess >= 6 || groupAccess == 2 || groupAccess >= 6) {

			dbManager.otherQuery("DELETE FROM `event` WHERE `id`=" + id);
			if (DBManager.events != null) {
				DBManager.events.remove(id);
			}
			try {
				conn.close();
				return true;
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				return false;
			}

		} else {
			System.out.println("Użytkownik nie ma uprawnień, by usunąć event!");
		}
		return false;
	}

	public static Blob loadBlob(String path) throws SQLException, IOException {
		dbManager.createConnection();
		Blob document = conn.createBlob();
		int got, bufferSize = 4096;
		byte[] buffer = new byte[bufferSize];
		try (OutputStream out = document.setBinaryStream(1); FileInputStream in = new FileInputStream(path)) {
			while ((got = in.read(buffer)) != -1) {
				out.write(buffer, 0, got);
			}
		}
		return document;
	}
	
	/**
	 * Metoda zwraca wszystkie faktury za pomocą HashMapy z id faktury jako klucz
	 * @return 
	 * 				- HashMapa<Integer, Invoice> faktur.
	 */
	public static HashMap<Integer, Invoice> getAllInvoices() {
		if (DBManager.invoices == null) {
			HashMap<Integer, Invoice> invoices = new HashMap<Integer, Invoice>();
			String query = "SELECT * FROM `invoice`";
			ResultSet rs = dbManager.selectQuery(query);
			try {
				while (rs.next()) {
					Invoice invoice = new Invoice(rs.getInt("id"), rs.getInt("creatorId"), rs.getString("name"), rs.getString("receiverName"),
							rs.getString("receiverAddress"), rs.getString("receiverNIP"), rs.getString("receiverDetails"), rs.getString("receiverBankAccount"), rs.getDate("creationDate"));
					invoices.put(rs.getInt("id"), invoice);
				}
				conn.close();
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
			for (Invoice invoice : invoices.values()) {
				try {
					query = "SELECT * FROM `invoiceDetails` WHERE `invoiceId`='"+invoice.getId()+"'";
					rs = dbManager.selectQuery(query);
					HashMap<Integer, InvoiceDetails> invoiceDetails = new HashMap<Integer, InvoiceDetails>();
					while(rs.next()) {
						invoiceDetails.put(rs.getInt("id"), new InvoiceDetails(rs.getInt("id"), invoice.getId(), rs.getString("name"), rs.getInt("quantity"), rs.getInt("baseprice")));
					}
					invoice.setInvoiceDetails(invoiceDetails);
					conn.close();
					DBManager.invoices.put(invoice.getId(), invoice);
				}
				catch (SQLException ex) {
					System.out.println(ex.getMessage());
				}
			}
		}
		return DBManager.invoices;
	}
	
	public static int getLastInvoiceId() {
		String query = "SELECT id FROM `invoice` ORDER BY id DESC LIMIT 1";
		int id = 0;
		
		ResultSet rs = dbManager.selectQuery(query);
		try {
			if (rs.next()) {
				id = rs.getInt("id");
			}
			conn.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return id;
	}
	
	/**
	 * Metoda zwraca dane faktury o zadanym id
	 * @param invoiceId
	 *            - id faktury
	 * @return
	 *            - faktura o zadanym id
	 */
	public static Invoice getInvoiceById(int invoiceId) {
		
		Invoice invoice = null;
		String query = "SELECT * FROM `invoice` WHERE `id`='"+invoiceId+"'";
		
		ResultSet rs = dbManager.selectQuery(query);
		try {
			if (rs.next()) {
				invoice = new Invoice(rs.getInt("id"), rs.getInt("creatorId"), rs.getString("name"), rs.getString("receiverName"),
						rs.getString("receiverAddress"), rs.getString("receiverNIP"), rs.getString("receiverDetails"), rs.getString("receiverBankAccount"), rs.getDate("creationDate"));
				query = "SELECT * FROM `invoiceDetails` WHERE `invoiceId`='"+invoiceId+"'";
				conn.close();
				rs = dbManager.selectQuery(query);
				HashMap<Integer, InvoiceDetails> invoiceDetails = new HashMap<Integer, InvoiceDetails>();
				while(rs.next()) {
					invoiceDetails.put(rs.getInt("id"), new InvoiceDetails(rs.getInt("id"), invoiceId, rs.getString("name"), rs.getInt("quantity"), rs.getInt("baseprice")));
				}
				invoice.setInvoiceDetails(invoiceDetails);
			}
			conn.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return invoice;
	}
	
	/**
	 * Metoda dodająca do bazy fakturę o następujących parametrach:
	 * 
	 * @param creatorId
	 *            - id użytkownika tworzącego fakturę
	 * @param name
	 *            - nazwa faktury
	 * @param receiverName
	 *            - nazwa odbiorcy faktury
	 * @param receiverAddress
	 *            - adres odbiorcy faktury
	 * @param receiverNIP
	 *            - numer identyfikacji podatkowej odbiorcy faktury
	 * @param receiverDetails
	 *            - dodatkowe dane na temat odbiorcy, numer buta, itp.
	 * @param receiverBankAccount
	 *            - numer konta bankowego odbiorcy faktury
	 * @return
	 *            - Objekt z danymi dodanej faktury
	 */
	public static Invoice addInvoice(int creatorId, String name, String receiverName, String receiverAddress, String receiverNIP, String receiverDetails, String receiverBankAccount) {
		
		Invoice invoice = null;
		
		Date creationDate = Date.valueOf(LocalDate.now());
		String query = "INSERT INTO `invoice` (`creatorId`, `name`, `receiverName`, `receiverAddress`, `receiverNIP`, `receiverDetails`, `receiverBankAccount`, `creationDate`) VALUES ('" + creatorId + "', '"
				+ name + "', '" + receiverName + "', '" + receiverAddress + "', '" + receiverNIP + "', '" + receiverDetails + "', '" + receiverBankAccount + "', '" + creationDate + "');";
		
		try {
			int result = dbManager.otherQuery(query);
			conn.close();
			if(result > 0) {
				query = "SELECT `id` FROM `invoice` WHERE `name`='" + name + "' AND `creationDate`='" + creationDate + "'";
				ResultSet rs = dbManager.selectQuery(query);
				if (rs.next()) {
					int id = rs.getInt("id");
					invoice = new Invoice(id, creatorId, name, receiverName, receiverAddress, receiverNIP,
							receiverDetails, receiverBankAccount, creationDate);
				}
				conn.close();
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return invoice;
	}
	
	/**
	 * Metoda służąca do edycji danych faktury
	 * 
	 * @param id
	 *            - id faktury, która ma być zmodyfikowana
	 * @param name
	 *            - nowa nazwa faktury, jeśli pole puste to nazwa bez zmian
	 * @param receiverName
	 *            - nowa nazwa odbiorcy, jeśli pole puste to nazwa bez zmian
	 * @param receiverAddress
	 *            - nowy adres odbiorcy, jeśli pole puste to adres bez zmian
	 * @param receiverNIP
	 *            - nowy NIP odbiorcy, jeśli pole puste to NIP bez zmian
	 * @param receiverDetails
	 *            - nowe szczegóły nt. odbiorcy, jeśli pole puste to bez zmian
	 * @param receiverBankAccount
	 *            - nowy numer konta odbiorcy, jeśli pole puste to numer bez zmian
	 * @return
	 *            - True jeśli operacja się powiodła i false w przeciwnym wypadku
	 */
	public static boolean updateDataForInvoiceById(int id, String name, String receiverName, String receiverAddress, String receiverNIP, String receiverDetails, String receiverBankAccount ) {
		
		String query = "";
		
		if( !name.isEmpty() ) {
			query += "`name`='" + name + "'";
		}
		if ( !receiverName.isEmpty() ) {
			if (!query.isEmpty())
				query += ", ";
			query += "`receiverName`='" + receiverName + "'";
		}
		if ( !receiverAddress.isEmpty() ) {
			if (!query.isEmpty())
				query += ", ";
			query += "`receiverAddress`='" + receiverAddress + "'";
		}if ( !receiverNIP.isEmpty() ) {
			if (!query.isEmpty())
				query += ", ";
			query += "`receiverNIP`='" + receiverNIP + "'";
		}if ( !receiverDetails.isEmpty() ) {
			if (!query.isEmpty())
				query += ", ";
			query += "`receiverDetails`='" + receiverDetails + "'";
		}if ( !receiverBankAccount.isEmpty() ) {
			if (!query.isEmpty())
				query += ", ";
			query += "`receiverBankAccount`='" + receiverBankAccount + "'";
		}
		if (!query.isEmpty()) {
			query = "UPDATE `invoice` SET " + query + "WHERE `id`='" + id + "'";
			try {
				int result = dbManager.otherQuery(query);
				conn.close();
				if (result > 0) {
					return true;
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Motoda do usuwania faktur
	 * 
	 * @param id
	 *            - id faktury do usunięcia
	 * @return
	 *            - true jeśli operacja się powiodła, false w przeciwnym wypadku
	 */
	public static boolean removeInvoiceById(int id) {
		
		int result = dbManager.otherQuery("DELETE FROM `invoiceDetails` WHERE `invoiceId`=" + id);
		try {
			conn.close();
			if (result > 0) {
				result = dbManager.otherQuery("DELETE FROM `invoice` WHERE `id`=" + id);
			}
			return result > 0;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	/**
	 * Metoda pozwalająca dodawać do faktury w bazie szczegóły, czyli np. wyszczególnione sprzedane klientowi wina, 
	 * do przekazania informacji należy użyć ArrayListy uproszczonego obiektu InvoiceDetails
	 * 
	 * @param id
	 *            - id faktury do której mają być dodane szczegóły
	 * @param invoiceDetails
	 *            - array lista uproszczonych obiektów InvoiceDetails
	 * @return
	 *            - zwracana jest faktura z nowo dodanymi polami
	 */
	public static Invoice addInvoiceDetailsArrayForInvoiceById(int id, ArrayList<InvoiceDetails> invoiceDetails) {
		
		try {
			for(int i=0; i<invoiceDetails.size(); i++) {
				int result = dbManager.otherQuery("INSERT INTO `invoiceDetails` (`invoiceId`, `name`, `quantity`, `baseprice`) VALUES ('" + invoiceDetails.get(i).getInvoiceId() + "', '" + invoiceDetails.get(i).getName() + "', '" + invoiceDetails.get(i).getQuantity() + "','" + invoiceDetails.get(i).getBaseprice() + "'");
				conn.close();
				if (result == 0) {
					System.out.println("Dodawanie nie powiodło się");
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		Invoice invoice = DBManager.getInvoiceById(id);
		
		return invoice;
	}
	
	/**
	 * Metoda do aktualizacji szczegółów faktur (InvoiceDetails) 
	 * 
	 * @param id
	 *            - id pola które ma być zmodyfikowane
	 * @param name
	 *            - nowa nazwa pola, jeśli pusta to nazwa nie zostanie zmieniona
	 * @param quantity
	 *            - nowa ilość produktów, jeśli 0 to wartość nie zostanie zmieniona
	 * @param baseprice
	 *            - nowa cena produktu, jeśli 0 to wartość nie zostanie zmieniona
	 * @return
	 *            - true jeśli operacja się powiodła, false jeśli nie
	 */
	public static boolean updateInvoiceDetailsById(int id, String name, int quantity, int baseprice) {
		
		String query = "";
		
		if( !name.isEmpty() ) {
			query += "`name`='" + name + "'";
		}
		if ( quantity != 0 ) {
			if (!query.isEmpty())
				query += ", ";
			query += "`quantity`='" + quantity + "'";
		}
		if ( baseprice != 0 ) {
			if (!query.isEmpty())
				query += ", ";
			query += "`baseprice`='" + baseprice + "'";
		}
		if (!query.isEmpty()) {
			query = "UPDATE `invoiceDetails` SET " + query + "WHERE `id`='" + id + "'";
			try {
				int result = dbManager.otherQuery(query);
				conn.close();
				if (result > 0) {
					return true;
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Metoda do ususwania pola szczegółów faktury o zadanym id
	 * 
	 * @param id
	 *            - id pola do usunięcia
	 * @return true jeśli się udało oraz false w przeciwnym wypadku
	 */
	public static boolean removeInvoiceDetailsById(int id) {
		
		int result = dbManager.otherQuery("DELETE FROM `invoiceDetails` WHERE `id`=" + id);
		try {
			conn.close();
			if (result == 0) {
				System.out.println("Usuwanie nie powiodło się!");
				return false;
			}
			DBManager.invoices.remove(id);
			return result > 0;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	/**
	 * Metoda zwracający obiekt wina
	 * 
	 * @param id
	 * 				- id wina, którą chcemy pobrać
	 * @return
	 * 				- Obiekt wina o zadanym id
	 */
	public static Wine getWineById(int id) {
		
		Wine wine = null;
		String query = "SELECT * FROM `wine` WHERE `id`='"+id+"'";
		
		ResultSet rs = dbManager.selectQuery(query);
		try {
			if (rs.next()) {
				wine = new Wine(rs.getInt("id"), rs.getString("name"), rs.getString("grapes"), rs.getString("color"),
						rs.getInt("produced"), rs.getInt("sold"), rs.getInt("baseprice"), rs.getInt("productionCost"), rs.getInt("year"), rs.getInt("protectedOrigin"), rs.getInt("forSale"));
				conn.close();
			}
			conn.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return wine;
	}
	
	public static HashMap<Integer, Wine> getWineByCororAndType(String color, int type) {
		
		HashMap<Integer, Wine> wines = new HashMap<Integer, Wine>();
		String query = "SELECT * FROM `wine` WHERE `color`='"+color+"' AND `protectedOrigin`='"+type+"'";
		
		ResultSet rs = dbManager.selectQuery(query);
		try {
			while (rs.next()) {
				Wine wine = new Wine(rs.getInt("id"), rs.getString("name"), rs.getString("grapes"), rs.getString("color"),
						rs.getInt("produced"), rs.getInt("sold"), rs.getInt("baseprice"), rs.getInt("productionCost"), rs.getInt("year"), rs.getInt("protectedOrigin"), rs.getInt("forSale"));
				wines.put(rs.getInt("id"), wine);
				conn.close();
			}
			conn.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return wines;
	}
	
	/**
	 * Metoda dodające wino do bazy danych
	 * 
	 * @param name
	 * 					- nazwa wina
	 * @param grapes
	 * 					- grona
	 * @param color
	 * 					- kolor wina
	 * @param produced
	 * 					- ilość wybrodukowanych sztuk
	 * @param sold
	 * 					- ilość sprzedanych sztuk
	 * @param baseprice
	 * 					- cena za sztukę
	 * @param productionCost
	 * 					- cena wybrodukowania
	 * @param year
	 * 					- rok produkcji
	 * @return
	 */
	public static Wine addWine(String name, String grapes, String color, int produced, int sold, int baseprice, int productionCost, int year, int protectedOrigin, int forSale) {
		
		Wine wine = null;
		
		String query = "INSERT INTO `wine` (`name`, `grapes`, `color`, `produced`, `sold`, `baseprice`, `productionCost`, `year`, `protectedOrigin`, `forSale`) VALUES ('" + name + "', '"
				+ grapes + "', '" + color + "', '" + produced + "', '" + sold + "', '" + baseprice + "', '" + productionCost + "', '" + year + "', '"+ protectedOrigin +"', '"+ forSale +"');";
		
		try {
			int result = dbManager.otherQuery(query);
			conn.close();
			if(result > 0) {
				query = "SELECT `id` FROM `wine` ORDER BY `id` DESC LIMIT 1";
				ResultSet rs = dbManager.selectQuery(query);
				if (rs.next()) {
					int id = rs.getInt("id");
					wine = new Wine(id, name, grapes, color, produced,
							sold, baseprice, productionCost, year, protectedOrigin, forSale);
				}
				conn.close();
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		
		return wine;
	}
	
	/**
	 * Metoda do aktualizacji danych na temat wina w bazie danych
	 * 
	 * @param id
	 * 				- id wina dla którego dane mają być zaktualizowane
	 * @param name
	 * 				- nowa nazwa wina, jeśli pusta to nie ulegnie zmianie
	 * @param grapes
	 * 				- nowe grona, gdy puste nie ulegnie zmianie
	 * @param color
	 * 				- nowy kolor wina, gdy puste nie ulegnie zmianie
	 * @param produced
	 * 				- nowa ilość wyprodukowanych sztuk, jeśli -1 nie ulegnie zmianie
	 * @param sold
	 * 				- nowa ilość sprzedanych sztuk, jeśli -1 nie ulegnie zmianie
	 * @param baseprice
	 * 				- nowa cena, jeśli 0 nie ulegnie zmianie
	 * @param productionCost
	 * 				- nowy koszt produkcji, jeśli 0 nie ulegnie zmianie
	 * @param year
	 * 				- nowy rok produkcji, jeśli 0, nie ulegnie zmianie
	 * @return
	 * 				- true jeśli operacja się powiodła, oraz false w przeciwnym wypadku
	 */
	public static boolean updateDataForWineById(int id, String name, String grapes, String color, int produced, int sold, int baseprice, int productionCost, int year, int protectedOrigin, int forSale ) {
		
		String query = "";
		
		if( !name.isEmpty() ) {
			query += "`name`='" + name + "'";
		}
		if ( !grapes.isEmpty() ) {
			if (!query.isEmpty())
				query += ", ";
			query += "`grapes`='" + grapes + "'";
		}
		if ( !color.isEmpty() ) {
			if (!query.isEmpty())
				query += ", ";
			query += "`receiverAddress`='" + color + "'";
		}
		if ( produced != -1 ) {
			if (!query.isEmpty())
				query += ", ";
			query += "`produced`='" + produced + "'";
		}
		if ( sold != -1 ) {
			if (!query.isEmpty())
				query += ", ";
			query += "`sold`='" + sold + "'";
		}
		if ( baseprice != 0 ) {
			if (!query.isEmpty())
				query += ", ";
			query += "`baseprice`='" + baseprice + "'";
		}
		if ( productionCost != 0 ) {
			if (!query.isEmpty())
				query += ", ";
			query += "`productionCost`='" + productionCost + "'";
		}
		if ( year != 0 ) {
			if (!query.isEmpty())
				query += ", ";
			query += "`year`='" + year + "'";
		}
		if ( forSale != 0 ) {
			if (!query.isEmpty())
				query += ", ";
			query += "`forSale`='" + forSale + "'";
		}
		if ( protectedOrigin != 0 ) {
			if (!query.isEmpty())
				query += ", ";
			query += "`protectedOrigin`='" + protectedOrigin + "'";
		}
		if (!query.isEmpty()) {
			query = "UPDATE `wine` SET " + query + "WHERE `id`='" + id + "'";
			try {
				int result = dbManager.otherQuery(query);
				conn.close();
				if (result > 0) {
					return true;
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		return false;
	}

	/**
	 * Metoda do usuwania wina o zadanym id z bazy danych
	 * 
	 * @param id
	 * 				- id wina do usunięcia
	 * @return
	 * 				- true jeśli operacja się powiodła, oraz false w przeciwnym wypadku
	 */
	public static boolean removeWineById(int id) {
		
		int result = dbManager.otherQuery("DELETE FROM `wine` WHERE `id`=" + id);
		try {
			conn.close();
			return result > 0;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	/**
	 * Metoda inicjalizująca dane na temat pól na plantacji dla zadanego sektora
	 * 
	 * @param sectorName
	 * 						- nazwa sektora, który ma być zainicjalizowany w bazie
	 * @param quantRows
	 * 						- ilość wierszy w tym sektorze
	 * @param quantColumns
	 * 						- liczba kolumn w sektorze
	 * @return
	 */
	public static boolean initFieldsCell(String sectorName, int quantRows, int quantColumns) {
		
		String query = "INSERT INTO `fieldCell` (`row`, `col`, `section`, `currentSatusId`) VALUES ";
		for(int i = 0; i<quantRows; i++) {
			for( int j = 0; j<quantColumns; j++ ) {
				if(j != 0) query += ", ";
				query += "('" + i + "', '" + j + "', '" + sectorName + "', '" + 1 + "')";
			}
		}
		int result = dbManager.otherQuery(query);
		try {
			conn.close();
			return result > 0;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	/**
	 * Metoda zwracająca pole na plantacji o zadanym id
	 * 
	 * @param id
	 * 				- id pola, które ma być pobrane
	 * @return
	 * 				- obiekt pola o zadanym id
	 */
	public static FieldCell getFieldCellById(int id) {
		
		FieldCell fieldCell = null;

		String query = "SELECT * FROM `fieldCell` WHERE `id`='"+id+"'";
		
		ResultSet rs = dbManager.selectQuery(query);
		try {
			if (rs.next()) {
				fieldCell = new FieldCell(rs.getInt("id"), rs.getInt("row"), rs.getInt("col"), rs.getString("section"),
						rs.getInt("currentStatusId"), rs.getString("description"), new Date(rs.getDate("date").getTime()));
				conn.close();
				fieldCell.setFieldStatus(DBManager.getFieldStatusById(fieldCell.getCurrentStatusId()));
			}
			conn.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return fieldCell;
	}
	
	/**
	 * Metoda zwracająca wszystkie pola plantacji
	 * 
	 * @return
	 * 			- HashMapa plantacji, czyli wszystkie dostępne w bazie pola
	 */
	public static HashMap<Integer, FieldCell> getAllFieldsCells() {
		
		HashMap<Integer, FieldCell> fieldsCells = new HashMap<Integer, FieldCell>();
		
		String query = "SELECT * FROM `fieldCell`";
		ResultSet rs = dbManager.selectQuery(query);
		try {
			while (rs.next()) {
				FieldCell fieldCell = new FieldCell(rs.getInt("id"), rs.getInt("row"), rs.getInt("col"), rs.getString("section"),
						rs.getInt("currentStatusId"), rs.getString("description"), new Date(rs.getDate("date").getTime()));
				fieldsCells.put(rs.getInt("id"), fieldCell);
			}
			conn.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return fieldsCells;
	}
	
	/**
	 * Metoda zwracająca status pola o zadanym id
	 * 
	 * @param id
	 * 				- id statusu, który ma być pobrany
	 * @return
	 * 				- obiekt statusu pola
	 */
	public static FieldStatus getFieldStatusById(int id) {
		
		FieldStatus fieldStatus = null;

		String query = "SELECT * FROM `fieldStatus` WHERE `id`='"+id+"'";
		
		ResultSet rs = dbManager.selectQuery(query);
		try {
			if (rs.next()) {
				fieldStatus = new FieldStatus(rs.getInt("id"), rs.getString("code"), rs.getString("category"), rs.getString("subcategory"));
				conn.close();
			}
			conn.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return fieldStatus;
	}
	
	/**
	 * Obiekt zwracające wszystkie możliwe statusy pól na plantacji
	 * 
	 * @return
	 * 			- HashMapa wszystkich możliwych statusów
	 */
	public static HashMap<Integer, FieldStatus> getAllFieldsStatuses() {
		
		HashMap<Integer, FieldStatus> fieldsStatuses = new HashMap<Integer, FieldStatus>();
		
		String query = "SELECT * FROM `fieldStatus`";
		ResultSet rs = dbManager.selectQuery(query);
		try {
			while (rs.next()) {
				FieldStatus fieldStatus = new FieldStatus(rs.getInt("id"), rs.getString("code"), rs.getString("category"), rs.getString("subcategory"));
				fieldsStatuses.put(rs.getInt("id"), fieldStatus);
			}
			conn.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return fieldsStatuses;
	}
	
	/**
	 * Metoda dodająca pole z plantacji do bazy danch
	 * 
	 * @param row
	 * 							- współrzędna Y pola
	 * @param column
	 * 							- wszpółrzędna X pola
	 * @param section
	 * 							- sekcja plantacji w której znajduje się pole
	 * @param currentStatusId
	 * 							- id aktualnego statusu pola
	 * @param description
	 * 							- opis pola
	 * @return
	 * 							- obiekt pola, które właśnie zostało do bazy danych
	 */
	public static FieldCell addFieldCell(int row, int column, String section, int currentStatusId, String description, Date date) {
		
		FieldCell fieldCell = null;
		
		String query = "INSERT INTO `fieldCell` (`row`, `column`, `section`, `currentStatusId`, `description`, `date`) VALUES ('" + row + "', '"
				+ column + "', '" + section + "', '" + currentStatusId + "', '" + description + "', '" + new java.sql.Date(date.getTime()) + "');";
		
		try {
			int result = dbManager.otherQuery(query);
			conn.close();
			if(result > 0) {
				query = "SELECT `id` FROM `fieldCell` WHERE `row` = '"+row+"' AND `column` = '"+column+"' AND `section` = '"+section+"' ORDER BY `id` DESC LIMIT 1";
				ResultSet rs = dbManager.selectQuery(query);
				if (rs.next()) {
					int id = rs.getInt("id");
					fieldCell = new FieldCell(id, row, column, section, currentStatusId, description, date);
				}
				conn.close();
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		
		return fieldCell;
	}
	
	/**
	 * @param id
	 * 							- id pola, które ma być zaktualizowane
	 * @param row
	 * 							- nowa współrzędna Y pola, jeśli -1 współrzędna pozostaje bez zmian
	 * @param column
	 * 							- nowa wszpółrzędna X pola, jeśli -1 współrzędna pozostaje bez zmian
	 * @param section
	 * 							- nowa sekcja plantacji w której znajduje się pole, jeśli pusta to bez zmian
	 * @param currentStatusId
	 * 							- nowe id aktualnego statusu pola, jeśli 0 to bez zmian
	 * @param description
	 * 							- nowy opis pola, jeśli puste to bez zmian
	 * @return
	 */
	public static boolean updateDataForFieldCellById(int id, int row, int column, String section, int currentStatusId, String description, Date date) {
		
		String query = "";
		java.sql.Date sqlDate = new Date(date.getTime());
		
		if( row != -1 ) {
			query += "`row`='" + row + "'";
		}
		if ( column != -1 ) {
			if (!query.isEmpty())
				query += ", ";
			query += "`column`='" + column + "'";
		}
		if ( !section.isEmpty() ) {
			if (!query.isEmpty())
				query += ", ";
			query += "`section`='" + section + "'";
		}
		if ( currentStatusId != 0 ) {
			if (!query.isEmpty())
				query += ", ";
			query += "`currentStatusId`='" + currentStatusId + "'";
		}
		if ( !description.isEmpty() ) {
			if (!query.isEmpty())
				query += ", ";
			query += "`description`='" + description + "'";
		}
		if ( date != null ) {
			if (!query.isEmpty())
				query += ", ";
			query += "`date`='" + sqlDate + "'";
		}
		if (!query.isEmpty()) {
			query = "UPDATE `fieldCell` SET " + query + "WHERE `id`='" + id + "'";
			try {
				int result = dbManager.otherQuery(query);
				conn.close();
				if (result > 0) {
					return true;
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Metoda usuwająca pole plantacji z bazy danych
	 * 
	 * @param id
	 * 				- id pola które ma być usunięte
	 * @return
	 * 				- true jeśli operacja się powiodła, oraz false w przeciwnym wypadku
	 */
	public static boolean removeFieldCellById(int id) {
	
		int result = dbManager.otherQuery("DELETE FROM `fieldCell` WHERE `id`=" + id);
		try {
			conn.close();
			return result > 0;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	/**
	 * Metoda zwracająca obiekt sadzonki o zadanym id
	 * 
	 * @param id
	 * 				- id sadzonki, która ma być pobrana
	 * @return
	 * 				- obiekt sadzonki o zadanym id
	 */
	public static Seed getSeedById(int id) {
		
		Seed seed = null;

		String query = "SELECT * FROM `seed` WHERE `id`='"+id+"'";
		
		ResultSet rs = dbManager.selectQuery(query);
		try {
			if (rs.next()) {
				seed = new Seed(rs.getInt("id"), rs.getString("name"), rs.getInt("qty"), rs.getInt("protectedOrigin"), rs.getInt("year"), rs.getString("additional"), rs.getString("additional2"));
				conn.close();
			}
			conn.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return seed;
	}
	
public static HashMap<Integer, Seed> getSeedsByType(int type) {
		
		HashMap<Integer, Seed> seeds = new HashMap<Integer, Seed>();

		String query = "SELECT * FROM `seed` WHERE `protectedOrigin`='"+type+"'";
		
		ResultSet rs = dbManager.selectQuery(query);
		try {
			while (rs.next()) {
				Seed seed = new Seed(rs.getInt("id"), rs.getString("name"), rs.getInt("qty"), rs.getInt("protectedOrigin"), rs.getInt("year"), rs.getString("additional"), rs.getString("additional2"));
				seeds.put(rs.getInt("id"), seed);
				conn.close();
			}
			conn.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return seeds;
	}
	
	/**
	 * Metoda służąca do dodawania danych sadzonki do bazy danych
	 * 
	 * @param name
	 * 						- nazwa sadzonki
	 * @param qty
	 * 						- ilość sadzonek
	 * @param additional
	 * 						- dodatkowe informacje o sadzonce
	 * @return
	 * 						- obiekt sadzonki właśnie dodanej do bazy danych sadzonki
	 */
	public static Seed addSeed(String name, int qty, int protectedOrigin, int year, String additional, String additional2) {
		
		Seed seed = null;
		
		String query = "INSERT INTO `seed` (`name`, `qty`, `protectedOrigin`, `year`, `additional`, `additional2`) VALUES ('" + name + "', '"
				+ qty + "', '" +  protectedOrigin  + "', '"  +  year  + "', '"  +additional  + "', '"  + additional2+ "');";
		
		try {
			int result = dbManager.otherQuery(query);
			conn.close();
			if(result > 0) {
				query = "SELECT `id` FROM `seed` WHERE `name` = '"+name+"' ORDER BY `id` DESC LIMIT 1";
				ResultSet rs = dbManager.selectQuery(query);
				if (rs.next()) {
					int id = rs.getInt("id");
					seed = new Seed(id, name, qty, protectedOrigin, year, additional, additional2);
				}
				conn.close();
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		
		return seed;
	}
	
	/**
	 * Metoda do aktualizacji danych na temat sadzonki o zadanym id
	 * 
	 * @param id
	 * 						- id sadzonki która ma być zaktualizowana
	 * @param name
	 * 						- nowa nazwa sadzonki, jeśli puste to bez zmian
	 * @param qty
	 * 						- nowa ilość sadzonek, jeśli -1 to bez zmian
	 * @param protectedOrigin
	 * 						- nowe id rodzaju chronienia nazwy pochodzenia, jeśli -1 to bez zmian
	 * @param year
	 * 						- nowy rok sadzenia, jeśli -1 to bez zmian
	 * @param additional
	 * 						- zaktualizowane dodatkowe informacje, jeśli puste to bez zmian
	 * @param additional2
	 * 						- zaktualizowane dodatkowe 2 informacje, jeśli puste to bez zmian
	 * @return
	 * 						- true jeśli operacja się powiodła, oraz false w przeciwnym wypadku
	 */
	public static boolean updateDataForSeedById(int id, String name, int qty, int protectedOrigin, int year, String additional, String additional2 ) {
		
		String query = "";
		
		if( !name.isEmpty() ) {
			query += "`name`='" + name + "'";
		}
		if ( qty != -1 ) {
			if (!query.isEmpty())
				query += ", ";
			query += "`qty`='" + qty + "'";
		}
		if ( protectedOrigin != -1 ) {
			if (!query.isEmpty())
				query += ", ";
			query += "`protectedOrigin`='" + protectedOrigin + "'";
		}
		if ( year != -1 ) {
			if (!query.isEmpty())
				query += ", ";
			query += "`year`='" + year + "'";
		}
		if ( !additional.isEmpty() ) {
			if (!query.isEmpty())
				query += ", ";
			query += "`additional`='" + additional + "'";
		}
		if ( !additional2.isEmpty() ) {
			if (!query.isEmpty())
				query += ", ";
			query += "`additional2`='" + additional2 + "'";
		}
		if (!query.isEmpty()) {
			query = "UPDATE `seed` SET " + query + "WHERE `id`='" + id + "'";
			try {
				int result = dbManager.otherQuery(query);
				conn.close();
				if (result > 0) {
					return true;
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Metoda usuwająca sadzonkę o zadanym id z bazy danych
	 * 
	 * @param id
	 * 				- id sadzonki, która ma być usunięta
	 * @return
	 * 				- true jeśli operacja się powiodła, oraz false w przeciwnym wypadku
	 */
	public static boolean removeSeedById(int id) {
	
		int result = dbManager.otherQuery("DELETE FROM `seed` WHERE `id`=" + id);
		try {
			conn.close();
			return result > 0;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	
	/**
	 * Metoda służąca do pobrania danych na temat pasków akcyzowych o zadanym id
	 * 
	 * @param id
	 * 				- id pasków akcyzowych, króre mają być pobrane
	 * @return
	 * 				- obiekt pasków akcyzowych o zadanym id
	 */
	public static Excise getExciseById(int id) {
		
		Excise excise = null;

		String query = "SELECT * FROM `excise` WHERE `id`='"+id+"'";
		
		ResultSet rs = dbManager.selectQuery(query);
		try {
			if (rs.next()) {
				excise = new Excise(rs.getInt("id"), rs.getString("name"), rs.getInt("amount"));
				conn.close();
			}
			conn.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return excise;
	}
	

	/**
	 * Metoda dodająca dane o paskach akcyzowych do bazy danych
	 * 
	 * @param name
	 * 					- nazwa pasków akcyzowych
	 * @param amount
	 * 					- liczba pasków akcyzowych
	 * @return
	 * 					- obiekt dodanych właśnie pasków akcyzowych
	 */
	public static Excise addExcise(String name, int amount) {
		
		Excise excise = null;
		
		String query = "INSERT INTO `excise` (`name`, `amount`) VALUES ('" + name + "', '"
				+ amount + "');";
		
		try {
			int result = dbManager.otherQuery(query);
			conn.close();
			if(result > 0) {
				query = "SELECT `id` FROM `excise` WHERE `name` = '"+name+"' ORDER BY `id` DESC LIMIT 1";
				ResultSet rs = dbManager.selectQuery(query);
				if (rs.next()) {
					int id = rs.getInt("id");
					excise = new Excise(id, name, amount);
				}
				conn.close();
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		
		return excise;
	}
	
	
	/**
	 * Metoda służąca do aktualizacji pasków akcyzowych o zadanym id
	 * 
	 * @param id
	 * 					- id pasków akcyzowych, które mają być zaktualizowane
	 * @param name
	 * 					- nowa nazwa pasków akcyzowych, jeśli pusta to bez zmian
	 * @param amount
	 * 					- nowa liczba pasków akcyzowych, jeśli -1 to bez zmian
	 * @return
	 * 					- true jeśli operacja się powiodła, false w przeciwnym wypadku
	 */
	public static boolean updateDataForExciseById(int id, String name, int amount ) {
		
		String query = "";
		
		if( !name.isEmpty() ) {
			query += "`name`='" + name + "'";
		}
		if ( amount != -1 ) {
			if (!query.isEmpty())
				query += ", ";
			query += "`amount`='" + amount + "'";
		}
		if (!query.isEmpty()) {
			query = "UPDATE `excise` SET " + query + "WHERE `id`='" + id + "'";
			try {
				int result = dbManager.otherQuery(query);
				conn.close();
				if (result > 0) {
					return true;
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Metoda usuwająca z bazy danych etykietki o zadanym id
	 * 
	 * @param id
	 * 				- id pasków akcyzowych, które mają być usunięte
	 * @return
	 * 				- true jeśli operacja się powiodła, false w przeciwnym wypadku
	 */
	public static boolean removeExciseById(int id) {
	
		int result = dbManager.otherQuery("DELETE FROM `excise` WHERE `id`=" + id);
		try {
			conn.close();
			return result > 0;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
}
