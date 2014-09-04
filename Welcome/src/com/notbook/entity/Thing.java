package com.notbook.entity;

import java.io.Serializable;

public class Thing implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String tone ;
	private String ponsuser ;
	private String text ;
	private String recuser ;
	private String uptime ;
	private String ctype ;
	private String type ;
	private String uId ;
	private String recname ;
	private String recphone ;
	private String cphoto ;
	private String ringtime ;
	private String cfirst ;
	private String id ;
	private String advtime ;
	private String chuanyupath ;
	private String first ;
	private String cname ;
	private String uname ;
	private int posttype;
	private String num ;
	public Thing() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getTone() {
		return tone;
	}
	public void setTone(String tone) {
		this.tone = tone;
	}
	public String getPonsuser() {
		return ponsuser;
	}
	public void setPonsuser(String ponsuser) {
		this.ponsuser = ponsuser;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getRecuser() {
		return recuser;
	}
	public void setRecuser(String recuser) {
		this.recuser = recuser;
	}
	public String getUptime() {
		return uptime;
	}
	public void setUptime(String uptime) {
		this.uptime = uptime;
	}
	public String getCtype() {
		return ctype;
	}
	public void setCtype(String ctype) {
		this.ctype = ctype;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public String getRecname() {
		return recname;
	}
	public void setRecname(String recname) {
		this.recname = recname;
	}
	public String getRecphone() {
		return recphone;
	}
	public void setRecphone(String recphone) {
		this.recphone = recphone;
	}
	public String getCphoto() {
		return cphoto;
	}
	public void setCphoto(String cphoto) {
		this.cphoto = cphoto;
	}
	public String getRingtime() {
		return ringtime;
	}
	public void setRingtime(String ringtime) {
		this.ringtime = ringtime;
	}
	public String getCfirst() {
		return cfirst;
	}
	public void setCfirst(String cfirst) {
		this.cfirst = cfirst;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAdvtime() {
		return advtime;
	}
	public void setAdvtime(String advtime) {
		this.advtime = advtime;
	}
	public String getChuanyupath() {
		return chuanyupath;
	}
	public void setChuanyupath(String chuanyupath) {
		this.chuanyupath = chuanyupath;
	}
	public String getFirst() {
		return first;
	}
	public void setFirst(String first) {
		this.first = first;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public int getPosttype() {
		return posttype;
	}
	public void setPosttype(int posttype) {
		this.posttype = posttype;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Thing(String tone, String ponsuser, String text, String recuser,
			String uptime, String ctype, String type, String uId,
			String recname, String recphone, String cphoto, String ringtime,
			String cfirst, String id, String advtime, String chuanyupath,
			String first, String cname, String uname, int posttype, String num) {
		super();
		this.tone = tone;
		this.ponsuser = ponsuser;
		this.text = text;
		this.recuser = recuser;
		this.uptime = uptime;
		this.ctype = ctype;
		this.type = type;
		this.uId = uId;
		this.recname = recname;
		this.recphone = recphone;
		this.cphoto = cphoto;
		this.ringtime = ringtime;
		this.cfirst = cfirst;
		this.id = id;
		this.advtime = advtime;
		this.chuanyupath = chuanyupath;
		this.first = first;
		this.cname = cname;
		this.uname = uname;
		this.posttype = posttype;
		this.num = num;
	}
	@Override
	public String toString() {
		return "Thing [tone=" + tone + ", ponsuser=" + ponsuser + ", text="
				+ text + ", recuser=" + recuser + ", uptime=" + uptime
				+ ", ctype=" + ctype + ", type=" + type + ", uId=" + uId
				+ ", recname=" + recname + ", recphone=" + recphone
				+ ", cphoto=" + cphoto + ", ringtime=" + ringtime + ", cfirst="
				+ cfirst + ", id=" + id + ", advtime=" + advtime
				+ ", chuanyupath=" + chuanyupath + ", first=" + first
				+ ", cname=" + cname + ", uname=" + uname + ", posttype="
				+ posttype + ", num=" + num + "]";
	}
	
}
