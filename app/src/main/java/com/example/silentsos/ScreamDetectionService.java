package com.example.silentsos; // Agar aapke project ka package name kuch aur hai toh woh yahan likhein

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class ScreamDetectionService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();

        Notification notification = new NotificationCompat.Builder(this, "ScreamServiceChannel")
                .setContentTitle("Silent SOS AI Active")
                .setContentText("Listening for distress sounds...")
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();

        // Service ko Foreground mein chalana taake screen off hone par bhi background mein band na ho
        startForeground(1, notification);

        // Yahan aap apna AudioRecord aur TFLite Interpreter ka code initialize karengi
    }

    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    "ScreamServiceChannel",
                    "AI Scream Detection Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Yeh service ko background mein active rakhega
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Background service band hone par resources release karne ke liye
    }
}