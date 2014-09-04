package com.notebook.service;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

public class BootReceiver extends BroadcastReceiver {

	private PendingIntent mAlarmSender;
	private Boolean flag = true ;
	@Override
	public void onReceive(Context context, Intent intent) {
//		for(int i = 0; i<10 ;i++){
			// 在这里干你想干的事（启动一个Service，Activity等），本例是启动一个定时调度程序，每30分钟启动一个Service去更新数据
			mAlarmSender = PendingIntent.getService(context, 0, new Intent(context,
					ChatService.class), 0);
			long firstTime = SystemClock.elapsedRealtime();
			AlarmManager am = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);
			am.cancel(mAlarmSender);
			am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime, 1000, mAlarmSender);
			
//			intent = new Intent(context,ChatService.class);
//			context.startService(intent);
//		}
	}

}
