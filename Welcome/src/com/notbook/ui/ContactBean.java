package com.notbook.ui;

import android.R.bool;

public class ContactBean implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String contactName = null;
	private String contactPhone;
	private String contactHomePhone;
	private String contactid ;
	private Boolean checkState=false;
	
	public Boolean getCheckState() {
		return checkState;
	}
	public void setCheckState(Boolean checkState) {
		this.checkState = checkState;
	}
	public String getContactid() {
		return contactid;
	}
	public void setContactid(String contactid) {
		this.contactid = contactid;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getContactHomePhone() {
		return contactHomePhone;
	}
	public void setContactHomePhone(String contactHomePhone) {
		this.contactHomePhone = contactHomePhone;
	}
	@Override
	public String toString() {
		return "ContactBean [contactName=" + contactName + ", contactPhone="
				+ contactPhone + ", contactHomePhone=" + contactHomePhone
				+ ", contactid=" + contactid + ", checkState=" + checkState
				+ "]";
	}
	
}
