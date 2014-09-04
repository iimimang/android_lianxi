package com.notbook.photodb;

import java.io.Serializable;

public class Info implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;//id
	private String date;//����
	public Info() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Info(String id, String date) {
		super();
		this.id = id;
		this.date = date;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "Info [id=" + id + ", date=" + date + "]";
	}

	
	
	
}
