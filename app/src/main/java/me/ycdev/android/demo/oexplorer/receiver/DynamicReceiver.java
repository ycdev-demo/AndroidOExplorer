package me.ycdev.android.demo.oexplorer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import me.ycdev.android.arch.utils.AppLogger;

public class DynamicReceiver extends BroadcastReceiver {
    private static final String TAG = "DynamicReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        AppLogger.i(TAG, "onReceiver: %s", intent);

        int appTargetSdk = context.getApplicationInfo().targetSdkVersion;
        AppLogger.i(TAG, "Device SDK: %d, app target SDK: %d", Build.VERSION.SDK_INT,
                appTargetSdk);

        AppLogger.d(TAG, "expected");
    }
}
