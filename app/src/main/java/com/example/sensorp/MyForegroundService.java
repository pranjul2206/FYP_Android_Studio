package com.example.sensorp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public class MyForegroundService extends Service {
    int count=0;
    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    @Override
    public void onCreate() {
        Log.d("background@@","called");
        super.onCreate();
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        int action=event.getAction();
        int keycode=event.getKeyCode();
        switch(keycode){
            case KeyEvent.KEYCODE_VOLUME_UP:{
                if(KeyEvent.ACTION_UP==action){
                    count+=1;
                    Log.d("background@@",count+"A");
                }
                break;
            }
            case KeyEvent.KEYCODE_VOLUME_DOWN:{
                if(KeyEvent.ACTION_UP==action){
                    count-=1;
                    Log.d("background@@",count+"B");
                }
                break;
            }
        }
        //returning true because we dont want to increase volume
        return true;
//        return super.dispatchKeyEvent(event);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText(input)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
        //do heavy work on a background thread
        //stopSelf();
        return START_NOT_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
