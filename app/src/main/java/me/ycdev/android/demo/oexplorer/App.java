package me.ycdev.android.demo.oexplorer;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;

import me.ycdev.android.arch.utils.AppLogger;
import me.ycdev.android.demo.oexlporer.common.CommonConstants;
import me.ycdev.android.demo.oexplorer.receiver.DynamicReceiver;

public class App extends Application {
    private static final String TAG = "OExplorer";

    private BroadcastReceiver mReceiver = new DynamicReceiver();

    @Override
    public void onCreate() {
        super.onCreate();
        AppLogger.i(TAG, "app start...");

        registerDynamicReceiver();
    }

    private void registerDynamicReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_MY_PACKAGE_REPLACED);

        filter.addAction(CommonConstants.ACTION_APP1_TEST_SELF);
        filter.addAction(CommonConstants.ACTION_APP1_TEST_NORMAL);
        filter.addAction(CommonConstants.ACTION_APP1_TEST_PERM_NORMAL);
        filter.addAction(CommonConstants.ACTION_APP1_TEST_PERM_SIGN);
        filter.addAction(CommonConstants.ACTION_APP1_TEST_PERM_SIGN_OR_SYSTEM);

        filter.addAction(CommonConstants.ACTION_APP2_TEST_NORMAL);
        filter.addAction(CommonConstants.ACTION_APP2_TEST_PERM_NORMAL);
        filter.addAction(CommonConstants.ACTION_APP2_TEST_PERM_SIGN);
        filter.addAction(CommonConstants.ACTION_APP2_TEST_PERM_SIGN_OR_SYSTEM);

        registerReceiver(mReceiver, filter);
    }
}
