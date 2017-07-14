package com.gkzxhn.wisdom.activity;

import android.os.Bundle;
import android.view.View;

import com.gkzxhn.wisdom.R;

/**
 * Created by Raleigh.Luo on 17/7/14.
 */

public class SignActivity extends SuperActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_layout);
    }
    public void onClickListener(View view){
        switch (view.getId()){
            case R.id.common_head_layout_iv_left:
                finish();
                break;
            case R.id.sign_layout_iv_sign://签到
                break;
            case R.id.sign_layout_tv_sign:
                findViewById(R.id.sign_layout_iv_sign).performClick();
                break;
        }
    }

}
