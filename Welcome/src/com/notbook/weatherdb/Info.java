package com.notbook.weatherdb;

import java.io.Serializable;

public class Info implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;//id
	private String city;//城市
	private String weather1;//天气状态
	private String weather2;
	private String weather3;//ʱ��
	private String weather4;
	private String weather5;//ʱ��
	private String temp1;//温度范围
	private String temp2;
	private String temp3;
	private String temp4;
	private String temp5;
	private String week1;//周几
	private String week2;
	private String week3;
	private String week4;
	private String week5;
	private String date1;//日期阳历
	private String date1_y;//阴历
	private String date2;
	private String date2_y;
	private String date3;
	private String date3_y;
	private String date4;
	private String date4_y;
	private String date5;
	private String date5_y;
	private String todaywendu;//当前温度
	private String shidu;//湿度
	public Info() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Info(String id, String city,
			String weather1,String weather2,String weather3,
			String weather4,String weather5,String temp1,String temp2
			,String temp3,String temp4,String temp5,String week1,String week2,String week3
			,String week4,String week5,String date1,String date2
			,String date3,String date4,String date5,String date1_y
			,String date2_y,String date3_y,String date4_y,String date5_y
			,String todaywendu,String shidu) {
		super();
		this.id = id;
		this.city = city;
		this.weather1 = weather1;
		this.weather2 = weather2;
		this.weather3 = weather3;
		this.weather4 = weather4;
		this.weather5 = weather5;
		this.temp1 = temp1;
		this.temp2 = temp2;
		this.temp3 = temp3;
		this.temp4 = temp4;
		this.temp5 = temp5;
		this.week1 = week1;
		this.week2 = week2;
		this.week3 = week3;
		this.week4 = week4;
		this.week5 = week5;
		this.date1_y = date1_y;
		this.date2_y = date2_y;
		this.date3_y = date3_y;
		this.date4_y = date4_y;
		this.date5_y = date5_y;
		this.todaywendu = todaywendu;
		this.shidu = shidu;
	}
	@Override
	public String toString() {
		return "Info [id=" + id + ", city=" + city + ", weather1=" + weather1+ ", weather2=" + weather2
				+ ", weather3=" + weather3+ ", weather4=" + weather4+ ", weather5=" + weather5
				+ ", temp1=" + temp1+ ", temp2=" + temp2+ ", temp3=" + temp3+ ", temp4=" + temp4
				+ ", temp5=" + temp5+  ", week1=" + week1+  ", week2=" + week2+  ", week3=" + week3
				+  ", week4=" + week4+  ", week5=" + week5+  ", week1=" + week1+ ", date1_y=" + date1_y+ ", date1_y=" + date1_y
				+ ", date2_y=" + date2_y+ ", date3_y=" + date3_y+ ", date4_y=" + date4_y+ ", date5_y=" + date5_y
				+ ", todaywendu=" + todaywendu+ ", shidu=" + shidu+"]";
	}
	
	public String getWeather2() {
		return weather2;
	}
	public void setWeather2(String weather2) {
		this.weather2 = weather2;
	}
	public String getWeather3() {
		return weather3;
	}
	public void setWeather3(String weather3) {
		this.weather3 = weather3;
	}
	public String getWeather4() {
		return weather4;
	}
	public void setWeather4(String weather4) {
		this.weather4 = weather4;
	}
	public String getWeather5() {
		return weather5;
	}
	public void setWeather5(String weather5) {
		this.weather5 = weather5;
	}
	public String getTemp2() {
		return temp2;
	}
	public void setTemp2(String temp2) {
		this.temp2 = temp2;
	}
	public String getTemp3() {
		return temp3;
	}
	public void setTemp3(String temp3) {
		this.temp3 = temp3;
	}
	public String getTemp4() {
		return temp4;
	}
	public void setTemp4(String temp4) {
		this.temp4 = temp4;
	}
	public String getTemp5() {
		return temp5;
	}
	public void setTemp5(String temp5) {
		this.temp5 = temp5;
	}

	public String getWeek1() {
		return week1;
	}

	public void setWeek1(String week1) {
		this.week1 = week1;
	}

	public String getWeek2() {
		return week2;
	}

	public void setWeek2(String week2) {
		this.week2 = week2;
	}

	public String getWeek3() {
		return week3;
	}

	public void setWeek3(String week3) {
		this.week3 = week3;
	}

	public String getWeek4() {
		return week4;
	}

	public void setWeek4(String week4) {
		this.week4 = week4;
	}

	public String getWeek5() {
		return week5;
	}

	public void setWeek5(String week5) {
		this.week5 = week5;
	}

	public String getDate1() {
		return date1;
	}

	public void setDate1(String date1) {
		this.date1 = date1;
	}

	public String getDate1_y() {
		return date1_y;
	}

	public void setDate1_y(String date1_y) {
		this.date1_y = date1_y;
	}

	public String getDate2() {
		return date2;
	}

	public void setDate2(String date2) {
		this.date2 = date2;
	}

	public String getDate2_y() {
		return date2_y;
	}

	public void setDate2_y(String date2_y) {
		this.date2_y = date2_y;
	}

	public String getDate3() {
		return date3;
	}

	public void setDate3(String date3) {
		this.date3 = date3;
	}

	public String getDate3_y() {
		return date3_y;
	}

	public void setDate3_y(String date3_y) {
		this.date3_y = date3_y;
	}

	public String getDate4() {
		return date4;
	}

	public void setDate4(String date4) {
		this.date4 = date4;
	}

	public String getDate4_y() {
		return date4_y;
	}

	public void setDate4_y(String date4_y) {
		this.date4_y = date4_y;
	}

	public String getDate5() {
		return date5;
	}

	public void setDate5(String date5) {
		this.date5 = date5;
	}

	public String getDate5_y() {
		return date5_y;
	}

	public void setDate5_y(String date5_y) {
		this.date5_y = date5_y;
	}

	public String getTodaywendu() {
		return todaywendu;
	}

	public void setTodaywendu(String todaywendu) {
		this.todaywendu = todaywendu;
	}

	public String getShidu() {
		return shidu;
	}

	public void setShidu(String shidu) {
		this.shidu = shidu;
	}

	public void setTemp1(String temp1) {
		this.temp1 = temp1;
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
	public String getWeather1() {
		return weather1;
	}
	public void setWeather1(String weather1) {
		this.weather1 = weather1;
	}
	public String getTemp1() {
		return temp1;
	}
	public void setTemp(String temp1) {
		this.temp1 = temp1;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
