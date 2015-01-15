package zing.sinawb;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MySinaWeiboActivity extends Activity
{
	private Button but0;
	private Button but1;
	private Button but2;
	private Button but3;
	private Button but4;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		but0 = (Button) findViewById(R.id.button0);
		but1 = (Button) findViewById(R.id.button1);
		but2 = (Button) findViewById(R.id.button2);
		but3 = (Button) findViewById(R.id.button3);
		but4 = (Button) findViewById(R.id.button4);
 
		Intent in = new Intent();
/*		if(MyResource.uid == null)
		{
			in.setClass(this, MyAuthorizeActivity.class);
			this.startActivity(in);
		}*/
		
		but0.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Intent in = new Intent(MySinaWeiboActivity.this, OAuthWeiboActivity.class);
				MySinaWeiboActivity.this.startActivity(in);
			}
		});
		
		but1.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Intent in = new Intent(MySinaWeiboActivity.this, SendWeiboActivity.class);
				MySinaWeiboActivity.this.startActivity(in);
			}
		});
		
		but2.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Intent in = new Intent(MySinaWeiboActivity.this, RepostWeiboActivity.class);
				MySinaWeiboActivity.this.startActivity(in);
				
			}
		});
		
		but3.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Intent in = new Intent(MySinaWeiboActivity.this, QueryWeiboActivity.class);
				MySinaWeiboActivity.this.startActivity(in);
				
			}
		});
		
		but4.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Intent in = new Intent(MySinaWeiboActivity.this, CommentWeiboActivity.class);
				MySinaWeiboActivity.this.startActivity(in);
				
			}
		});
	}
}