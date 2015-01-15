package cn.chinat2t.cloud;


import cn.chinat2t.cloud.activity.BusinessActivity;
import cn.chinat2t.cloud.activity.BusinessDetailActivity;
import cn.chinat2t.cloud.activity.BusinessGroup;
import cn.chinat2t.cloud.activity.MoreActivity;
import cn.chinat2t.cloud.activity.MoreGroup;
import cn.chinat2t.cloud.activity.NewsActivity;
import cn.chinat2t.cloud.activity.NewsDetailActivity;
import cn.chinat2t.cloud.activity.NewsGroup;
import cn.chinat2t.cloud.activity.ProductActivity;
import cn.chinat2t.cloud.activity.ProductGroup;
import cn.chinat2t.cloud.activity.ShopActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class MainActivity extends TabActivity implements OnCheckedChangeListener,OnClickListener{
	
	private  RadioGroup radioGroup = null;
	private TabHost tabHost;
	public RadioButton button0,button1,button2,button3,button4;
	public Intent mShopIntent,mNewsIntent,mProductInent,mBusinessIntent,mMoreIntent,intent;
	private final String TAB_SHOP = "ShopActivity";
	private final String TAB_NEWS = "NewsGroup";
	private final String TAB_PRODUCT = "ProductGroup";
	private final String TAB_BUSINESS = "BusinessGroup";
	//private final String TAB_BUSINESS = "BusinessActivity";
	//private final String TAB_MORE = "MoreActivity";
	private final String TAB_MORE = "MoreGroup";
	public static final int TAB_SHOP_INDEX = 0;
	public static final int TAB_NEWS_INDEX = 1;
	public static final int TAB_PRODUCT_INDEX = 2;
	public static final int TAB_BUSINESS_INDEX = 3;
	public static final int TAB_MORE_INDEX = 4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      

        initViews();
        
        prepareIntent();
		setupIntent();
		setCurrentActivity(TAB_SHOP_INDEX);
    }
    
    private void initViews(){
    	radioGroup = (RadioGroup) findViewById(R.id.main_radio);
    	this.tabHost = getTabHost();
    	
    	button0 = (RadioButton) findViewById(R.id.radio_button0);
    	button1 = (RadioButton) findViewById(R.id.radio_button1);
    	button2 = (RadioButton) findViewById(R.id.radio_button2);
    	button3 = (RadioButton) findViewById(R.id.radio_button3);
    	button4 = (RadioButton) findViewById(R.id.radio_button4);
    	
    	
    	button0.setOnClickListener(this);
    	button1.setOnClickListener(this);
    	button2.setOnClickListener(this);
    	button3.setOnClickListener(this);
    	button4.setOnClickListener(this);
    }
    

    private void prepareIntent() {
    	mShopIntent = new Intent(MainActivity.this, ShopActivity.class);
    	mNewsIntent = new Intent(MainActivity.this,
    			NewsGroup.class);
    	mProductInent = new Intent(MainActivity.this, ProductGroup.class);
    	mBusinessIntent = new Intent(MainActivity.this, BusinessGroup.class);
    	mMoreIntent = new Intent(MainActivity.this, MoreGroup.class);
	}

	private void setupIntent() {
		TabHost localTabHost = this.tabHost;
		localTabHost.addTab(buildTabSpec(TAB_SHOP, TAB_SHOP, mShopIntent));
		localTabHost.addTab(buildTabSpec(TAB_NEWS, TAB_NEWS,mNewsIntent));
		localTabHost.addTab(buildTabSpec(TAB_PRODUCT, TAB_PRODUCT,mProductInent));
		localTabHost.addTab(buildTabSpec(TAB_BUSINESS, TAB_BUSINESS,mBusinessIntent));
		localTabHost.addTab(buildTabSpec(TAB_MORE, TAB_MORE,mMoreIntent));
	}
	
	/**
	 * 设置当前主页的方法
	 * 
	 * @param index
	 */
	public void setCurrentActivity(int index) {
		switch (index) {
		case TAB_SHOP_INDEX:
			onCheckedChanged(radioGroup, R.id.radio_button0);
			break;
			
		case TAB_NEWS_INDEX:
			onCheckedChanged(radioGroup, R.id.radio_button1);
			break;

		case TAB_PRODUCT_INDEX:
			onCheckedChanged(radioGroup, R.id.radio_button2);
			break;

		case TAB_BUSINESS_INDEX:
			onCheckedChanged(radioGroup, R.id.radio_button3);
			break;

		case TAB_MORE_INDEX:
			onCheckedChanged(radioGroup, R.id.radio_button4);
			break;
		}
	}
	
	private TabHost.TabSpec buildTabSpec(String tag, String resLabel,
			final Intent content) {
		return this.tabHost.newTabSpec(tag).setIndicator(resLabel)
				.setContent(content);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		findViewById(R.id.radio_select0).setVisibility(View.INVISIBLE);
		findViewById(R.id.radio_select1).setVisibility(View.INVISIBLE);
		findViewById(R.id.radio_select2).setVisibility(View.INVISIBLE);
		findViewById(R.id.radio_select3).setVisibility(View.INVISIBLE);
		findViewById(R.id.radio_select4).setVisibility(View.INVISIBLE);
		switch (checkedId) {
		case R.id.radio_button0:
			this.tabHost.setCurrentTabByTag(TAB_SHOP);
			button0.setChecked(true);
			button1.setChecked(false);
			button2.setChecked(false);
			button3.setChecked(false);
			button4.setChecked(false);
			findViewById(R.id.radio_select0).setVisibility(View.VISIBLE);
			break;
		case R.id.radio_button1:
			this.tabHost.setCurrentTabByTag(TAB_NEWS);
			findViewById(R.id.radio_select1).setVisibility(View.VISIBLE);
			button1.setChecked(true);
			button0.setChecked(false);
			button2.setChecked(false);
			button3.setChecked(false);
			button4.setChecked(false);
			break;
		case R.id.radio_button2:
			this.tabHost.setCurrentTabByTag(TAB_PRODUCT);
			findViewById(R.id.radio_select2).setVisibility(View.VISIBLE);
			button2.setChecked(true);
			button0.setChecked(false);
			button1.setChecked(false);
			button3.setChecked(false);
			button4.setChecked(false);
			break;
		case R.id.radio_button3:
			this.tabHost.setCurrentTabByTag(TAB_BUSINESS);
			findViewById(R.id.radio_select3).setVisibility(View.VISIBLE);
			button3.setChecked(true);
			button0.setChecked(false);
			button2.setChecked(false);
			button1.setChecked(false);
			button4.setChecked(false);
			break;
		case R.id.radio_button4:
			this.tabHost.setCurrentTabByTag(TAB_MORE);
			findViewById(R.id.radio_select4).setVisibility(View.VISIBLE);
			button4.setChecked(true);
			button0.setChecked(false);
			button2.setChecked(false);
			button3.setChecked(false);
			button1.setChecked(false);
			break;

		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.radio_button0:
			setCurrentActivity(TAB_SHOP_INDEX);
			break;
		case R.id.radio_button1:
			//Log.i("wyq", "执行了");
			setCurrentActivity(TAB_NEWS_INDEX);
			Intent intent3 = new Intent(MainActivity.this,NewsActivity.class);
			NewsGroup.getInstance().switchActivity("NewsActivity",intent3,-1,-1);
			break;
		case R.id.radio_button2:
			setCurrentActivity(TAB_PRODUCT_INDEX);
			Intent intent2 = new Intent(MainActivity.this,ProductActivity.class);
			ProductGroup.getInstance().switchActivity("ProductActivity",intent2,-1,-1);
			break;
		case R.id.radio_button3:
			setCurrentActivity(TAB_BUSINESS_INDEX);
			Intent intent1 = new Intent(MainActivity.this,BusinessActivity.class);
			BusinessGroup.getInstance().switchActivity("BusinessActivity",intent1,-1,-1);
			break;
		case R.id.radio_button4:
			
			setCurrentActivity(TAB_MORE_INDEX);
			Intent intent = new Intent(MainActivity.this,MoreActivity.class);
			MoreGroup.getInstance().switchActivity("MoreActivity",intent,-1,-1);
			break;

		default:
			break;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			
			exitApp();
			return true;

		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	public void exitApp(){
		new AlertDialog.Builder(this).setTitle("确定退出").setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				MainActivity.this.finish();
				System.exit(0);
			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		}).create().show();
	}
}
