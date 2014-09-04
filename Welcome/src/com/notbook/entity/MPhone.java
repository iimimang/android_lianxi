package com.notbook.entity;

public class MPhone {

	private String id ;
	private String uphone ;
	private String uname ;
	private String uaddress ;
	private String uphoto ;
	private Boolean checkState=false;
	private String uemail ;
	private String job ;
	
	public MPhone() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MPhone(String id, String uphone, String uname, String uaddress,
			String uphoto, Boolean checkState, String uemail, String job) {
		super();
		this.id = id;
		this.uphone = uphone;
		this.uname = uname;
		this.uaddress = uaddress;
		this.uphoto = uphoto;
		this.checkState = checkState;
		this.uemail = uemail;
		this.job = job;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUphone() {
		return uphone;
	}

	public void setUphone(String uphone) {
		this.uphone = uphone;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getUaddress() {
		return uaddress;
	}

	public void setUaddress(String uaddress) {
		this.uaddress = uaddress;
	}

	public String getUphoto() {
		return uphoto;
	}

	public void setUphoto(String uphoto) {
		this.uphoto = uphoto;
	}

	public Boolean getCheckState() {
		return checkState;
	}

	public void setCheckState(Boolean checkState) {
		this.checkState = checkState;
	}

	public String getUemail() {
		return uemail;
	}

	public void setUemail(String uemail) {
		this.uemail = uemail;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	@Override
	public String toString() {
		return "MPhone [id=" + id + ", uphone=" + uphone + ", uname=" + uname
				+ ", uaddress=" + uaddress + ", uphoto=" + uphoto
				+ ", checkState=" + checkState + ", uemail=" + uemail
				+ ", job=" + job + "]";
	}
	
	
	
	
	
	
}
