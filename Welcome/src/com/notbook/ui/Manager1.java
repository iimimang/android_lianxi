package com.notbook.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notbook.R;
import com.notbook.app.MyGridView;
import com.notbook.typedb.Meta;

public class Manager1 extends BaseActivity {

	private Button more_back ,managesave,delete,add;
	static ContentResolver resolver;
	private MyGridView findshare_2;
	private MyAdapter myadapter = null;
	private EditText add_text;
	Cursor c ;
	List<Boolean> mChecked = new ArrayList<Boolean>();
	HashMap<Integer,View> map = new HashMap<Integer,View>(); 
	public static List<String> list = new ArrayList<String>();
	List<Integer> listItemID = new ArrayList<Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
		findViewById(R.id.one).setBackgroundResource(background_photobg[num - 1]);
		findViewById(R.id.one1).setBackgroundResource(background_photobg1[num - 1]);
		more_back = (Button)findViewById(R.id.more_back1);
		managesave = (Button)findViewById(R.id.managesave);
		delete = (Button)findViewById(R.id.delete);
		add = (Button)findViewById(R.id.add);
		add_text = (EditText)findViewById(R.id.add_type);
		findshare_2 = (MyGridView) findViewById(R.id.findshare_2);
		resolver = getContentResolver();
		list.clear();
		list.add("会议");
		list.add("生日");
		list.add("记事");
		list.add("聚会");
		c = resolver.query(Meta.CONTENT_URI, null, null, null, null);
		
		if(c!=null){
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext())  {  
				int nameColumn = c.getColumnIndex(Meta.TableMeta._TYPE);  
				String typeArr = c.getString(nameColumn);
				list.add(typeArr);
			}
		}
		for(int i=0;i<list.size();i++){
			mChecked.add(false);
		}
		
		myadapter = new MyAdapter();
		findshare_2.setAdapter(myadapter);
		myadapter.notifyDataSetChanged();
		more_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Eventadd.NUM = 0;
				finish();
			}
		});
		add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String text = add_text.getText().toString().trim();
				for(int i=0;i<list.size();i++){
					if(list.get(i).toString().equals(text)){
						Toast.makeText(getApplicationContext(), "类型已添加", 0).show();
						return;
					}
				}
				if(list.size()>=20){
					Toast.makeText(getApplicationContext(), "类型已满不能添加", 0).show();
					return;
				}{
				if("".equals(text)){
					Toast.makeText(getApplicationContext(), "输入栏为空不能添加", 0).show();
					return;
				}
				String regex = "([a-z]|[A-Z]|[\\u4e00-\\u9fa5])+";
				Pattern pat = Pattern.compile(regex);
				pat.matcher(text).matches();
				if (pat.matcher(text).matches() == false) {
					Toast.makeText(Manager1.this, "姓名只能是中文，英文", 0).show();
					return;
				}
				
				if (Register.length(text) > 12) {
					Toast.makeText(Manager1.this, "输入栏的长度过长", 0).show();
					return;
				}else{
					list.add(text);
					myadapter.notifyDataSetChanged();
					ContentValues values = new ContentValues();
					values.put(Meta.TableMeta._TYPE, text);
					mChecked.add(false);
					Uri uri = resolver.insert(Meta.CONTENT_URI, values);
					Log.i("log", "=====================uri========"+uri);
					if(uri != null){
						Log.i("log", "添加成功！");
						Toast.makeText(getApplicationContext(), "添加成功", 0).show();
						add_text.setText("");
					}
					Eventadd.NUM = 0;
					myadapter.notifyDataSetChanged();
				}
				}
			}
		});
		
		delete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(Manager1.this)
				.setTitle("温馨提示")
				.setMessage("确认要删除吗？")
				.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
							}
						})
				.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								myadapter.notifyDataSetChanged();
								for(int i=0;i<list.size();i++){
									if(mChecked.get(i)==true){
										int n = getContentResolver()  
												.delete(Meta.CONTENT_URI,
														"_type=?",
														new String[] { list.get(i) });
										
										if (n > 0) {
//											Toast.makeText(getApplication(), "删除成功！", 0).show();
											mChecked.set(i, false);
										}
									}
									
								}
								list.clear();
								list.add("会议");
								list.add("生日");
								list.add("记事");
								list.add("聚会");
								c = resolver.query(Meta.CONTENT_URI, null, null, null,null);
								
								if(c!=null){
									for(c.moveToFirst();!c.isAfterLast();c.moveToNext())  {  
										int nameColumn = c.getColumnIndex(Meta.TableMeta._TYPE);  
										String typeArr = c.getString(nameColumn);
										list.add(typeArr);
									}
								}
								myadapter.notifyDataSetChanged();
							}
						}).show();
			}
		});
		managesave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Eventadd.NUM = 0;
				finish();
			}
		});
	}
	private class MyAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			ViewHolder holder = null;
			if (convertView == null) {
				//Toast.makeText(getApplication(), position+"-------------", 0).show();
				holder = new ViewHolder();
				convertView = View.inflate(getApplicationContext(),
						R.layout.share_list4, null);
				holder.type=(TextView)convertView.findViewById(R.id.type);
				holder.selected = (CheckBox)convertView.findViewById(R.id.xuanze);
				
				final int p = position;
				map.put(position, convertView);
				
				
				holder.selected.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						CheckBox cb = (CheckBox)v;
						mChecked.set(p, cb.isChecked());
						
						
					}
				});
				convertView.setTag(holder);
			} else {

				holder = (ViewHolder) convertView.getTag();
				
			}
			holder.selected.setChecked(false);
			holder.type.setText(list.get(position).toString());
			if(position<4){
				holder.selected.setEnabled(false);
			}else
				holder.selected.setEnabled(true);
			
			return convertView;
		}
		
	}
	static class ViewHolder {
		TextView type;
		CheckBox selected;
	}
}
