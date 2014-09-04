/**
 * 
 */
package com.flysnow.sina.weibo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 文件操作 Activity
 * @author 
 * @since 2011-3-8
 */
public class Filesave extends Activity implements Button.OnClickListener{

	/*要使用的对象、变量声明*/
	/*保存部分*/
	private EditText inputArt;/*编辑框，输入用户字符串*/
	private Button saveButton;/*按钮，保存*/
	private String Text_of_input;/*字符串，用户输入的字符串*/
	private OutputStream os;/*文件输出流，保存文件流*/
	/*读取部分*/
	private TextView showmyText;/*TextView，显示读取文件内容*/
	private Button openTxt,cleanTxt;/*按钮，打开文件*/
	private String Text_of_output;/*字符串，从文件中读取到得 字符串*/
	private InputStream is;/*文件输入流，读取文件流*/
	private byte[] b;/*字节数组，用来读取文件内容*/
	
		@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setLayoutShow(R.layout.filesave);
		
		Ui_init("filesave"); //初始化UI
		control("filesave"); //UI对应逻辑
		
		
	}
		 
	/*设置主屏
	*/
	private void setLayoutShow(int layoutID) {
		// TODO Auto-generated method stub
		setContentView(layoutID);/*设置当前主屏布局*/
	}	 
	
	
	private void Ui_init(String ui){
		
		if(ui.equals("filesave")){
			inputArt = (EditText) findViewById(R.id.EditText_Txt);
			saveButton = (Button) findViewById(R.id.Button_Save);
			
		}else{
			
			showmyText = (TextView) findViewById(R.id.TextView_showTxt);
			openTxt = (Button) findViewById(R.id.Button_openTxt);
			cleanTxt=(Button)findViewById(R.id.Button_clean);
		}
			
	}
	
	private void control(String control){

		// TODO Auto-generated method stub
		/*为按钮添加事件处理*/
		if (control.equals("filesave")) {
		
		saveButton.setOnClickListener(this);
		
		} else if (control.equals("open")) {
		
		openTxt.setOnClickListener(this);
		cleanTxt.setOnClickListener(this);
		}
		
	}
	
	@Override
	public void onClick(View v) {
		/*根据ID判断按钮事件*/
		
		Log.i("aaaa",v.getId()+"ID");
		switch (v.getId()) {
			case R.id.Button_Save: {
			/*提示*/
			NoteDebug("文件保存");
			// TODO Auto-generated method stub
			/*获得用户输入的字符串*/
			Text_of_input = inputArt.getText().toString();
			try {
				
				Log.i("aaaa",Text_of_input);
				
				/*打开文件输出流，名称txtME，以不覆盖模式打开*/
				os = this.openFileOutput("txtME", MODE_PRIVATE);
				/*把字符串转换成字节数组，写入文件中*/
				os.write(Text_of_input.getBytes());
				
				Log.i("aaaa","666666666666");
			} catch (FileNotFoundException e) {
				/*文件未找到，异常*/
				// TODO Auto-generated catch block
				Log.i("aaaa","文件关闭失败666666666666");
				NoteDebug("文件关闭失败" + e);
			} catch (IOException e) {
				/*文件写入错误*/
				// TODO Auto-generated catch block
				Log.i("aaaa","文件写入失败666666666666");
				NoteDebug("文件写入失败" + e);
			} finally {
				try {
				/*关闭文件输出流*/
				os.close();
				} catch (IOException e) {
				// TODO Auto-generated catch block
				NoteDebug("文件关闭失败" + e);
				}
			}
			/*输入框清空*/
			inputArt.setText("");
			}
			break;
	case R.id.Button_openTxt: {
		NoteDebug("文件打开");
		try {
				/*打开文件输入流，名称txtME*/
				is = this.openFileInput("txtME");
				/*初始化字节数组*/
				b = new byte[1024];
				/*从文件输入流中读取内容到字节数组中，返回内容长度*/
				int length = is.read(b);
				
				Log.i("aaaa",length+"长度");
				/*把字节数组转换成字符串*/
				Text_of_output = new String(b);
				Log.i("aaaa",Text_of_output+"11111111111111111111");
				/*设置标题，显示文件内容长度*/
				setTitle("文件字数：" + length);
				/*显示文件内容*/
				showmyText.setText(Text_of_output);
			} catch (FileNotFoundException e) {
				/*文件未找到，异常*/
				// TODO Auto-generated catch block
				NoteDebug("文件打开失败" + e);
			} catch (IOException e) {
				/*文件读取错误，异常*/
				// TODO Auto-generated catch block
				NoteDebug("文件读取失败" + e);
			}
			finally {
				try {
				/*关闭文件输入流*/
				is.close();
				} catch (IOException e) {
				// TODO Auto-generated catch block
				NoteDebug( "文件关闭失败"+e);
				}
			}
			}
			break;
		case R.id.Button_clean:{
			/*清空*/
			showmyText.setText("");
			NoteDebug("清空");
			}
			break;
		default:
			break;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		/*添加三个菜单项目，并设置图片*/
		menu.add(0, 1, 1, "Edit").setIcon(R.drawable.ic_menu_03);
		menu.add(0, 2, 2, "Open").setIcon(R.drawable.ic_menu_05);
		menu.add(0, 3, 3, "Exit").setIcon(R.drawable.ic_menu_07);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
			case 1:
				/*显示main.xml为主屏布局*/
				setLayoutShow(R.layout.filesave);
				Ui_init("filesave"); //初始化UI
				control("filesave"); //UI对应逻辑
				NoteDebug("编辑文件Layout");
				break;
			case 2:
				/*显示open.xml为主屏布局*/
				setLayoutShow(R.layout.open);
				Ui_init("open"); //初始化UI
				control("open"); //UI对应逻辑
				NoteDebug( "打开文件Layout");
				break;
			case 3:
				/*退出*/
				finish();
				NoteDebug( "Byebye");
				break;
				default:
				break;
		}
		
		return super.onOptionsItemSelected(item);
		
	} 
	
	private void NoteDebug(String showString){
		/*显示Toast提示*/
		Toast.makeText(this,showString, Toast.LENGTH_SHORT).show();
	}
		
		
		
	}