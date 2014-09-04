package com.notbook.ui;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.example.notbook.R;
import com.notbook.app.ImageZoomState;
import com.notbook.app.ImageZoomView;
import com.notbook.app.SimpleImageZoomListener;
import com.notbook.calendar.CalendarView;
import com.notbook.things.Meta;

public class Eventdetail extends BaseActivity {

	private Button iamgebutton1, iamgebutton2, iamgebutton3, iamgebutton4,
			iamgebutton5;
	private Button more_back, eventedit, delete, readrecoder;
	private LinearLayout moreinformation;
	public static int EventdetailNUM = 0;
	public static String id = "";
	public static String Eventdetailid = "";
	private TextView leixing, time, mtime, more01, jishi, zhuti, youxian,
			lingyin;
	private String lunyin = "";
	private String selecttime = "";//图片的编号
	private String selecttime_1 = "";//图片的编号
	private ImageView iamge,imageview;
	private MediaPlayer mPlayer = null;
	private Cursor c = null;
	private Cursor c1 = null;
	private String num_id = "";
	private RelativeLayout more, shouqi;
	private ScrollView scrollview ;
	private String imageurl = "";
	private Boolean flag = true;
	private String u_photo = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.eventdetail);
		findViewById(R.id.one).setBackgroundResource(
				background_photobg[num - 1]);
		findViewById(R.id.one2).setBackgroundResource(
				background_photobg1[num - 1]);
		findViewById(R.id.one1).setBackgroundResource(
				background_photobg1[num - 1]);
		more_back = (Button) findViewById(R.id.more_back);
		moreinformation = (LinearLayout) findViewById(R.id.moreinformation);
		shouqi = (RelativeLayout) findViewById(R.id.shouqi);
		eventedit = (Button) findViewById(R.id.eventedit);
		delete = (Button) findViewById(R.id.delete);
		readrecoder = (Button) findViewById(R.id.readrecoder);
		leixing = (TextView) findViewById(R.id.leixing);
		time = (TextView) findViewById(R.id.time);
		mtime = (TextView) findViewById(R.id.mtime);
		more01 = (TextView) findViewById(R.id.more01);
		jishi = (TextView) findViewById(R.id.jishi);
		zhuti = (TextView) findViewById(R.id.zhuti);
		youxian = (TextView) findViewById(R.id.youxian);
		lingyin = (TextView) findViewById(R.id.lingyin);
		more = (RelativeLayout) findViewById(R.id.more);
		iamge = (ImageView) findViewById(R.id.iamge);
		imageview = (ImageView) findViewById(R.id.imageview);
		scrollview = (ScrollView) findViewById(R.id.scrollview);
		
		
		
		Bundle bundle = getIntent().getExtras();
		if(null!=bundle&&!bundle.equals("")){
			id = bundle.getString("id");
		}
		if(Eventlist.Eventlistid.equals("")){
			if(null!=id&&!id.equals("")){
				c = getContentResolver().query(Meta.CONTENT_URI, null,
						"_id = ?", new String[] { id }, null);
			}
			Eventdetailid  = id ;
			
		}else{
			c = getContentResolver().query(Meta.CONTENT_URI, null,
					"_id = ?", new String[] { id }, null);
			Eventdetailid  = Eventlist.Eventlistid ;
		}
		

		if (c != null&&c.moveToFirst()) {
			c.moveToFirst();
			
			leixing.setText(c.getString(c.getColumnIndex(Meta.TableMeta._LEIXING)));
			time.setText(c.getString(c.getColumnIndex(Meta.TableMeta._TIME)));
			mtime.setText(c.getString(c.getColumnIndex(Meta.TableMeta._MTIME)));
			jishi.setText(c.getString(c.getColumnIndex(Meta.TableMeta._JISHI)));
			zhuti.setText(c.getString(c.getColumnIndex(Meta.TableMeta._ZHUTI)));
			lunyin = c.getString(c.getColumnIndex(Meta.TableMeta._LUYIN));
			youxian.setText(c.getString(c
					.getColumnIndex(Meta.TableMeta._YOUXIAN)));
			lingyin.setText(c.getString(c
					.getColumnIndex(Meta.TableMeta._LINGYIN)));
			selecttime_1 = c.getString(c
					.getColumnIndex(Meta.TableMeta._SELECTTIME));
			byte[] picData = c.getBlob(c.getColumnIndex(Meta.TableMeta._IMAGE));
			if (picData != null) {
				try {
					ShowImg(c
							.getString(c.getColumnIndex(Meta.TableMeta._IMAGE)),
							iamge);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			imageurl = c.getString(c.getColumnIndex(Meta.TableMeta._IMAGE));
			num_id = c.getString(c.getColumnIndex(Meta.TableMeta._ID));
			Log.i("log", "----------------num_id="+num_id);
		}
		c1 = getContentResolver().query(com.notbook.photodb.Meta.CONTENT_URI, null,
				null, null, null);
		if(c1!=null&&c1.moveToFirst()){
			c1.moveToFirst();
			selecttime = c1.getString(c1.getColumnIndex(com.notbook.photodb.Meta.TableMeta._DATE));
		}
		
		
		
		more_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle b = getIntent().getExtras();
				String str = "";
				String day = "";
				if(null != b&& !b.equals("")){
					str = b.getString("list").toString();
					day = b.getString("day").toString();
				}
				if(str.equals("true")){
					Intent intent1 = new Intent(Eventdetail.this, Celenderdetail.class);
					intent1.putExtra("day", day);
					startActivity(intent1);
				}
				if(str.equals("back")){
					Intent intent2 = new Intent(Eventdetail.this,MainActivity3.class);
					startActivity(intent2);
				}
				if(str.equals("false")){
					Intent intent = new Intent(Eventdetail.this,MainActivity4.class);
					startActivity(intent);
				}
				finish();
			}
		});
		
		iamge.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(flag == true){
					if (c != null&&c.moveToFirst()) {
						c.moveToFirst();
						byte[] picData = c.getBlob(c.getColumnIndex(Meta.TableMeta._IMAGE));
						if (picData != null) {
							u_photo = c.getString(c.getColumnIndex(Meta.TableMeta._IMAGE));
							if(null != u_photo){
								imageview.setVisibility(View.VISIBLE);
								AnimationSet animationSet = new AnimationSet(true);
								ScaleAnimation translateAnimation = new ScaleAnimation(
										0.5f, 1f,
										0.5f, 1f,
										Animation.RELATIVE_TO_SELF, 0.5f,
										Animation.RELATIVE_TO_SELF, 0.5f);
								translateAnimation.setDuration(1000);
								animationSet.addAnimation(translateAnimation);
								imageview.startAnimation(animationSet);
								scrollview.setVisibility(View.GONE);
								try {
									ShowImg(u_photo,imageview);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							
						}
					}
					flag = false;
				}else{
					if(null != u_photo){
						imageview.setVisibility(View.GONE);
						scrollview.setVisibility(View.VISIBLE);
					}
					flag = true;
				}
				
				
				
			}
		});
		imageview.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(null != u_photo){
					imageview.setVisibility(View.GONE);
					scrollview.setVisibility(View.VISIBLE);
				}
				flag = true;
			}
		});
		more.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				more.setVisibility(View.GONE);
				more01.setVisibility(View.GONE);
				moreinformation.setVisibility(View.VISIBLE);
			}
		});
		shouqi.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				more.setVisibility(View.VISIBLE);
				more01.setVisibility(View.VISIBLE);
				moreinformation.setVisibility(View.GONE);
			}
		});
		delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(Eventdetail.this)
						.setTitle("温馨提示")
						.setMessage("是否删除这条记事?")
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
										int n = 0;
										if(Eventlist.Eventlistid.equals("")){
											n = getContentResolver().delete(
													Meta.CONTENT_URI, "_id=?",
													new String[] { num_id });
										}else{
											int n5 = getContentResolver().delete(
													Meta.CONTENT_URI, "_id=?",
													new String[] { num_id });
										}
										if(!selecttime.equals("")){
//											String jishinum = selecttime.substring(10, 11);
											int n1 = getContentResolver()
													.delete(com.notbook.photodb.Meta.CONTENT_URI,
															"_date=?",
															new String[] { selecttime });
											int n2 = getContentResolver()
													.delete(com.notbook.things.Meta.CONTENT_URI,
															"_date=?",
															new String[] { selecttime_1 });
											if (n > 0) {
												Toast.makeText(getApplication(),
														"删除成功！", 0).show();
											}

										}
										Bundle b = getIntent().getExtras();
										String str = "";
										String day = "";
										if(null != b&& !b.equals("")){
											str = b.getString("list").toString();
											day = b.getString("day").toString();
										}
										if(str.equals("true")){
											Intent intent1 = new Intent(Eventdetail.this, Celenderdetail.class);
											intent1.putExtra("day", day);
											startActivity(intent1);
										}
										if(str.equals("back")){
											Intent intent2 = new Intent(Eventdetail.this,MainActivity3.class);
											startActivity(intent2);
										}
										if(str.equals("false")){
											Intent intent = new Intent(Eventdetail.this,MainActivity4.class);
											startActivity(intent);
										}
										finish();
									}
								}).show();
			}
		});
		eventedit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Eventdetail.this, Eventadd.class);
				intent.putExtra("list", "detail");
				intent.putExtra("day", "");
				startActivity(intent);
				EventdetailNUM = 1;
			}
		});
		if(null!=lunyin&&!lunyin.equals("")){
			readrecoder
			.setBackgroundResource(R.drawable.yuyinbuttonone);
		}else{
			readrecoder.setBackgroundResource(R.drawable.yuyingbutton);
		}
		
		readrecoder.setText("点击播放");
		//播放
		readrecoder.setOnClickListener(new OnClickListener() {
			boolean mStartRecording = true;

			@Override
			public void onClick(View v) {
				if(null!=lunyin&&!lunyin.equals("")){
					onPlay(mStartRecording);
					if (mStartRecording) {
						readrecoder
								.setBackgroundResource(R.drawable.yuyinbuttonone);
						readrecoder.setText("正在播放");
					} else {
//						readrecoder.setBackgroundResource(R.drawable.yuyingbutton);
						readrecoder.setText("点击播放");
					}
					mStartRecording = !mStartRecording;
				}else{
					readrecoder.setBackgroundResource(R.drawable.yuyingbutton);
				}
				
			}
		});
		init();
		
	}

	public void init() {
		iamgebutton1 = (Button) findViewById(R.id.iamgebutton1);
		iamgebutton1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Eventdetail.this,
						MainActivity0.class);
				startActivity(intent);
			}
		});
		iamgebutton2 = (Button) findViewById(R.id.iamgebutton2);
		iamgebutton2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Eventdetail.this,
						MainActivity2.class);
				startActivity(intent);
			}
		});
		iamgebutton3 = (Button) findViewById(R.id.iamgebutton3);
		iamgebutton3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Eventdetail.this,
						MainActivity3.class);
				startActivity(intent);
			}
		});
		iamgebutton4 = (Button) findViewById(R.id.iamgebutton4);
		iamgebutton4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Eventdetail.this,MainActivity4.class);
				startActivity(intent);
			}
		});
		iamgebutton5 = (Button) findViewById(R.id.iamgebutton5);
		iamgebutton5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Eventdetail.this,
						MainActivity1.class);
				startActivity(intent);
			}
		});
	}

	public static void ShowImg(String uri, ImageView iv) throws IOException {
		FileInputStream fs = new FileInputStream(uri);
		BufferedInputStream bs = new BufferedInputStream(fs);
		Bitmap btp = BitmapFactory.decodeStream(bs);
		iv.setImageBitmap(btp);
		bs.close();
		fs.close();
		btp = null;
	}

	private void onPlay(boolean start) {
		if (start) {
			startPlaying();
		} else {
			stopPlaying();
		}
	}

	private void startPlaying() {
		mPlayer = new MediaPlayer();
		try {
			mPlayer.setDataSource(lunyin);
			mPlayer.prepare();
			mPlayer.start();
		} catch (IOException e) {
		}
	}

	private void stopPlaying() {
		mPlayer.release();
		mPlayer = null;
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mPlayer != null) {
			mPlayer.release();
			mPlayer = null;
		}
		
		c1.close();
	}
}
