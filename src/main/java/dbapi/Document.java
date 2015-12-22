package dbapi;

import java.sql.Blob;
import java.sql.Date;

public class Document {

	private int id;
	private String description;
	private String name;
	private String type;
	private Date createdAt;
	private Blob document;
	private int creatorId;

	public Document(int id, int creatorId, String name, String description, String type, Date createdAt,
			Blob document) {
		super();
		this.id = id;
		this.creatorId = creatorId;
		this.name = name;
		this.description = description;
		this.type = type;
		this.createdAt = createdAt;
		this.document = document;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Blob getDocument() {
		return document;
	}

	public void setDocument(Blob document) {
		this.document = document;
	}

	public int getId() {
		return id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public int getCreatorId() {
		return creatorId;
	}

}
