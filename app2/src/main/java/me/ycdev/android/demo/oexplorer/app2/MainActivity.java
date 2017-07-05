package me.ycdev.android.demo.oexplorer.app2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import me.ycdev.android.demo.oexlporer.common.CommonConstants;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.send_broadcast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendBroadcasts();
            }
        });
    }

    private void sendBroadcasts() {
        Intent intent = new Intent(CommonConstants.ACTION_APP2_TEST_TARGET);
        intent.setPackage("me.ycdev.android.demo.oexplorer");
        sendBroadcast(intent);

        intent = new Intent(CommonConstants.ACTION_APP2_TEST_NORMAL);
        sendBroadcast(intent);

        intent = new Intent(CommonConstants.ACTION_APP2_TEST_PERM_NORMAL);
        sendBroadcast(intent, CommonConstants.PERM_NORMAL);

        intent = new Intent(CommonConstants.ACTION_APP2_TEST_PERM_SIGN);
        sendBroadcast(intent, CommonConstants.PERM_SIGN);

        intent = new Intent(CommonConstants.ACTION_APP2_TEST_PERM_SIGN_OR_SYSTEM);
        sendBroadcast(intent, CommonConstants.PERM_SIGN_OR_SYSTEM);
    }
}
