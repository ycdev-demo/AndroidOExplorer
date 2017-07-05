package me.ycdev.android.demo.oexplorer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import me.ycdev.android.arch.utils.AppLogger;
import me.ycdev.android.demo.oexlporer.common.CommonConstants;

public class StaticReceiver extends BroadcastReceiver {
    private static final String TAG = "StaticReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        AppLogger.i(TAG, "onReceiver: %s", intent);

        int appTargetSdk = context.getApplicationInfo().targetSdkVersion;
        AppLogger.i(TAG, "Device SDK: %d, app target SDK: %d", Build.VERSION.SDK_INT,
                appTargetSdk);

        String action = intent.getAction();
        if (Intent.ACTION_POWER_CONNECTED.equals(action)
                || CommonConstants.ACTION_APP1_TEST_NORMAL.equals(action)
                || CommonConstants.ACTION_APP1_TEST_PERM_NORMAL.equals(action)
                || CommonConstants.ACTION_APP1_TEST_PERM_SIGN_OR_SYSTEM.equals(action)
                || CommonConstants.ACTION_APP2_TEST_NORMAL.equals(action)
                || CommonConstants.ACTION_APP2_TEST_PERM_NORMAL.equals(action)
                || CommonConstants.ACTION_APP2_TEST_PERM_SIGN_OR_SYSTEM.equals(action)) {
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O
                    && appTargetSdk == Build.VERSION_CODES.O) {
                throw new RuntimeException("Should not happen!");
            } else {
                AppLogger.i(TAG, "expected");
            }
        } else if (Intent.ACTION_MY_PACKAGE_REPLACED.equals(action)
                || CommonConstants.ACTION_APP1_TEST_SELF.equals(action)
                || CommonConstants.ACTION_APP1_TEST_PERM_SIGN.equals(action)
                || CommonConstants.ACTION_APP2_TEST_PERM_SIGN.equals(action)) {
            AppLogger.i(TAG, "expected");
        }
    }
}
