package com.gkzxhn.wisdom.property.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.gkzxhn.wisdom.property.R;

/**
 * Created by Raleigh.Luo on 17/9/1.
 */

public class SplashActivity extends Activity {
    private   final long SPLASH_DELAY_MILLIS = 1000;//1秒后跳转

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        mHandler.sendEmptyMessageDelayed(1, SPLASH_DELAY_MILLIS);
    }
    /**
     * Handler:跳转到不同界面
     */
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    };
}