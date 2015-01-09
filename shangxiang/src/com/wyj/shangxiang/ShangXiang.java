package com.wyj.shangxiang;

import java.io.File;

import com.wyj.Activity.R;


import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Handler;

public class ShangXiang extends Application {
	public static ShangXiang APP;
	public static String VERSION = "";
	public static int AR_ORIGINAL = 0;
	public static int AR_CUR = 0;

	public static int tabContent = R.id.tab_content;
	public static String cookies = null;
	public static boolean dialogUpdate = false;
	public static Class<?> classCurr = null;

	public static Handler tabHandler = new Handler();
	public static Handler listTempleHandler = new Handler();
	public static Handler discoverHandler = new Handler();
	public static Handler orderRecordHandler = new Handler();

	public boolean logined = false;
	private String cfgName = "shangxiang_android";
	private String cfgCookies = "save_cookies";
	private String cfgSaveLoginCheckbox = "save_login_checkbox";
	private boolean cfgSaveLoginCheckboxDef = false;
	private String cfgSaveMobile = "save_mobile";
	private String cfgSavePassword = "save_password";
	private String cfgConfig = "save_config";

	@SuppressLint("SdCardPath")
	@Override
	public void onCreate() {
		super.onCreate();
		APP = this;
		try {
			VERSION = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		SinhaCrash sc = SinhaCrash.getInstance();
		sc.init(APP);

		File file = new File(Consts.FOLDER_LOCAL);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(Consts.UPDATE_LOCAL);
		if (!file.exists()) {
			file.mkdirs();
		}

		File cache = StorageUtils.getOwnCacheDirectory(APP, "/mnt/sdcard/ShangXiang/Cache");
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(APP)
		.threadPriority(Thread.NORM_PRIORITY - 2)
		.threadPoolSize(5)
		.denyCacheImageMultipleSizesInMemory()
		.diskCache(new UnlimitedDiscCache(cache))
		.diskCacheFileNameGenerator(new Md5FileNameGenerator())
		.diskCacheSize(50 * 1024 * 1024)
		.tasksProcessingOrder(QueueProcessingType.LIFO)
		.imageDownloader(new BaseImageDownloader(APP, 5 * 1000, 30 * 1000))
		.build();
		ImageLoader.getInstance().init(config);
	}

	public String getCookies() {
		if (null != ShangXiang.cookies) {
			return ShangXiang.cookies;
		} else {
			SharedPreferences sp = getSharedPreferences(cfgName, Context.MODE_PRIVATE);
			return sp.getString(cfgCookies, null);
		}
	}

	public void setCookies(String cookies) {
		ShangXiang.cookies = cookies;
		Editor editor = getSharedPreferences(cfgName, Context.MODE_PRIVATE).edit();
		editor.putString(cfgCookies, cookies);
		editor.commit();
	}

	public boolean getSaveLoginCheckBox() {
		SharedPreferences sp = getSharedPreferences(cfgName, Context.MODE_PRIVATE);
		return sp.getBoolean(cfgSaveLoginCheckbox, cfgSaveLoginCheckboxDef);
	}

	public void setSaveLoginCheckBox(boolean isChecked) {
		Editor editor = getSharedPreferences(cfgName, Context.MODE_PRIVATE).edit();
		editor.putBoolean(cfgSaveLoginCheckbox, isChecked);
		editor.commit();
	}

	public boolean getLogin() {
		return this.logined;
	}

	public void setLogin(boolean isLogin, String mobile, String password) {
		this.logined = isLogin;
		Editor editor = getSharedPreferences(cfgName, Context.MODE_PRIVATE).edit();
		editor.putString(cfgSaveMobile, mobile);
		editor.putString(cfgSavePassword, password);
		editor.commit();
	}

	public String getMobile() {
		SharedPreferences sp = getSharedPreferences(cfgName, Context.MODE_PRIVATE);
		return sp.getString(cfgSaveMobile, null);
	}

	public String getPassword() {
		SharedPreferences sp = getSharedPreferences(cfgName, Context.MODE_PRIVATE);
		return sp.getString(cfgSavePassword, null);
	}

	public String getConfig() {
		SharedPreferences sp = getSharedPreferences(cfgName, Context.MODE_PRIVATE);
		return sp.getString(cfgConfig, null);
	}

	public void setConfig(String config) {
		Editor editor = getSharedPreferences(cfgName, Context.MODE_PRIVATE).edit();
		editor.putString(cfgConfig, config);
		editor.commit();
	}

	public void Logout() {
		this.setLogin(false, null, null);
	}
}