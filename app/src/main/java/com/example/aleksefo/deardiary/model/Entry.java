package com.example.aleksefo.deardiary.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import java.util.Date;

public class Entry extends RealmObject {

	//	@Required todo set Required
//	The @PrimaryKey annotation indicates that this field is set as a Primary key and must not be null.
	@PrimaryKey
	private String id;
	//	@Required
	private Date date;
	//	@Required
	private String title;
	//	@Required
	private String descr;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}