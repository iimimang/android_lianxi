package com.example.photoalbum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class MainActivity extends Activity implements OnItemClickListener{
	private GridView mGridView;
	private int[] imgIds;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		imgIds = new int[]{R.drawable.item01,R.drawable.item02,R.drawable.item03,R.drawable.item04,
				R.drawable.item05, R.drawable.item06, R.drawable.item07, R.drawable.item08,R.drawable.item09,
				R.drawable.item10, R.drawable.item11, R.drawable.item12};
		
		mGridView = (GridView) findViewById(R.id.gridView1);
		mGridView.setAdapter(new GridAdapter());
		mGridView.setOnItemClickListener(this);
		
	}

	
	
	public class GridAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return imgIds.length;
		}

		@Override
		public Object getItem(int position) {
			return imgIds[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if(convertView == null){
				convertView = LayoutInflater.from(getApplication()).inflate(R.layout.item, null);
				viewHolder = new ViewHolder();
				viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView1);
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder) convertView.getTag();
			}
		
			viewHolder.imageView.setPadding(4, 4, 4, 4);
			viewHolder.imageView.setImageResource(imgIds[position]);
			
			return convertView;
		}
		
		
		class ViewHolder {
			public ImageView imageView;
		}
		
	}



	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent();
		intent.putExtra("position", position);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.setClass(getApplication(), ShowPhotoActivity.class);
		startActivity(intent);
	}

}
