package com.notbook.entity;

public class User {

	private String userid ;
	private String username ;
	private String password ;
	private String resetpwd ;
	private int uphone ;//手机号码
	private int vcode ;//短信验证码
	private String job ;//职业
	private String uemail ;//邮箱
	private String uaddress ;//地址
	private String trade ;//关注行业
	private String item ;//关注项目
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(String userid, String username, String password,
			String resetpwd, int uphone, int vcode, String job, String uemail,
			String uaddress, String trade, String item) {
		super();
		this.userid = userid;
		this.username = username;
		this.password = password;
		this.resetpwd = resetpwd;
		this.uphone = uphone;
		this.vcode = vcode;
		this.job = job;
		this.uemail = uemail;
		this.uaddress = uaddress;
		this.trade = trade;
		this.item = item;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getResetpwd() {
		return resetpwd;
	}
	public void setResetpwd(String resetpwd) {
		this.resetpwd = resetpwd;
	}
	public int getUphone() {
		return uphone;
	}
	public void setUphone(int uphone) {
		this.uphone = uphone;
	}
	public int getVcode() {
		return vcode;
	}
	public void setVcode(int vcode) {
		this.vcode = vcode;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getUemail() {
		return uemail;
	}
	public void setUemail(String uemail) {
		this.uemail = uemail;
	}
	public String getUaddress() {
		return uaddress;
	}
	public void setUaddress(String uaddress) {
		this.uaddress = uaddress;
	}
	public String getTrade() {
		return trade;
	}
	public void setTrade(String trade) {
		this.trade = trade;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	@Override
	public String toString() {
		return "User [userid=" + userid + ", username=" + username
				+ ", password=" + password + ", resetpwd=" + resetpwd
				+ ", uphone=" + uphone + ", vcode=" + vcode + ", job=" + job
				+ ", uemail=" + uemail + ", uaddress=" + uaddress + ", trade="
				+ trade + ", item=" + item + "]";
	}
	
	
	
	
}
