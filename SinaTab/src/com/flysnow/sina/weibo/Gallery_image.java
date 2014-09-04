package com.flysnow.sina.weibo;
import com.flysnow.sina.adapter.myImageAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Gallery_image extends Activity {
		/** Called when the activity is first created. */
		/*定义要使用的对象*/
		private Gallery gallery;
		private ImageView imageview;
		private myImageAdapter myimageadapter ;
		
		protected void onCreate(Bundle savedInstanceState) { 
			super.onCreate(savedInstanceState);
			
			setContentView(R.layout.gallery);
			imageview =(ImageView) findViewById(R.id.ImageView_photo);
			gallery=(Gallery)findViewById(R.id.Gallery_preView); 
			
			myimageadapter=new myImageAdapter(this);
			
			gallery.setAdapter(myimageadapter);
			gallery.setOnItemClickListener(new Gallery.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View v, int position,
						long id) {
					// TODO Auto-generated method stub
					
					/*显示该图片是几号*/
				Toast.makeText(Gallery_image.this,"这是图片："+position+"号", Toast.LENGTH_SHORT).show();
				imageview.setBackgroundResource(myimageadapter.myImageIds[position]);
				}
			});
			
		}
}