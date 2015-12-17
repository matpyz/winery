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

	DBManager() {
		this.polaczenieURL = "jdbc:mysql://db4free.net/testwina?user=testwina&password=testwina";
	}

	/**
	 * @param query
	 *            - metoda wywołująca zapytania do bazy
	 * @return zwraza wynik zapytania, który należy następnie obrobić, by
	 *         następnie zamknąć połączenie
	 */
	ResultSet selectQuery(String query) {
		try {

			conn = DriverManager.getConnection(polaczenieURL);
			Class.forName("com.mysql.jdbc.Driver");
			Statement stmt = conn.createStatement();
			ResultSet rs = null;
			rs = stmt.executeQuery(query);

			return rs;
		} catch (ClassNotFoundException wyjatek) {
			System.out.println("Problem ze sterownikiem");
			return null;
		} catch (SQLException wyjatek) {
			System.out.println("SQLException: " + wyjatek.getMessage());
			System.out.println("SQLState: " + wyjatek.getSQLState());
			System.out.println("VendorError: " + wyjatek.getErrorCode());
			return null;
		}
	}

	int otherQuery(String query) {
		try {

			conn = DriverManager.getConnection(polaczenieURL);
			Class.forName("com.mysql.jdbc.Driver");
			Statement stmt = conn.createStatement();
			int rs = stmt.executeUpdate(query);

			return rs;
		} catch (ClassNotFoundException wyjatek) {
			System.out.println("Problem ze sterownikiem");
			return 0;
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
		if (DBManager.users == null) {
			HashMap<Integer, User> users = new HashMap<Integer, User>();
			DBManager.users = new HashMap<Integer, User>();
			String query = "SELECT `user`.`id`, `login`, `user`.`name`, `surname`, `email`, `groupId`, `group`.`name` as `groupName` FROM `user`, `user2group`, `group` WHERE `user`.`id`=`user2group`.`userId` AND `user2group`.`groupId`=`group`.`id`";
			ResultSet rs = dbManager.selectQuery(query);
			try {
				while (rs.next()) {
					User user = new User(rs.getInt("id"), rs.getString("login"), rs.getString("name"),
							rs.getString("surname"), rs.getString("email"), rs.getInt("groupId"),
							rs.getString("groupName"));
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
		String query = "SELECT `user`.`id`, `login`, `user`.`name`, `surname`, `email`, `groupId`, `group`.`name` as `groupName` FROM `user`, `user2group`, `group` WHERE `user`.`id`="
				+ userId + " AND `user`.`id`=`user2group`.`userId` AND `user2group`.`groupId`=`group`.`id`";
		ResultSet rs = dbManager.selectQuery(query);
		User user = null;
		try {
			if (rs.next()) {
				user = new User(rs.getInt("id"), rs.getString("login"), rs.getString("name"), rs.getString("surname"),
						rs.getString("email"), rs.getInt("groupId"), rs.getString("groupName"));
			}
			if (DBManager.users != null) {
				DBManager.users.replace(userId, user);
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
		String query = "SELECT `user`.`id`, `login`, `user`.`name`, `surname`, `email`, `groupId`, `group`.`name` as `groupName` FROM `user`, `user2group`, `group` WHERE `user`.`id`=`user2group`.`userId` AND `user2group`.`groupId`=`group`.`id` AND `login`='"
				+ login + "' AND `password`='" + password + "'";
		ResultSet rs = dbManager.selectQuery(query);
		User user = null;
		try {
			if (rs.next()) {
				user = new User(rs.getInt("id"), rs.getString("login"), rs.getString("name"), rs.getString("surname"),
						rs.getString("email"), rs.getInt("groupId"), rs.getString("groupName"));
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
	 */
	public static void addUser(String login, String password, String mail, String name, String surname, int groupId) {
		String query = "INSERT INTO `user` (`name`, `email`, `password`, `login`, `surname`) VALUES ('" + name + "', '"
				+ mail + "', '" + password + "', '" + login + "', '" + surname + "');";
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
					query = "SELECT `user`.`id`, `login`, `user`.`name`, `surname`, `email`, `groupId`, `group`.`name` as `groupName` FROM `user`, `user2group`, `group` WHERE `user`.`id`=`user2group`.`userId` AND `user2group`.`groupId`=`group`.`id` AND `user`.`id`='"
							+ id + "'";
					rs = dbManager.selectQuery(query);
					User user = new User(rs.getInt("id"), rs.getString("login"), rs.getString("name"),
							rs.getString("surname"), rs.getString("email"), rs.getInt("groupId"),
							rs.getString("groupName"));
					DBManager.users.put(rs.getInt("id"), user);

				}
			}
			conn.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
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
					if (userId == id)
						return true;
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
	 * @return Zwraca true jeśli operacja się udała, a w przeciwnym wypadku
	 *         domyśl się
	 */
	public static boolean changeUserData(int userId, String login, String password, String mail, String name,
			String surname, int groupId) {
		String query = "";
		boolean isUpdateAllWithoutGroup = true;
		boolean isUpdateGroup = true;
		if (!login.isEmpty())
			query += "`login` = '" + login + "'";
		if (!password.isEmpty()) {
			if (!query.isEmpty())
				query += " AND ";
			query += "`password` = '" + password + "'";
		}
		if (!mail.isEmpty()) {
			if (!query.isEmpty())
				query += " AND ";
			query += "`email` = '" + mail + "'";
		}
		if (!name.isEmpty()) {
			if (!query.isEmpty())
				query += " AND ";
			query += "`name` = '" + name + "'";
		}
		if (!surname.isEmpty()) {
			if (!query.isEmpty())
				query += " AND ";
			query += "`surname` = '" + surname + "'";
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
		if (isUpdateAllWithoutGroup && isUpdateGroup)
			return true;
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
			query += "`name`='" + name + "' ";
		}
		if (!description.isEmpty()) {
			if (!query.isEmpty())
				query += "AND ";
			query += "`description`='" + description + "' ";
		}
		if (!type.isEmpty()) {
			if (!query.isEmpty())
				query += "AND ";
			query += "`type`='" + type + "' ";
		}
		if (document != null) {
			if (!query.isEmpty())
				query += "AND ";
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
				query += "`name`='" + name + "' ";
			}
			if (!description.isEmpty()) {
				if (!query.isEmpty())
					query += "AND ";
				query += "`description`='" + description + "' ";
			}
			if (startDate == null) {
				if (!query.isEmpty())
					query += "AND ";
				query += "`startDate`='" + startDate + "' ";
			}
			if (endDate != null) {
				if (!query.isEmpty())
					query += "AND ";
				query += "`endDate`='" + endDate + "' ";
			}
			if (!location.isEmpty()) {
				if (!query.isEmpty())
					query += "AND ";
				query += "`location`='" + location + "' ";
			}
			if (eventTypeId != 0) {
				if (!query.isEmpty())
					query += "AND ";
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
		Blob document = conn.createBlob();
		int bufferSize = 4096, got;
		byte[] buffer = new byte[bufferSize];
		try (OutputStream out = document.setBinaryStream(0); FileInputStream in = new FileInputStream(path)) {
			while ((got = in.read(buffer)) != -1) {
				out.write(buffer, 0, got);
			}
		}
		return document;
	}
}
