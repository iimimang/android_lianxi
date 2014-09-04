package com.notbook.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.notbook.R;
import com.notbook.entity.Thing;

public class Receiveperson extends BaseActivity {

	private Button more_back ;
	private String recnametext = "";//接收人
	private String ponsuser = "";//响应人
	private String recuser = ""; //总人数
	private TextView ponsuserTextview ;
	private TextView noponsuserTextview ;
	private ArrayList<Thing> things = new ArrayList<Thing>();
	private ArrayList<Thing> things1 = new ArrayList<Thing>();
	private ArrayList<Thing> things2 = new ArrayList<Thing>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.receiveperson);
		findViewById(R.id.one).setBackgroundResource(background_photobg[num - 1]);
		findViewById(R.id.one1).setBackgroundResource(background_photobg1[num - 1]);
		more_back = (Button)findViewById(R.id.more_back);
		ponsuserTextview = (TextView)findViewById(R.id.ponsuserTextview);
		noponsuserTextview = (TextView)findViewById(R.id.noponsuserTextview);
		more_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		Bundle bundle = getIntent().getExtras();
		if(null != bundle && !bundle.equals("")){
			recnametext = bundle.getString("recnametext");
			recuser = bundle.getString("recuser").toString();
			ponsuser = bundle.getString("ponsuser");
			//接收人
			String str1[]  = recnametext.split("\\,");
			things1.clear();
			for(int i=0;i<str1.length;i++){
				Thing thing1 = new Thing();
				String name1 = str1[i];
				thing1.setRecname(name1);
				things1.add(thing1);
			}
			//响应人
			String str[]  = ponsuser.split("\\,");
			things.clear();
			for(int i=0;i<str.length;i++){
				Thing thing = new Thing();
				String name = str[i];
				thing.setRecname(name);
				things.add(thing);
			}
			//总人数
			String str2[]  = recuser.split("\\,");
			things2.clear();
			for(int i=0;i<str2.length;i++){
				Thing thing2 = new Thing();
				String name2 = str2[i];
				thing2.setRecname(name2);
				things2.add(thing2);
			}
			if(things.size()<=0){
				noponsuserTextview.setText(recnametext);
			}else{
				StringBuffer s = new StringBuffer();
				StringBuffer s1 = new StringBuffer();
				String strtext = "";
				for(int i=0;i<things2.size();i++){
					for(int j=0;j<things.size();j++){
						if(things.get(j).getRecname().toString().equals(things2.get(i).getRecname().toString())){
							strtext = things1.get(i).getRecname().toString().trim();
							strtext = s.append(strtext).append(",").toString();
							if(!strtext.equals("")&&strtext.length()>0){
								strtext = strtext.substring(0, strtext.lastIndexOf(","));
							}
						}
					}
					
				}
				ponsuserTextview.setText(strtext);
				
				String strtext2 = "";
				for(int i=0;i<things2.size();i++){
					for(int j=0;j<things.size();j++){
						if(!things.get(j).getRecname().toString().equals(things2.get(i).getRecname().toString())){
							strtext2 = things1.get(i).getRecname().toString().trim();
							strtext2 = s1.append(strtext2).append(",").toString();
							if(!strtext2.equals("")&&strtext2.length()>0){
								strtext2 = strtext2.substring(0, strtext2.lastIndexOf(","));
							}
						}
					}
					
				}
				noponsuserTextview.setText(strtext2);
			}
			
		}
	}
}
