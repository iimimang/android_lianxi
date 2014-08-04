/**
 * 
 */
package com.flysnow.sina.weibo;


import java.text.DecimalFormat;
import java.text.NumberFormat;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * 更多Activity
 * @author 
 * @since 2011-3-8
 */
public class MoreActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.layout2);     
		TextView a=(TextView) findViewById(R.id.textView111);
		Bundle budle = this.getIntent().getExtras();
		String sex =budle.getString("sex");
		double height = budle.getDouble("height");
		
		
	}
	
	/* 四舍五入的method */
	private String format(double num) {
		NumberFormat formatter = new DecimalFormat("0.00");
		String s = formatter.format(num);
		return s;
	}
	
	private String getWeight(String sex, double height) {
		String weight = "";
		if (sex.equals("男性")) {
		weight = format((height - 80) * 0.7);
		} else {
		weight = format((height - 70) * 0.6);
		} 
		
		return weight;
	}
	
}
