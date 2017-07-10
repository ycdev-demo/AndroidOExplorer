package me.ycdev.android.demo.oexplorer.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import me.ycdev.android.arch.utils.AppLogger;

public class BackgroundService extends Service {
    private static final String TAG = "BackgroundService";

    public static boolean sRunning = false;

    @Override
    public void onCreate() {
        super.onCreate();
        AppLogger.d(TAG, "onCreate");
        sRunning = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppLogger.d(TAG, "onDestroy");
        sRunning = false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
