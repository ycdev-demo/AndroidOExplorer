package me.ycdev.android.demo.oexplorer;

import android.annotation.TargetApi;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import java.util.concurrent.TimeUnit;

import me.ycdev.android.arch.utils.AppLogger;
import me.ycdev.android.demo.oexlporer.common.CommonConstants;
import me.ycdev.android.demo.oexplorer.receiver.DynamicReceiver;
import me.ycdev.android.demo.oexplorer.service.BackgroundService;

public class App extends Application implements Handler.Callback {
    private static final String TAG = "OExplorer";

    private static final int MSG_START_BACKGROUND_SERVICE = 1;

    private static final long BACKGROUND_SERVICE_CHECK_INTERVAL = TimeUnit.MINUTES.toMillis(2);

    private Handler mHandler = new Handler(this);
    private BroadcastReceiver mReceiver = new DynamicReceiver();

    @Override
    public void onCreate() {
        super.onCreate();
        AppLogger.setLogEnabled(true);
        AppLogger.i(TAG, "app start...");

        registerDynamicReceiver();

        registerNotificationChannels();

        mHandler.sendEmptyMessageDelayed(MSG_START_BACKGROUND_SERVICE,
                BACKGROUND_SERVICE_CHECK_INTERVAL);
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

    @TargetApi(Build.VERSION_CODES.M)
    private void registerNotificationChannels() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return;
        }

        NotificationManager nm = getSystemService(NotificationManager.class);

        NotificationChannel testChannel = new NotificationChannel(Constants.NOTIFICATION_CHANNEL_ID_TEST,
                "TestChannel", NotificationManager.IMPORTANCE_DEFAULT);
        testChannel.setDescription("Test channel desc");
        testChannel.enableLights(true);
        testChannel.setLightColor(Color.GREEN);
        testChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});
        nm.createNotificationChannel(testChannel);
    }

    private void startBackgroundService() {
        AppLogger.d(TAG, "startBackgroundService: %b", BackgroundService.sRunning);
        mHandler.sendEmptyMessageDelayed(MSG_START_BACKGROUND_SERVICE,
                BACKGROUND_SERVICE_CHECK_INTERVAL);
        if (!BackgroundService.sRunning) {
            try {
                Intent intent = new Intent(this, BackgroundService.class);
                startService(intent);
//                startForegroundService(intent);
            } catch (Exception e) {
                AppLogger.w(TAG, "cannot start service in background", e);
                mHandler.removeMessages(MSG_START_BACKGROUND_SERVICE);
            }
        }
    }

    @Override
    public boolean handleMessage(Message message) {
        if (message.what == MSG_START_BACKGROUND_SERVICE) {
            startBackgroundService();
        }
        return false;
    }
}
