package com.notbook.db;

import java.io.Serializable;

public class Info implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;//id
	private String city;//����
	private String weather;//ʱ��
	private String temp;//����
	public Info() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Info(String id, String city, String weather, String temp) {
		super();
		this.id = id;
		this.city = city;
		this.weather = weather;
		this.temp = temp;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "Info [id=" + id + ", city=" + city + ", weather=" + weather
				+ ", temp=" + temp + "]";
	}
		
	
	
}
