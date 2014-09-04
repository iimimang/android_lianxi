package com.notbook.clock;

import java.util.Calendar;

import com.notbook.ui.Eventadd;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {     
	private MediaPlayer mMediaPlayer;
    Context context;
    public static int AlarmReceivernum = 0;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int minute = calendar.get(Calendar.MINUTE);
        CharSequence text = String.valueOf(minute);
//        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
        this.context = context;
        Bundle bundle = intent.getExtras();
        Intent serviceIntent = new Intent("chief_musicService");
        serviceIntent.putExtras(bundle);
        AlarmReceivernum = 1;
        if(bundle != null) {
            Log.i("log", String.valueOf(bundle.getBoolean("music")));
            
            if(bundle.getBoolean("music")){
            	context.startService(serviceIntent);
   
            	Intent i=new Intent(context, Eventadd.class);  
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
                intent.putExtra("music", true);
                context.startActivity(i); 
             	
            }
            if(bundle.getBoolean("music1")){
            	Toast.makeText(context, "--------------------------", Toast.LENGTH_LONG).show();
            	
            	context.stopService(serviceIntent);
            }
                
        }
        
    }

}
