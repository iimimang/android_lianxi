package com.notbook.clock;

import java.io.IOException;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

public class AlarmService extends Service {

    // MediaPlayer实例   
    private MediaPlayer player;   
  
    // IBinder实例   
    @Override
    public IBinder onBind(Intent intent) {
        playMusic();   
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        playMusic();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (player != null) {   
            player.stop();   
            player.release();   
        }
    }

    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);
         if (intent != null) {   
                Bundle bundle = intent.getExtras();   
                if (bundle != null) {
                    if(bundle.getBoolean("music"))
                        playMusic();
                    else
                        stopMusic();
                }   
            }
    }
    
    public void playMusic() {
        if(player == null) {
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            try {
                player = new MediaPlayer();
                player.setDataSource(this, uri);
                final AudioManager audioManager = (AudioManager)this
                        .getSystemService(Context.AUDIO_SERVICE);
                if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                    player.setAudioStreamType(AudioManager.STREAM_ALARM);
                    player.setLooping(true);
                    player.prepare();
                }
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        if(!player.isPlaying()) {
            player.start();
        }
    }
    public void stopMusic() {   
        if (player != null) { 
            player.stop();   
            player.release();
            try {   
                // 在调用stop后如果需要再次通过start进行播放,需要之前调用prepare函数   
                player.prepare();   
            } catch (IOException ex) {   
                ex.printStackTrace();   
            }   
        }
    }
    @Override
    public void unbindService(ServiceConnection conn) {
    	// TODO Auto-generated method stub
    	if (player != null) { 
            player.stop();   
            player.release();
            try {   
                // 在调用stop后如果需要再次通过start进行播放,需要之前调用prepare函数   
                player.prepare();   
            } catch (IOException ex) {   
                ex.printStackTrace();   
            }   
        }
    	super.unbindService(conn);
    }
}