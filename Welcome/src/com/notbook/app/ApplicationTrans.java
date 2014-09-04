package com.notbook.app;


import android.app.Application;

public class ApplicationTrans extends Application {

	public String id ;
	public static int broadFlag ;
	public static int citycode ;
	public String things1 ;
	
	
	public String getThings1() {
		return things1;
	}

	public void setThings1(String things1) {
		this.things1 = things1;
	}

	public int getCitycode() {
		return citycode;
	}

	public void setCitycode(int citycode) {
		this.citycode = citycode;
	}

	public static int getBroadFlag() {
		return broadFlag;
	}

	public static void setBroadFlag(int broadFlag) {
		ApplicationTrans.broadFlag = broadFlag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

	
	
}
