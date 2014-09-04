package com.notbook.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Xiangqing implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id ;
	private String projectName ;
	private String text ;
	private String foundTime ;
	private List list=new ArrayList() ;
	private int num = 0;
	public Xiangqing() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getFoundTime() {
		return foundTime;
	}
	public void setFoundTime(String foundTime) {
		this.foundTime = foundTime;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Xiangqing(String id, String projectName, String text,
			String foundTime, List list, int num) {
		super();
		this.id = id;
		this.projectName = projectName;
		this.text = text;
		this.foundTime = foundTime;
		this.list = list;
		this.num = num;
	}
	@Override
	public String toString() {
		return "Xiangqing [id=" + id + ", projectName=" + projectName
				+ ", text=" + text + ", foundTime=" + foundTime + ", list="
				+ list + ", num=" + num + "]";
	}
	
	
	
	
	
}
