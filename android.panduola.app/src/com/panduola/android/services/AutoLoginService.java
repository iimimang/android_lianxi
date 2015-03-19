package com.panduola.android.services;

import org.json.JSONException;
import org.json.JSONObject;

import com.panduola.android.PanDuoLa;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AutoLoginService extends Service {
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		try {
			PanDuoLa.memberInfo = new JSONObject(PanDuoLa.APP.getConfig());
			PanDuoLa.APP.setLogin(true, PanDuoLa.memberInfo.optString("memberid", ""), PanDuoLa.APP.getMobile(), PanDuoLa.APP.getPassword());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return super.onStartCommand(intent, flags, startId);
	}
}