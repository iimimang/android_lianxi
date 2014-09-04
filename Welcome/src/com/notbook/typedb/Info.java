package com.notbook.typedb;

import java.io.Serializable;

public class Info implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;//id
	private String type;//����
	private Boolean checked ;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Info(String id, String type, Boolean checked) {
		super();
		this.id = id;
		this.type = type;
		this.checked = checked;
	}
	public Info() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Info [id=" + id + ", type=" + type + ", checked=" + checked
				+ "]";
	}
	
	
	
	

	
	
	
}
