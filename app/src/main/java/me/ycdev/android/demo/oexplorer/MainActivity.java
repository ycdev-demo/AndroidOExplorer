package me.ycdev.android.demo.oexplorer;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import me.ycdev.android.arch.utils.AppLogger;
import me.ycdev.android.demo.oexlporer.common.CommonConstants;
import me.ycdev.android.demo.oexplorer.service.BackgroundService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendBroadcasts();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void sendBroadcasts() {
        Intent intent = new Intent(CommonConstants.ACTION_APP1_TEST_SELF);
        intent.setPackage(getPackageName());
        sendBroadcast(intent);

        intent = new Intent(CommonConstants.ACTION_APP1_TEST_NORMAL);
        sendBroadcast(intent);

        intent = new Intent(CommonConstants.ACTION_APP1_TEST_PERM_NORMAL);
        sendBroadcast(intent, CommonConstants.PERM_NORMAL);

        intent = new Intent(CommonConstants.ACTION_APP1_TEST_PERM_SIGN);
        sendBroadcast(intent, CommonConstants.PERM_SIGN);

        intent = new Intent(CommonConstants.ACTION_APP1_TEST_PERM_SIGN_OR_SYSTEM);
        sendBroadcast(intent, CommonConstants.PERM_SIGN_OR_SYSTEM);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            testBackgroundService();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void testBackgroundService() {
        AppLogger.d(TAG, "test background service");
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return;
        }

        Intent intent = new Intent(this, BackgroundService.class);
        startService(intent);

        PendingIntent pi = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(this, Constants.NOTIFICATION_CHANNEL_ID_TEST)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("TestTicker")
                .setContentTitle("TestTitle")
                .setContentText("TestText")
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManager nm = getSystemService(NotificationManager.class);
        nm.notify(Constants.NOTIFICATION_ID_BKG_SERVICE, notification);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
