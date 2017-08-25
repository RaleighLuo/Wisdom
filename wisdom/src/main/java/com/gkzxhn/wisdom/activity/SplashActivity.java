package com.gkzxhn.wisdom.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.common.Constants;

/**
 * Created by Raleigh.Luo on 17/7/14.
 */

public class SplashActivity extends Activity {
    private   final long SPLASH_DELAY_MILLIS = 1000;//1秒后跳转
    private final int TO_LOGIN_PAGE=1;
    private final int TO_MAIN_PAGE=2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        SharedPreferences sp=getSharedPreferences(Constants.USER_TABLE, Activity.MODE_PRIVATE);
        if(sp.getString(Constants.USER_TOKEN,"").length()==0||sp.getString(Constants.USER_RESIDENTIALAREASID,"").length()==0){//未登录
            mHandler.sendEmptyMessageDelayed(TO_LOGIN_PAGE, SPLASH_DELAY_MILLIS);
        }else{//已登录
            mHandler.sendEmptyMessageDelayed(TO_MAIN_PAGE, SPLASH_DELAY_MILLIS);
        }
    }
    /**
     * Handler:跳转到不同界面
     */
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if(msg.what==TO_LOGIN_PAGE){//跳转到登录界面
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }else if(msg.what==TO_MAIN_PAGE){//已经登录，跳转到主页
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            super.handleMessage(msg);

        }
    };
}
