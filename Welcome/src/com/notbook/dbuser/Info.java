package com.notbook.dbuser;

import java.io.Serializable;

public class Info implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;//id
	private String username;
	private String job;
	private String email;
	private String address;
	public Info() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Info(String id, String username, String job, String email,
			String address) {
		super();
		this.id = id;
		this.username = username;
		this.job = job;
		this.email = email;
		this.address = address;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "Info [id=" + id + ", username=" + username + ", job=" + job
				+ ", email=" + email + ", address=" + address + "]";
	}
	
	
	
	
		
	
	
}
