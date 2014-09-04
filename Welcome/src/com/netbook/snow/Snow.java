package com.netbook.snow;

import java.util.Random;

public class Snow {
	Coordinate coordinate;
	int speed= 50;
	
	public Snow(int x, int y, int speed){
		coordinate = new Coordinate(x, y);
		System.out.println("Speed:"+speed);
		this.speed = speed;
		if(this.speed == 0) {
			this.speed =1;
		}
	}
	
}
