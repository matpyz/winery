package dbapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class User {

	private int id;
	private String login;
	private String name;
	private String surname;
	private String mail;
	private int groupId;
	private String groupName;
	private int payment;
	private HashMap<Integer, Permission> permissions;

	public User(int id, String login, String name, String surname, String mail, int groupId, String groupName, int payment) {
		super();
		this.id = id;
		this.login = login;
		this.name = name;
		this.surname = surname;
		this.mail = mail;
		this.groupId = groupId;
		this.groupName = groupName;
		this.payment = payment;
	}

	public int getPayment() {
		return payment;
	}

	public void setPayment(int payment) {
		this.payment = payment;
	}

	public ArrayList<String> getData() {
		return new ArrayList<>(Arrays.asList(name,surname,mail,login,groupName));
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public String getGroupName() {
		return groupName;
	}

	public HashMap<Integer, Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(HashMap<Integer, Permission> permissions) {
		this.permissions = permissions;
	}
}
